package org.vbazurtob.jobsity.bowlingapp.test;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.FileSystemResource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.vbazurtob.jobsity.bowlingapp.IncompleteFrameDataException;
import org.vbazurtob.jobsity.bowlingapp.InvalidPinfallDataOnFrame;
import org.vbazurtob.jobsity.bowlingapp.TxtFileParser;
import org.vbazurtob.jobsity.bowlingapp.config.AppConfig;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes= {AppConfig.class})
public class TxtFileParserTest {

	private String inputFileStr;

	@Autowired
	private ApplicationContext ctx;
	
	@Before
	public void setUp() {
		inputFileStr=null;
	}

	private String getResourcesDir(String inputFileStr) {
		ClassLoader cloader = ctx.getClassLoader();
		
		return cloader.getResource(inputFileStr).getFile();
	}
	
	@Test
	public void testNoInputFile() throws IOException, IncompleteFrameDataException, InvalidPinfallDataOnFrame {
    	
		boolean isError = false;
    	try {
    		File file =  new File(getResourcesDir(inputFileStr));
    		FileSystemResource fr = new FileSystemResource(file);
    		TxtFileParser parser = (TxtFileParser) ctx.getBean("txtFileParser");
    		parser.parse(fr.getInputStream());  
    		
    	}catch(Exception e) {
    		isError = true;
    	}
    	
    	assertEquals(true, isError);
				
	}
	
	
	
	

}
