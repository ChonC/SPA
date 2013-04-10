package spa.sax;

import static spa.sax.ShopSAX_Command.*;

import java.util.ArrayList;
import java.util.Vector;

import spa.util.Sort;

/**
 * Shop SAX task class.  ShopSAXManager uses this to set the task and 
 * pass this to ShopSAXParser to perform SAX XML data reading task.  Then, ShopSAXParser stores the result-data into this, and 
 * returns this to ShopSAXManager. 
 * 
 * @author Chon Chung
 *
 */
public class ShopTask {

	  /** Shop ArrayList Object to store all the Shops-list those meet the current search criteria. 
	   * It may collect String[id], String[id, value], or ShopElement Object. */
	  public ArrayList<Object> shopList = new ArrayList<Object>();
	  
	  /** The current shop-element Id (a numerical string format). */
	  public String currentID = null;
	  /** Indicates the current searching criteria. It's value can be one of the 
	   * ShopSAX_Command class's searching status value: such as FIND_ELEMENT, 
	   * FIND_ELEMENT_VALUE (ELEMENT with a specific value), or FIND_INFO...  */
	  public int findCriteria = C_FIND_NOTHING; 
	  /** Target value matching criteria. It can be one of 
	   *  the ShopSAX_Command class's matching status value:
	   *  VALUE_EXACT, VALUE_EQUAL_OR_BIGGER, VALUE_EQUAL_OR_SMALLER or etc. */
	  public int matchingCriteria = C_VALUE_NULL;
	  

	  /** The current selected shop-elements ID lists for further searching or comparison. */
	  public String[] selectedShopList = null;
	  /** Indicates the main Shop Element XML node, 
	   * all the shop properties' nodes belong to this node. e.g. '<store/>' */
	  public String shopMainNode;
	  /** The target XML node for searching. i.e. If we are comparing all the gas stations with 
	   *  their Unread gas prices, the target node would be '<unread>'. */
	  public String targetNode = null;
	  /** The value that we are looking for...  For example, if you are looking
	   *  for all the gas stations with Texaco brand, the targetNode should be '<brand>',
	   *  and the targetValue should be 'Texaco'*/
	  public String targetValue = null;
	  /** Indicates the target value type. e.g. String, float, integer, etc. */
	  public String valueType = null;
	  /** A Vector list to store shopListVO objects, which consist of an shop ID, one of
	   *  the shop's property values.  The list is usually used for a sorting task such
	   *  as finding the gas station with the lowest gas price from the Vector list.  */
	  public Vector shopList_IdValues = null;

	  
	  /** Indicates the default searching criteria is finding the business elements
	   *  from the lowest value.  If false, the parser will find the elements
	   *  from the highest value. */
	  public boolean b_FindLowest = true;
	  /** Indicates the current entity(Shop), which the SAX parser is currently reading, is the target entity. */
	  public boolean b_TargetEntity = false;
	  /** Indicates the current node, which the SAX parser is reading, is the target node. */
	  public boolean b_TargetNode = false;
	  /** Indicates the current search is conducted from the previously selected 'shopList'. */
	  public boolean b_UsingLastShopList = false;
	  
	  
	  /** totalNumber of elements */
	  public int totalEntity = 0;
	  /** the index of current entity */
	  public int entityIndex = 0;
	  /** the index of target entity */
	  public int targetIndex = 0;
	  
	  public ShopTask(){}
	  

	  /** Resets this properties to conduct a new search from a different XML data source. . 
	   * */
	  public void resetForNewSearch(String _shopNode){
	      shopMainNode = _shopNode;
	      shopList =  new ArrayList<Object>(); 
	      totalEntity = 0;
	      //~~~~~~~~~~~~~~~~~~~
	      currentID = null;
	      selectedShopList = null;
	      targetIndex = 0;
	      targetNode = null;
	      targetValue = null;
	      b_TargetEntity = false;
	      b_TargetNode = false;
	      valueType = null;
	      matchingCriteria = C_VALUE_NULL;
	      entityIndex = 0;
	      b_FindLowest = true;
	      b_UsingLastShopList = false;
	      shopList_IdValues = null;
	  }
	  

	  /** Resets this properties to their default values for the next search from the same XML data source.
	   *  
	   **/
	  public void resetForNextSearch(){
	      findCriteria = C_FIND_NOTHING;
	      shopList =  new ArrayList<Object>(); 
	      currentID = null;
	      //selectedShopList = Note: do not reset selectedShopList, if you want to further search from the last select list
	      targetIndex = 0;
	      targetNode = null;
	      targetValue = null;
	      b_TargetEntity = false;
	      b_TargetNode = false;
	      valueType = null;
	      matchingCriteria = C_VALUE_NULL;
	      entityIndex = 0;
	      b_FindLowest = true;
	      b_UsingLastShopList = false;
	      shopList_IdValues = null;
	  }
	  

	  /** Resets this properties to their default values for the fresh new search from the same XML data source.
	   *  It also reset selectedShopList (last searched list). 
	   **/
	  public void resetForFreshSearch(){
	      findCriteria = C_FIND_NOTHING;
	      shopList =  new ArrayList<Object>(); 
	      currentID = null;
	      selectedShopList = null; //reset for fresh next search
	      targetIndex = 0;
	      targetNode = null;
	      targetValue = null;
	      b_TargetEntity = false;
	      b_TargetNode = false;
	      valueType = null;
	      matchingCriteria = C_VALUE_NULL;
	      entityIndex = 0;
	      b_FindLowest = true;
	      b_UsingLastShopList = false;
	      shopList_IdValues = null;
	  }
	  

	  /**
	   * Sorts the ShopList Vector.
	   *
	   * TODO  1. sort the array from the highest value to the lowest.
	   *       2. Status: under construction: requires further implementation.
	   *
	   */
	  public void sortShopListValues(){
	      if (b_FindLowest){
	          //sort the shopListValues from the the lowest value to the highest
	    	  shopList_IdValues = Sort.sortVector(shopList_IdValues);
	      }else {
	          //*** Under construction ***
	          /**TODO Implement this procedures to sort the list from the highest value to the lowest one*/
	          //throw new java.lang.UnsupportedOperationException("ShopSAXManager.sortTempShopList() not yet fully implemented for this situation."); <-- Not in JDK 1.1
	          System.out.println("ShopSAXManager.sortTempShopList() not yet fully implemented for this situation.");
	          //shopListValues = Sort.selectSort(shopListValues);
	      }
	      //*** after the sorting insert it into the shopList, and notify the DataSet that it is sorted.
	  }
	  

	  /** Inserts the matching entity's ID to the 'shopList'.  When the search methods
	   * find a matching business entity from the target data source of
	   * a remote Web server, the search method stores the ID of
	   * the business entity to the 'shopList' by using this method. */
	  public void addEntityToShopList(String id){
		  shopList.add(id);
	  }
	  
	  /*
	   * For debugging only. 
	   */
	  @Override
	  public String toString(){
		  String s = "ShopTask { shopList:" + shopList + "\n" + 
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
			  "targetIndex:" + targetIndex + "};";		  
		  return s; 		  
	  }
}
