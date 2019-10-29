package edu.temple.capstone.BinBotServer.instructions;

import edu.temple.capstone.BinBotServer.instructions.TreadInstruction;

import edu.temple.capstone.BinBotServer.util.Pair;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.*;


class TreadInstructionTest
{
	@Test
	void calcInstructionsTest() {
		List<Pair<Double, Double>> list = TreadInstruction.calcInstructions(0, 0, 0, 0);
		assertEquals(0.0, list.get(0).key());
		assertEquals(0.0, list.get(0).value());
	}
}
