package edu.temple.capstone.BinBotServer.instructions;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InstructionTest
{
	private Instruction jsonInstruction = null;
	private Instruction objectInstruction = null;

	private String json = "{\"status\":\"PATROL\",\"img\":\"temporary\",\"treads\":[{\"angle\":0.0,\"distance\":0.0}],\"arms\":[{\"angle\":0.0}]}";
	private Object[][] o = new Object[1][1];

	@BeforeAll
	void pre() {
		this.jsonInstruction = new Instruction(this.json);
		this.objectInstruction = new Instruction(o);
	}

	@Test
	void json() {
		assertEquals(this.json, this.jsonInstruction.json());
	}
}