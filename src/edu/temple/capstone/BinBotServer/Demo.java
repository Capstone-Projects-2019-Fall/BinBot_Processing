package edu.temple.capstone.BinBotServer;

import edu.temple.capstone.BinBotServer.connections.BotConnection;
import edu.temple.capstone.BinBotServer.data.Prediction;
import edu.temple.capstone.BinBotServer.instructions.Instruction;
import edu.temple.capstone.BinBotServer.instructions.Status;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

/**
 * Class used to containerize any methods or functionality needed for demo 1.
 *
 * @author Sean Reddington
 * @version 1.0
 * @since 2019-10-25
 */
public class Demo {
    private final static int BOT_PORT = 7001;
    private static BotConnection botConnection = null;

    public static void main(String[] args) throws IOException {
        BufferedImage img = ImageIO.read(new File("res/test.jpg"));
        WasteDetector detector = new WasteDetector();
        BufferedImage testImage = detector.imageDetect(img);
        ArrayList<Prediction> predictions = detector.getPredictions();

        File outputFile = new File("res/result.jpg");
        ImageIO.write(testImage, "jpg", outputFile);

        if (predictions.size() > 0) {
            System.out.println("Prediction latency = " + detector.getMostRecentLatency() + " milliseconds.");
            System.out.println("Parent image width = " + predictions.get(0).getParentImageWidth() +
                    ", Parent image height = " + predictions.get(0).getParentImageHeight() + ", Time = " +
                    new Date(predictions.get(0).getTimeStamp()));
        }
        for (Prediction prediction : predictions) {
            System.out.println("Predicted box: (" + prediction.getUpperLeftX() + ", " +
                    prediction.getUpperLeftY() + "), (" + prediction.getLowerRightX() + ", " +
                    prediction.getLowerRightY() + ") Class: " + prediction.getIdClass() +
                    "  Score: " + prediction.getCertainty());
            System.out.println("Width = " + prediction.getWidth() + ", Height = " + prediction.getHeight() +
                    ", Center = (" + prediction.getCenterX() + ", " + prediction.getCenterY() + ")");
        }
    }


    /**
     * Method to establish connection to BinBot's other components
     *
     * @author Sean DiGirolamo
     * @since 2019-10-25
     */
    public static void setUp() throws IOException {
        System.out.println("BinBot Server starting...");
        System.out.println("Connecting to BinBot Robot...");
        botConnection = new BotConnection(BOT_PORT);
        botConnection.accept();
    }

    /**
     * Method to receive BufferedImage from client's live feed, pass the image into the waste detection model,
     * then send a modified BufferedImage, including any bounding boxes, back to the client.
     *
     * @author Sean Reddington
     * @since 2019-10-25
     */
    public static void openCVTest() {
        BufferedImage img = receiveCapture();
        WasteDetector wasteDetector = new WasteDetector();
        BufferedImage result = wasteDetector.imageDetect(img);
        sendCapture(result);
    }

    /**
     * Method to send BufferedImage back to connected client.
     *
     * @author Sean Reddington
     * @since 2019-10-25
     */
    private static void sendCapture(BufferedImage image) {
        // Send to server
        Instruction imgSend = new Instruction(Status.PATROL, image, null, null);
        System.out.print("Sending image to server..");
        botConnection.send(imgSend.json());
        System.out.println("Sent");
    }

    /**
     * Method to receive BufferedImage from connected client.
     *
     * @author Sean Reddington
     * @since 2019-10-25
     */
    private static BufferedImage receiveCapture() {
        System.out.println("Attempting to receive");
        String jsonReceive = null;
        jsonReceive = botConnection.receive();
        System.out.println("received image from server");
        Instruction imgReceive = new Instruction(jsonReceive);
        return imgReceive.getImage();
    }

}

