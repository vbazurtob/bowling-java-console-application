package org.vbazurtob.jobsity.bowlingapp;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * @author voltaire
 * A class representing a regular frame (from 1-9) composed by two shots (two chances of throwing
 * the ball).
 */
@Component
@Scope("prototype")
public class RegularScoreFrame implements ScoreFrame {
	
	private int firstShot =-1;
	private int secondShot =-1;
	
	private boolean firstShotIsFail = false;
	private boolean secondShotIsFail = false;
	
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
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
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
	public String getStatusFrame() {
		return statusFrame;
	}
	public void setStatusFrame(String statusFrame) {
		this.statusFrame = statusFrame;
	}
	
	
	public int getTotalPinfallCount() {
		return getFirstShot() + getSecondShot();
	}
	
	public boolean isStrike() {
		return getSecondShot() == 10;
	}
	
	public boolean isSpare() {
		return (getFirstShot() + getSecondShot()) == 10;
	}
}
