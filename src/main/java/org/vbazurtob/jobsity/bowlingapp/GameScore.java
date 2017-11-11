package org.vbazurtob.jobsity.bowlingapp;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author voltaire
 * Stores in memory a list with all players and their respective score in a bowling game
 *
 */
@Component
public class GameScore {

	private HashMap<String, Player> AllPlayersScore;

	public HashMap<String, Player> getAllPlayersScore() {
		return AllPlayersScore;
	}

	public void setAllPlayersScore(HashMap<String, Player> allPlayersScore) {
		AllPlayersScore = allPlayersScore;
	}
	
	

	@Autowired
	@CompleteListOfScores
	public GameScore( HashMap<String, Player> allPlayersScore) {
		super();
		AllPlayersScore = allPlayersScore;
	}  
	
	
	
}
