package edu.temple.capstone.BinBotServer;

import edu.temple.capstone.BinBotServer.connections.AppConnection;
import edu.temple.capstone.BinBotServer.connections.BotConnection;
import edu.temple.capstone.BinBotServer.instructions.Instruction;
import edu.temple.capstone.BinBotServer.instructions.Status;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Main
{
	final static int BOT_PORT = 7001;
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
		botConnection = new BotConnection(BOT_PORT);
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
		String path = "C:/Users/Sean/Desktop/Server/";
		String sendFilename = "img.jpg";
		String recieveFilename = "out.jpg";

		System.out.println("Reading file " + path + sendFilename);
		BufferedImage img = ImageIO.read(new File(path + sendFilename));
		Instruction instrSend = new Instruction(Status.PATROL, img, null, null);
		System.out.println("Attempting to send: " + instrSend.json());
		botConnection.sendToBot(instrSend.json());
		System.out.println("Sent");

		System.out.println("Attempting to recieve");
		String jsonRecieve = botConnection.recieve();
		System.out.print("recieved: " + jsonRecieve);
		System.out.println("Saving image as: " + path + recieveFilename);
		Instruction instrRecieve = new Instruction(jsonRecieve);
		ImageIO.write(instrRecieve.img(), "jpg", new File(path + recieveFilename));
		System.out.println("saved!");
	}
}
