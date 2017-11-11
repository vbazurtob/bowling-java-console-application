package org.vbazurtob.jobsity.bowlingapp;

import java.util.ArrayList;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * @author voltaire
 *
 * Class responsible of computing the score for a given player based 
 * on his/her pinfalls during a game
 *
 */
@Component
public class PlayerScorer {

	@Autowired
	private ApplicationContext ctx;
	
	@Autowired
	private ConsolePrinter cprinter;
	
	
	/**
	 * @param player
	 * @param pinfall
	 * @param originalStr
	 * 
	 * This method adds a pinfall number to the array list holding the list of frames with the scores for every chance that
	 * a player had during the game
	 * 
	 * 
	 */
	public void addFrameScoreString(Player player, int pinfall,  String originalStr) throws IncompleteFrameDataException, InvalidPinfallDataOnFrame {
		
		ArrayList<ScoreFrame> listScoreFrames = player.getListScoreFrames();
		
		//If the list holding the frames contains less than 10 elements, assume 
		//we are going to instantiate RegularScoreFrame objects
		if ( listScoreFrames.size() <  10) {//Regular
			
			RegularScoreFrame rf = (RegularScoreFrame) ctx.getBean("regularScoreFrame");
			//Was the pinfall score a Strike??
			if(pinfall==10) {
				
				//Is the next element to be created the final frame??
				if(listScoreFrames.size()<9) {
					
					
					if(listScoreFrames.size()>0) {
						
						
						
						//Check if last frame is still incomplete because a fail or pending shot
						RegularScoreFrame lastFrameRg = (RegularScoreFrame) listScoreFrames.get(listScoreFrames.size()-1);
						
						
						if(lastFrameRg.getSecondShot()<0) {//incomplete
							
							
							//Fix the part validate case (F, F) (10) two fouls and 1 strike
							
							if(lastFrameRg.isFirstShotIsFail()) {//Strike was landed in the second shot after a 
								//foul in the first shot during the last frame. I handle this gracefully by
								//treating the last frame as an strike.
								lastFrameRg.setFirstShot(0);
								lastFrameRg.setFirstShotIsFail(false);
								lastFrameRg.setSecondShot(pinfall);							
								lastFrameRg.setStatusFrame("S");
								return;
							}else {
							
								// For e.g.: we have a row in the file reported as 2 representing the first shot on a regular frame.
								//Immedieately after that instead of having a number for the second shot, the file contains a 10 (Strike).
								//I decided to throw an exception and warn about the possible missing data for the frame before
								//the strike reported. Of course, I could have handle it this gracefully and in a silence way, by
								//setting the second shot as 0 and then creating a new frame as an strike frame.
								
								if(lastFrameRg.getSecondShot()<0) {//Second shot in frame is not set or incomplete
									//We cannot create a new strike frame without completing the information on previous frame.
									//Maybe data is missing
									String tmpStr = cprinter.getPinfallsAsString(player);
									
									throw new IncompleteFrameDataException("A strike cannot be present immediately after"
											+ " a non-zero pinfall in the first chance of a regular frame.\nMaybe you are missing"
											+ " the pinfall score for the second chance in a regular frame\n"
										    + tmpStr
											+ "\n");
									
								}else {//create new strike frame
									rf.setFirstShot(0);
									rf.setSecondShot(pinfall);
									rf.setStatusFrame("S");
									listScoreFrames.add(rf);
								}
								
							} 
							
						}else {//complete
							rf.setFirstShot(0);
							rf.setSecondShot(pinfall);
							rf.setStatusFrame("S");
							listScoreFrames.add(rf);
						}
						
						
					}else {//first frame
						
						//create the strike frame and add it
						rf.setFirstShot(0);
						rf.setSecondShot(pinfall);
						rf.setStatusFrame("S");
						listScoreFrames.add(rf);
					}
					
				}else {
					FinalScoreFrame ff = (FinalScoreFrame) ctx.getBean("finalScoreFrame");
					ff.setFirstShot(pinfall);
					listScoreFrames.add(ff);
				}

				return;
			}else {
			
				//Initialize the first frame if list of frames is empty
				if ( listScoreFrames.size() == 0 ) {
					rf.setFirstShot(pinfall);
					
					if(originalStr.equals("F")) {
						rf.setFirstShotIsFail(true);
					}
					
					listScoreFrames.add(rf);
					return;
				}else {
					
					//Get last frame in the frames list
					RegularScoreFrame lastRf = (RegularScoreFrame) listScoreFrames.get( listScoreFrames.size() - 1); 
															

					//Was the second shot pinfall score already set in the last frame in the list?
					if(lastRf.getSecondShot() < 0) {
						
						if((lastRf.getFirstShot()+pinfall)> 10) {
							throw new InvalidPinfallDataOnFrame("Invalid pinfall data on frame. The total sum of "
									+ " both shots in the same frame must not be greater than 10.\nPlease check the input file "
									+ " and correct it.\n"
									+  cprinter.getPinfallsAsString(player) + "," + pinfall + " \n\n");
						}
					
						
						
						lastRf.setSecondShot(pinfall);
						
						if(lastRf.getFirstShot() + lastRf.getSecondShot() == 10 ) {//check if last frame was an spare
							lastRf.setStatusFrame("R");
						}else {
							lastRf.setStatusFrame("N");
						}
						
						//Check if it was a zero or a foul
						if(originalStr.equals("F")) {
							//rf.setSecondShotIsFail(true);
							lastRf.setSecondShotIsFail(true);
						}
						
						
						return;
					
					}else {
						
						//Check if the next frame to be created is the 10th frame or not
						if(listScoreFrames.size() < 9) {
							
							//Work with a new regular frame
							rf.setFirstShot(pinfall);
							if(originalStr.equals("F")) {
								rf.setFirstShotIsFail(true);
							}
							
							listScoreFrames.add(rf);
							
						}else {
							//Create an instance of a final frame object
							FinalScoreFrame ff = (FinalScoreFrame) ctx.getBean("finalScoreFrame");
							ff.setFirstShot(pinfall);
							if(originalStr.equals("F")) {
								ff.setFirstShotIsFail(true);
							}
							
							listScoreFrames.add(ff);
							
						}
					
						return;
					}
					
				}
				
			}
			
			
		}else {//Final frame 
			
			
			FinalScoreFrame lastRf = (FinalScoreFrame) listScoreFrames.get( listScoreFrames.size() - 1); 
			
			//Set the values for the second and third  shots in this frame.
			if(lastRf.getSecondShot() < 0) {
								
				if(originalStr.equals("F")) {
					lastRf.setSecondShotIsFail(true);
				}
				
				lastRf.setSecondShot(pinfall);
				return;
			}
							
							
			if(lastRf.getThirdShot() < 0) {
								
				if(originalStr.equals("F")) {
					lastRf.setThirdShotIsFail(true);
				}
								
				lastRf.setThirdShot(pinfall);
				return;
			}
			
		}
		
	}



	

