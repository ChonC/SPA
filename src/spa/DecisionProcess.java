package spa;

import java.net.URL;

import spa.dph.DPHwriter;
import spa.question.Question;


/**
 * Title:        DecisionProcess
 *
 * <p>Description:  DecisionProcess ask a user decision-questions and get answers,
 *                  and record the processes for automation. This class provides 
 *                  functions for record Decision Processes for automation. </P>
 *                  
 *                  After we developed the Decision Questions, we logically place them 
 *                  in this DecisionProcess class. The DecisionProcess class should be able 
 *                  to interact with a user to collect his/her decision processes 
 *                  by presenting the questions and collecting answers in a logical manner. 
 *                  By extending the super classes, we can develop the DecisionProcess classes.
 *
 * Note:            To support sub-classes' static methods, DecisionProcess should not
 *                  be an Interface.
 *
 *
 * Copyright:    Copyright (c) 2001-2004 Chon Chung.
 * @author Chon Chung
 * @version Demo 0.7
 * 
 * 
 * <p>          License: Lesser General Public License (LGPL)</p> */

abstract public class DecisionProcess {

  /** Task status Constant.  Indicates the SPA decision task is in progressing. */
  public static final int TASK_IN_PROGRESSING = 1;
  /** Task status Constant.  Indicates the SPA decision task has been completed. */
  public static final int TASK_DONE = 2;


  /** ID of this class instance. Format = "Name::version::date" */
  protected static String id_DP;
  /** the shop's ID list, which met the current searching criterion. */
  protected static String[] shopListArr;
  /** Indicate the SPA Task status. */
  protected static int taskStatus;
  /** Reference for the next question. */
  protected static Object refNextQuestion;
  /** Indicates the current store list has been sorted based on one of the store's property value. */
  protected static boolean isSorted;
  /** Indicates the current question is the last question. */
  protected static boolean isLastQuestion;
  /** The maximum allowable size of final shop list before report it to a user.
   *  The size should be small enough for a person to able to summarize it.
   *  However, if the shopList has been either sorted or has a numeric rank, 
   *  this 'maxShopListSize' is no longer necessary. */
  protected static int maxShopListSize;
  /** Stores the current user Decision Process History in a XML format for the one-click auto process. */
  protected static String userDPH;


  /** Default constructor. */
  public DecisionProcess(){
      id_DP = "DecisionProcess::demo-0.7::12-9-2001";//Note: it is the first presentation day of this application (Dec/09/2001) at Houston Java Users Group (www.hjug.org). I did not have the communication skill to do a presentation at the time, but I could demo a program.  That is how it start...
      taskStatus = TASK_IN_PROGRESSING;
      isSorted = false;
      isLastQuestion = false;
      maxShopListSize = 10;
      userDPH = DPHwriter.newDPH(id_DP); //DPHwriter writes a user Decision Processes History to a xml file.  
  }

  
  /** Reset this class properties values for the one-click auto search. */
  public void resetForAuto(){
      taskStatus = TASK_IN_PROGRESSING;
      isSorted = false;
      isLastQuestion = false;
      maxShopListSize = 10;
  }

  public static void setNextQuestion(Object question){
      refNextQuestion = question;
  }
  /**
   * Abstract method. To dictate the implementation of getNextQuestion() method in a sub classes.
   **/
  abstract public Question getNextQuestion();

  public static void setTaskStatus(int status){
      taskStatus = status;
  }
  public static int getTaskStatus(){
      return taskStatus;
  }

  public static void setShopList(String[] newList){
    shopListArr = newList;
  }
  public static void resetShopList(){
    shopListArr = null;
  }

  /** Add a new shop-entities list to the existing list.
   *  @toDo: need to improve the combining two arrays procedures //******************************************************
   **/
  public static void addToShopList(String[] newList){
      String[] combineList;

      int i = 0;
      if (shopListArr == null){
          combineList = new String[newList.length];
      }else{
          combineList = new String[shopListArr.length + newList.length];
          for ( ;i < shopListArr.length;i++)
              combineList[i] = shopListArr[i];
      }

      for (int j=0; j<newList.length;j++,i++)
          combineList[i] = newList[j];

      shopListArr = combineList;
  }

