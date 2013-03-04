package cityfind.gui;

import cityfind.CityFinderJWS;

import java.awt.*;

/**
 * <p>Title: CityFinder</p>
 * <p>Description: ToNextCity is a panel class to display the Houston city
 *                 search result as text, and the "Search Austin" button.</p>
 *
 * <p>Copyright: Copyright (c) 2001 ~ 2003 Chon Chung</p>
 * @author Chon Chung
 * @version Demo 0.6
 */

public class ToNextCityPanel extends Panel {
  TextArea textArea = new TextArea();
  Label label1 = new Label();
  Label label2 = new Label();
  Label label3 = new Label();
  Button searchBut = new Button();
  Label label4 = new Label();

  public ToNextCityPanel(CityFinderJWS parent) {
    try {
		textArea.setBounds(new Rectangle(1, 38, 250, 190));
		this.setLayout(null);
		label1.setForeground(Color.blue);
		label1.setText("Successfully record your preferences.");
		label1.setBounds(new Rectangle(0, 244, 250, 26));
		label2.setForeground(Color.blue);
		label2.setText("Click the \"Search Austin\" button");
		label2.setBounds(new Rectangle(1, 265, 241, 26));
		label3.setForeground(Color.blue);
		label3.setText("to do the \"one-click\" search.");
		label3.setBounds(new Rectangle(2, 284, 238, 25));
		searchBut.setLabel("Search Austin");
		searchBut.setBounds(new Rectangle(47, 320, 128, 35));
		label4.setFont(new java.awt.Font("Dialog", 1, 14));
		label4.setForeground(Color.blue);
		label4.setText("Search Result:");
		label4.setBounds(new Rectangle(3, 9, 218, 27));
		this.add(label2, null);
		this.add(label3, null);
		this.add(label4, null);
		this.add(textArea, null);
		this.add(label1, null);
		this.add(searchBut, null);
    }
    catch(Exception e) {
      e.printStackTrace();
    }
    searchBut.setActionCommand(CityFinderJWS.ONE_CLICK_EVENT);
    searchBut.addActionListener(parent);
  }

  /** Sets the TextArea's text for displaying the stores' contact information. */
  public void setText(String inString) {
    if (inString != null)
        textArea.setText(inString);
  }
}