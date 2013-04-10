package cityfind.gasfind.que;

import cityfind.gasfind.GasDecisionProcess;
import cityfind.gasfind.sax.GasSAXManager;


import java.util.ArrayList;

import spa.dph.DPHreader;
import spa.question.Question;


/**
 * Title:        CityFinder (SPA Demo Applet)
 *
 * Description:  Q3_ChooseBrands is a question class, which implements Question
 *               interface.  It holds the following question:<br><br>
 *
 *               Choose Brands.                               <br>
 *               1) Chevron                                   <br>
 *               2) Exxon                                     <br>
 *               2) Shell                                     <br>
 *               2) Texaco                                    <br>
 *
 *
 *
 *
 * @toDo:  1. Coding should be independent from the brand names such as chevron,
 *            shell, and others because different city has different brands.  <br><br>
 *
 *         2. For the next time, we should have a general question package that has
 *            all different question and answers Interfaces, which provide basic structure
 *            and functionalities.  So, each Question class can simply implements them.
 *
 *
 * Copyright:    Copyright (c) 2001-2002 Chon Chung.
 * @author       Chon Chung
 * @version      Demo 0.1
 * 
 * <p>          License: Lesser General Public License (LGPL)</p>
 */

public class Q3_ChooseBrands implements Question{

  /** Decision Factor for the question */
  public static final String DF = "Brand";

  public static final String QUESTION = "3/4 Choose Brands:";
  public static final int ID = 3;

   public static final int CHEVRON = 1;
   public static final int EXXON = 2;
   public static final int SHELL = 3;
   public static final int TEXACO = 4;

   public static final String CHEVRON_STR = "Chevron";
   public static final String EXXON_STR = "Exxon";
   public static final String SHELL_STR = "Shell";
   public static final String TEXACO_STR = "Texaco";

   /** Boolean parameter indicates a user has responded to the question. */
   public boolean isUserResponded;

   /** the selection status of Chevron*/
   private boolean isChevron;
   /** the selection status of Exxon*/
   private boolean isExxon;
   /** the selection status of Shell*/
   private boolean isShell;
   /** the selection status of Texaco*/
   private boolean isTexaco;
   
   private GasSAXManager gasSAXManager; 


   public Q3_ChooseBrands(GasSAXManager _gasSAXReader) {
	  gasSAXManager = _gasSAXReader; 
      isUserResponded = false;

      isChevron = false;
      isExxon = false;
      isShell = false;
      isTexaco = false;
   }

  /** returns ID */
  public int getID(){
    return ID;
  }

  public void setIsChevron(boolean in){
    isChevron = in;
  }

  public void setIsExxon(boolean in){
    isExxon = in;
  }
  public void setIsShell(boolean in){
    isShell = in;
  }

  public void setIsTexaco(boolean in){
    isTexaco = in;
  }

  /** Based on the user answer, processes the business data, sets the User
   *  DPH (Decision Process History), and sets the next question. */
  public void doAction(){

      if (isUserResponded){
          String[] oldShopList = GasDecisionProcess.getShopListCopy();
          
          GasDecisionProcess.resetShopList();
          ArrayList<String> ans = new ArrayList<String>(4);

          if (isChevron) {
              GasDecisionProcess.addToShopList(gasSAXManager.findBrandShops("Chevron", oldShopList));
              ans.add(Integer.toString(CHEVRON));
          }
          if (isExxon) {
              GasDecisionProcess.addToShopList(gasSAXManager.findBrandShops("Exxon", oldShopList));
              ans.add(Integer.toString(EXXON));
          }
          if (isShell) {
              GasDecisionProcess.addToShopList(gasSAXManager.findBrandShops("Shell", oldShopList));
              ans.add(Integer.toString(SHELL));
          }
          if (isTexaco) {
              GasDecisionProcess.addToShopList(gasSAXManager.findBrandShops("Texaco", oldShopList));
              ans.add(Integer.toString(TEXACO));
          }

          String[] answers = new String[ans.size()];

          for (int i=0; i < ans.size(); i++){
            answers[i] = (String) ans.get(i);
          }

          GasDecisionProcess.addAnswersToDPH(Integer.toString(ID), answers);
      }
      setNextQuestion();
  }


  /** Based on the user saved DPH, this method performs automatic actions. */
  public void doAutoAction(){
      isChevron = false;
      isExxon = false;
      isShell = false;
      isTexaco = false;

      DPHreader dphReader = DPHreader.getInstance();
      ArrayList<String> userChoices = dphReader.readAnswers(GasDecisionProcess.getUserDPH(), ID);
      isUserResponded = true;
      int answer;

      for (int i=0; i < userChoices.size(); i++){
          answer = Integer.parseInt((String)userChoices.get(i));
          if (answer == CHEVRON) isChevron = true;
          if (answer == EXXON) isExxon = true;
          if (answer == SHELL) isShell = true;
          if (answer == TEXACO) isTexaco = true;
      }

      String[] oldShopList = GasDecisionProcess.getShopListCopy();
      
 	  
 	  
      GasDecisionProcess.resetShopList();

      if (isChevron) {
          GasDecisionProcess.addToShopList(gasSAXManager.findBrandShops("Chevron", oldShopList));
      }
      if (isExxon) {
          GasDecisionProcess.addToShopList(gasSAXManager.findBrandShops("Exxon", oldShopList));
      }
      if (isShell) {
          GasDecisionProcess.addToShopList(gasSAXManager.findBrandShops("Shell", oldShopList));
      }
      if (isTexaco) {
          GasDecisionProcess.addToShopList(gasSAXManager.findBrandShops("Texaco", oldShopList));
      }
	      
      setNextQuestion();
  }

  /** Set the next question. */
  public void setNextQuestion(){
       if (!isUserResponded){
          GasDecisionProcess.setNextQuestion(GasDecisionProcess.QueStatus.Q3_CHOOSE_BRANDS);
       }else{
          GasDecisionProcess.setNextQuestion(GasDecisionProcess.QueStatus.Q4_PRICE_OR_DISTANCE);
       }
  }
  public String toString(){
      return "Q3_ChooseBrands (DF:" + DF + " ): finds the user prefered brands.";
  }

}