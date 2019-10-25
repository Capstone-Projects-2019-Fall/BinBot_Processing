package edu.temple.capstone.BinBotServer.instructions;

import edu.temple.capstone.BinBotServer.instructions.TreadInstruction;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.*;


class TreadInstructionTest
{
	@Test
	void calcInstructionsTest() {
		List<Map.Entry<Double, Double>> list = TreadInstruction.calcInstructions(0, 0, 0, 0);
		assertEquals(0.0, list.get(0).getKey());
		assertEquals(0.0, list.get(0).getValue());
	}
}
