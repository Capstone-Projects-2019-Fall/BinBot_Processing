package edu.temple.capstone.BinBotServer.data;

/**
 * Class to handle OpenCV's object classification model for BinBot's waste detection.
 *
 * @author Michael Savitski
 * @version 1.0
 * @since 2019-11-16
 */
public class Prediction {

    private int upperLeftX, upperLeftY, lowerRightX, lowerRightY, idClass, parentImageWidth, parentImageHeight;
    private float certainty;
    private long timeStamp;

    public Prediction() {
        upperLeftX = 0;
        upperLeftY = 0;
        lowerRightX = 0;
        lowerRightY = 0;
        idClass = 1;
        certainty = 0.0f;
        parentImageWidth = 0;
        parentImageHeight = 0;
        timeStamp = System.currentTimeMillis();
    }

    public Prediction(int upperLeftX, int upperLeftY, int lowerRightX, int lowerRightY, int idClass, float certainty,
                      int parentImageWidth, int parentImageHeight, long timeStamp) {
        this.upperLeftX = upperLeftX;
        this.upperLeftY = upperLeftY;
        this.lowerRightX = lowerRightX;
        this.lowerRightY = lowerRightY;
        this.idClass = idClass;
        this.certainty = certainty;
        this.parentImageWidth = parentImageWidth;
        this.parentImageHeight = parentImageHeight;
        this.timeStamp = timeStamp;
    }

    public int getCenterX() {
        return ((upperLeftX + lowerRightX) / 2);
    }

    public int getCenterY() {
        return ((upperLeftY + lowerRightY) / 2);
    }

    public int getWidth() {
        return (lowerRightX - upperLeftX);
    }

    public int getHeight() {
        return (lowerRightY - upperLeftY);
    }

    public int getUpperLeftX() {
        return upperLeftX;
    }

    public void setUpperLeftX(int upperLeftX) {
        this.upperLeftX = upperLeftX;
    }

    public int getUpperLeftY() {
        return upperLeftY;
    }

    public void setUpperLeftY(int upperLeftY) {
        this.upperLeftY = upperLeftY;
    }

    public int getLowerRightX() {
        return lowerRightX;
    }

    public void setLowerRightX(int lowerRightX) {
        this.lowerRightX = lowerRightX;
    }

    public int getLowerRightY() {
        return lowerRightY;
    }

    public void setLowerRightY(int lowerRightY) {
        this.lowerRightY = lowerRightY;
    }

    public int getIdClass() {
        return idClass;
    }

    public void setIdClass(int idClass) {
        this.idClass = idClass;
    }

    public float getCertainty() {
        return certainty;
    }

    public void setCertainty(float certainty) {
        this.certainty = certainty;
    }

    public int getParentImageWidth() {
        return parentImageWidth;
    }

    public void setParentImageWidth(int parentImageWidth) {
        this.parentImageWidth = parentImageWidth;
    }

    public int getParentImageHeight() {
        return parentImageHeight;
    }

    public void setParentImageHeight(int parentImageHeight) {
        this.parentImageHeight = parentImageHeight;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }
}