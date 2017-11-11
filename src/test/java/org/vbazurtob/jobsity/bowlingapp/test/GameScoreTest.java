package org.vbazurtob.jobsity.bowlingapp.test;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.vbazurtob.jobsity.bowlingapp.GameScore;
import org.vbazurtob.jobsity.bowlingapp.Player;
import org.vbazurtob.jobsity.bowlingapp.config.AppConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={AppConfig.class})
public class GameScoreTest {

	@Autowired
	private ApplicationContext ctx;
	
	
	
	@Test
	public void testTwoPlayersCreationAndScoreStoring() {
	
		GameScore gs = (GameScore) ctx.getBean("gameScore");
		
		Player p1 = (Player) ctx.getBean("player");
		p1.setName("Player1");
		
		Player p2 = (Player) ctx.getBean("player");
		p2.setName("Player2");
		
		gs.getAllPlayersScore().put(p1.getName(), p1);
		gs.getAllPlayersScore().put(p2.getName(), p2);
		
		assertEquals("Player1 vs Player2", p1.getName()+" vs "+p2.getName());
	}

}
