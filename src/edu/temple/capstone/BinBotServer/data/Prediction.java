package edu.temple.capstone.BinBotServer.data;

/**
 * Class to handle OpenCV's object classification model for BinBot's waste detection.
 *
 * @author Michael Savitski
 * @version 1.0
 * @since 2019-11-16
 */
public class Prediction {

    private int upperX, upperY, lowerX, lowerY, idClass;
    private float certainty;

    public Prediction() {
        upperX = 0;
        upperY = 0;
        lowerX = 0;
        lowerY = 0;
        idClass = 1;
        certainty = 0.0f;
    }

    public Prediction(int upperX, int upperY, int lowerX, int lowerY, int idClass, float certainty) {
        this.upperX = upperX;
        this.upperY = upperY;
        this.lowerX = lowerX;
        this.lowerY = lowerY;
        this.idClass = idClass;
        this.certainty = certainty;
    }

    public int getUpperX() {
        return upperX;
    }

    public void setUpperX(int upperX) {
        this.upperX = upperX;
    }

    public int getUpperY() {
        return upperY;
    }

    public void setUpperY(int upperY) {
        this.upperY = upperY;
    }

    public int getLowerX() {
        return lowerX;
    }

    public void setLowerX(int lowerX) {
        this.lowerX = lowerX;
    }

    public int getLowerY() {
        return lowerY;
    }

    public void setLowerY(int lowerY) {
        this.lowerY = lowerY;
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
}