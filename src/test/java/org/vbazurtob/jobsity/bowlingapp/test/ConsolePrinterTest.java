package org.vbazurtob.jobsity.bowlingapp.test;

import static org.junit.Assert.assertEquals;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.vbazurtob.jobsity.bowlingapp.ConsolePrinter;
import org.vbazurtob.jobsity.bowlingapp.IncompleteFrameDataException;
import org.vbazurtob.jobsity.bowlingapp.InvalidPinfallDataOnFrame;
import org.vbazurtob.jobsity.bowlingapp.Player;
import org.vbazurtob.jobsity.bowlingapp.PlayerScorer;
import org.vbazurtob.jobsity.bowlingapp.RegularScoreFrame;
import org.vbazurtob.jobsity.bowlingapp.config.AppConfig;

import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={AppConfig.class})
public class ConsolePrinterTest {

	@Autowired
	private Player player;
	
	@Autowired
	private ApplicationContext ctx;
	
	@Autowired
	private ConsolePrinter cprinter;
	
	@Autowired
	private PlayerScorer scorer;
	
	
	
	@Test
	public void testGetPlayerPinfallsAsString() {
		
		player.setName("Player1");
	
		RegularScoreFrame rsf = (RegularScoreFrame) ctx.getBean("regularScoreFrame");
		
		rsf.setFirstShot(5);
		rsf.setSecondShot(0);
		player.getListScoreFrames().add(rsf);
		
		rsf = (RegularScoreFrame) ctx.getBean("regularScoreFrame");
		
		rsf.setFirstShot(2);
		rsf.setSecondShot(8);
		
		player.getListScoreFrames().add(rsf);
		
		assertEquals("Player1's pinfalls: 5,0, 2,8,", cprinter.getPinfallsAsString(player).trim());
		
	}
	
	private void prepareTestData() throws IncompleteFrameDataException, InvalidPinfallDataOnFrame {
		player.setName("Player1");
		//Set values of pinfalls. We load 3 frames. Frame 1: Strike, Frame 2: Spare, Frame3: Foul and 6
		scorer.addFrameScoreString(player, 10, "10");
		scorer.addFrameScoreString(player, 4, "4");
		scorer.addFrameScoreString(player, 6, "6");
		scorer.addFrameScoreString(player, 0, "F");
		scorer.addFrameScoreString(player, 6, "6");
		scorer.computeFrameScores(player);		
	}
	
	private String[] getPlayerListScores() throws IncompleteFrameDataException, InvalidPinfallDataOnFrame {
		prepareTestData();
		String scores = cprinter.printFrameScores(player.getListScoreFrames());
		String[]  scoresValues = scores.split("\\|");
		return scoresValues;
	}

	@Test
	public void testScoreInFirstFrame() throws IncompleteFrameDataException, InvalidPinfallDataOnFrame {
		String[]  scoresValues = getPlayerListScores();
		assertEquals("20", scoresValues[0].replaceAll("Score", "").trim());
	}

	@Test
	public void testScoreInSecondFrame() throws IncompleteFrameDataException, InvalidPinfallDataOnFrame {
		String[]  scoresValues = getPlayerListScores();
		assertEquals("30", scoresValues[1].trim());
	}

	@Test
	public void testScoreInThirdFrame() throws IncompleteFrameDataException, InvalidPinfallDataOnFrame {
		prepareTestData();
		String[]  scoresValues = getPlayerListScores();
		assertEquals("36", scoresValues[2].trim());
	}
	
	
}
