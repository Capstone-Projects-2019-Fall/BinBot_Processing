package edu.temple.capstone.BinBotServer;

import edu.temple.capstone.BinBotServer.connections.BotConnection;
import edu.temple.capstone.BinBotServer.instructions.Instruction;
import edu.temple.capstone.BinBotServer.instructions.Status;

import java.awt.image.BufferedImage;
import java.io.IOException;

public class Demo {
    final static int BOT_PORT = 7001;
    private static BotConnection botConnection = null;

    public static void setUp() throws IOException {
        System.out.println("BinBot Server starting...");
        //System.out.println("Connecting to Mobile Application...");
        //appConnection = new AppConnection(7001);
        //appConnection.accept();
        System.out.println("Connecting to BinBot Robot...");
        botConnection = new BotConnection(BOT_PORT);
        botConnection.accept();
    }

    public static void openCVTest() throws IOException {
        BufferedImage img = receiveCapture();
        WasteDetector wasteDetector = new WasteDetector();
        BufferedImage result = wasteDetector.imageDetect(img);
        sendCapture(result);
    }

    public static void sendCapture(BufferedImage image) {
        // Send to server
        Instruction imgSend = new Instruction(Status.NAVIGATE, image, null, null);
//        System.out.print("Sending: " + imgSend.json());
        System.out.print("Sending image to server..");
        try {
            botConnection.sendToBot(imgSend.json());
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Sent");
    }

    public static BufferedImage receiveCapture() {
        System.out.println("Attempting to receive");
        String jsonReceive = null;
        try {
            jsonReceive = botConnection.receive();
        } catch (IOException e) {
            e.printStackTrace();
        }
//        System.out.println("received: " + jsonReceive);
        System.out.println("received image from server");
        Instruction imgReceive = new Instruction(jsonReceive);
        return imgReceive.img();
    }

}

