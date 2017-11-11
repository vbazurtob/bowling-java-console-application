package org.vbazurtob.jobsity.bowlingapp;

import java.util.ArrayList;
import org.springframework.stereotype.Component;



/**
 * @author voltaire
 * 
 * This class represents a player and his/her respective  list of frame objects holding the 
 * score during a bowling game.
 *
 */
@Component
public class Player {

	
	private String name;
	private int finalScore;
	private ArrayList<ScoreFrame> listScoreFrames;
	
	public String getName() {
		return name;
	}
	
	public ArrayList<ScoreFrame> getListScoreFrames() {
		return listScoreFrames;
	}
	public void setListScoreFrames(ArrayList<ScoreFrame> listScoreFrames) {
		this.listScoreFrames = listScoreFrames;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public int getFinalScore() {

		if(listScoreFrames.size()==10) {
			finalScore = listScoreFrames.get(listScoreFrames.size()-1).getScore();
		}
		
		
		
		return finalScore;
	}
	
	public void setFinalScore(int finalScore) {
		this.finalScore = finalScore;
	}
	
	
}
