package edu.temple.capstone.BinBotServer;

import edu.temple.capstone.BinBotServer.mobileInterface.AppConnectionThread;
import edu.temple.capstone.BinBotServer.mobileInterface.AppMessage;
import edu.temple.capstone.BinBotServer.connections.AppConnection;
import edu.temple.capstone.BinBotServer.connections.BotConnection;
import edu.temple.capstone.BinBotServer.instructions.Instruction;
import edu.temple.capstone.BinBotServer.instructions.Status;

import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Main class to run the BinBot processing server.
 *
 * @author Sean Reddington
 * @version 1.0
 * @since 2019-10-25
 */
public class Main {
    private final static int BOT_PORT = 7001;
    private final static int APP_PORT = 7002;

    private static AppConnectionThread appConnectionThread;

    private static AppConnection appConnection = null;
    private static BotConnection botConnection = null;

    /**
     * Main method to instantiate and loop through the BinBot server application.
     *
     * @author Sean Reddington
     * @since 2019-10-25
     */
    public static void main(String[] args) throws IOException {
        //Demo 1
        setup();
        loop();
    }

    /**
     * Sets up the server to operate, establishing threads for connections.
     *
     * @author Sean DiGirolamo
     * @since 2019-10-29
     */
    public static void setup() {
        System.out.println("BinBot server starting...");
        try {
            appConnection = new AppConnection(APP_PORT);
            System.out.println("Connecting to App...");
            appConnection.accept();
            System.out.println("Connected to App");
            appConnectionThread = new AppConnectionThread(appConnection);
            appConnectionThread.start();
            System.out.println("AppConnectionThread started");

            System.out.println("Connecting to bot");
            botConnection = new BotConnection(BOT_PORT);
            botConnection.accept();
            System.out.println("Connected to Bot");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loop that is repeated during execution of the BinBot server application.
     *
     * @author Sean Reddington, Sean DiGirolamo
     * @since 2019-10-29
     */
    public static void loop() throws IOException {
        WasteDetector wasteDetector = new WasteDetector();
        while (true) {
            while (appConnectionThread.poweredState()) {
                String json = botConnection.receive();
                Instruction instruction = new Instruction(json);
                BufferedImage img = wasteDetector.imageDetect(instruction.img());
                AppMessage appMessage = new AppMessage(true, img);
                Instruction response = new Instruction(Status.PATROL, img, null, null);
                appConnection.send(appMessage.json());
                botConnection.sendToBot(response.json());
            }
        }
    }
}