	/**
	 * @param player
	 * 
	 * Compute the score for every frame in the game of a given player according 
	 * to the pinfalls values in memory obtained from the input file
	 */
	public void computeFrameScores(Player player) {
		
		ArrayList<ScoreFrame> listScoreFrames = player.getListScoreFrames();
	
		for(ScoreFrame sf: listScoreFrames) {
			
			int currentIndex = listScoreFrames.indexOf(sf);
			
			ScoreFrame currentFrame = listScoreFrames.get(currentIndex);
			
			int tmpAcumFrameScore = 0;
			
			//Compute first frame
			if(currentIndex == 0) {
				tmpAcumFrameScore = getCurrentFramePartialPinfallScoreAccordingRules(player, currentIndex);
				
			}else {
				//Any other frame acum the score of the previous frame
				ScoreFrame previousFrame = listScoreFrames.get(currentIndex-1);
				tmpAcumFrameScore= previousFrame.getScore() + getCurrentFramePartialPinfallScoreAccordingRules(player, currentIndex);
				
			}
			currentFrame.setScore(tmpAcumFrameScore);
			
			
		}
		
	}
	
	/**
	 * @param player
	 * @return
	 * 
	 * This method checks the integrity of every frame in a player game score.
	 * The method checks the length of frames and if every shot chance has been
	 * set in memory.
	 * 
	 */
	public boolean checkPlayerScoreFramesIntegrity(Player player) {
		
		if (player.getListScoreFrames().size() == 10) {
			
			for(ScoreFrame sf : player.getListScoreFrames()) {
				
				if(sf instanceof RegularScoreFrame) {
					RegularScoreFrame rsf = (RegularScoreFrame) sf;
					if(rsf.getFirstShot()==-1 || rsf.getSecondShot()==-1) {
						
						return false;
					}
					
				}else {//Final frame
					FinalScoreFrame fsf = (FinalScoreFrame) sf;
					if(fsf.getFirstShot()==-1 || fsf.getSecondShot()==-1 || fsf.getThirdShot()==-1) {
						return false;
					}
				}
				
			}
			
			return true;
		}else {
			return false;
		}
		
	}
	
	
	

/**
 * @param player
 * @param currentIndex
 * @return
 * 
 * Compute the value for the current frame according to the bowling games rules. The returned
 * value can be used later to be added with the previous cumulative frame score.
 */
public int getCurrentFramePartialPinfallScoreAccordingRules(Player player, int currentIndex) {
	
	ArrayList<ScoreFrame> listScoreFrames = player.getListScoreFrames();
	
	   int returnValue=0;
	   ScoreFrame sf = listScoreFrames.get(currentIndex);
	   
	   //Is the current frame the 10th frame or a regular one?
	   if( sf instanceof RegularScoreFrame ) {
		   RegularScoreFrame currentFrame = (RegularScoreFrame) sf;
		   
		   //Did a strike occured in the current frame?
		   if(currentFrame.isStrike()) {
			   
			   //Find if next frame was also a strike			   
			   ScoreFrame firstNext = listScoreFrames.get(currentIndex + 1 );
			   
			   //Is the next frame the 10th frame or not??
			   if(firstNext instanceof RegularScoreFrame) {
				   
				   RegularScoreFrame  firstNextRg =  (RegularScoreFrame) firstNext;
				   
				   //We need to check again if the next frame is a strike
				   if(firstNextRg.isStrike()) {
					   //find if next is Strike
					   ScoreFrame secondNext = listScoreFrames.get(currentIndex + 2 );
					   
					   //Is the second next a regular or 10th frame?
					   if(secondNext instanceof RegularScoreFrame) {
						   
						   RegularScoreFrame  secondNextRg =  (RegularScoreFrame) secondNext;
						
						   if( secondNextRg.isStrike() ) {
							   
							    returnValue = 30; //is the total value after getting three consecutive strikes
							   
						   }else {
							   returnValue = 20 + secondNextRg.getFirstShot();// two strikes in a row
						   }
						   
					   }else {//Final frame
						   
						   FinalScoreFrame secondNextFn = (FinalScoreFrame) secondNext;
						   
						   returnValue = secondNextFn.getFirstShot() + 20;
						   
					   }
					   
				   }else {
				    
					   returnValue = 10 + firstNextRg.getTotalPinfallCount(); //one strike
				   }
				   
				   
				   
			   }else {//Final
				   FinalScoreFrame firstNextFn = (FinalScoreFrame) firstNext;
				   
				   returnValue = 10 + firstNextFn.getFirstShot() + firstNextFn.getSecondShot(); // One strike  in the final frame
				   
			   }
			   
		   }else {//Check if in this frame occured a Spare
			   
			   if(currentFrame.isSpare()) {
				   
				   ScoreFrame firstNext = listScoreFrames.get(currentIndex+1);
				   
				   if(firstNext instanceof RegularScoreFrame) {
					
					   RegularScoreFrame firstNextRg = (RegularScoreFrame) firstNext;
					   
					   returnValue = 10 + firstNextRg.getFirstShot();
					   
				   }else {//
					   FinalScoreFrame firstNexFn = (FinalScoreFrame) firstNext;
					   returnValue = 10 + firstNexFn.getFirstShot();	   
					   
				   }
				   
				   
			   }else {
				   returnValue = currentFrame.getTotalPinfallCount();
			   }
			   
		   }
		   
	   }else {// A normal frame
		   FinalScoreFrame currentFrame = (FinalScoreFrame) sf;
		   
		   returnValue = currentFrame.getTotalPinfallCount();
		   
	   }
	
	   return returnValue;
   }
	
}
