package cityfind.gasfind.que;

import spa.dph.DPHreader;
import spa.question.Question;

import cityfind.gasfind.GasDecisionProcess;
import cityfind.gasfind.sax.GasSAXManager;


/**
 * Title:        Que1
 *
 * Description:  Q1_GasType is a question class that implements Question interface.
 *               It asks a user for his choice of a gas type.
 * 
 *               After a question class collects a user choice, it instructs
 *               other classes how to search and collect information from the 
 *               remote data server.
 *
 *
 *               As you can see, this class is quiet simple because the spa-core packages
 *               already provide foundations.  Because its simplicity, it seems very possible
 *               to dynamically create this class from an XML artifact instruction.
 * 
 *               It has the following question:<br><br>
 *
 *               First, what type of gas?           <br>
 *               1) unlead                          <br>
 *               2) plus-unlead                     <br>
 *               3) premium-unlead                  <br>
 *               4) diesel                          <br><br>
 *
 * Copyright:    Copyright (c) 2001-2002 Chon Chung.
 * @author       Chon Chung
 * @version      Demo 0.5
 * 
 * 
 * <p>          License: Lesser General Public License (LGPL)</p>
 *
 */

public class Q1_GasType implements Question{

  /** Decision Factor for the question */
  public static final String DF = "Gas Type";

  public static final String QUESTION = "First, what type of gas?";
  public static final int ID = 1;

  public static final int UNLEAD = 1;
  public static final int PLUS_UNLEAD = 2;
  public static final int PREMIUM_UNLEAD = 3;
  public static final int DIESEL = 4;

  public static final String UNLEAD_STR = "unlead";
  public static final String PLUS_UNLEAD_STR = "plus-unlead";
  public static final String PREMIUM_UNLEAD_STR = "premium-unlead";
  public static final String DIESEL_STR = "diesel";

  private GasSAXManager gasSAXManager; 
  /** the user answer. */
  private int userChoice = UNLEAD;//default value

  public Q1_GasType(GasSAXManager _gasSAXReader) {
	  gasSAXManager = _gasSAXReader; 
  }

  /** returns ID */
  public int getID(){
    return ID;
  }

  public int getUserChoice (){
    return userChoice;
  }

  public void setUserChoice (int choice){
    userChoice = choice;
  }
  /** Based on the user choice, process the business list data, set User
   *  DPH (Decision Process History), and set the next question.
   *  @status: under improvement
   *  @toDo: 1. if (GasInteract.isBizListEmpty()), ask a user to make another choice. */
  public void doAction(){
	  GasDecisionProcess.setShopList(gasSAXManager.findShopHasGas(getGasType(userChoice)));

 	  //@Todo if (GasDecisionProcess.isShopListEmpty()) <--should check the condition. if true, we should ask a user to make another choice.

      GasDecisionProcess.addAnswerToDPH(Integer.toString(ID), String.valueOf(userChoice)); //Stores the user answer
      setNextQuestion();
  }


  /** Based on the user saved DPH, this method performs appropriate actions. */
  public void doAutoAction(){
      DPHreader dihReader = DPHreader.getInstance();
      userChoice = Integer.parseInt(dihReader.readAnswer(GasDecisionProcess.getUserDPH(), ID));
      GasDecisionProcess.setShopList(gasSAXManager.findShopHasGas(getGasType(userChoice)));
      setNextQuestion();
  }

  /** Set the next question. */
  public void setNextQuestion(){
      GasDecisionProcess.setNextQuestion(GasDecisionProcess.QUESTION_2);
  }

  /** Returns the product XML tag value as a string. */
  protected String getGasType(int userChoice){
      if (userChoice == UNLEAD) return UNLEAD_STR;
      else if (userChoice == PLUS_UNLEAD) return PLUS_UNLEAD_STR;
      else if (userChoice == PREMIUM_UNLEAD) return PREMIUM_UNLEAD_STR;
      else if (userChoice == DIESEL) return DIESEL_STR;
      return null;
  }

  public String toString(){
      return "Q1_GasType (DF:" + DF + " ): identify the user prefered " + DF + ".";
  }

}