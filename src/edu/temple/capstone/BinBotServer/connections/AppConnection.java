package edu.temple.capstone.BinBotServer.connections;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * The AppConnection class represents the network connection between the BinBot Mobile Application and the BinBot
 * Server. Its methods allow the server to initiate and wait for a connection to be established, and then once it is
 * established, data can be sent back and forth to the mobile application over a socket.
 *
 * @author Sean DiGirolamo
 * @version 1.0
 * @since 2019-10-13
 */
public class AppConnection {
    private ServerSocket servSock;
    private Socket sock = null;

    /**
     * This constructor creates an AppConnection object which can be used for initiating and communicating with
     * the BinBot mobile application. It takes the port number the server should listen for connections on as its only
     * argument.
     *
     * @author Sean DiGirolamo
     * @since 2019-10-11
     */
    public AppConnection(int port) {
        try {
            servSock = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method takes as input a string which will be sent over the socket to whatever client is connected to
     * it, in this case the mobile application
     *
     * @author Sean DiGirolamo
     * @since 2019-10-11
     */
    public void send(String s) {
        try {
            this.stuffString(s);
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    /**
     * This method instructs the server to wait to receive a bit stream from the client. This bit stream will be converted
     * to a string and returned to the caller
     *
     * @author Sean DiGirolamo
     * @since 2019-10-11
     */
    public String receive() {
        String retval = null;
        try {
            retval = this.pull();
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

    /**
     * This method should be the very first method called by this object. It initiates the socket connection, and doesn't
     * complete until a client has connected. This must happen before any other methods or data transfers can complete
     *
     * @author Sean DiGirolamo
     * @since 2019-10-14
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
