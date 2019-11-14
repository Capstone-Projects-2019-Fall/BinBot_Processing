package edu.temple.capstone.BinBotServer.util;

import edu.temple.capstone.BinBotServer.instructions.Movement;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PairTest
{
	private static final double KEY = 0.0;
	private static final double VALUE = 1.1;

	private static Movement movement;

	@BeforeAll
	static void constructor() {
		movement = new Movement(KEY, VALUE);
	}

	@Test
	void key() {
		assertEquals(KEY, movement.angle());
	}

	@Test
	void value() {
		assertEquals(VALUE, movement.distance());
	}
}