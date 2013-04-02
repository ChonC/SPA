package cityfind.gasfind;

import cityfind.gasfind.que.*;
import cityfind.gasfind.sax.GasSAXManager;
import cityfind.gasfind.sax.GasXMLTag;
import cityfind.gui.gasfind.que.panel.*;

import java.awt.Panel;
import java.net.URL;

import spa.DecisionProcess;
import spa.ShopElement;
import spa.dph.DPHwriter;
import spa.question.Question;
import spa.util.Sort;
/**
 *<p>
 *Title: GasDecisionProcess
 *
 *<p>Description:  GasDecisionProcess is a SPA-DecisionProcess class 
 *	to find Gas-stations near the user location.  This class provides 
 *	functions for recording a Gas-stations-search Decision-Processes 
 *  for one-click automation.<br/><br/>
 *                
 *	This class displays 4 decision questions and asks a user his/her decision choices.
 *	It extends the super DecisionProcess class, which provides the framework and 
 *	base functions.
 *<p>
 *	The task to find the best gas stations has
 *	4 decision factors (DFs): gas type, gas-company-brand, price, and distance.  
 *	This provides 4 questions for the each decision factor (DF).
 *	Based on his/her decision choices, it presents 
 *	the matching gas stations list as a result, and records 
 *	the user's decisions for the one-click automation.
 *
 *<p>Copyright: Copyright (c) 2001-2013 Chon Chung.
 *<p>@author	Chon Chung
 *<p>@version	Demo 0.7
 *
 *
 * <p>          License: Lesser General Public License (LGPL)
 * 
 * TODO	This class might need to handle concurrent requests, 
 * therefore it should store each user request process in a session like HashMap or ThreadLocal. 
 */
public class GasDecisionProcess extends DecisionProcess{

  /**  question number status enum. */
  public enum QueStatus { 
	  Q1_GAS_TYPE, Q2_ASK_BRAND, Q3_CHOOSE_BRANDS, Q4_PRICE_OR_DISTANCE; 
  }

  /** the main entity node of business XML data. i.e. <store> */
  public static final String ELEMENT_MAIN_NODE = GasXMLTag.NAME_TAG_STORE;

  /** ID of this GasDecisionProcess class. Format = "Name::version::date" */
  static String id_GDP;
  /** Gas-SAX-XML reader. */
  private static GasSAXManager gasSAXManager;
  /** the next question number status.*/
  private static QueStatus nextQuestion = QueStatus.Q1_GAS_TYPE;

  /**
   * Constructor 
   *
   * @param xmlSource  the XML data file source.*/
  public GasDecisionProcess(String _xmlSource) {
    id_GDP = "GasDecisionProcess::demo-0.7::12-9-2001"; //12-9-2001 was the presentation date of this App at Houston Java User Group. 
    /** initialized the userDPH (User Decision Process History, which contains the user decision processes and answers) */
    userDPH = DPHwriter.newDPH(id_GDP);
    /** Maximum allowable size of the final shopList-set before reporting it to a user. */
    maxShopListSize = 5;
    nextQuestion = QueStatus.Q1_GAS_TYPE;
    /** initialized the XML-SAX-reader. */    
    gasSAXManager = new GasSAXManager(_xmlSource, ELEMENT_MAIN_NODE); 
    
  }
  

  /**
   * Constructor 
   *
   * @param _xmlSource  the XML data file source.
   * @param xmlSource  the XML data file source.*/
  public GasDecisionProcess(URL _url, String _xmlSource) {
    id_GDP = "GasDecisionProcess::demo-0.1::12-9-2001";
    /** initialized the userDPH (Decision Process History, which contains the user decision process answers) */
    userDPH = DPHwriter.newDPH(id_GDP);
    /** Maximum allowable size of the final shopList-set before reporting it to a user. */
    maxShopListSize = 5;
    nextQuestion = QueStatus.Q1_GAS_TYPE;
    /** initialized the XML-SAX-reader. */    
    gasSAXManager = new GasSAXManager(_url, _xmlSource, ELEMENT_MAIN_NODE); 
    
  }


  
  /** Resets this with a different XML data source to conduct a new search with a different set of data.     *  
   *  It could be used for an automation search.
   *
   * @param xmlSource  the XML data-file source.*/
  public void reset(String xmlSource){
    super.resetForAuto();
    /** Maximum allowable size of the final shopList-set to report to a user. */
    maxShopListSize = 5;
    nextQuestion = QueStatus.Q1_GAS_TYPE;
    gasSAXManager.reset(xmlSource, ELEMENT_MAIN_NODE);
  }

