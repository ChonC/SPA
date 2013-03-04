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
 *<p>Title:        GasDecisionProcess</p>
 *
 *<p>Description:  GasDecisionProcess is a SPA-DecisionProcess class to find gas service stations 
 *                 near the user GPS location.  This class provides 
 *                 functions for record the finding Gas-stations Decision Processes for automation.<br/><br/>
 *                 
 *                 This class interactively asks a user 
 *                 for his/her answers by displaying 4 questions on the GUI screen.
 *                 It extends the super DecisionProcess class,
 *                 which provides the framework and functions
 *                 for all sub SPA-DecisionProcess classes.</p>
 *<p>
 *                 The task to find the best gas station near the user location has
 *                 4 decision factors (DFs): gas type, brand, price, and distance.  This
 *                 GasDecisionProcess instance provides 4 questions for the each DF.
 *                 Based on his/her decision answers, it presents 
 *                 the appropriate gas stations list as a result, and record 
 *                 the user's decisions for the one-click automation.</p>
 *
 *<p>Copyright:    Copyright (c) 2001-2004 Chon Chung.</p>
 *<p>@author       Chon Chung</p>
 *<p>@version      Demo 0.7</p>
 *
 *
 * <p>          License: Lesser General Public License (LGPL)</p>
 */
public class GasDecisionProcess extends DecisionProcess{

  /**  Constants for a question. Note, these could be in a ArrayList (@todo ). */
  public static final int QUESTION_1 = 1,
                          QUESTION_2 = 2,
                          QUESTION_3 = 3,
                          QUESTION_4 = 4;

  /** Constants for the main business entity XML node. i.e. 'store' */
  public static final String ELEMENT_MAIN_NODE = GasXMLTag.NAME_TAG_STORE;

  /** ID of this GasDecisionProcess class. Format = "Name::version::date" */
  static String id_GDP;
  /** Reference of SAX-XML reader. */
  private static GasSAXManager gasSAXManager;
  /** Indicates the next question.*/
  private static int nextQuestion = QUESTION_1;

  /**
   * Constructor 
   *
   * @param xmlSource  the XML data file source.*/
  public GasDecisionProcess(String _xmlSource) {
    id_GDP = "GasDecisionProcess::demo-0.1::12-9-2001";
    /** initialized the userDPH (User Decision Process History, which contains the user decision processes and answers) */
    userDPH = DPHwriter.newDPH(id_GDP);
    /** Maximum allowable size of the final shopList-set before reporting it to a user. */
    maxShopListSize = 5;
    nextQuestion = QUESTION_1;
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
    nextQuestion = QUESTION_1;
    /** initialized the XML-SAX-reader. */    
    gasSAXManager = new GasSAXManager(_url, _xmlSource, ELEMENT_MAIN_NODE); 
    
  }


  
  /** Reset this class instance with a different XML data source.  This method
   *  is used to conduct a new search with a different set of data.  
   *  
   *  This could be used for an automation new search.
   *
   * @param xmlSource  the XML data-file source.
   * @param bizNode    The main business entity XML node. i.e. '<store/>' */
  public void reset(String xmlSource){
    super.resetForAuto();
    /** Maximum allowable size of the final shopList-set to report to a user. */
    maxShopListSize = 5;
    nextQuestion = QUESTION_1;
    gasSAXManager.reset(xmlSource, ELEMENT_MAIN_NODE);
  }

  /** Reset this class instance with a new XML data-source.  This method
   *  is used to conduct a new search from a new XML data-source.
   *
    * @param url          the URL for a remote XML data-source
    * @param xmlSource    local XML data-file source only for when the url connection is unavailable.
    * @param bizNode      The main business entity XML node. i.e. '<store/>' */
  public void reset(URL url, String xmlSource){
    super.resetForAuto();
    /** Maximum allowable size of the final shopList-set to report to a user. */
    maxShopListSize = 5;
    nextQuestion = QUESTION_1;
    gasSAXManager.reset(url, xmlSource, ELEMENT_MAIN_NODE);
  }
  

  /** Sets the next question. */
  public static void setNextQuestion(int next){
      nextQuestion = next;
  }

  /** Returns the next question reference. We logically place all the Decision Questions in here,  */
  public Question getNextQuestion() {
      if (nextQuestion == QUESTION_1) return new Q1_GasType(gasSAXManager);
      else if (nextQuestion == QUESTION_2) return new Q2_AskBrand(gasSAXManager);
      else if (nextQuestion == QUESTION_3) return new Q3_ChooseBrands(gasSAXManager);
      else if (nextQuestion == QUESTION_4) return new Q4_PriceDistance(gasSAXManager);
      return new Q1_GasType(gasSAXManager);
  }

  /** Returns the next question panel for the Java Web Start Demo
   *  application.  This method is for the Demo application only.
   *
   *  @todo For a real application, we should have a generic question panel
   *  that is able to dynamically display the each question with a different layout.
   */
  public Panel getNextPanelQuestion(){
      if (nextQuestion == QUESTION_1) return new QP1_GasType(gasSAXManager);
      else if (nextQuestion == QUESTION_2) return new QP2_AskBrand(gasSAXManager);
      else if (nextQuestion == QUESTION_3) return new QP3_ChooseBrands(gasSAXManager);
      else if (nextQuestion == QUESTION_4) return new QP4_PriceDistance(gasSAXManager);
      return new QP1_GasType(gasSAXManager);
  }

  /** Returns the gas store contact information (id, address, phone, distance, etc) as a String */
  public String getShopInfo(String storeId){
      return gasSAXManager.getGasShopInfo(storeId);
  }

  /** Returns an array of ShopElement[], which contains name,
   *  id, address, phone, distance, and other store information. The shopList is
   *  usually used for displaying the stores' contact information to a user.  */
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
          //have been sorted based on the shops' property value
          //such as price or distance.
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
   *  Before we present the final data-set to the user, we should check its
   *  condition.  The data-set should be either sorted or be less than a certain
   *  size for a person, so he can comprehend it.  If the data-set is too big or unsorted,
   *  it will not provide any meaningful value to the user.
   *
   *  If the final data-set fails to meet the condition, we should do additional interactive processes.
   *  
   *  @toDo: If more than one biz-entities have the same values, the output to the screen
   *         should indicate those.  But the current sorting method should not be able to identify
   *         entities with same value, it's based on the first come first serve mode.
   */
  public static boolean isQualified(){
      //*** should check the size of shopList, if (shopList.length = 0) return false;

      if (isSorted) return true;
      else if (shopListArr.length <= maxShopListSize) return true;

      return false;
  }

  /** Returns the description of this IDSS process as a String. */
  public String toString(){
      return "GasDecision Process SPA( " + id_GDP + "): finds gas-stations near the user's GPS location.\n" +
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

