package org.vbazurtob.jobsity.bowlingapp.config;


import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.vbazurtob.jobsity.bowlingapp.App;
import org.vbazurtob.jobsity.bowlingapp.CompleteListOfScores;
import org.vbazurtob.jobsity.bowlingapp.Player;
import org.vbazurtob.jobsity.bowlingapp.ScoreFrame;

@Configuration
@ComponentScan(basePackageClasses={App.class})//Define base package through a class living in that base package
public class AppConfig {
	

	@Bean
	@Scope("prototype")
	public Player player() {
		Player p = new Player();
		p.setListScoreFrames(new ArrayList<ScoreFrame>());
		return p;
	}
	
	@Bean(name="reusableStrBuilder")
	public StringBuilder getTmpStringBuilder() {
		return new StringBuilder();
	}
	
	@Bean(name="reusablePlayersMapBean")//Convenient name for this kind of beans
	@CompleteListOfScores//Using custom qualifiers in order to resolve ambiguity if other similar return type beans exist
	public HashMap<String, Player> reusablePlayerMapBean(){
		return new HashMap<String, Player>();
	}
	
	
	

}
