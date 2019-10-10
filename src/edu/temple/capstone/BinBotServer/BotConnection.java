package edu.temple.capstone.BinBotServer;

import java.io.InputStream;

public interface BotConnection
{
	void send(InputStream in);
	InputStream recieve();
	void listen();
}
