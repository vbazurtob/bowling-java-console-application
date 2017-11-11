package org.vbazurtob.jobsity.bowlingapp.test;

import static org.junit.Assert.assertEquals;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.vbazurtob.jobsity.bowlingapp.ConsolePrinter;
import org.vbazurtob.jobsity.bowlingapp.FinalScoreFrame;
import org.vbazurtob.jobsity.bowlingapp.config.AppConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=AppConfig.class)
public class FinalScoreFrameTest {

	
	@Autowired
	private FinalScoreFrame finalFrame;
	
	@Autowired
	private ConsolePrinter cprinter;
	
	@Test
	public void testThreeStrikesStringFormat() {
		
		finalFrame.setFirstShot(10);
		finalFrame.setSecondShot(10);
		finalFrame.setThirdShot(10);
		
		assertEquals("X\tX\tX\t", cprinter.formatFrameAsString( finalFrame ) );
	}
	
	@Test
	public void testFoulAndZeroStringFormat() {
		
		finalFrame.setFirstShot(0);
		finalFrame.setFirstShotIsFail(true);
		finalFrame.setSecondShot(5);
		finalFrame.setThirdShot(0);
		
		assertEquals("F\t5\t0\t", cprinter.formatFrameAsString( finalFrame ));
	}

}
