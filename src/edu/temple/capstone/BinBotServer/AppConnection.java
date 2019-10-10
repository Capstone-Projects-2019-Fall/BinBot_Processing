package edu.temple.capstone.BinBotServer;

import java.io.InputStream;

public interface AppConnection
{
	void send(InputStream in);
	InputStream recieve();
	void listen();
}
