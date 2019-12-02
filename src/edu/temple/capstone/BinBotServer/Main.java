package edu.temple.capstone.BinBotServer;

import edu.temple.capstone.BinBotServer.data.Prediction;
import edu.temple.capstone.BinBotServer.instructions.TreadInstruction;
import edu.temple.capstone.BinBotServer.mobileInterface.AppConnectionThread;
import edu.temple.capstone.BinBotServer.connections.BotConnection;
import edu.temple.capstone.BinBotServer.instructions.Instruction;
import edu.temple.capstone.BinBotServer.instructions.Status;
import edu.temple.capstone.BinBotServer.instructions.Movement;
import edu.temple.capstone.BinBotServer.mobileInterface.AppMessage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Main class to run the BinBot processing server.
 *
 * @author Sean Reddington
 * @version 1.0
 * @since 2019-10-25
 */
public class Main {
    private final static int BOT_PORT = 7001;
    private final static int APP_PORT = 7002;

    private static AppConnectionThread appConnectionThread;

    private static BotConnection botConnection = null;

    private static PatrolSequence patrolSequence = new PatrolSequence();

    private static WasteDetector wasteDetector = new WasteDetector();

    /**
     * Main method to instantiate and loop through the BinBot server application.
     *
     * @author Sean Reddington
     * @since 2019-10-25
     */
    public static void main(String[] args) throws IOException {
        BufferedImage img = ImageIO.read(new File("res/longcat.jpg"));
        AppMessage a = new AppMessage(true, img);
        System.out.println(a.json());
        setup();
        loop();
    }

    /**
     * Sets up the server to operate, establishing threads for connections.
     *
     * @author Sean DiGirolamo
     * @since 2019-10-29
     */
    public static void setup() {
        System.out.println("BinBot server starting...");

        appConnectionThread = new AppConnectionThread(APP_PORT);
        appConnectionThread.start();
        System.out.println("AppConnectionThread started");

        botConnection = new BotConnection(BOT_PORT);
    }

    /**
     * Loop that is repeated during execution of the BinBot server application.
     *
     * @author Sean Reddington, Sean DiGirolamo
     * @since 2019-10-2
     */
    public static void loop() throws IOException {
        while (true) {
            System.out.println(appConnectionThread.poweredState());
            while (appConnectionThread.poweredState()) {
                botConnection.accept();

                String json = botConnection.receive();

                Instruction fromBot = new Instruction(json);
                Instruction response = generateInstruction(fromBot);

                botConnection.send(response.json());
                botConnection.close();
                System.out.println("Sent a message to BinBot");
            }
        }
    }

    public static Instruction generateInstruction(Instruction prev) {
        Status status = null;
        List<Movement> treads = new ArrayList<>();
        Movement movement = null;

        List<Prediction> preds = wasteDetector.getPredictions();
        if (preds == null || preds.isEmpty()) {
            status = Status.PATROL;
            treads.add(patrolSequence.next());
        } else {
            Prediction p = preds.get(0);
            treads = TreadInstruction.calcInstructions(p.getUpperLeftX(), p.getCenterX(),
                    p.getUpperLeftY(), p.getCenterY(),
                    p.getWidth(), p.getHeight(),
                    p.getParentImageWidth(), p.getParentImageHeight(), prev.distance
            );
            movement = treads.get(0);
            if (movement.angle() == 0.0 && movement.distance() == 1.0) { // in range of arm
                status = Status.RETRIEVE;
            } else {
                status = Status.MOVE;
            }

            patrolSequence.reset();
        }

        return new Instruction(status, wasteDetector.getBufferedImage(), treads, null);
    }
}
