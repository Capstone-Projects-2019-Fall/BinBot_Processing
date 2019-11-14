package edu.temple.capstone.BinBotServer;

import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;

/**
 * Class to handle OpenCV's object classification model for BinBot's waste detection.
 *
 * @author Sean Reddington
 * @version 1.0
 * @since 2019-10-25
 */
class WasteDetector {

    private Mat mat;
    private BufferedImage bufferedImage;

    // Loading the OpenCV core library
    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    /**
     * Takes the passed BufferedImage and passes it through OpenCV's object detection model.
     *
     * @param img: BufferedImage from client
     * @return Modified BufferedImage from OpenCV
     * @author Sean Reddington
     * @since 2019-10-25
     */
    BufferedImage imageDetect(BufferedImage img) {
        this.bufferedImage = img;
        bufferedImg2Mat();
        return this.getImage(this.mat);
    }

    /**
     * Ensures the BufferedImage matches to the Mat object.
     *
     * @param mat: Mat object decoded from the BufferedImage
     * @author Sean Reddington
     * @since 2019-10-25
     */
    private void getSpace(Mat mat) {
        int type = 0;

        if (mat.channels() == 1) {
            type = BufferedImage.TYPE_BYTE_GRAY;
        } else if (mat.channels() == 3) {
            type = BufferedImage.TYPE_3BYTE_BGR;
        }

        this.mat = mat;
        int w = mat.cols();
        int h = mat.rows();

        if (bufferedImage == null || bufferedImage.getWidth() != w || bufferedImage.getHeight() != h || bufferedImage.getType() != type) {
            bufferedImage = new BufferedImage(w, h, type);
        }
    }

    /**
     * Passes Mat object to OpenCV's CascadeClassifier to detect any objects from the live feed.
     *
     * @param mat: Mat object decoded from the BufferedImage
     * @author Sean Reddington
     * @since 2019-10-25
     */
    private BufferedImage getImage(Mat mat) {
        getSpace(mat);

        CascadeClassifier wasteDetector = new CascadeClassifier();
        String freezeModelFile = "haarcascade_frontalface_alt.xml";
        wasteDetector.load(freezeModelFile);

        MatOfRect wasteDetections = new MatOfRect();
        wasteDetector.detectMultiScale(mat, wasteDetections);

        for (Rect rect : wasteDetections.toArray()) {
            Imgproc.rectangle(mat, new Point(rect.x, rect.y),
                    new Point(rect.x + rect.width, rect.y + rect.height),
                    new Scalar(0, 255, 0));
            Imgproc.putText(mat, "Waste", new Point(rect.x, rect.y - 2), 1, 1, new Scalar(0, 255, 0));
        }

        WritableRaster raster = bufferedImage.getRaster();
        DataBufferByte dataBuffer = (DataBufferByte) raster.getDataBuffer();
        byte[] data = dataBuffer.getData();
        mat.get(0, 0, data);
        return bufferedImage;
    }

    /**
     * Method to convert a BufferedImage to a Mat object.
     *
     * @author Sean Reddington
     * @since 2019-10-25
     */
    private void bufferedImg2Mat() {
        this.mat = new Mat(this.bufferedImage.getHeight(), this.bufferedImage.getWidth(), CvType.CV_8UC3);
        byte[] pixels = ((DataBufferByte) this.bufferedImage.getRaster().getDataBuffer()).getData();
        this.mat.put(0, 0, pixels);
    }

    public boolean containsWaste() {
        return true;
    }

    public double objX() {
        return 0;
    }

    public double objY() {
        return 0;
    }

    public double objHeight() {
        return 0;
    }

    public double objWidth() {
        return 0;
    }

    public double imgHeight() {
        return 0;
    }

    public double imgWidth() {
        return 0;
    }

    public void loadImage(BufferedImage img) {
        return;
    }

}
