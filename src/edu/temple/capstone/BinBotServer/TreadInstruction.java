package edu.temple.capstone.BinBotServer;

import javafx.util.Pair;

import java.util.List;

public interface TreadInstruction
{
	List<Pair<Double, Double>> calcInstructions(double x, double y, double w, double h);
}
