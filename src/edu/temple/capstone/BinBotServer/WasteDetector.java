package edu.temple.capstone.BinBotServer;

import edu.temple.capstone.BinBotServer.data.Prediction;
import org.opencv.core.*;
import org.opencv.core.Point;
import org.opencv.imgproc.Imgproc;
import org.tensorflow.SavedModelBundle;
import org.tensorflow.Tensor;
import org.tensorflow.types.UInt8;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

/**
 * Class to handle OpenCV's object classification model for BinBot's waste detection.
 *
 * @author Sean Reddington, Michael Savitski
 * @version 2.0
 * @since 2019-11-16
 */
public class WasteDetector {

    private Mat mat;
    private BufferedImage bufferedImage;
    private SavedModelBundle model, model2;
    private ArrayList<Prediction> predictions;
    private long startTime, endTime, mostRecentLatency;

    final private int CUP = 47;
    final private int FORK = 48;

    final private int BAR_WRAPPER = 1;
    final private int JUICE_BOX = 2;
    final private int K_CUP = 3;
    final private int PILL_BOTTLE = 4;
    final private int PLASTIC_FORK = 5;

    //This value can be adjusted, currently only showing boxes with 70% or more prediction score
    final private float minimumScore = .70f;

    //This array can be modified to contain classes you wish to predict
    final private int[] desiredClasses = {FORK, CUP, BAR_WRAPPER, JUICE_BOX, K_CUP, PILL_BOTTLE, PLASTIC_FORK};

    //This value represents the percent difference between culled predictions
    final private float cullPercent = .02f;

    // Loading the OpenCV core library
    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    /**
     * Default constructor.  Loads the prediction model.
     *
     * @author Michael Savitski
     * @since 2019-11-16
     */
    public WasteDetector() {
        String modelPath = "res/faster_rcnn_inception_v2_coco_2018_01_28/saved_model";
        String modelPath2 = "res/001/saved_model";
        model = SavedModelBundle.load(modelPath, "serve");
        model2 = SavedModelBundle.load(modelPath2, "serve");
    }