  /** Resets this with a new XML data-source to conduct a new search from a new XML data-source.
   *
    * @param url          the URL for a remote XML data-source
    * @param xmlSource    local XML data-source. When the url connection is unavailable, it will be used. */
  public void reset(URL url, String xmlSource){
    super.resetForAuto();
    /** Maximum allowable size of the final shopList-set to report to a user. */
    maxShopListSize = 5;
    nextQuestion = QueStatus.Q1_GAS_TYPE;
    gasSAXManager.reset(url, xmlSource, ELEMENT_MAIN_NODE);
  }
  

  /** Sets the next question. */
  public static void setNextQuestion(QueStatus next){
      nextQuestion = next;
  }

  /** Returns the next question reference.  The Decision Question are logically placed in this method.  */
  public Question getNextQuestion() {
      if (nextQuestion == QueStatus.Q1_GAS_TYPE) return new Q1_GasType(gasSAXManager);
      else if (nextQuestion == QueStatus.Q2_ASK_BRAND) return new Q2_AskBrand(gasSAXManager);
      else if (nextQuestion == QueStatus.Q3_CHOOSE_BRANDS) return new Q3_ChooseBrands(gasSAXManager);
      else if (nextQuestion == QueStatus.Q4_PRICE_OR_DISTANCE) return new Q4_PriceDistance(gasSAXManager);
      return new Q1_GasType(gasSAXManager);
  }

  /** Returns the next question panel for the Java Web Start Demo
   *  application.  This method is for the Demo application only.
   *
   *  TODO For a real application, we should have a generic question panel
   *  that is able to dynamically display the each question with a different layout.
   */
  public Panel getNextPanelQuestion(){
      if (nextQuestion == QueStatus.Q1_GAS_TYPE) return new QP1_GasType(gasSAXManager);
      else if (nextQuestion == QueStatus.Q2_ASK_BRAND) return new QP2_AskBrand(gasSAXManager);
      else if (nextQuestion == QueStatus.Q3_CHOOSE_BRANDS) return new QP3_ChooseBrands(gasSAXManager);
      else if (nextQuestion == QueStatus.Q4_PRICE_OR_DISTANCE) return new QP4_PriceDistance(gasSAXManager);
      return new QP1_GasType(gasSAXManager);
  }

  /** Returns the gas-station contact information (id, address, phone, distance, etc) as a String */
  public String getShopInfo(String storeId){
      return gasSAXManager.getGasShopInfo(storeId);
  }

  /** Returns an array of ShopElement[], which contains name,
   *  id, address, phone, distance, and other stop contact information. The ShopElement[] is
   *  usually used for displaying the stores' contact information on the screen.  
   *  
   * @return ShopElement[]  	Returns ShopElement[] or null */
  public ShopElement[] getShopListInfo(){
      ShopElement[] finalList = new ShopElement[shopListArr.length];
     
	  //Since a SAX XML parser reads a file from the beginning to end,
      //we need to sort the current shopList array in ascending order to read the XML file
      //from the beginning.
      if(isSorted) {

          ShopElement[] tempList = new ShopElement[shopListArr.length];

          //Sort the shopList in ascending order
          String[] ascendingArray = Sort.sortArray((String[]) shopListArr.clone());
          tempList = gasSAXManager.getShopListInfo(ascendingArray);

          //After getting the information, it changes back the ascending array
          //to the original shopList order.  The original shopList array may
          //have been sorted based on the shops' property value(price or distance).
          for (int i=0; i< shopListArr.length; i++)
              for (int j=0; j< ascendingArray.length; j++)
                  if (shopListArr[i].endsWith(ascendingArray[j])){
                      finalList[i] = tempList[j];
                      break;
                  }
      }else{
          finalList = gasSAXManager.getShopListInfo((String[]) shopListArr.clone());
      }
      
 
      return finalList;
  }

  /**
   *  Checks the final data-set condition.
   *
   *  The data-set should be either sorted or be less than a certain
   *  size for a person, so he can comprehend it. 
   *
   *  If the final data-set fails to meet the condition, we should do additional interactive processes.
   *  
   *  TODO: If more than one shop-entities have the same values, find a way to display them.  
   */
  public static boolean isQualified(){
      //*** should check the size of shopList, if (shopList.length = 0) return false;

      if (isSorted) return true;
      else if (shopListArr.length <= maxShopListSize) return true;

      return false;
  }

  /** Returns the description of this IDSS process as a String. */
  public String toString(){
      return "GasDecision Process SPA( " + id_GDP + "): finds the gas-stations based on your decision choices.\n" +
             "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n" +
             "    " + new Q1_GasType(gasSAXManager).toString() + "\n" +
             "    " + new Q2_AskBrand(gasSAXManager).toString() + "\n" +
             "    " + new Q3_ChooseBrands(gasSAXManager).toString() + "\n" +
             "    " + new Q4_PriceDistance(gasSAXManager).toString() + "\n" +
             "==================================================================";
  }


 /** For testing, prints the shopList to the outPut-Screen. */
  public static void printShopList(){
      printShopList(id_GDP);
  }
}

