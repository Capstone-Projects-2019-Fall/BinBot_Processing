package edu.temple.capstone.BinBotServer;

import java.awt.image.BufferedImage;

public class WasteDetect implements OpenCVWrapper {

    public static BufferedImage imageDetect(BufferedImage img) {

        return img;
    }

    @Override
    public BufferedImage getAppImg() {
        return null;
    }

    @Override
    public Object[][] getMatrix() {
        return new Object[0][];
    }

    @Override
    public double getX() {
        return 0;
    }

    @Override
    public double getY() {
        return 0;
    }

    @Override
    public double getHeight() {
        return 0;
    }

    @Override
    public double getWidth() {
        return 0;
    }
}
