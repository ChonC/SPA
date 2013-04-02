package cityfind.gui.gasfind.que.panel;

import java.awt.*;
import java.awt.event.*;

import spa.question.Question;

import cityfind.gasfind.que.Q2_AskBrand;
import cityfind.gasfind.sax.GasSAXManager;

/**
 * Title:        QP2_AskBrand
 *
 * Description:  QP2_AskBrand is the Panel class to display the Second question class
 *               that asks a user the following question: "Do you prefer particular brand?".
 *
 *
 * Copyright:    Copyright (c) 2001-2002 Chon Chung.
 * @author       Chon Chung
 * @version      Demo 0.1
 * 
 * <p>          License: Lesser General Public License (LGPL)</p>
 */

public class QP2_AskBrand extends Panel implements Question, ItemListener{

  /** Decision Factor for the question */
  public static final String DF = "Brand";
  public static final int ID = 2;

  private Q2_AskBrand que;
  private Checkbox cBoxYes, cBoxNo;

  public QP2_AskBrand(GasSAXManager _gasSAXReader) {
    que = new Q2_AskBrand(_gasSAXReader);

    try {
		Label label1 = new Label(new String("2/4 Do you prefer particular"));
		Label label2 = new Label(new String("brand (such as Texaco) ?"));

		CheckboxGroup booleanGroup = new CheckboxGroup();
		cBoxYes = new Checkbox(Q2_AskBrand.YES_STR, booleanGroup, true);
		cBoxYes.setForeground(Color.WHITE); 
		cBoxNo = new Checkbox(Q2_AskBrand.NO_STR, false);
		cBoxNo.setForeground(Color.WHITE); 
		cBoxYes.addItemListener(this);
		cBoxNo.addItemListener(this);
		cBoxNo.setCheckboxGroup(booleanGroup);

		label1.setForeground(Color.WHITE);
		label1.setBounds(new Rectangle(17, 58, 172, 23));
		label2.setForeground(Color.WHITE);
		label2.setBounds(new Rectangle(35, 77, 153, 18));

		this.setLayout(null);

		cBoxYes.setBounds(new Rectangle(44, 98, 118, 22));
		cBoxNo.setBounds(new Rectangle(44, 123, 119, 22));


		this.add(label1, null);
		this.add(cBoxYes, null);
		this.add(cBoxNo, null);
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
      if(evt.getStateChange() == ItemEvent.SELECTED){
          if (evt.getSource() == cBoxYes){
              que.setUserChoice(Q2_AskBrand.YES);
          }else if (evt.getSource() == cBoxNo) {
              que.setUserChoice(Q2_AskBrand.NO);
          }
      }
  }
  public String toString(){
      return "Question 2 (DF:" + DF + " ): identify whether a user prefer a particular brand.";
  }
}