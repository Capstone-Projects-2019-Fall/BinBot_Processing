package edu.temple.capstone.BinBotServer.util;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PairTest
{
	private static final double KEY = 0.0;
	private static final double VALUE = 1.1;

	private static Pair<Double, Double> pair;

	@BeforeAll
	static void constructor() {
		pair = new Pair<>(KEY, VALUE);
	}

	@Test
	void key() {
		assertEquals(KEY, pair.key());
	}

	@Test
	void value() {
		assertEquals(VALUE, pair.value());
	}
}