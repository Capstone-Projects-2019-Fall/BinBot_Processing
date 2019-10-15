package edu.temple.capstone.BinBotServer;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;

class AppConnectionTest
{
	private static AppConnection appConnection = null;

	@BeforeAll
	static void construct() throws IOException {
		appConnection = new AppConnection(7001);
	}

	@Test
	void sendToApp() throws IOException {
		//appConnection.sendToApp("");
	}

	@Test
	void recieve() throws IOException {
		//assertEquals("", appConnection.recieve());
	}

	@Test
	void initiate() throws IOException {
		//appConnection.accept();
	}
}