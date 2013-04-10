package cityfind.gui;


/**
 * Title:        CityFinder (SPA Demo Applet)
 * Description:  Displays error massages on the screen.
 *
 * Copyright:    Copyright (c) 2001-2003 Chon D. Chung.
 * @author       Chon D. Chung
 * @version      Demo 0.6
 */
public interface I_MsgDisplay {
	/** Prints out the error message on the screen. */
	void printError(String errMsg);
	/** Prints the appending message on the screen. */
	void appendText(String inString);
}