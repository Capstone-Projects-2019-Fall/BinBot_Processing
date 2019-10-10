package edu.temple.capstone.BinBotServer;

import java.io.InputStream;

public interface AppConnection
{
	void sendToApp(InputStream in);
	InputStream recieve();
	void listen();
}
