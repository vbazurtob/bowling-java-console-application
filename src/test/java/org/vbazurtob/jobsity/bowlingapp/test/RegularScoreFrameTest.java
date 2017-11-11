package org.vbazurtob.jobsity.bowlingapp.test;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.vbazurtob.jobsity.bowlingapp.ConsolePrinter;
import org.vbazurtob.jobsity.bowlingapp.RegularScoreFrame;
import org.vbazurtob.jobsity.bowlingapp.config.AppConfig;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=AppConfig.class)
public class RegularScoreFrameTest {

	@Autowired
	private RegularScoreFrame regularFrame;
	
	@Autowired
	private ConsolePrinter cprinter;
	
	@Test
	public void testPrintZeroPinfalls() {
		regularFrame.setFirstShot(0);
		regularFrame.setSecondShot(0);
		
		assertEquals("0\t0\t", cprinter.formatFrameAsString( regularFrame ));
	}

	@Test
	public void testPrintStrike() {
		regularFrame.setFirstShot(0);
		regularFrame.setSecondShot(10);
		
		assertEquals(" \tX\t", cprinter.formatFrameAsString( regularFrame));
	}

	@Test
	public void testPrintSpare() {
		regularFrame.setFirstShot(8);
		regularFrame.setSecondShot(2);
		
		assertEquals("8\t/\t", cprinter.formatFrameAsString( regularFrame ) );
	}
	
	@Test
	public void testPrintFail() {
		regularFrame.setFirstShot(0);
		//In order to identify if a 0 obtained in a chance is a Foul and not a regular zero, a flag must be set to true
		//regularFrame.setFirstShotIsFail(true);
		regularFrame.setSecondShot(2);
		
		assertNotEquals("F\t2\t", cprinter.formatFrameAsString( regularFrame ) );
	}
	
	
}
