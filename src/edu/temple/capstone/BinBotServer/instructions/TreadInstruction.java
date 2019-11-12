package edu.temple.capstone.BinBotServer.instructions;

import java.util.*;

/**
 * The TreadInstruction class consists only of one static method "calcInstructions", which is responsible for
 * calculating the movements BinBot should take to navigate to a piece of waste in regards to its treads.
 *
 * @author Sean DiGirolamo
 * @version 1.0
 * @since 2019-10-13
 */
public class TreadInstruction {
    /**
     * This method takes as input, an x coordinate, a y coordinate, a width, and a height, all indicating the location
     * of an identified object in a matrix. Using these values, it will estimate a distance between the camera and the
     * object, and the angle that the camera is viewing the object at. The function will then generate a list of
     * movements that should be taken to navigate to the object, as angle-direction pairs, meant to indicate first a
     * turn, then a direction traveled straight. This list will be returned to the caller.
     *
     * @author Sean DiGirolamo
     * @since 2019-10-09
     */
    public static List<Movement> calcInstructions(double x, double y, double w, double h, double imgW, double imgH) {
        // I calculated the angle it sees at, 52, based on sees 1 ft wide at 1ft distance
        List<Movement> retval = new ArrayList<>();
        double angle = -1.0;

        double imgHalfPoint = (imgW + imgH) / 2;

        if (imgHalfPoint > x && imgHalfPoint < y) {
            angle = 0.0;
        } else {
            angle = (x / 100) * 52;
        }

        retval.add(new Movement(angle, 1.0));

        return retval;
    }
}
