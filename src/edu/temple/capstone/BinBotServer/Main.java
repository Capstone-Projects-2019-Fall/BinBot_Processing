package edu.temple.capstone.BinBotServer;

import edu.temple.capstone.BinBotServer.connections.AppConnection;
import edu.temple.capstone.BinBotServer.connections.BotConnection;

import java.io.IOException;

/**
 * Main class to run the BinBot processing server.
 *
 * @author Sean Reddington
 * @version 1.0
 * @since 2019-10-25
 */
public class Main {
    final static int BOT_PORT = 7001;
    private static AppConnection appConnection = null;
    private static BotConnection botConnection = null;
    private static boolean poweredState = true;

    /**
     * Main method to instantiate and loop through the BinBot server application.
     *
     * @author Sean Reddington
     * @version 1.0
     * @since 2019-10-25
     */
    public static void main(String[] args) throws IOException {
        //Demo 1
        Demo.setUp();
        loop();
    }

    /**
     * Loop that is repeated during execution of the BinBot server application.
     *
     * @author Sean Reddington
     * @version 1.0
     * @since 2019-10-25
     */
    public static void loop() throws IOException {
        while (true) {
            Demo.openCVTest();

//            while (!poweredState) {
//            }
//            String json = botConnection.receive();
//            Instruction instruction = new Instruction(json);
//            BufferedImage img = instruction.img();
            //OpenCVWrapper openCVWrapper = new OpenCVWrapper(img);
            //Instruction response = new Instruction(openCVWrapper.getMatrix());
            //botConnection.sendToBot(response.json());
        }
    }
}
