package cityfind.gasfind.que;

import spa.dph.DPHreader;
import spa.question.Question;

import cityfind.gasfind.GasDecisionProcess;
import cityfind.gasfind.sax.GasSAXManager;

/**
 * Title:        CityFinder (SPA Demo Application)
 *
 * Description:  Q2_AskBrand is a question class that implements Question interface.
 *               It has the following question:<br><br>
 *
 *               Do you prefer particular brand?          <br>
 *               1) Yes                                   <br>
 *               2) No                                    <br>
 *
 * Copyright:    Copyright (c) 2001-2002 Chon D. Chung.
 * @author       Chon D. Chung
 * @version      Demo 0.1
 * 
 * 
 * <p>          License: Lesser General Public License (LGPL)</p>
 */

public class Q2_AskBrand implements Question{


  /** Decision Factor for the question */
  public static final String DF = "Brand";

  public static final String QUESTION = "2/4 Do you prefer particular brand (such as Texaco) ?";
  public static final int ID = 2;

  public static final int YES = 1;
  public static final int NO = 2;

  public static final String YES_STR = "Yes";
  public static final String NO_STR = "No";

  private GasSAXManager gasSAXManager; 
  /** the user answer. */
  private int userChoice = 1;

  public Q2_AskBrand(GasSAXManager _gasSAXReader) {
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

  /** Based on the user answer, processes the business data, set the User
   *  DIH (Decision Interactive History), and set the next question. */
  public void doAction(){
      GasDecisionProcess.addAnswerToDPH(Integer.toString(ID), String.valueOf(userChoice));
      setNextQuestion();
  }


  /** Based on the user saved DIH, this method performs automatic actions. */
  public void doAutoAction(){
      DPHreader dihReader = DPHreader.getInstance();
      userChoice = Integer.parseInt(dihReader.readAnswer(GasDecisionProcess.getUserDPH(), ID)); //userChoice is required for the setNextQuestion() method.      
      setNextQuestion();
  }

  /** Set the next question. */
  public void setNextQuestion(){
    if (userChoice == YES){
        GasDecisionProcess.setNextQuestion(GasDecisionProcess.QueStatus.Q3_CHOOSE_BRANDS);//set the next question of GasInteract process
    }else {
        GasDecisionProcess.setNextQuestion(GasDecisionProcess.QueStatus.Q4_PRICE_OR_DISTANCE);//set the next question of GasInteract process
    }
  }
  public String toString(){
      return "Q2_AskBrand (DF:" + DF + " ): identify whether a user prefer a particular brand.";
  }
}