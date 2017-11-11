package org.vbazurtob.jobsity.bowlingapp.test;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.vbazurtob.jobsity.bowlingapp.Player;
import org.vbazurtob.jobsity.bowlingapp.config.AppConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=AppConfig.class)
public class PlayerTest {

	@Autowired
	private Player player;
	
	
	@Test
	public void testPlayerDataCreation() {

		assertNotNull(player);
		
	}
	
}
