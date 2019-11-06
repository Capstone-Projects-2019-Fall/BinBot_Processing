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
		this.setup();
		while (true) {
			try {
				this.loop();
			} catch (IOException e) {
				this.setup();
			}
		}
	}

	public void setup() {
		System.out.println("Connecting to App...");
		try {
			appConnection = new AppConnection(port);
			appConnection.accept();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Connected to App");
	}

	private void loop() throws IOException {
		while (true) {
			String msgString = this.appConnection.receive();
			AppMessage appMessage = new AppMessage(msgString);
			this.poweredState = appMessage.poweredState();
		}
	}

	public AppConnection appConnection() {
		return this.appConnection;
	}

	public Boolean poweredState() {
		return this.poweredState;
	}
}
