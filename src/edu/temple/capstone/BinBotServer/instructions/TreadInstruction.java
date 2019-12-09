package edu.temple.capstone.BinBotServer.instructions;

import java.util.*;

/**
 * The TreadInstruction class consists only of one static method "calcInstructions", which is responsible for
 * calculating the movements BinBot should take to navigate to a piece of waste in regards to its treads.
 *
 * @author Sean DiGirolamo, Sean Reddington
 * @version 2.0
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
     * @author Sean DiGirolamo, Sean Reddington
     * @since 2019-10-09
     */
    public static List<Movement> calcInstructions(double x, double xBoxCenter, double y, double yBoxCenter, double w, double h, double imgW, double imgH, double distance) {
        // I calculated the angle it sees at, 52, based on sees 1 ft wide at 1ft distance
        List<Movement> movements = new ArrayList<>();
        double angle = -1.0;
        distance *= 10.0;
        double xImgCenter = imgW / 2;
        double yImgCenter = imgH / 2;
        double imgHalfPoint = (xImgCenter + yImgCenter);

        final double centeredBias = 113; // approximation that bounding box is in center of image

        if (xBoxCenter > xImgCenter) { // Box is right of img center
            double xDiff = xBoxCenter - xImgCenter;
            double rightBias = xImgCenter + centeredBias;
            if (xDiff < centeredBias) { // Box is approximately in center of image
                if (inRange(distance)) { // If BinBot is in reach of the object
                    System.out.println("IN RANGE");
                    movements.add(new Movement(0.0, 1.0)); // Send instruction to pick up
                } else {
                    movements.add(new Movement(0.0, distance / 2)); // Move forward half the distance to sensor's distance
                }
            } else {
                // Turn more right to center
                double theta = Math.atan2(distance * 10, xDiff);
                angle = Math.toDegrees(theta);
                movements.add(new Movement(angle, 1.0));
                if (!inRange(distance)) { // If BinBot is not in range, also move forward
                    movements.add(new Movement(0.0, distance / 4));
                }

            }
        } else { // Box is left of img center
            double xDiff = xImgCenter - xBoxCenter;
            double leftBias = xImgCenter - centeredBias;
            if (xDiff < centeredBias) { // Box is approximately in center of image
                if (inRange(distance)) { // If BinBot is in reach of the object
                    System.out.println("IN RANGE");
                    movements.add(new Movement(0.0, 1.0)); // Send instruction to pick up
                } else {
                    movements.add(new Movement(0.0, distance / 2)); // Move forward half the distance to sensor's distance
                }
            } else {
                // Turn more left to center
                double theta = Math.atan2(distance * 10, xDiff);
                angle = Math.toDegrees(theta);
                angle += 180.0; // angle > 180 tells BinBot to turn left
                movements.add(new Movement(angle, 1.0));
                if (!inRange(distance)) { // If BinBot is not in range, also move forward
                    movements.add(new Movement(0.0, distance / 4));
                }
            }
        }

//        if (imgHalfPoint > x && imgHalfPoint < y) {
//            angle = 0.0;
//        } else {
//            // Further from item, more impactful angle will be in determining distance from center
//            // Assume closest length, which has most accurate angle, so that we will not overturn, but will likely underturn
//            // multiple times until at correct distance
//            // Turn same percentage of 52 degrees as percentage distance from center
//            double diff = imgHalfPoint - x;
//            double percent;
//            boolean left;
//            if (diff > 0) {
//                left = true;
//            } else {
//                diff = -diff;
//                left = false;
//            }
//            percent = diff / imgHalfPoint;
//            angle = percent * 52;
//        }
//
//        movements.add(new Movement(angle, 1.0));

        return movements;
    }

    /**
     * Checks if BinBot is within range for the arm to reach the object
     *
     * @author Sean Reddington
     * @since 2019-12-01
     */
    public static boolean inRange(double distance) {
//        return 0.10 < distance && distance < 0.11;
//        return 3.1 < distance && distance < 3.4;
        return distance < 3.4;
    }
}