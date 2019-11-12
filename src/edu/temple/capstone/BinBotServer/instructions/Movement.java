package edu.temple.capstone.BinBotServer.instructions;

public class Movement
{
	private Double key;
	private Double value;

	public Movement(Double key, Double value) {
		this.key = key;
		this.value = value;
	}

	public Double angle() {
		return key;
	}

	public Double distance() {
		return value;
	}
}
