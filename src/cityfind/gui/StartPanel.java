package cityfind.gui;

import cityfind.CityFinder;

import java.awt.*;


/**
 * <p>Title: CityFinder</p>
 * <p>Description: This start panel contains a start button of this application. </p>
 * <p>Copyright: Copyright (c) 2002 Chon Chung</p>
 * <p>Company: </p>
 * @author Chon Chung
 * @version Demo 0.6
 */

public class StartPanel extends Panel implements I_MsgDisplay{
  /** Status String on the TextArea to display the current application status. */
  private String statusStr;

  Label label1 = new Label();
  Label label2 = new Label();
  Component component1;
  Button startBut = new Button();
  Label label3 = new Label();
  Label label4 = new Label();
  TextArea statusText = new TextArea();
  Label label5 = new Label();

  public StartPanel(CityFinder parent) {
    statusStr = new String();
    try {
		label1.setFont(new java.awt.Font("Dialog", 1, 60));
		label1.setForeground(Color.white);
		label1.setText("City");
		label1.setBounds(new Rectangle(55, 56, 115, 70));
		this.setBackground(Color.blue);
		this.setLayout(null);
		label2.setFont(new java.awt.Font("DialogInput", 0, 70));
		label2.setForeground(Color.white);
		label2.setText("Finder");
		label2.setBounds(new Rectangle(160, 62, 266, 62));
		startBut.setFont(new java.awt.Font("SansSerif", 0, 40));
		startBut.setForeground(new Color(0, 0, 184));
		startBut.setLabel("Start");
		startBut.setBounds(new Rectangle(59, 340, 156, 51));
		label3.setFont(new java.awt.Font("Dialog", 0, 15));
		label3.setForeground(Color.white);
		label3.setText("________________________________________________________________________");
		label3.setBounds(new Rectangle(55, 119, 581, 19));
		label4.setText("Version: Demo 0.6 (c) 2001~2003");
		label4.setBounds(new Rectangle(61, 413, 458, 22));
		statusText.setBackground(Color.blue);
		statusText.setFont(new java.awt.Font("Dialog", 0, 12));
		statusText.setForeground(Color.WHITE);
		statusText.setBounds(new Rectangle(59, 168, 405, 128));
		label5.setFont(new java.awt.Font("Dialog", 1, 14));
		label5.setForeground(Color.lightGray);
		label5.setText("Server connection status: ");
		label5.setBounds(new Rectangle(59, 147, 216, 23));
		this.add(label1, null);
		this.add(label3, null);
		this.add(label2, null);
		this.add(label4, null);
		this.add(statusText, null);
		this.add(label5, null);
		this.add(startBut, null);
		startBut.setVisible(false);
    }
    catch(Exception e) {
      e.printStackTrace();
    }
    startBut.setActionCommand(CityFinder.START_APP_EVENT);
    startBut.addActionListener(parent);
  }

  /** Prints out the message String on the TextArea. */
  public void appendText(String msg) {
    if (msg != null){
        statusText.append(msg);
    }
  }

  /** Prints out error message on the TextArea. */
  public void printError(String errorMsg){
    statusText.setFont(new java.awt.Font("SansSerif", 0, 16));
    statusText.setForeground(Color.red);
    if (errorMsg != null){
        statusText.append(errorMsg);
    }
    statusText.setForeground(Color.lightGray);
    statusText.setFont(new java.awt.Font("Dialog", 0, 12));
  }
  /** Returns the status text. */
  public String getText(){
    return statusStr.toString();
  }
  /** Enables the start Button by making it to visible. */
  public void enableStartButton(){
    startBut.setVisible(true);
  }
}