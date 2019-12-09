package edu.temple.capstone.BinBotServer;

import com.google.gson.Gson;
import edu.temple.capstone.BinBotServer.instructions.Instruction;
import edu.temple.capstone.BinBotServer.instructions.Movement;
import edu.temple.capstone.BinBotServer.instructions.Status;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GsonTest
{
	public static void main(String args[]) throws IOException
	{
		BufferedImage img = ImageIO.read(new File("C:/Users/Sean/Desktop/baby_yoda.jpg"));
		List<Movement> treads = new ArrayList<>();
		treads.add(new Movement(1.0, 1.0));
		Instruction i = new Instruction(Status.PATROL, img, treads, null);
		Gson gson = new Gson();
		String json = i.json();
		System.out.println(json);

		Instruction j = new Instruction(json);
		System.out.println(j.json());
	}
}
