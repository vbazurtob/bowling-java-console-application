package org.vbazurtob.jobsity.bowlingapp;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author voltaire
 *
 * An interface to define the methods required to implement
 * file parsers for reading input data related to a bowling game.
 * By using this interface, further classes capable of reading
 * different kind of file formats can be implemented, allowing 
 * to extend the application
 *
 */
public interface FileParser {

	public void parse(InputStream inputStream) throws IOException, NumberFormatException, IncompleteFrameDataException, InvalidPinfallDataOnFrame ;
	
}
