package edu.temple.capstone.BinBotServer;

import edu.temple.capstone.BinBotServer.connections.AppConnection;
import edu.temple.capstone.BinBotServer.connections.BotConnection;
import edu.temple.capstone.BinBotServer.instructions.Instruction;
import edu.temple.capstone.BinBotServer.instructions.Status;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Main {
    final static int BOT_PORT = 7001;
    private static AppConnection appConnection = null;
    private static BotConnection botConnection = null;
    private static boolean poweredState = true;

    public static void main(String[] args) throws IOException {
        setUp();
//		loopTest();
        while (true)
            openCVTest();
    }

    public static void setUp() throws IOException {
        System.out.println("BinBot Server starting...");
        //System.out.println("Connecting to Mobile Application...");
        //appConnection = new AppConnection(7001);
        //appConnection.accept();
        System.out.println("Connecting to BinBot Robot...");
        botConnection = new BotConnection(BOT_PORT);
        botConnection.accept();
    }

    public static void loop() throws IOException {
        while (true) {
            while (!poweredState) {
            }
            String json = botConnection.receive();
            Instruction instruction = new Instruction(json);
            BufferedImage img = instruction.img();
            //OpenCVWrapper openCVWrapper = new OpenCVWrapper(img);
            //Instruction response = new Instruction(openCVWrapper.getMatrix());
            //botConnection.sendToBot(response.json());
        }
    }

    public static void openCVTest() throws IOException {

        BufferedImage img = receiveCapture();

        //pass to CV
        img = WasteDetect.imageDetect(img);

        sendCapture(img);

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

    public static void loopTest() throws IOException {
        String path = "C:/Users/Sean/Desktop/Server/";
        String sendFilename = "img.jpg";
        String receiveFilename = "out.jpg";

        System.out.println("Reading file " + path + sendFilename);
        BufferedImage img = ImageIO.read(new File(path + sendFilename));
        Instruction instrSend = new Instruction(Status.PATROL, img, null, null);
        System.out.println("Attempting to send: " + instrSend.json());
        botConnection.sendToBot(instrSend.json());
        System.out.println("Sent");

        System.out.println("Attempting to receive");
        String jsonReceive = botConnection.receive();
        System.out.print("received: " + jsonReceive);
        System.out.println("Saving image as: " + path + receiveFilename);
        Instruction instrReceive = new Instruction(jsonReceive);
        ImageIO.write(instrReceive.img(), "jpg", new File(path + receiveFilename));
        System.out.println("saved!");
    }
}
