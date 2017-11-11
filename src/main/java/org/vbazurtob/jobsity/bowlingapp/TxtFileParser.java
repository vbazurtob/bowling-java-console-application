package org.vbazurtob.jobsity.bowlingapp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;


/**
 * @author voltaire
 * Class that reads an input file containing the pinfall scores for each player in 
 * every line and then loads all the data in memory
 */
@Component
public class TxtFileParser implements FileParser{

	@Autowired
	private ApplicationContext ctx;

	@Autowired
	private PlayerScorer playerScorer;

	@Autowired
	private ConsolePrinter cprinter;
	
	
    public void parse(InputStream inputStream) throws IOException, NumberFormatException, IncompleteFrameDataException, InvalidPinfallDataOnFrame {
    	
    	StringBuilder sbuilder = (StringBuilder) ctx.getBean("reusableStrBuilder");
    	@SuppressWarnings("unchecked")
		HashMap<String, Player> players = (HashMap<String, Player>) ctx.getBean("reusablePlayersMapBean"); 
    	//Clear the map because bean is singleton (We only need one list of players per game)
    	players.clear();
    	
    	//Define the format
		Pattern patternObj = Pattern.compile("(^[a-zA-Z_]\\w+)(\\s+)(F|\\d{1,2})");
		
		
    	
    	
    	//Read every line
  		try (BufferedReader breader = new BufferedReader(new InputStreamReader(inputStream))) {
  		        String line;
  		        while ((line = breader.readLine()) != null ) {
  		            line = line.trim();
  		        	if( !line.equals("") ) {//skip blank lines
  		        		sbuilder.append(line).append("\n");
  		        		
  		        		Matcher matcher = patternObj.matcher(line);
  		      		
  		        		if(!matcher.matches()) {
  		        			throw new IOException("Invalid format in line:\n"
  		        					+ line + "\n\n"
  		        					+ "Please check that for every line in the file, there must be a player name starting "
  		        					+ "with an alphabet character or underscore, a whitespace separator and a pinfall score represented by a "
  		        					+ "number or the letter F.\n"
  		        					+ "Syntax: PlayerName<space>Number\n\n"
  		        					+ "Sample: JohnDoe	2\n");
  		        		}
  	  		        	
  		        		
  	  		            String pinfall, playerName;
  	  		            
  	  		            playerName = matcher.group(1).trim();
  	  		            pinfall = matcher.group(3).trim();
  	  		            
  	  		            //Parse every number in the file to a numeric representation
  	  		            int pinfallScore;
  	  		            if(pinfall.equals("F")) {
  	  		            	pinfallScore = 0;
  	  		            }else {
  	  		            	
  	  		            		pinfallScore = Integer.parseInt(pinfall);
  	  		            	
	  	  		            	if(pinfallScore < 0 || pinfallScore > 10) {
	  	  		            		throw new IOException("Invalid format in line:\n"
	  	  		        					+ line + "\n\n"
	  	  		        					+ "The pinfall score after the whitespace(s) separator must be"
	  	  		        					+ " a number between 0 and 10. The letter F is also valid representing a"
	  	  		        					+ " foul shot.\n"
	  	  		        					+ "Syntax: PlayerName<space>Number\n\n"
	  	  		        					+ "Sample: "
	  	  		        					+ "JohnDoe	2\n"
	  	  		        					+ "Sam F\n");
	  	  		            	}
  	  		            	
  	  		            	
  	  		            }
  	  		            
  	  		           
  	  		            //If a player is not present in the list add it, otherwise retrieve a previous reference to update values
  	  		            if( players.containsKey(playerName) ) {
  	  		            	playerScorer.addFrameScoreString(players.get(playerName), pinfallScore, pinfall);
  	  		            }else {
  	  		            	
  	  		            	Player  p = (Player) ctx.getBean("player");
  	  			            p.setName(playerName);
  	  			            playerScorer.addFrameScoreString(p, pinfallScore, pinfall);
  		            		players.put(playerName, p);	
  	  		            }
  		        	
  		        	}	            
		            
  		        }
  		        
  		        
  		}
  		
  		
  		
  		//check integrity of players score
  		for(String playerName : players.keySet()) {
  			Player player = players.get(playerName);
  			if(playerScorer.checkPlayerScoreFramesIntegrity(player)==false){
  				throw new IncompleteFrameDataException("Player "+ playerName +" doesn't have enough pinfalls to "
  						+ "complete a 10 frames bowling game.\nSome data might be incomplete in the input file or this player.\n"
  						+ "Pinfalls: " + cprinter.getPinfallsAsString(player) + "\t\n");
  			}
  		}
  		
  	}

}