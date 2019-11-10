package edu.temple.capstone.BinBotServer.mobileInterface;

import edu.temple.capstone.BinBotServer.connections.AppConnection;

import java.io.IOException;

public class AppConnectionThread extends Thread
{
	private AppConnection appConnection;
	private Boolean poweredState = true;
	private int port;

	public AppConnectionThread(int port) {
		super();
		this.port = port;
	}

	@Override
	public void run() {
		appConnection = new AppConnection(port);
		while (true) {
			System.out.println("Connecting to App...");
			appConnection.accept();
			System.out.println("Connected to App");

			String msgString = this.appConnection.receive();
			AppMessage appMessage = new AppMessage(msgString);
			System.out.println("From app: " + appMessage.json());
			this.poweredState = appMessage.poweredState();

			this.appConnection.close();
		}
	}

	public Boolean poweredState() {
		return this.poweredState;
	}
}
