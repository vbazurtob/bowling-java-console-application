/**
 * 
 */
package org.vbazurtob.jobsity.bowlingapp;

/**
 * @author voltaire
 * This exception should be thrown when the numeric information
 * related to the reported pinfalls to the first and second shot
 * in a regular frame doesn't make sense. For example:
 * a regular frame with a first shot reported as 3 and a second 
 * shot reported as 8 is incorrect, because the sum of the number of pinfalls
 * in the first and second shot should not exceed the total amount of pins per frame,
 * which is 10
 */
@SuppressWarnings("serial")
public class InvalidPinfallDataOnFrame extends Exception {

	public InvalidPinfallDataOnFrame() {
		super();
	}

	public InvalidPinfallDataOnFrame(String msg) {
		super(msg);
	}
	

}
