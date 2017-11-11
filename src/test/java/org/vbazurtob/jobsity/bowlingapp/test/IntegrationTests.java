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
import org.vbazurtob.jobsity.bowlingapp.ConsolePrinter;
import org.vbazurtob.jobsity.bowlingapp.TxtFileParser;
import org.vbazurtob.jobsity.bowlingapp.GameScore;
import org.vbazurtob.jobsity.bowlingapp.IncompleteFrameDataException;
import org.vbazurtob.jobsity.bowlingapp.InvalidPinfallDataOnFrame;
import org.vbazurtob.jobsity.bowlingapp.Player;
import org.vbazurtob.jobsity.bowlingapp.PlayerScorer;
import org.vbazurtob.jobsity.bowlingapp.config.AppConfig;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=AppConfig.class)
public class IntegrationTests {

	@Autowired
	private ApplicationContext ctx;
	
	@Autowired
	private ConsolePrinter cprinter;
	
	@Autowired
	private PlayerScorer scorer;
	
	@Autowired
	private GameScore gameScores;
	
	
	private String perfectGameInputFile;
	private String worstCaseGameInputFile;
	private String jobsityInputFile;
	private String foulGameInputFile;
	private String badInputFormat;
	private String extraRows;
	
	private void readInputTestFile(String inputFile) throws IOException, NumberFormatException, IncompleteFrameDataException, InvalidPinfallDataOnFrame{
		ClassLoader cloader = ctx.getClassLoader();
		File file =  new File(cloader.getResource(inputFile).getFile());
		
    	FileSystemResource fr = new FileSystemResource(file);  	
    	TxtFileParser parser = (TxtFileParser) ctx.getBean("txtFileParser");
    	
    	//Parse the input file and load it into memory
    	parser.parse(fr.getInputStream());
	}
	
	
	
	@Before
	public void setUp() throws Exception {
		
		perfectGameInputFile = "perfect-game.txt";
		worstCaseGameInputFile = "worst-case.txt";
		jobsityInputFile = "bowling-file.txt";
		foulGameInputFile = "all-fouls.txt";
		badInputFormat = "bad-input-format.txt";
		extraRows = "extra-rows.txt";
	}
	
	@Test
	public void testBadInput() throws IOException, NumberFormatException, IncompleteFrameDataException, InvalidPinfallDataOnFrame{
		boolean isInputError = false;
		try {
			readInputTestFile(badInputFormat);
			Player player = (Player) gameScores.getAllPlayersScore().get("Sam");
			scorer.computeFrameScores(player);
		}catch(Exception e) {
			if(e instanceof IncompleteFrameDataException || e instanceof  NumberFormatException
					|| e instanceof  InvalidPinfallDataOnFrame) {
				isInputError = true;
			}
		}
		
		assertEquals(true, isInputError);
	}
	

	@Test
	public void testPerfectGame() throws NumberFormatException, IOException, IncompleteFrameDataException, InvalidPinfallDataOnFrame {
		readInputTestFile(perfectGameInputFile); 
		Player john = (Player) gameScores.getAllPlayersScore().get("John");
		scorer.computeFrameScores(john);
			
		int finalScore = john.getFinalScore();
		assertEquals(300, finalScore);//Test john score in a perfect game
	}

		
	@Test
	public void testFoulGame() throws NumberFormatException, IOException, IncompleteFrameDataException, InvalidPinfallDataOnFrame {
		
		readInputTestFile(foulGameInputFile);
		Player carl = (Player) gameScores.getAllPlayersScore().get("Carl");
		scorer.computeFrameScores(carl);
					
		int finalScore = carl.getFinalScore();
		String  lastFrameStr = cprinter.formatFrameAsString(carl.getListScoreFrames().get(9));
		
		assertEquals("last frame: F\tF\tF last score: 0", "last frame: "+lastFrameStr.trim()+" last score: "+ finalScore);
		
	}
	
	@Test
	public void testWorstCaseGame() throws NumberFormatException, IOException, IncompleteFrameDataException, InvalidPinfallDataOnFrame {
	
		readInputTestFile(worstCaseGameInputFile);
		Player carl = (Player) gameScores.getAllPlayersScore().get("Carl");
		scorer.computeFrameScores(carl);
					
		int finalScore = carl.getFinalScore();
		String  lastFrameStr = cprinter.formatFrameAsString(carl.getListScoreFrames().get(9));
		assertEquals("last frame: 0\t0\t0 last score: 0", "last frame: "+lastFrameStr.trim()+" last score: "+ finalScore);		
	}
	
	@Test
	public void testExtraRows() throws NumberFormatException, IOException, IncompleteFrameDataException, InvalidPinfallDataOnFrame {
	
		readInputTestFile(extraRows);
		Player lisa = (Player) gameScores.getAllPlayersScore().get("Lisa");
		scorer.computeFrameScores(lisa);
					
		int finalScore = lisa.getFinalScore();
		String  lastFrameStr = cprinter.formatFrameAsString(lisa.getListScoreFrames().get(9));
		assertEquals("last frame: X\t4\t9 last score: 130", "last frame: "+lastFrameStr.trim()+" last score: "+ finalScore);		
	}

	@Test
	public void testJeffScoreJobsityDataset() throws NumberFormatException, IOException, IncompleteFrameDataException, InvalidPinfallDataOnFrame {
		readInputTestFile(jobsityInputFile);
		Player jeff = (Player) gameScores.getAllPlayersScore().get("Jeff");
		scorer.computeFrameScores(jeff);
					
		int finalScore = jeff.getFinalScore();
		String  lastFrameStr = cprinter.formatFrameAsString(jeff.getListScoreFrames().get(9));
		assertEquals("last frame: X\t8\t1 last score: 167", "last frame: "+lastFrameStr.trim()+" last score: "+ finalScore);		
		
		
	}

}
