package edu.temple.capstone.BinBotServer;

import edu.temple.capstone.BinBotServer.connections.AppConnection;
import edu.temple.capstone.BinBotServer.connections.BotConnection;

import java.io.IOException;

public class Main
{
	private static AppConnection appConnection = null;
	private static BotConnection botConnection = null;
	private static boolean poweredState = false;

	public static void main(String[] args) throws IOException {
		setUp();
		loop();
	}

	public static void setUp() throws IOException {
		System.out.println("BinBot Server starting...");
		System.out.println("Connecting to Mobile Application...");
		appConnection = new AppConnection(7001);
		appConnection.accept();
		System.out.println("Connecting to BinBot Robot...");
		botConnection = new BotConnection(7002);
		botConnection.accept();
	}

	public static void loop() throws IOException {
		while(true) {
			while(!poweredState) {
			}
			String json = botConnection.recieve();
			// parse json
		}
	}
}
