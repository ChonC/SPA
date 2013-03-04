package cityfind.gasfind.que;

import spa.dph.DPHreader;
import spa.question.Question;

import cityfind.gasfind.GasDecisionProcess;
import cityfind.gasfind.sax.GasSAXManager;


/**
 * Title:        CityFinder (IDSS Demo Applet)
 *
 * Description:  Q4_PriceDistance is a question class, which implements Question
 *               interface.  It holds the following question:<br><br>
 *
 *               4/4 The last, which one is more important?           <br>
 *               1) Price                                             <br>
 *               2) Distance                                          <br>
 *
 * Copyright:    Copyright (c) 2001-2002 Chon Chung.
 * @author       Chon Chung
 * @version      Demo 0.1
 * 
 * 
 * <p>          License: Lesser General Public License (LGPL)</p>
 */

public class Q4_PriceDistance implements Question{

  /** Decision Factors for the question */
  public static final String DF = "Distance and Price";

  public static final String QUESTION = "4/4 The last question, which one is more important?";
  public static final int ID = 4;

  public static final int PRICE = 1;
  public static final int DISTANCE = 2;

  public static final String PRICE_STR = "Price";
  public static final String DISTANCE_STR = "Distance";


  /** the user chosen answer */
  private int userChoice = 1;

  private GasSAXManager gasSAXManager; 

  public Q4_PriceDistance(GasSAXManager _gasSAXReader) {
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

  /** Based on the user choice, process the business data, sets the User
   *  DPH, and sets the next question. */
  public void doAction(){
    //*** have to check if it is sorted ************************************************************


      String[] oldShopList = GasDecisionProcess.getShopListCopy();

      GasDecisionProcess.resetShopList(); //Set the ShopList to null  
      if (userChoice == 1) {
          DPHreader dphReader = DPHreader.getInstance();

          /* get the selected Gas type from the number 1 question's answer. */
          String selectedGas = new Q1_GasType(gasSAXManager).getGasType(Integer.parseInt(dphReader.readAnswer(GasDecisionProcess.getUserDPH(), 1)));
          
          //sort the ShopList based on the gas-price
          GasDecisionProcess.addToShopList(gasSAXManager.sortLowestGas(selectedGas, oldShopList));
          GasDecisionProcess.setSorted(true); //<-- Notify that the current data has been sorted.
      }else if (userChoice == 2) {
          GasDecisionProcess.addToShopList(gasSAXManager.sortShortestDistance(oldShopList));
          GasDecisionProcess.setSorted(true); //<-- Notify that the current data has been sorted.
      }

      GasDecisionProcess.addAnswerToDPH(Integer.toString(ID),String.valueOf(userChoice));

      GasDecisionProcess.setTaskStatus(GasDecisionProcess.TASK_DONE);
      setNextQuestion();

  }



  /** Based on the user saved DPH, this method performs appropriate actions. */
  public void doAutoAction(){

      String[] oldBizList = GasDecisionProcess.getShopListCopy();

      GasDecisionProcess.resetShopList(); //Set the BizList to null ************************* Should change name
      DPHreader dphReader = DPHreader.getInstance();
      userChoice = Integer.parseInt(dphReader.readAnswer(GasDecisionProcess.getUserDPH(), ID));

      if (userChoice == 1) {

          /* get the selected Gas type from the number 1 question's answer. */
          String selectedGas = new Q1_GasType(gasSAXManager).getGasType(Integer.parseInt(dphReader.readAnswer(GasDecisionProcess.getUserDPH(), 1)));
          GasDecisionProcess.addToShopList(gasSAXManager.sortLowestGas(selectedGas, oldBizList));
          GasDecisionProcess.setSorted(true); //<-- Notify that the current data has been sorted.
      }else if (userChoice == 2) {
          GasDecisionProcess.addToShopList(gasSAXManager.sortShortestDistance(oldBizList));
          GasDecisionProcess.setSorted(true); //<-- Notify that the current data has been sorted.
      }
		
	  GasDecisionProcess.setTaskStatus(GasDecisionProcess.TASK_DONE);
	  
  }


  /** Sets the next question. */
  public void setNextQuestion(){
        GasDecisionProcess.setTaskStatus(GasDecisionProcess.TASK_DONE);
  }

  public String toString(){
      return "Question 4 (2 DFs:" + DF + " ): Indentify the user prefered DF, price or distance.";
  }
}