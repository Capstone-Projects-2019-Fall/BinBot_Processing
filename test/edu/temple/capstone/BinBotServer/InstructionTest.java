package edu.temple.capstone.BinBotServer;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InstructionTest
{

	@Test
	void getJsonInstructions() {
		assertEquals("", Instruction.getJsonInstructions(new Object[5][5]));
	}
}