package cityfind.gui.gasfind.que.panel;

import java.awt.*;
import java.awt.event.*;

import spa.question.Question;

import cityfind.gasfind.que.Q1_GasType;
import cityfind.gasfind.sax.GasSAXManager;

/**
 * Title:        QP1_GasType
 *
 * Description:  QP1_GasType is a Panel class for displaying the question number one.<br><br>
 *
 *               First, what type of gas?           <br>
 *               1) unlead                          <br>
 *               2) plus-unlead                     <br>
 *               3) premium-unlead                  <br>
 *               4) diesel                          <br><br>
 *
 *               <b>Note:</b> QP1_GasType extends java.awt.Panel as a temporary quick 
 *               solution for this demo application.  For the real app-version, a generic 
 *               question panel should be able to dynamically display different questions 
 *               with different layouts by aggregating the question as its property. 
 *               <br><br>

 *
 * Copyright:    Copyright (c) 2001-2002 Chon Chung.
 * @author       Chon Chung
 * @version      Demo 0.1
 * 
 * <p>          License: Lesser General Public License (LGPL)</p>
 *
 */

public class QP1_GasType extends Panel implements Question, ItemListener{

  /** Decision Factor for the question */
  public static final String DF = "Gas Type";

  public static final int ID = 1;
  private Q1_GasType que;
  private Checkbox cBoxUnlead, cBoxPlus, cBoxPremium, cBoxDiesel;

  public QP1_GasType(GasSAXManager _gasSAXReader) {
    que = new Q1_GasType(_gasSAXReader);

    try {      
		Label label1 = new Label(new String("Initial training: 4 questions"));
		Label label2 = new Label(new String("will be asked to collect"));
		Label label3 = new Label(new String("your preferences."));

		Label label4 = new Label(new String("1/4.  First, what type of gas?"));
		CheckboxGroup gasTypeGroup = new CheckboxGroup();
		cBoxUnlead = new Checkbox(Q1_GasType.UNLEAD_STR, gasTypeGroup, true);
		cBoxPlus = new Checkbox(Q1_GasType.PLUS_UNLEAD_STR, gasTypeGroup, false);
		cBoxPremium = new Checkbox(Q1_GasType.PREMIUM_UNLEAD_STR, gasTypeGroup, false);
		cBoxDiesel = new Checkbox(Q1_GasType.DIESEL_STR, false);
		cBoxUnlead.addItemListener(this); //***Event
		cBoxPlus.addItemListener(this);
		cBoxPremium.addItemListener(this);
		cBoxDiesel.addItemListener(this);
		cBoxDiesel.setCheckboxGroup(gasTypeGroup);

		  this.setLayout(null);

		  label1.setForeground(Color.blue);
		  label1.setBounds(new Rectangle(14, 12, 187, 22));
		  label2.setForeground(Color.blue);
		  label2.setBounds(new Rectangle(17, 35, 168, 21));
		  label3.setForeground(Color.blue);
		  label3.setBounds(new Rectangle(17, 54, 93, 21));
		  label4.setForeground(Color.black);
		  label4.setBounds(new Rectangle(17, 94, 168, 21));


		  cBoxUnlead.setBounds(new Rectangle(44, 119, 118, 22));
		  cBoxPlus.setBounds(new Rectangle(44, 144, 119, 22));
		  cBoxPremium.setBounds(new Rectangle(44, 170, 118, 22));
		  cBoxDiesel.setBounds(new Rectangle(44, 195, 117, 22));


		  this.add(label1, null);
		  this.add(label2, null);
		  this.add(label3, null);
		  this.add(cBoxPlus, null);
		  this.add(label4, null);
		  this.add(cBoxUnlead, null);
		  this.add(cBoxPremium, null);
		  this.add(cBoxDiesel, null);
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }
  }

  /** returns ID */
  public int getID(){
    return ID;
  }

  /** Base on the user's answer selection, set the userChoice parameter. */
  public void itemStateChanged(ItemEvent evt){//***Event
        if(evt.getStateChange() == ItemEvent.SELECTED) {
            if (evt.getSource() == cBoxUnlead) {
                que.setUserChoice(Q1_GasType.UNLEAD);
            }else if (evt.getSource() == cBoxPlus) {
                que.setUserChoice(Q1_GasType.PLUS_UNLEAD);
            }else if (evt.getSource() == cBoxPremium) {
                que.setUserChoice(Q1_GasType.PREMIUM_UNLEAD);
            } else if (evt.getSource() == cBoxDiesel){
                que.setUserChoice(Q1_GasType.DIESEL);
            }
        }
  }

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
  public String toString(){
      return "Question 1 (DF:" + DF + " ): identify the user prefer " + DF + ".";
  }

}