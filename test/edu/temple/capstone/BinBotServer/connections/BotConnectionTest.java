package edu.temple.capstone.BinBotServer.connections;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class BotConnectionTest
{
	private static final String JSON_TEST = "{\"message\":\"Hello world!\"}";
	private static BotConnection botConnection = null;

	@BeforeAll
	static void construct() throws IOException {
		botConnection = new BotConnection(7001);
	}

	@Test
	static void test() throws IOException {
		botConnection.accept();
		botConnection.send(JSON_TEST);
		String received = botConnection.receive();
		assertEquals(JSON_TEST, received);
	}
}