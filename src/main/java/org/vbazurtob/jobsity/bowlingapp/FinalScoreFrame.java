package org.vbazurtob.jobsity.bowlingapp;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * @author voltaire
 * A class representing the 10th frame in a bowling game. It allows 
 * three shots (chances of bowling)
 */
@Component
@Scope("prototype")
public class FinalScoreFrame implements ScoreFrame{

	private int firstShot =-1;
	private int secondShot =-1;
	private int thirdShot =-1;
	
	private boolean firstShotIsFail = false;
	private boolean secondShotIsFail = false;
	private boolean thirdShotIsFail = false;
	
	
	private String statusFrame;
	
	private int score;
	
	public int getFirstShot() {
		return firstShot;
	}
	public void setFirstShot(int firstShot) {
		this.firstShot = firstShot;
	}
	public int getSecondShot() {
		return secondShot;
	}
	public void setSecondShot(int secondShot) {
		this.secondShot = secondShot;
	}
	public int getThirdShot() {
		return thirdShot;
	}
	public void setThirdShot(int thirdShot) {
		this.thirdShot = thirdShot;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	
	
	public String getStatusFrame() {
		return statusFrame;
	}
	public void setStatusFrame(String statusFrame) {
		this.statusFrame = statusFrame;
	}
	
	public int getTotalPinfallCount() {
		return getFirstShot() + getSecondShot() + getThirdShot();
	}

	
	public boolean isFirstShotIsFail() {
		return firstShotIsFail;
	}
	public void setFirstShotIsFail(boolean firstShotIsFail) {
		this.firstShotIsFail = firstShotIsFail;
	}
	public boolean isSecondShotIsFail() {
		return secondShotIsFail;
	}
	public void setSecondShotIsFail(boolean secondShotIsFail) {
		this.secondShotIsFail = secondShotIsFail;
	}
	public boolean isThirdShotIsFail() {
		return thirdShotIsFail;
	}
	public void setThirdShotIsFail(boolean thirdShotIsFail) {
		this.thirdShotIsFail = thirdShotIsFail;
	}
	
}
