package org.vbazurtob.jobsity.bowlingapp;


import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.io.FileSystemResource;
import org.vbazurtob.jobsity.bowlingapp.config.AppConfig;

/**
 * Main application class to compile and launch from console using the parameter
 * <file> which specifies the path to the inputfile to be read.
 *
 */
public class App 
{
	
	private static ApplicationContext ctx;
	
	public static void print(String str) {
	    	System.out.println(str);
	}
	
	public static void readFileInputAndGetData(String inputFile) throws Exception{

		//Read the input file
    	FileSystemResource fr = new FileSystemResource(inputFile);  	
    	FileParser parser = (FileParser) ctx.getBean("txtFileParser");
    	
    	//Parse the input file and load it into memory
    	parser.parse(fr.getInputStream());
   }
	
	
	
    public static void main( String[] args )
    {
    	//Check the input file parameter
    	if(args.length <=0) {
    		print("You must to specify the <file> parameter to read. Example:  bowling.jar <file>\n");
    		return;
    	}
    	
    	String inputFile = args[0].trim();
    	
    	try {
    		ctx = new AnnotationConfigApplicationContext(AppConfig.class);
        	
    		readFileInputAndGetData(inputFile);
    		
    		//Print the scores
    		ConsolePrinter cp = (ConsolePrinter) ctx.getBean("consolePrinter");
    		cp.printGameScore();
    	}catch(Exception e) {
    		print("E " + e.getMessage());
    		e.printStackTrace();;
    	}
    	
    }
}
