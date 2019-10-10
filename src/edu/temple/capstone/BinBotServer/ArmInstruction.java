package edu.temple.capstone.BinBotServer;

import java.util.List;

public interface ArmInstruction
{
	List<Double> calcInstructions(double x, double y, double w, double h);
}
