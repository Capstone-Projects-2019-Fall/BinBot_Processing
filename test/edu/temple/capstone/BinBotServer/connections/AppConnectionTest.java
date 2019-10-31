package edu.temple.capstone.BinBotServer.connections;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class AppConnectionTest
{
	private static final String JSON_TEST = "{\"message\":\"Hello world!\"}";
	private static AppConnection appConnection = null;

	@BeforeAll
	static void construct() throws IOException {
		appConnection = new AppConnection(7001);
	}

	@Test
	static void test() throws IOException {
		appConnection.accept();
		appConnection.send(JSON_TEST);
		String recieved = appConnection.receive();
		assertEquals(JSON_TEST, recieved);
	}
}