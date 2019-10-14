package edu.temple.capstone.BinBotServer;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * <h1>AppConnection</h1>
 * The AppConnection class represents the network connection between the BinBot Mobile Application and the BinBot
 * Server. Its methods allow the server to initiate and wait for a connection to be established, and then once it is
 * established, data can be sent back and forth to the mobile application over a socket.
 *
 *
 *
 * @author Sean DiGirolamo
 * @version 1.0
 * @since   2019-10-13
 */
public interface AppConnection
{
	/**
	 * <h1>sendToApp(InputStream in)</h1>
	 * This method takes as input an InputStream which will be sent over the socket to whatever client is connected to
	 * it, in this case the mobile application
	 *
	 *
	 *
	 * @author  Sean DiGirolamo
	 * @version 1.0
	 * @since   2019-10-11
	 */
	void sendToApp(InputStream in);

	/**
	 * <h1>recieve()</h1>
	 * This method instructs the server to wait to recieve a bitstream from the client. This bitstream will be returned
	 * to the caller as an outputStream
	 *
	 *
	 *
	 * @author  Sean DiGirolamo
	 * @version 1.0
	 * @since   2019-10-11
	 */
	OutputStream recieve();

	/**
	 * <h1>initiate()</h1>
	 * This method should be the very first method called by this object. It initiates the socket connection, and doesn't
	 * complete until a client has connected. This must happen before any other methods or data tranfers can complete
	 *
	 *
	 *
	 * @author  Sean DiGirolamo
	 * @version 1.0
	 * @since   2019-10-11
	 */
	void initiate();
}
