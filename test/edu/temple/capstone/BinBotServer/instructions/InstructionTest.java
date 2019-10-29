package edu.temple.capstone.BinBotServer.instructions;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InstructionTest
{
	private static Instruction jsonInstruction = null;
	private static Instruction objectInstruction = null;

	private static String json = "{\"status\":\"PATROL\",\"img\":\"\",\"treads\":[{\"angle\":0.0,\"distance\":0.0}],\"arms\":[{\"angle\":0.0}]}";
	private static Object[][] o = new Object[1][1];

	@BeforeAll
	static void pre() {
		jsonInstruction = new Instruction(json);
		objectInstruction = new Instruction(o);
	}

	@Test
	void json() {
		assertEquals(json, jsonInstruction.json());
	}
}