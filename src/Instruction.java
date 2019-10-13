package edu.temple.capstone.BinBotServer;

/******************************************************************************
 * Filename:		Instruction.java
 * Creation Date:	Wed 09 Oct 2019 05:06:28 PM EDT
 * Last Modified:	Wed 09 Oct 2019 05:11:57 PM EDT
 * Purpose:		Interface for the Instruction class in BinBot Server.
 *			This class is in charge of taking a matrix indicating
 *			the location of identified waste in an image (from
 *			opencv) and returning a json String that can be sent to
 *			and interpreted by BinBot to properly navigate and
 *			retrieve that trash.
 ******************************************************************************/

public interface Instruction
{
	/**
	 * <h1>getJsonInstructions(Object[][] matrix)</h1>
	 * This method takes as input, a matrix of objects generated from OpenCV
	 * computer vision and returns a json String of the format:
	 * {
	 *     "status":,
	 *     "treads":[
	 * 			{
	 * 			 "angle":,
	 * 			 "momentum",
	 * 			}
	 * 		],
	 * 		"arms":[
	 * 			{
	 * 			 "angle":
	 * 			}
	 * 		]
	 * }
	 * This json string is meant to be sent to the BinBot Robot to instruct the robot what movements it should take.
	 * Within this function, the server will decide how BinBot should proceed based on the matrix it is provided. The
	 * matrix will first be used to calculate the distance the object is predicted to be from BinBot and its angle.
	 * If the trash is too far, the function will decide that the robot must move to the trash and will generate a list
	 * of "treads" instructions to tell the robot how it should move. If it is decided that the Robot IS close enough,
	 * instead of generating a list of tread instructions, a list of arm instructions will be generated, instructing
	 * each limb how they should move to retrieve the trash. Either way, these instructions will be packed into the json
	 * a json string and returned to the caller.
	 *
	 *
	 *
	 * @author  Sean DiGirolamo
	 * @version 1.0
	 * @since   2019-10-09
	 */
	String getJsonInstructions(Object[][] matrix);
}
