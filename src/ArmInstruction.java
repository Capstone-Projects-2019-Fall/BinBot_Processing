package edu.temple.capstone.BinBotServer;

import java.util.List;

/**
 * <h1>ArmInstruction</h1>
 * The ArmInstruction class consists only of one static method "calcInstructions", which is responsible for calculating
 * the movements BinBot should take to retrieve a piece of trash using the joints in its arm.
 *
 *
 *
 * @author Sean DiGirolamo
 * @version 1.0
 * @since   2019-10-13
 */
public interface ArmInstruction
{
	/**
	 * <h1>calcInstructions(double x, double y, double w, double h)</h1>
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
	 * @version 1.0
	 * @since   2019-10-09
	 */
	List<Double> calcInstructions(double x, double y, double w, double h);
}
