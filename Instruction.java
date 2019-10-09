/******************************************************************************
 * Filename:		Instruction.java
 * Author:		Sean DiGirolamo
 * Email:		tug70176@temple.edu
 * Creation Date:	Wed 09 Oct 2019 05:06:28 PM EDT
 * Last Modified:	Wed 09 Oct 2019 05:11:57 PM EDT
 * Purpose:		Interface for the Instruction class in BinBot Server.
 *			This class is in charge of taking a matrix indicating
 *			the location of identified waste in an image (from
 *			opencv) and returning a json String that can be sent to
 *			and interpreted by BinBot to properly navigate and
 *			retrieve that trash.
 ******************************************************************************/

public interface Instruction
{
	public String getJsonInstructions(Object[][] matrix);
}
