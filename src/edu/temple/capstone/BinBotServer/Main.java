package edu.temple.capstone.BinBotServer;

import edu.temple.capstone.BinBotServer.instructions.TreadInstruction;
import edu.temple.capstone.BinBotServer.mobileInterface.AppConnectionThread;
import edu.temple.capstone.BinBotServer.connections.BotConnection;
import edu.temple.capstone.BinBotServer.instructions.Instruction;
import edu.temple.capstone.BinBotServer.instructions.Status;
import edu.temple.capstone.BinBotServer.instructions.Movement;

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
     * @since 2019-10-29
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
    	Instruction retval = null;
    	Status status = null;
    	Movement movement = null;

    	switch (prev.status()) {
		case PATROL:
			wasteDetector.loadImage(prev.img());
			if (wasteDetector.containsWaste()) {
				status = Status.ANGLE;
				movement = TreadInstruction.calcInstructions(wasteDetector.x(), wasteDetector.y(), wasteDetector.w(), wasteDetector.h());
				patrolSequence.reset();
			} else {
				status = Status.PATROL;
				movement = patrolSequence.next();
			}
			break;
		case ANGLE:
			movement = TreadInstruction.calcInstructions(wasteDetector.x(), wasteDetector.y(), wasteDetector.w(), wasteDetector.h());
			if (movement.angle() == 0.0) {
				status = Status.RETRIEVE;
			} else {
				status = Status.ANGLE;
			}
			break;
		default:
			break;
		}

		List<Movement> treads = new ArrayList<>();
    	treads.add(movement);

		return new Instruction(status, null, treads, null);
	}
}
