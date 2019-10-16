package edu.temple.capstone.BinBotServer.connections;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * The BotConnection class represents the network connection between the BinBot Robot and the BinBot
 * Server. Its methods allow the server to initiate and wait for a connection to be established, and then once it is
 * established, data can be sent back and forth to the Robot over a socket
 *
 *
 *
 * @author Sean DiGirolamo
 * @version 1.0
 * @since   2019-10-13
 */
public class BotConnection
{
	private ServerSocket servSock;
	private Socket sock = null;
	private DataOutputStream out = null;
	private DataInputStream in = null;

	/**
	 * This constructor creates a BotConnection object which can be used for initiating and communicating with
	 * the BinBot Robot. It takes the port number the server should listen for connections on as its only
	 * argument.
	 *
	 *
	 *
	 * @author  Sean DiGirolamo
	 * @since   2019-10-16
	 */
	public BotConnection(int port) throws IOException {
		servSock = new ServerSocket(port);
	}

	/**
	 * This method takes as input a string which will be sent over the socket to whatever client is connected to
	 * it, in this case the BinBot robot
	 *
	 *
	 *
	 * @author  Sean DiGirolamo
	 * @since   2019-10-11
	 */
	public void sendToBot(String s) throws IOException {
		out.writeBytes(s);
	}

	/**
	 * This method instructs the server to wait to recieve a bitstream from the client. This bitstream will be converted
	 * into a string and returned to the caller
	 *
	 *
	 *
	 * @author  Sean DiGirolamo
	 * @since   2019-10-11
	 */
	public String recieve() throws IOException {
		return in.readUTF();
	}

	/**
	 * This method should be the very first method called by this object. It initiates the socket connection, and doesn't
	 * complete until a client has connected. This must happen before any other methods or data tranfers can complete
	 *
	 *
	 *
	 * @author  Sean DiGirolamo
	 * @since   2019-10-16
	 */
	public void accept() throws IOException {
		sock = servSock.accept();
		out = new DataOutputStream(sock.getOutputStream());
		in = new DataInputStream(new BufferedInputStream(sock.getInputStream()));
	}
}
