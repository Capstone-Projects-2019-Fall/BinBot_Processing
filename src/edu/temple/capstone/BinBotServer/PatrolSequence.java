package edu.temple.capstone.BinBotServer;

import edu.temple.capstone.BinBotServer.instructions.Movement;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents the sequence of instructions BinBot should follow during its patrol to search for trash
 *
 * @author Sean DiGirolamo
 * @version 1.0
 * @since 2019-11-12
 */
public class PatrolSequence
{
	private List<Movement> seq = new ArrayList<>();
	private int selection = 0;

	/**
	 * Returns a fully formed patrol sequence, starting just before the first movement
	 *
	 * @author Sean DiGirolamo
	 * @since 2019-11-12
	 */
	public PatrolSequence() {
//		this.add(179.0, 1.0);
//		this.add(179.0, 1.0);
//		this.add(0.0, 1.55);
		this.add(45.0, 1.0);
		this.add(45.0, 1.0);
		this.add(45.0, 1.0);
		this.add(45.0, 1.0);
		this.add(45.0, 1.0);
		this.add(45.0, 1.0);
		this.add(45.0, 1.0);
		this.add(45.0, 1.0);
		this.add(90.0, 1.0);
		this.add(0.0, 1.55);
		this.add(45.0, 1.0);
		this.add(45.0, 1.0);
		this.add(45.0, 1.0);
		this.add(45.0, 1.0);
		this.add(45.0, 1.0);
		this.add(45.0, 1.0);
		this.add(45.0, 1.0);
		this.add(45.0, 1.0);
		this.add(90.0, 1.0);
		this.add(0.0, 3.1);
		this.add(225.0, 1.0);
		this.add(225.0, 1.0);
		this.add(225.0, 1.0);
		this.add(225.0, 1.0);
		this.add(225.0, 1.0);
		this.add(225.0, 1.0);
		this.add(225.0, 1.0);
		this.add(225.0, 1.0);
		this.add(90.0, 1.0);
		this.add(0.0, 3.1);
		this.add(225.0, 1.0);
		this.add(225.0, 1.0);
		this.add(225.0, 1.0);
		this.add(225.0, 1.0);
		this.add(225.0, 1.0);
		this.add(225.0, 1.0);
		this.add(225.0, 1.0);
		this.add(225.0, 1.0);
		this.add(90.0, 1.0);
		this.add(0.0, 3.1);
		this.add(225.0, 1.0);
		this.add(225.0, 1.0);
		this.add(225.0, 1.0);
		this.add(225.0, 1.0);
		this.add(225.0, 1.0);
		this.add(225.0, 1.0);
		this.add(225.0, 1.0);
		this.add(225.0, 1.0);
		this.add(180.0, 2.2);
		this.add(225.0, 1.0);
		this.add(270.0, 1.0);
		this.add(270.0, 1.0);
		this.add(270.0, 1.0);
	}

	private void add(Double a, Double d) {
		seq.add(new Movement(a, d));
	}

	/**
	 * Returns the next movement in the patrol sequence that BinBot should follow, and moves the pointer to the next
	 * item in the sequence.
	 *
	 * @author Sean DiGirolamo
	 * @since 2019-11-12
	 */
	public Movement next() {
		Movement retval = this.seq.get(this.selection);
		this.selection++;
		if (this.selection >= this.seq.size()) {
			this.selection = 0;
		}

		return retval;
	}

	/**
	 * Resets the sequence back to its first movement
	 *
	 * @author Sean DiGirolamo
	 * @since 2019-11-12
	 */
	public void reset() {
		this.selection = 0;
	}
}
