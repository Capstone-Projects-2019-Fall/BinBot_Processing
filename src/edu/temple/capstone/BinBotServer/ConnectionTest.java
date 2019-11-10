package edu.temple.capstone.BinBotServer;

import edu.temple.capstone.BinBotServer.connections.BotConnection;
import edu.temple.capstone.BinBotServer.instructions.Instruction;

import java.io.IOException;

public class ConnectionTest
{
	private static final int PORT = 7001;

	public static void main(String[] args) {
		BotConnection connection = new BotConnection(PORT);

		System.out.println("Waiting for connections...");
		connection.accept();
		System.out.println("Connection established!");

		System.out.println("Attempting to receive string from connection...");
		String rec = connection.receive();
		System.out.println("Received " + rec + ".");

		Instruction irec = new Instruction(rec);

		System.out.println("Attempting to send string " + irec.json() + " back to client...");
		connection.send(irec.json());
		System.out.println("Successfully sent string to client!");

		System.out.println("Closing connection...");
		System.out.println("Test was SUCCESSFUL!");
	}
}
