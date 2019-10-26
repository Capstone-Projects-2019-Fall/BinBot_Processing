package edu.temple.capstone.BinBotServer;

import edu.temple.capstone.BinBotServer.connections.BotConnection;
import edu.temple.capstone.BinBotServer.instructions.Instruction;
import edu.temple.capstone.BinBotServer.instructions.Status;

import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Class used to containerize any methods or functionality needed for demo 1.
 *
 * @author Sean Reddington
 * @version 1.0
 * @since 2019-10-25
 */
public class Demo {
    final static int BOT_PORT = 7001;
    private static BotConnection botConnection = null;


    /**
     * Method to establish connection to BinBot's other components
     *
     * @author Sean DiGirolamo
     * @version 1.0
     * @since 2019-10-25
     */
    public static void setUp() throws IOException {
        System.out.println("BinBot Server starting...");
        //System.out.println("Connecting to Mobile Application...");
        //appConnection = new AppConnection(7001);
        //appConnection.accept();
        System.out.println("Connecting to BinBot Robot...");
        botConnection = new BotConnection(BOT_PORT);
        botConnection.accept();
    }

    /**
     * Method to receive BufferedImage from client's live feed, pass the image into the waste detection model,
     * then send a modified BufferedImage, including any bounding boxes, back to the client.
     *
     * @author Sean Reddington
     * @version 1.0
     * @since 2019-10-25
     */
    public static void openCVTest() throws IOException {
        BufferedImage img = receiveCapture();
        WasteDetector wasteDetector = new WasteDetector();
        BufferedImage result = wasteDetector.imageDetect(img);
        sendCapture(result);
    }

    /**
     * Method to send BufferedImage back to connected client.
     *
     * @author Sean Reddington
     * @version 1.0
     * @since 2019-10-25
     */
    public static void sendCapture(BufferedImage image) {
        // Send to server
        Instruction imgSend = new Instruction(Status.NAVIGATE, image, null, null);
        System.out.print("Sending image to server..");
        try {
            botConnection.sendToBot(imgSend.json());
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Sent");
    }

    /**
     * Method to receive BufferedImage from connected client.
     *
     * @author Sean Reddington
     * @version 1.0
     * @since 2019-10-25
     */
    public static BufferedImage receiveCapture() {
        System.out.println("Attempting to receive");
        String jsonReceive = null;
        try {
            jsonReceive = botConnection.receive();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("received image from server");
        Instruction imgReceive = new Instruction(jsonReceive);
        return imgReceive.img();
    }

}

