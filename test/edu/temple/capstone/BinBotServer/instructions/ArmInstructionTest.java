package edu.temple.capstone.BinBotServer.instructions;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ArmInstructionTest
{
	@Test
	void calcInstructionsTest() {
		List<Double> list = ArmInstruction.calcInstructions(0, 0, 0, 0);
		assertEquals(0.0, list.get(0));
	}
}