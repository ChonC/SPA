package cityfind.gui;

import java.awt.*;
import javax.swing.*;


/**
 * <p>Title: CityFinder</p>
 * <p>Description: A panel to display the current one click automation process 
 *                 status. </p>
 * <p>Copyright: Copyright (c) 2002 Chon Chung</p>
 * <p>Company: </p>
 * @author Chon Chung
 * @version Demo 0.6
 */

public class OneClickStatusPanel extends Panel implements I_MsgDisplay{
  Label label = new Label();
  JProgressBar progressBar = new JProgressBar();
  TextArea textArea = new TextArea();
  Label donelabel = new Label();
  Label oneClicklabel = new Label();
  Label oneClicklabel2 = new Label();

  public OneClickStatusPanel() {
    try {
		label.setText("Progress:");
		label.setBounds(new Rectangle(5, 5, 102, 20));
		label.setForeground(Color.blue);
		label.setFont(new java.awt.Font("Dialog", 1, 14));
		this.setLayout(null);
		progressBar.setBounds(new Rectangle(5, 29, 245, 15));
		textArea.setBounds(new Rectangle(5, 68, 247, 216));
		donelabel.setFont(new java.awt.Font("Dialog", 0, 14));
		donelabel.setForeground(Color.blue);
		donelabel.setText("Process complete.");
		donelabel.setVisible(false);
		donelabel.setBounds(new Rectangle(14, 293, 144, 28));
		oneClicklabel.setFont(new java.awt.Font("Dialog", 1, 14));
		oneClicklabel.setForeground(Color.blue);
		oneClicklabel.setText("Wherever you are, ");
		oneClicklabel.setBounds(new Rectangle(13, 337, 204, 26));
		oneClicklabel.setVisible(false);
		oneClicklabel2.setBounds(new Rectangle(15, 363, 200, 27));
		oneClicklabel2.setText("just one click Automation!");
		oneClicklabel2.setVisible(false);
		oneClicklabel2.setFont(new java.awt.Font("Dialog", 1, 14));
		oneClicklabel2.setForeground(Color.blue);
		this.add(label, null);
		this.add(progressBar, null);
		this.add(textArea, null);
		this.add(donelabel, null);
		this.add(oneClicklabel, null);
		this.add(oneClicklabel2, null);
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }

  /** Sets the TextArea text for displaying the progress status. */
  public void appendText(String inString) {
    if (inString != null)
        textArea.append(inString);
  }

  /** Prints out error messages on the TextArea. */
  public void printError(String errorMsg){
    textArea.setFont(new java.awt.Font("SansSerif", 0, 16));
    textArea.setForeground(Color.red);
    if (errorMsg != null){
        textArea.append(errorMsg);
    }
    textArea.setForeground(Color.lightGray);
    textArea.setFont(new java.awt.Font("Dialog", 0, 12));
  }

  /** Increase the progress bar size-value. */
  public void increaseProgressBar(int increment){
    progressBar.setValue(progressBar.getValue() + increment);
  }

  /** Displays the final message text. */
  public void displayFinalText(){
    donelabel.setVisible(true);
    oneClicklabel.setVisible(true);
    oneClicklabel2.setVisible(true);
  }
}