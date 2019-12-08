package edu.temple.capstone.BinBotServer;

import edu.temple.capstone.BinBotServer.connections.BotConnection;
import edu.temple.capstone.BinBotServer.instructions.Instruction;
import edu.temple.capstone.BinBotServer.mobileInterface.AppConnectionThread;
import org.opencv.core.Core;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;


/**
 * JFrame class to display video feed including the bounding boxes of the object detector results. Using the
 * VideoCap class, the instance's thread captures a BufferedImage which is sent to the processing server. The server
 * will return the BufferedImage with the resulting bounding boxes.
 *
 * @author Sean Reddington
 * @version 1.0
 * @since 2019-10-25
 */
public class DemoClient extends JFrame {

    // Loading the OpenCV core library
    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    private JPanel contentPane;
    private static final int BOT_PORT = 7001;
    private final static int APP_PORT = 7002;
    private BotConnection botConnection;
    private static AppConnectionThread appConnectionThread;
    public WasteDetector detector;
    public PatrolSequence patrolSequence;

    /**
     * Main class to test the image capturing and display lifecycle of BinBot. The Robot's Raspberry Pi software will
     * Capture an image, then send a json payload to the server containing the image. The server then processes the
     * payload and displays the image.
     *
     * @author Sean Reddington
     * @version 1.0
     * @since 2019-12-01
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                DemoClient frame = new DemoClient();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Configures connection to the BinBot processing server via the ServerConnection class.
     *
     * @author Sean Reddington
     * @version 1.0
     * @since 2019-12-01
     */
    public void setup() throws IOException {
        System.out.println("BinBot server starting...");
        this.detector = new WasteDetector();
        this.patrolSequence = new PatrolSequence();

        // Android app connection
        appConnectionThread = new AppConnectionThread(APP_PORT);
        appConnectionThread.start();
        System.out.println("AppConnectionThread started");

        // BinBot connection
        this.botConnection = new BotConnection(BOT_PORT);
        System.out.println("Waiting for connections...");
        botConnection.accept();
        System.out.println("Connection established!");
    }

    /**
     * Configures JPanel for video feed display and starts thread.
     *
     * @author Sean Reddington
     * @version 1.0
     * @since 2019-12-01
     */
    private DemoClient() {
        try {
            setup();
        } catch (IOException e) {
            e.printStackTrace();
        }
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(0, 0, 1280, 720);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(0, 0, 0, 0));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        new MyThread().start();

    }

    /**
     * Receives a payload from BinBot's Raspberry Pi via the BotConnection instance.
     *
     * @author Sean Reddington
     * @version 1.0
     * @since 2019-12-01
     */
    private BufferedImage processPayload() {
        System.out.println("Attempting to receive string from connection...");
        String jsonReceive = this.botConnection.receive();
        //        System.out.println("received: " + jsonReceive);
        System.out.println("Received payload from BinBot");
        Instruction fromBinBot = new Instruction(jsonReceive);
//        BufferedImage processed_image = instruction.getImage();
        BufferedImage processed_image = this.detector.imageDetect(fromBinBot.getImage());
        long latency = detector.getMostRecentLatency();
        System.out.println("latency: " + Long.toBinaryString(latency) + " ms");
        Instruction toBinBot = fromBinBot.generateInstruction(this.detector, this.patrolSequence);
//        BufferedImage processed_image = this.detector.getBufferedImage();

//        //Write image from BinBot to file
//        try {
//            File outputFile = new File("res/result.jpg");
//            ImageIO.write(processed_image, "jpg", outputFile);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        System.out.println("Sending payload to BinBot");
        this.botConnection.send(toBinBot.json());
        this.botConnection.close();
        return processed_image;
    }

    /**
     * After receiving a payload from BinBot and processing the instructions; draws a modified BufferedImage
     * containing bounding boxes of any object detected from the machine learning model.
     *
     * @author Sean Reddington
     * @version 1.0
     * @since 2019-12-01
     */
    @Override
    public void paint(Graphics g) {
        System.out.println(appConnectionThread.poweredState());
        while (appConnectionThread.poweredState()) {
            g = contentPane.getGraphics();
            BufferedImage image = processPayload();
            g.drawImage(image, 0, 0, this); // Draw the new image to the JFrame
            botConnection.accept();
        }

//        // Without mobile app
//        g = contentPane.getGraphics();
//        BufferedImage image = processPayload();
//        g.drawImage(image, 0, 0, this); // Draw the new image to the JFrame
//        botConnection.accept();

    }

    /**
     * Thread class to handle the JFrame
     *
     * @author Sean Reddington
     * @version 1.0
     * @since 2019-12-01
     */
    class MyThread extends Thread {
        @Override
        public void run() {
            for (; ; ) {
                repaint();
                try {
                    Thread.sleep(30);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
