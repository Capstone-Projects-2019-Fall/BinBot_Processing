package edu.temple.capstone.BinBotServer;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
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
public class AppConnection
{
	private ServerSocket servSock;
	private Socket sock = null;
	private DataOutputStream out = null;
	private DataInputStream in = null;

	/**
	 * This constructor creates an AppConnection object which can be used for initiating and communicating with
	 * the BinBot mobile application. It takes the port number the server should listen for connections on as its only
	 * argument.
	 *
	 *
	 *
	 * @author  Sean DiGirolamo
	 * @since   2019-10-11
	 */
	public AppConnection(int port) throws IOException {
		servSock = new ServerSocket(port);
	}

	/**
	 * This method takes as input a string which will be sent over the socket to whatever client is connected to
	 * it, in this case the mobile application
	 *
	 *
	 *
	 * @author  Sean DiGirolamo
	 * @since   2019-10-11
	 */
	public void sendToApp(String s) throws IOException {
		out.writeBytes(s);
	}

	/**
	 * This method instructs the server to wait to recieve a bitstream from the client. This bitstream will be converted
	 * to a string and returned to the caller
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
	 * complete until a client has connected. This must happen before any other methods or data transfers can complete
	 *
	 *
	 *
	 * @author  Sean DiGirolamo
	 * @since   2019-10-11
	 */
	public void accept() throws IOException {
		sock = servSock.accept();
		out = new DataOutputStream(sock.getOutputStream());
		in = new DataInputStream(new BufferedInputStream(sock.getInputStream()));
	}
}
