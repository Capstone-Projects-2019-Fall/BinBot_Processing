package edu.temple.capstone.BinBotServer;

import javafx.util.Pair;

import java.util.List;

/**
 * The Instruction class consists of a single static method which is responsible for providing a json string which can
 * be used to properly maneuver BinBot to retrieve trash when given a matrix from opencv. More details can be found in
 * the method documentation itself.
 *
 *
 *
 * @author Sean DiGirolamo
 * @since   2019-10-13
 */
public class Instruction
{
	/**
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
	 * @since   2019-10-09
	 */
	public static String getJsonInstructions(Object[][] matrix) {
		String retval = "";
		List<Pair<Double, Double>> treads = TreadInstruction.calcInstructions(0, 0, 0, 0);
		List<Double> arms = ArmInstruction.calcInstructions(0, 0, 0, 0);

		return retval;
	}
}
