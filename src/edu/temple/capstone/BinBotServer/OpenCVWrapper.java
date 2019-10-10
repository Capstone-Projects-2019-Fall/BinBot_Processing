package edu.temple.capstone.BinBotServer;

import java.awt.image.BufferedImage;

/******************************************************************************
 * Filename:		OpenCVWrapper.java
 * Creation Date:	Wed 09 Oct 2019 05:06:28 PM EDT
 * Last Modified:	Wed 09 Oct 2019 05:11:57 PM EDT
 * Purpose:			Wrapper for handling openCV logic. Created by providing an
 *                  and used to locate identified waste.
 ******************************************************************************/

public interface OpenCVWrapper
{
	BufferedImage getAppImg();
	Object[][] getMatrix();
}