  /** Returns a duplicated copy of the ShopList[] */
  public static String[] getShopListCopy(){
    String[] copyArray = new String[shopListArr.length];
    for (int i=0; i < shopListArr.length; i++)
      copyArray[i] = shopListArr[i];

    return copyArray;
  }

  /** Returns an array of ShopElement array objects, which have shops' contact information */
  abstract public ShopElement[] getShopListInfo();

  /** Returns the size of the ShopElement List. */
  public static int getSizeOfShopList(){
    return shopListArr.length;
  }

  /** Checks whether the ShopList has elements or not. */
  public static boolean isShopListEmpty(){
    return (shopListArr.length == 0) ? true:false;
  }

  /**
   * @note: not used method.  Ignore it.//************************************************************************************
   * @toDo: check and delete this method 
   *  Execute a decision Interaction process. */
  public static void executeDP(){
  /**  Since the store list in the xml file is in numeric sequential format by their Identification numbers,
       got to change the sorted shoplist back into the numeric sequential format before executing the
       sax-xml parser.  Then after the process is finished, we have to sort them back into the
       original sorted-format. */
       if (isSorted) ;//unSortedData

    //*** After finish sortBack to the data.
    //sortBack(OriginalList, newList);
  }

  /** Checks the current shopList that if it has been sorted. 
   *  Since a SAX XML parser reads a file from the beginning to end,
   *  we need to sort the current shopList[] array in ascending order 
   *  to read the XML file from the beginning.*/
  public static boolean isSorted(){
      return isSorted;
  }

  public static void setSorted(boolean condition){
      isSorted = condition;
  }


  /**
   *  Check the final ShopList condition.
   *
   *  Before presenting the final ShopList to a client, we should check the list
   *  condition.  The store list should be either sorted or be small enough 
   *  for a person to be able to summarize it.  If the ShopList is too big or unsorted,
   *  it will not provide any meaningful information to a user.
   *
   *  If the final ShopList fails to meet the conditions, we should do more Decision processes. 
   *
   *  @toDo: If more than one shop-entities have the same values, the current sorting method does not
   *         handle this condition properly.  Need to complete the sort-rank property in the ShopElement class. //***
   *  @todo  if (shopList = 0) return false; should check the size of shopList 
   */
  public static boolean isQualified(){
      //if (shopList = 0) return false;   //@todo should check the size of shopList 
      if (isSorted) return true;
      if (shopListArr.length <= maxShopListSize) return true;
      return false;
  }

  public String toString(){
      return "DecisionProcess (" + id_DP + "): records Decision process.";
  }

  /** Add the current user answers to the user DPH (Decision Process History) file
  *  as an XML format.  The saved DPH would be used for one-click automation.*/
  public static void addAnswerToDPH (String questionId, String answer){
      userDPH = DPHwriter.addAnswer(userDPH, questionId, answer);
  }

  /** Add multiple answers to the DPH (Decision Process History) file.
  *  When the question has more than one answer, this method would be used.*/
  public static void addAnswersToDPH(String questionId,
                                       String[] answers){
      userDPH = DPHwriter.addAnswers(userDPH, questionId, answers);
  }

  /** Returns the User Decision Process History. */
  public static String getUserDPH(){
    return userDPH;
  }

  /** Returns the Shop information. */
  abstract public String getShopInfo(String shopId);

 /** Prints the shopList to the console-Screen. For TESTING ONLY!!! */
  public static void printShopList(){
        printShopList(id_DP);
  }

  /**Prints the shopList to the console-Screen. For TESTING ONLY!!!  */
  public static void printShopList(String iD){

        System.out.println("DecisionProcess( " + iD + "): printShopList----------(Start)--------------------------------");
            for (int i=0; i < shopListArr.length; i++){
                System.out.println("shopList[" + i + "] = " + shopListArr[i] );
            }
        System.out.println("DecisionProcess( " + iD + "): printShopList-----------(End)---------------------------------");
  }
}