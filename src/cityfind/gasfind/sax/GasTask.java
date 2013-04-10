package cityfind.gasfind.sax;

import java.util.ArrayList;

import cityfind.gasfind.GasElement;
import spa.sax.ShopTask;

/*
 * Gas SAX task class.  GasSAXManager uses this to set the task and 
 * pass this to GasSAXParser to perform SAX XML data reading task.  
 * Then, GasSAXParser stores the result-data into this, and 
 * returns this to GasSAXManager. 
 * 
 * @author       Chon Chung
 * 
 */
public class GasTask extends ShopTask {

	
	  /** Stores the last Shop-entity lists for the undo process */
	  public ArrayList<Object> lastShopList = null;
	  /** stores the current XML tag id. */
	  public  int theCurrentTagId = 0;
	  /** the current shop information reference. */
	  public  GasElement gasShopInfo = null;

	  public GasTask() {
		  super(); 
	  } 
	  
	  public void reset(){
		    lastShopList = null;
		    theCurrentTagId = 0;
		    gasShopInfo = null;
	  }
	  
	  /**
	   * For debugging only. 
	   */
	  @Override
	  public String toString(){
		  String s = "GasTask { shopList:" + shopList + "\n" + 
			  "shopList.size:" + shopList.size() + "\n" + 
			  "currentID:" + currentID + "\n" + 
			  "findCriteria:" + findCriteria + "\n" + 
			  "matchingCriteria:" + matchingCriteria + "\n" + 
			  "selectedShopList:" + selectedShopList + "\n" + 	  
			  "shopMainNode:" + shopMainNode + "\n" + 
			  "targetNode:" + targetNode + "\n" + 
			  "targetValue:" + targetValue + "\n" + 
			  "valueType:" + valueType + "\n" + 
			  "shopList_IdValues:" + shopList_IdValues + "\n" + 
			  "b_FindLowest:" + b_FindLowest + "\n" + 
			  "b_TargetEntity:" + b_TargetEntity + "\n" + 
			  "b_TargetNode:" + b_TargetNode + "\n" + 
			  "b_UsingLastShopList:" + b_UsingLastShopList + "\n" + 
			  "totalEntity:" + totalEntity + "\n" + 
			  "entityIndex:" + entityIndex + "\n" + 
			  "targetIndex:" + targetIndex + "\n" +
		  	  "lastShopList:" + lastShopList + "\n" + 			  
			  "theCurrentTagId:" + theCurrentTagId + "\n" + 
			  "gasShopInfo:" + gasShopInfo + "};";		  
		  return s; 		  
	  }
}
