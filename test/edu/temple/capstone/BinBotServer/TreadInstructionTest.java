package edu.temple.capstone.BinBotServer;

import javafx.util.Pair;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

class TreadInstructionTest
{
	@Test
	void calcInstructionsTest() {
		List<Pair<Double, Double>> list = TreadInstruction.calcInstructions(0, 0, 0, 0);
		assertEquals(0.0, list.get(0).getKey());
		assertEquals(0.0, list.get(0).getValue());
	}
}
