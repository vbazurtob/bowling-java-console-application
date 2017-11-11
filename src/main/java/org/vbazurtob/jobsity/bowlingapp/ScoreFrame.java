package org.vbazurtob.jobsity.bowlingapp;

/**
 * @author voltaire
 *
 *	This interface defines the methods for every Frame in the bowling game.
 *	Bowling games are composed of 10 frames where every frame except the last 
 *  one allows 2 chances of bowling the ball. Last frame allows 3 shots.
 *  
 *  Therefore 2 concrete classes implements this interface:
 *  
 *  RegularScoreFrame for representing any frame different than the 10th frame
 *  FinalScoreFrame representing the last frame of the game
 *
 */
public interface ScoreFrame {

	/**
	 * @return int
	 * Returns the value of the computed cumulative score for this frame
	 */
	public int getScore();
	
	/**
	 * @return int
	 * Computes and return the total pinfall value for the current frame
	 */
	public int getTotalPinfallCount();
	public void setScore(int score);
	
	
}
