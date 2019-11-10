package edu.temple.capstone.BinBotServer.connections;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * The BotConnection class represents the network connection between the BinBot Robot and the BinBot
 * Server. Its methods allow the server to initiate and wait for a connection to be established, and then once it is
 * established, data can be sent back and forth to the Robot over a socket
 *
 * @author Sean DiGirolamo
 * @version 1.0
 * @since 2019-10-13
 */
public class BotConnection {

    private ServerSocket servSock;
    private Socket sock;

    /**
     * This constructor creates a BotConnection object which can be used for initiating and communicating with
     * the BinBot Robot. It takes the port number the server should listen for connections on as its only
     * argument.
     *
     * @author Sean DiGirolamo
     * @since 2019-10-16
     */
    public BotConnection(int port) {
		try {
			servSock = new ServerSocket(port);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

    /**
     * This method takes as input a string which will be sent over the socket to whatever client is connected to
     * it, in this case the BinBot robot
     *
     * @author Sean DiGirolamo
     * @since 2019-10-11
     */
    public void send(String s) {
		try {
			this.sendLength(s);
			this.stuffString(s);
			//this.waitACK();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

    private void sendLength(String s) throws  IOException {
    	this.stuffInt(s.length());
    	//this.waitACK();
	}

	private void sendACK() throws IOException {
		byte[] b = new byte[1];
		b[0] = 1;
		this.stuffBytes(b);
	}

	private void stuffString(String s) throws IOException {
		OutputStream o = sock.getOutputStream();
		PrintWriter out = new PrintWriter(o, true);
		o.flush();
		out.flush();
		out.println(s);
		o.flush();
		out.flush();
	}

    private void stuffInt(int i) throws IOException {
    	OutputStream o = sock.getOutputStream();
    	DataOutputStream out = new DataOutputStream(o);
    	o.flush();
    	out.flush();
    	out.write(i);
		o.flush();
		out.flush();
	}

	private void stuffBytes(byte[] b) throws IOException {
    	OutputStream o = sock.getOutputStream();
    	o.flush();
    	o.write(b);
    	o.flush();
	}

    /**
     * This method instructs the server to wait to receive a bit stream from the client. This bit stream will be converted
     * into a string and returned to the caller
     *
     * @author Sean DiGirolamo
     * @since 2019-10-11
     */
    public String receive() {
		String retval = null;
		try {
			retval = pull();
			this.sendACK();
		} catch (IOException e) {
			e.printStackTrace();

		}

        return retval;
    }

    private String pull() throws IOException {
		String retval;
		InputStream is = sock.getInputStream();
		InputStreamReader isr = new InputStreamReader(is);
		BufferedReader br = new BufferedReader(isr);
		while (!br.ready()) {
		}
		retval = br.readLine();
		return retval;
	}

	private void waitACK() throws IOException {
    	this.pull();
	}

    /**
     * This method should be the very first method called by this object. It initiates the socket connection, and doesn't
     * complete until a client has connected. This must happen before any other methods or data tranfers can complete
     *
     * @author Sean DiGirolamo
     * @since 2019-10-16
     */
    public void accept() {
        try {
            sock = servSock.accept();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

	/**
	 * This method closes the connection, but still allows the server to get more connections with accept.
	 *
	 * @author Sean DiGirolamo
	 * @since 2019-11-10
	 */
	public void close() {
		try {
			this.sock.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
