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
    public BotConnection(int port) throws IOException {
        servSock = new ServerSocket(port);
    }

    /**
     * This method takes as input a string which will be sent over the socket to whatever client is connected to
     * it, in this case the BinBot robot
     *
     * @author Sean DiGirolamo
     * @since 2019-10-11
     */
    public void sendToBot(String s) throws IOException {
        OutputStream o = sock.getOutputStream();
        PrintWriter out = new PrintWriter(o, true);
        out.println(s);
    }

    /**
     * This method instructs the server to wait to receive a bit stream from the client. This bit stream will be converted
     * into a string and returned to the caller
     *
     * @author Sean DiGirolamo
     * @since 2019-10-11
     */
    public String receive() throws IOException {
        String retval;
        InputStream is = sock.getInputStream();
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);
        while (!br.ready()) {
        }
        retval = br.readLine();
        return retval;
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
}