    /**
     * Takes the passed BufferedImage and passes it through OpenCV's object detection model.
     *
     * @param img: BufferedImage from client
     * @return Modified BufferedImage from OpenCV
     * @author Sean Reddington
     * @since 2019-10-25
     */
    public BufferedImage imageDetect(BufferedImage img) {
        this.bufferedImage = img;
        startTime = System.currentTimeMillis();
        bufferedImg2Mat();
        endTime = System.currentTimeMillis();
        mostRecentLatency = endTime - startTime;
        return this.processImage(this.mat);
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
    private BufferedImage processImage(Mat mat) {
        getSpace(mat);

        predictions = new ArrayList<>();
        BufferedImage outputImage = null;

        List<Tensor<?>> outputs, outputs2;
        Tensor<UInt8> input = makeImageTensor(bufferedImage);
        outputs = model.session().runner().feed("image_tensor", input)
                .fetch("detection_scores").fetch("detection_classes").fetch("detection_boxes").run();
        outputs2 = model2.session().runner().feed("image_tensor", input)
                .fetch("detection_scores").fetch("detection_classes").fetch("detection_boxes").run();

        predictions = new ArrayList<>();

        createPredictions(outputs);

        //Change the class numbers of classes 1-5 so they don't conflict with the second model
        for (Prediction prediction : predictions) {
            if (prediction.getIdClass() <= 5) {
                prediction.setIdClass(prediction.getIdClass() + 100);
            }
        }

        createPredictions(outputs2);

        //Remove all predictions not of a desired class or certainty
        ArrayList<Prediction> predictionsToDrop = new ArrayList<>();
        for (Prediction prediction : predictions) {
            boolean keepClass = false;
            for (int classID : desiredClasses) {
                if (prediction.getIdClass() == classID && prediction.getCertainty() >= minimumScore) {
                    keepClass = true;
                }
            }
            if (!keepClass) {
                predictionsToDrop.add(prediction);
            }
        }

        predictions.removeAll(predictionsToDrop);

        if (predictions.size() > 0) {

            //Assign the thickness of the box based on the idea that a 600 x 600 image would result in 1 pixel
            float[] predictionScale = {bufferedImage.getWidth(), bufferedImage.getHeight()};
            int thickness = Math.round((1.0f / 600) * ((predictionScale[0] + predictionScale[1]) / 2));

            for (Prediction prediction : predictions) {

                if (prediction.getCertainty() > minimumScore) {

                    String percent = String.format("%.02f", (prediction.getCertainty() * 100));

                    Imgproc.rectangle(mat, new Point(prediction.getUpperLeftX(), prediction.getUpperLeftY()),
                            new Point(prediction.getLowerRightX(), prediction.getLowerRightY()),
                            new Scalar(0, 255, 0), thickness);
                    Imgproc.putText(mat, classIDtoName(prediction.getIdClass()) + ": " +
                                    percent + "%",
                            new Point(prediction.getUpperLeftX(), prediction.getUpperLeftY() - thickness), 1,
                            thickness, new Scalar(0, 255, 0), 2);

                }
            }
        }

        int w = mat.cols();
        int h = mat.rows();
        int type = BufferedImage.TYPE_3BYTE_BGR;
        outputImage = new BufferedImage(w, h, type);

        WritableRaster raster = outputImage.getRaster();
        DataBufferByte dataBuffer = (DataBufferByte) raster.getDataBuffer();
        byte[] data = dataBuffer.getData();
        mat.get(0, 0, data);

        if (outputImage != null) {
            bufferedImage = outputImage;
            return outputImage;
        } else {
            return bufferedImage;
        }
    }

    private void createPredictions(List<Tensor<?>> outputs) {

        float[] scores, classes;
        float[][] boxes;

        try (Tensor<Float> scoresT = outputs.get(0).expect(Float.class);
             Tensor<Float> classesT = outputs.get(1).expect(Float.class);
             Tensor<Float> boxesT = outputs.get(2).expect(Float.class)) {
            // All these tensors have:
            // - 1 as the first dimension
            // - maxObjects as the second dimension
            // While boxesT will have 4 as the third dimension (2 sets of (x, y) coordinates).
            // This can be verified by looking at scoresT.shape() etc.
            int maxObjects = (int) scoresT.shape()[1];
            scores = scoresT.copyTo(new float[1][maxObjects])[0];
            classes = classesT.copyTo(new float[1][maxObjects])[0];
            boxes = boxesT.copyTo(new float[1][maxObjects][4])[0];
            boolean foundSomething = false;
            for (int i = 0; i < scores.length; ++i) {
                if (scores[i] < 0.5) {
                    continue;
                }
                foundSomething = true;
            }
            if (foundSomething) {
                float[] predictionScale = {bufferedImage.getWidth(), bufferedImage.getHeight()};
                for (float[] box : boxes) {
                    box[0] = box[0] * predictionScale[1];
                    box[1] = box[1] * predictionScale[0];
                    box[2] = box[2] * predictionScale[1];
                    box[3] = box[3] * predictionScale[0];
                }

                long predictionTimeStamp = System.currentTimeMillis();

                for (int i = 0; i < boxes.length; i++) {
                    if ((boxes[i][0] + boxes[i][1] + boxes[i][2] + boxes[i][3]) == 0) {
                        break;
                    } else {
                        int y1 = Math.round(boxes[i][0]);
                        int x1 = Math.round(boxes[i][1]);
                        int y2 = Math.round(boxes[i][2]);
                        int x2 = Math.round(boxes[i][3]);
                        boolean doAdd = true;
                        if (predictions.size() > 0) {
                            for (Prediction prediction : predictions) {
                                int widthRange = Math.round(prediction.getParentImageWidth() * cullPercent);
                                int heightRange = Math.round(prediction.getParentImageHeight() * cullPercent);
                                if ((Math.abs(x1 - prediction.getUpperLeftX()) <= widthRange) &&
                                        (Math.abs(x2 - prediction.getLowerRightX()) <= widthRange) &&
                                        (Math.abs(y1 - prediction.getUpperLeftY()) <= heightRange) &&
                                        (Math.abs(y2 - prediction.getLowerRightY()) <= heightRange)) {
                                    doAdd = false;
                                    break;
                                }
                            }
                        }
                        if (doAdd) {
                            predictions.add(new Prediction(x1, y1, x2, y2, Math.round(classes[i]), scores[i], mat.width(), mat.height(),
                                    predictionTimeStamp));
                        }
                    }
                }
            }
        }
    }

    private static Tensor<UInt8> makeImageTensor(BufferedImage bufferedImage) {

        if (bufferedImage.getType() != BufferedImage.TYPE_3BYTE_BGR) {
            BufferedImage newImage = new BufferedImage(
                    bufferedImage.getWidth(), bufferedImage.getHeight(), BufferedImage.TYPE_3BYTE_BGR);
            Graphics2D graphics = newImage.createGraphics();
            graphics.drawImage(bufferedImage, 0, 0, bufferedImage.getWidth(), bufferedImage.getHeight(), null);
            graphics.dispose();
            bufferedImage = newImage;
        }

        byte[] data = ((DataBufferByte) bufferedImage.getData().getDataBuffer()).getData();
        bgr2rgb(data);
        final long BATCH_SIZE = 1;
        final long CHANNELS = 3;
        long[] shape = new long[]{BATCH_SIZE, bufferedImage.getHeight(), bufferedImage.getWidth(), CHANNELS};
        return Tensor.create(UInt8.class, shape, ByteBuffer.wrap(data));
    }

    private static void bgr2rgb(byte[] data) {
        for (int i = 0; i < data.length; i += 3) {
            byte tmp = data[i];
            data[i] = data[i + 2];
            data[i + 2] = tmp;
        }
    }

    private String classIDtoName(int classID) {
        String className = "Unknown";

        if (classID == FORK) {
            className = "Fork";
        } else if (classID == CUP) {
            className = "Cup";
        } else if (classID == BAR_WRAPPER) {
            className = "Bar Wrapper";
        } else if (classID == JUICE_BOX) {
            className = "Juice Box";
        } else if (classID == K_CUP) {
            className = "K-Cup";
        } else if (classID == PILL_BOTTLE) {
            className = "Pill Bottle";
        } else if (classID == PLASTIC_FORK) {
            className = "Plastic Fork";
        }

        return className;
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

    public ArrayList<Prediction> getPredictions() {
        return predictions;
    }

    public long getMostRecentLatency() {
        return mostRecentLatency;
    }

    public void setMostRecentLatency(long mostRecentLatency) {
        this.mostRecentLatency = mostRecentLatency;
    }

    public BufferedImage getBufferedImage() {
        return this.bufferedImage;
    }
}
