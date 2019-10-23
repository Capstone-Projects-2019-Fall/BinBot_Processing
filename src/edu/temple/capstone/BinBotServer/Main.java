package edu.temple.capstone.BinBotServer;

import edu.temple.capstone.BinBotServer.connections.AppConnection;
import edu.temple.capstone.BinBotServer.connections.BotConnection;
import edu.temple.capstone.BinBotServer.instructions.Instruction;
import edu.temple.capstone.BinBotServer.instructions.Status;

import java.awt.image.BufferedImage;
import java.io.IOException;

public class Main
{
	private static AppConnection appConnection = null;
	private static BotConnection botConnection = null;
	private static boolean poweredState = true;

	public static void main(String[] args) throws IOException {
		setUp();
		loopTest();
	}

	public static void setUp() throws IOException {
		System.out.println("BinBot Server starting...");
		//System.out.println("Connecting to Mobile Application...");
		//appConnection = new AppConnection(7001);
		//appConnection.accept();
		System.out.println("Connecting to BinBot Robot...");
		botConnection = new BotConnection(7002);
		botConnection.accept();
	}

	public static void loop() throws IOException {
		while(true) {
			while(!poweredState) {
			}
			String json = botConnection.recieve();
			Instruction instruction = new Instruction(json);
			BufferedImage img = instruction.img();
			//OpenCVWrapper openCVWrapper = new OpenCVWrapper(img);
			//Instruction response = new Instruction(openCVWrapper.getMatrix());
			//botConnection.sendToBot(response.json());
		}
	}

	public static void loopTest() throws IOException {
		while(true) {
			String json = botConnection.recieve();
			System.out.println(json);
			Instruction instruction = new Instruction(Status.PATROL, null, null, null);
			botConnection.sendToBot(instruction.json());
		}
	}
}
