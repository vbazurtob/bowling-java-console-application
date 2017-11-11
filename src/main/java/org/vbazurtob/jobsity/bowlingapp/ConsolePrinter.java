package org.vbazurtob.jobsity.bowlingapp;


import java.util.ArrayList;
import java.util.HashMap;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author voltaire
 * A helper class used to print into the system console the computed score results of a bowling game. 
 * The console output is formatted using the standard bowling scoring notation.
 *  
 */
@Component
public class ConsolePrinter implements ScorePrinter{

	public final static int MAX_CHAR_NAME = 10;
	
	@Autowired
	private PlayerScorer playerScorer;
	
	private GameScore gameScore;
	
	@Autowired
	public ConsolePrinter(GameScore gameScore) {
		this.gameScore = gameScore;
	}
	
	

	
	/**
	 * Return a string containing all players frames and their scores using 
	 * the standard bowling scoring format
	 * @return
	 */
	private String getPlayersScoreInfo() {

		
		StringBuilder sbResult =  new StringBuilder();
		HashMap<String, Player> mapPlayersScores = gameScore.getAllPlayersScore();
		
		for(String key : mapPlayersScores.keySet() ) {
			String linePinfalls="";
			String lineFrameScores="";
			
			Player player = mapPlayersScores.get(key);
			
			//Compute scores for every player before printing. This ensures the most up-data data will always
			//be printed
			playerScorer.computeFrameScores(player);
			
			sbResult.append(player.getName()+"\n");
			
    		linePinfalls = (  printFramesPinfalls(player.getListScoreFrames()) );
    		lineFrameScores = (  printFrameScores(player.getListScoreFrames()) );
    		sbResult.append( linePinfalls +"\n"+ lineFrameScores + "\n" );
		}
		
		return sbResult.toString();
	}
	
	
	/**
	 * Format the first word on every line to match a maximum length of characters
	 * specified by the constant MAX_CHAR_NAME. It helps to format names of players that
	 * can be too long.
	 * @param str
	 * @return
	 */
	public String cutStrMaxChar(String str) {
		
		if(str.length()> MAX_CHAR_NAME) {
			return str.substring(0, MAX_CHAR_NAME);
		}else if(str.length()<MAX_CHAR_NAME){
			String paddingWhitespaces= new String(new char[MAX_CHAR_NAME-str.length()]).replace("\0", " ");
			str+=paddingWhitespaces;
		}
		
		return str;
	}
	
	/**
	 * This method returns a string with the header of the game score chart
	 * @return
	 */
	private String getScoreHeader() {
		StringBuilder sb =  new StringBuilder();
		sb.append( cutStrMaxChar("Frames") +"\t");
		
		for(int i=0; i < 10; i++) {
			sb.append((i+1)+"\t\t");
			
			if(i==9) {
				sb.append("\t");
			}
			
			sb.append("|");
		}
		
		
		return sb.toString();
	}
	
	
	/**
	 * Iterates a list of frames and return a string with the pinfalls formatted with the bowling
	 * standard score symbols
	 * @param listScoreFrames
	 * @return
	 */
	public String printFramesPinfalls(ArrayList<ScoreFrame> listScoreFrames) {
		StringBuffer sb = new StringBuffer();
		sb.append(cutStrMaxChar("Pinfalls")+"\t");
		
		for(ScoreFrame frame: listScoreFrames) {
			
			sb.append( formatFrameAsString(frame) + "|");
		}
		
		
		return sb.toString();
	}
	
	/**
	 * Iterates a list of frames and return a string with the score for every
	 * frame
	 * @param listScoreFrames
	 * @return
	 */
	public String printFrameScores(ArrayList<ScoreFrame> listScoreFrames) {
		StringBuffer sb = new StringBuffer();
		sb.append(cutStrMaxChar("Score")  +"\t");
		
		for(ScoreFrame frame: listScoreFrames) {
			
			sb.append(frame.getScore() + "\t\t");
			if(listScoreFrames.indexOf(frame)==9) {
				sb.append("\t");
			}
			sb.append("|");
		}
		
		
		return sb.toString();
	}
	
	
	

