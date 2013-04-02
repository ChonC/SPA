package cityfind.gui.gasfind.que.panel;

import java.awt.*;
import java.awt.event.*;

import spa.question.Question;

import cityfind.gasfind.que.Q4_PriceDistance;
import cityfind.gasfind.sax.GasSAXManager;

/**
 * Title:        QP4_PriceDistance
 *
 * Description:  QP4_PriceDistance is a Panel class to display the Fourth question
 *               that asks a user the following question: 
 *               
 *               "The last, which one is more important?   Price or distance".
 *
 * Copyright:    Copyright (c) 2001-2002 Chon Chung.
 * @author       Chon Chung
 * @version      Demo 0.1
 * 
 * 
 * <p>          License: Lesser General Public License (LGPL)</p> */

public class QP4_PriceDistance extends Panel implements Question, ItemListener{

  /** Decision Factor for the question */
  public static final String DF = "Distance and Price";

  public static final int ID = 4;
  private Q4_PriceDistance que;
  private Checkbox cBox_Price, cBox_Distance;

  public QP4_PriceDistance(GasSAXManager _gasSAXReader) {
    que = new Q4_PriceDistance(_gasSAXReader);
    try {
		Label label1 = new Label(new String("4/4 The last, which one is"));
		Label label2 = new Label(new String(" more important?"));
		CheckboxGroup choiceGroup = new CheckboxGroup();
		cBox_Price = new Checkbox(Q4_PriceDistance.PRICE_STR, choiceGroup, true);
		cBox_Price.setForeground(Color.WHITE);
		cBox_Distance = new Checkbox(Q4_PriceDistance.DISTANCE_STR, false);
		cBox_Distance.setForeground(Color.WHITE);
		cBox_Price.addItemListener(this);
		cBox_Distance.addItemListener(this);
		cBox_Distance.setCheckboxGroup(choiceGroup);

		label1.setForeground(Color.WHITE);
		label2.setForeground(Color.WHITE);
		label1.setBounds(new Rectangle(17, 58, 165, 21));
		label2.setBounds(new Rectangle(38, 78, 127, 16));

		this.setLayout(null);

		cBox_Price.setBounds(new Rectangle(38, 101, 118, 22));
		cBox_Distance.setBounds(new Rectangle(38, 126, 119, 22));

		this.add(cBox_Price, null);
		this.add(cBox_Distance, null);
		this.add(label1, null);
		this.add(label2, null);
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }
  }

  /** returns ID */
  public int getID(){
    return ID;
  }

  /** Based on the user answer, process the business lits data, set the User
   *  DIH (Decision Interactive History), and set the next question. */
  public void doAction(){
      que.doAction();
  }

  /** Based on the user saved DIH, this method performs appropriate actions. */
  public void doAutoAction(){
      que.doAutoAction();
  }

  public void setNextQuestion(){
      que.setNextQuestion();
  }

  /** For the user selected answer event. */
  public void itemStateChanged(ItemEvent evt){//***Event
      if(evt.getStateChange() == ItemEvent.SELECTED) {
          if (evt.getSource() == cBox_Price) {
              que.setUserChoice(Q4_PriceDistance.PRICE);
          }else if (evt.getSource() == cBox_Distance) {
              que.setUserChoice(Q4_PriceDistance.DISTANCE);
          }
      }
  }

  public String toString(){
      return "Question 4 (2 DFs:" + DF + " ): Indentify the user prefered DF, price or distance.";
  }
}