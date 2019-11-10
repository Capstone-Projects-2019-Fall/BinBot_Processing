package edu.temple.capstone.BinBotServer;

import edu.temple.capstone.BinBotServer.mobileInterface.AppConnectionThread;
import edu.temple.capstone.BinBotServer.connections.BotConnection;
import edu.temple.capstone.BinBotServer.instructions.Instruction;
import edu.temple.capstone.BinBotServer.instructions.Status;
import edu.temple.capstone.BinBotServer.util.Pair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

    private static BotConnection botConnection = null;

    /**
     * Main method to instantiate and loop through the BinBot server application.
     *
     * @author Sean Reddington
     * @since 2019-10-25
     */
    public static void main(String[] args) throws IOException {
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

		appConnectionThread = new AppConnectionThread(APP_PORT);
		appConnectionThread.start();
		System.out.println("AppConnectionThread started");

		botConnection = new BotConnection(BOT_PORT);
    }

    /**
     * Loop that is repeated during execution of the BinBot server application.
     *
     * @author Sean Reddington, Sean DiGirolamo
     * @since 2019-10-29
     */
    public static void loop() throws IOException {
        //WasteDetector wasteDetector = new WasteDetector();
        while (true) {
            while (appConnectionThread.poweredState()) {

				System.out.println("Connecting to bot");
            	botConnection.accept();
				System.out.println("Connected to Bot");
				System.out.println();

                String json = botConnection.receive();

                //Instruction instruction = new Instruction(json);
                //BufferedImage img = wasteDetector.imageDetect(instruction.img());
                List<Pair<Double, Double>> treads = new ArrayList<>();
                treads.add(new Pair<>(90.0, 0.5));

                Instruction response = new Instruction(Status.PATROL, null, treads, null);

                botConnection.send(response.json());

                botConnection.close();
            }
        }
    }
}
