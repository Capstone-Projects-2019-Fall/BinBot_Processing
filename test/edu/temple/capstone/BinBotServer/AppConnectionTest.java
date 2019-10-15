package edu.temple.capstone.BinBotServer;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AppConnectionTest
{
	AppConnection appConnection = new AppConnection();

	@Test
	void sendToApp() {
		appConnection.sendToApp("");
	}

	@Test
	void recieve() {
		assertEquals("", appConnection.recieve());
	}

	@Test
	void initiate() {
		appConnection.initiate();
	}
}