package edu.temple.capstone.BinBotServer.mobileInterface;

import edu.temple.capstone.BinBotServer.connections.AppConnection;

import java.io.IOException;

public class AppConnectionThread extends Thread
{
	private AppConnection appConnection;
	private Boolean poweredState = true;

	public AppConnectionThread(AppConnection appConnection) {
		super();
		this.appConnection = appConnection;
	}

	@Override
	public void run() {
		this.loop();
	}

	private void loop() {
		while (this.appConnection.connected()) {
			try {
				String msgString = this.appConnection.receive();
				AppMessage appMessage = new AppMessage(msgString);
				this.poweredState = appMessage.poweredState();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public Boolean poweredState() {
		return this.poweredState;
	}
}
