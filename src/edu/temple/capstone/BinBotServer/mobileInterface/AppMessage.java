package edu.temple.capstone.BinBotServer.mobileInterface;

import org.json.JSONObject;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Base64;

/**
 * This class is used to communicate between the BinBot mobile application and the server. It is used for both alerting
 * the Server to stop operation and for sending images to display to the app. It is sent as a json string in the format
 * {
 *     "poweredState":<Boolean>,
 *     "img":<String>,
 *     "height":<int>,
 *     "width":<int>
 * }
 * where poweredState is true when the system should continue operation and the img is a BufferedImage converted into
 * a string which will be displayed in the mobile application.
 *
 * @author Sean DiGirolamo
 * @since 2019-10-29
 */
public class AppMessage
{
	private Boolean poweredState;
	private BufferedImage img = null;
	private int height;
	private int width;

	/**
	 * This constructor takes as input a json string. It assumes that the json is properly formatted in the proper
	 * configuration and results in an AppMessage object based on the json string provided.
	 *
	 * @author Sean DiGirolamo
	 * @since 2019-10-29
	 */
	public AppMessage(String json) {
		JSONObject jsonObject = new JSONObject(json);

		this.poweredState = jsonObject.getBoolean("poweredState");
		String imgString = jsonObject.getString("img");
		if (imgString != null) {
			this.img = this.stringToBufferedImage(imgString);
		}
		this.height = jsonObject.getInt("height");
		this.width = jsonObject.getInt("width");
	}

	/**
	 * This method creates a new immutable AppMessage object based on the arguments provided.
	 *
	 * @author Sean DiGirolamo
	 * @since 2019-10-29
	 */
	public AppMessage(Boolean poweredState, BufferedImage img) {
		this.poweredState = poweredState;
		this.img = img;
		if (img != null) {
			this.height = img.getHeight();
			this.width = img.getWidth();
		} else {
			this.height = -1;
			this.width = -1;
		}
	}

	/**
	 * This method returns the AppMessage class a a json string which can be sent to the BinBot Mobile Application.
	 *
	 * @author Sean DiGirolamo
	 * @since 2019-10-29
	 */
	public String json() {
		StringBuilder retval = new StringBuilder("{\"poweredState\":")
			.append(this.poweredState.toString())
			.append(",")
			.append("\"img\":" + "\"");

		if (this.img != null) {
			retval.append(bufferedImageToString(img));
		}

		retval.append("\",")
			.append("\"height\":").append(this.height).append(",")
			.append("\"width\":").append(this.width).append("}");

		return retval.toString();
	}

	/**
	 * This method returns the value of the poweredState boolean contained within the AppMessage
	 *
	 * @author Sean DiGirolamo
	 * @since 2019-10-29
	 */
	public Boolean poweredState() {
		return this.poweredState;
	}

	/**
	 * This method returns the Buffered img contained within the AppMessage
	 *
	 * @author Sean DiGirolamo
	 * @since 2019-10-21
	 */
	public BufferedImage img() {
		return this.img;
	}

	private BufferedImage stringToBufferedImage(String s) {
		BufferedImage retval = null;
		byte[] bytes = Base64.getDecoder().decode(s);
		try {
			retval = ImageIO.read(new ByteArrayInputStream(bytes));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return retval;
	}

	private String bufferedImageToString(BufferedImage bi) {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {
			ImageIO.write(bi, "jpg", out);
		} catch (Exception e) {
			e.printStackTrace();
		}
		byte[] byteArray = out.toByteArray();
		return Base64.getEncoder().encodeToString(byteArray);

	}
}
