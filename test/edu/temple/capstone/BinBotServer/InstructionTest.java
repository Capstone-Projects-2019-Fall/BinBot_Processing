package edu.temple.capstone.BinBotServer;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InstructionTest
{

	@Test
	void getJsonInstructions() {
		assertEquals("{\"treads\":[{\"distance\":0,\"angle\":0}],\"img\":\"\",\"arms\":[{\"distance\":0,\"angle\":0}],\"status\":\"PATROL\"}", Instruction.getJsonInstructions(new Object[5][5]));
	}
}