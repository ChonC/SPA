package cityfind.gui.gasfind.que.panel;

import java.awt.*;
import java.awt.event.*;

import spa.question.Question;

import cityfind.gasfind.que.Q3_ChooseBrands;
import cityfind.gasfind.sax.GasSAXManager;

/**
 * Title:        CityFinder (IDSS Demo Applet)
 *
 * Description:  QP3_ChooseBrands is a Panel class to display the third question class
 *               that asks a user the following: "Choose Brands".
 *
 * @todo:        Bug, Seems the question 1 result disappears: review doAction() ***********************************************************************
 *
 * Copyright:    Copyright (c) 2001-2002 Chon D. Chung.
 * @author       Chon D. Chung
 * @version      Demo 0.1
 * 
 * 
 * <p>          License: Lesser General Public License (LGPL)</p>
 *
 */

public class QP3_ChooseBrands extends Panel  implements Question, ItemListener{

  /** Decision Factor for the question */
  public static final String DF = "Brand";

   public static final int ID = 3;
   private Q3_ChooseBrands que;
   private Checkbox cBox_Chevron, cBox_Exxon, cBox_Shell, cBox_Texaco;
   private int totalAnswers = 0;

   public QP3_ChooseBrands(GasSAXManager _gasSAXReader) {
      que = new Q3_ChooseBrands(_gasSAXReader);
      try {
		Label label1 = new Label(new String("3/4 Choose Brands:"));
		cBox_Chevron = new Checkbox(Q3_ChooseBrands.CHEVRON_STR, false);
		cBox_Exxon = new Checkbox(Q3_ChooseBrands.EXXON_STR, false);
		cBox_Shell = new Checkbox(Q3_ChooseBrands.SHELL_STR, false);
		cBox_Texaco = new Checkbox(Q3_ChooseBrands.TEXACO_STR, false);
		cBox_Chevron.addItemListener(this); //***Event
		cBox_Exxon.addItemListener(this);
		cBox_Shell.addItemListener(this);
		cBox_Texaco.addItemListener(this);

		  label1.setForeground(Color.black);
		  label1.setBounds(new Rectangle(25, 53, 160, 30));
		  this.setLayout(null);

		  cBox_Chevron.setBounds(new Rectangle(40, 89, 118, 22));
		  cBox_Exxon.setBounds(new Rectangle(40, 114, 119, 22));
		  cBox_Shell.setBounds(new Rectangle(40, 140, 118, 22));
		  cBox_Texaco.setBounds(new Rectangle(40, 165, 117, 22));

		  this.add(cBox_Shell, null);
		  this.add(cBox_Exxon, null);
		  this.add(cBox_Chevron, null);
		  this.add(label1, null);
		  this.add(cBox_Texaco, null);
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
        if(evt.getStateChange() == ItemEvent.DESELECTED) {
            if (evt.getSource() == cBox_Chevron) {
              que.setIsChevron(false);
              totalAnswers--;
            }else if (evt.getSource() == cBox_Exxon) {
              que.setIsExxon(false);
              totalAnswers--;
            }else if (evt.getSource() == cBox_Shell) {
              que.setIsShell(false);
              totalAnswers--;
            }else if (evt.getSource() == cBox_Texaco) {
              que.setIsTexaco(false);
              totalAnswers--;
            }
        }else if (evt.getStateChange() == ItemEvent.SELECTED){
            //*** solution -- from main: when the next button clicked, it ask this class to return all selected checkbox.
            if (evt.getSource() == cBox_Chevron) {
              que.setIsChevron(true);
              totalAnswers++;
            }else if (evt.getSource() == cBox_Exxon) {
              que.setIsExxon(true);
              totalAnswers++;
            }else if (evt.getSource() == cBox_Shell) {
              que.setIsShell(true);
              totalAnswers++;
            }else if (evt.getSource() == cBox_Texaco) {
              que.setIsTexaco(true);
              totalAnswers++;
            }
        }

        que.isUserResponded = (totalAnswers > 0) ? true : false;

  }
  public String toString(){
      return "Question 3 (DF:" + DF + " ): finds the user prefered brand.";
  }
}