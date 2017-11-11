package org.vbazurtob.jobsity.bowlingapp.test;


import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.vbazurtob.jobsity.bowlingapp.IncompleteFrameDataException;
import org.vbazurtob.jobsity.bowlingapp.InvalidPinfallDataOnFrame;
import org.vbazurtob.jobsity.bowlingapp.Player;
import org.vbazurtob.jobsity.bowlingapp.PlayerScorer;
import org.vbazurtob.jobsity.bowlingapp.ScoreFrame;
import org.vbazurtob.jobsity.bowlingapp.config.AppConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=  AppConfig.class)
public class PlayerScorerTest {

	@Autowired
	private PlayerScorer scorer;
	
	@Autowired
	private Player player;
	
	
	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testSpareScoringStrategyForFrame() throws IncompleteFrameDataException, InvalidPinfallDataOnFrame {

		player.setName("Player1");
		//Init frame score list
		player.setListScoreFrames(new ArrayList<ScoreFrame>());

		
		//Fill values for frame 1
		scorer.addFrameScoreString(player, 5, "5");
		scorer.addFrameScoreString(player, 5, "5");
		//Fill values for frame 2
		scorer.addFrameScoreString(player, 1, "1");
		scorer.addFrameScoreString(player, 0, "F");
		
		assertEquals(11, scorer.getCurrentFramePartialPinfallScoreAccordingRules(player, 0));
	}
	
	@Test
	public void testStrikeScoringStrategyForFrame() throws IncompleteFrameDataException, InvalidPinfallDataOnFrame{
		
		player.setName("Player1");
		//Init frame score list
		player.setListScoreFrames(new ArrayList<ScoreFrame>());

		//Fill values for frame 1
		scorer.addFrameScoreString(player, 10, "0");
		
		//Fill values for frame 2
		scorer.addFrameScoreString(player, 4, "4");
		scorer.addFrameScoreString(player, 3, "3");
		
		assertEquals(17, scorer.getCurrentFramePartialPinfallScoreAccordingRules(player, 0));
	}
	
	@Test
	public void testDoubleStrikeScoringInARow() throws IncompleteFrameDataException, InvalidPinfallDataOnFrame{
		
		player.setName("Player1");
		//Init frame score list
		player.setListScoreFrames(new ArrayList<ScoreFrame>());

		//Fill values for frame 1
		scorer.addFrameScoreString(player, 10, "0");
		//Frame 2
		scorer.addFrameScoreString(player, 10, "0");
		//Fill values for frame 3
		scorer.addFrameScoreString(player, 4, "4");
		scorer.addFrameScoreString(player, 3, "3");
		
		assertEquals(24, scorer.getCurrentFramePartialPinfallScoreAccordingRules(player, 0));
	}
	
	@Test
	public void testTripleStrikeScoringInARow() throws IncompleteFrameDataException, InvalidPinfallDataOnFrame{
		
		player.setName("Player1");
		//Init frame score list
		player.setListScoreFrames(new ArrayList<ScoreFrame>());

		
		//Fill values for frame 1
		scorer.addFrameScoreString(player, 10, "0");
		//Frame 2
		scorer.addFrameScoreString(player, 10, "0");
		//Frame 3
		scorer.addFrameScoreString(player, 10, "0");
		//Fill values for frame 4
		scorer.addFrameScoreString(player, 4, "4");
		scorer.addFrameScoreString(player, 3, "3");
		
		assertEquals(30, scorer.getCurrentFramePartialPinfallScoreAccordingRules(player, 0));
	}

}