	@Override
	public void printGameScore() {
		System.out.println(  getScoreHeader() );
		System.out.println(  getPlayersScoreInfo() );
	}
	
	public static String formatSingleNumericPinfallToStr(int pinfall) {
		String str;
		switch (pinfall) {
			case 10:
				str="X";
				break;
			default:
				str=pinfall+"";
				break;
		}
		return str;
	}
	
	public static String getSpareFormat(RegularScoreFrame f) {
		
		return f.getFirstShot() +"\t/\t";	
		
	}
	
	public static String getStrikeFormat() {
		
		return " \tX\t";	
		
	}
	
	
	/**
	 * Returns the formatted string representation of a frame according
	 * the bowling scoring symbols
	 * @param frame
	 * @return
	 */
	public String formatFrameAsString(ScoreFrame frame){
		
		String fs,ss,ts;
		
		
		if(frame instanceof RegularScoreFrame) {
			
			RegularScoreFrame regularFrame = (RegularScoreFrame) frame;
			
			if( regularFrame.isStrike() ) {
				return ConsolePrinter.getStrikeFormat();
			}
			
			if( regularFrame.isSpare()) {
				return ConsolePrinter.getSpareFormat(regularFrame);
			}
			
			if( regularFrame.isFirstShotIsFail()) {
				fs="F";
			}else {
				fs= regularFrame.getFirstShot()+"";
			}
			
			if( regularFrame.isSecondShotIsFail()) {
				ss="F";
			}else {
				ss= regularFrame.getSecondShot()+"";
			}
			
			return fs + "\t"+ 
				   ss + "\t";
			
		}else { // if(frame instanceof FinalScoreFrame){
			
			FinalScoreFrame finalFrame = (FinalScoreFrame) frame;
			
			if(finalFrame.isFirstShotIsFail()) {
				fs="F";
			}else {
				fs= ConsolePrinter.formatSingleNumericPinfallToStr(finalFrame.getFirstShot());
			}
			
			if(finalFrame.isSecondShotIsFail()) {
				ss="F";
			}else {
				ss=ConsolePrinter.formatSingleNumericPinfallToStr(finalFrame.getSecondShot());
			}
			
			if(finalFrame.isThirdShotIsFail()) {
				ts="F";
			}else {
				ts= ConsolePrinter.formatSingleNumericPinfallToStr(finalFrame.getThirdShot());
			}
			
			
			return fs +
					"\t" + ss +
					"\t" + ts + "\t";
			
		}
		
		
	}
	
	/**
	 * Returns a player's score frame list as human readable string
	 * @param player
	 * @param listScoreFrames
	 * @return
	 */
	public String getPinfallsAsString(Player player) {
		String tmpStr= player.getName()+"'s pinfalls: ";
		for(ScoreFrame f : player.getListScoreFrames()) {
			
			if(f instanceof RegularScoreFrame) {
				RegularScoreFrame rsf = (RegularScoreFrame) f;
				if(rsf.getFirstShot()>= 0) {
					tmpStr+= rsf.getFirstShot() ;
				}
				if(rsf.getSecondShot()>= 0) {
					tmpStr+= "," + rsf.getSecondShot() + ", ";
				}
			}else {
				FinalScoreFrame fsf = (FinalScoreFrame) f;
				if(fsf.getFirstShot()>= 0) {
					tmpStr+= fsf.getFirstShot() ;
				}
				if(fsf.getSecondShot()>= 0) {
					tmpStr+= "," + fsf.getSecondShot() + ", ";
				}
				if(fsf.getThirdShot()>= 0) {
					tmpStr+= fsf.getThirdShot() ;
				}
				
			}
			
		}
		return tmpStr;
	}
	
}
