package edu.temple.capstone.BinBotServer;

import edu.temple.capstone.BinBotServer.connections.AppConnection;
import edu.temple.capstone.BinBotServer.connections.BotConnection;

import java.io.IOException;

public class Main {
    final static int BOT_PORT = 7001;
    private static AppConnection appConnection = null;
    private static BotConnection botConnection = null;
    private static boolean poweredState = true;

    public static void main(String[] args) throws IOException {
        //Demo 1
        Demo.setUp();
        loop();
    }

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
