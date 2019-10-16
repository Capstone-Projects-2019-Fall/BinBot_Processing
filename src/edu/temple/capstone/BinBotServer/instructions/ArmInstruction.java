package edu.temple.capstone.BinBotServer.instructions;

import java.util.ArrayList;
import java.util.List;

/**
 * The ArmInstruction class consists only of one static method "calcInstructions", which is responsible for calculating
 * the movements BinBot should take to retrieve a piece of trash using the joints in its arm.
 *
 *
 *
 * @author Sean DiGirolamo
 * @since   2019-10-13
 */
public class ArmInstruction
{
	/**
	 * This method takes as input, an x coordinate, a y coordinate, a width, and a height, all indicating the location
	 * of an identified object in a matrix. Using these values, it will estimate a distance between the camera and the
	 * object, and the angle that the camera is viewing the object at. The function will then generate a list of
	 * movements that should be applied to the arm's joins, such that the arm will be in a position where it would grab
	 * the object were it to squeeze. These instructions will be in the form of a list of doubles, each double
	 * indicating the angle their corresponding joint should rotate. This list is returned to the caller.
	 *
	 *
	 *
	 * @author  Sean DiGirolamo
	 * @since   2019-10-09
	 */
	public static List<Double> calcInstructions(double x, double y, double w, double h) {
		List<Double> retval = new ArrayList<>();
		retval.add(0.0);
		return retval;
	}
}
