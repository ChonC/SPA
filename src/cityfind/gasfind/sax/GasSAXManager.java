package cityfind.gasfind.sax;

import cityfind.gasfind.GasElement;

import org.xml.sax.*;

import spa.ShopElement;
import spa.sax.ShopSAXManager;
import spa.sax.ShopTask; 
import spa.sax.ShopSAX_Command;
import spa.util.ShopVO;

import java.util.ArrayList;
import java.util.Vector;
import java.io.*;
import java.net.*;//<-- for URL reading

/**
 * <p>Title:        CityFinder (SPA Demo Applet)</p> *
 * <p><b>
 * Description:</b> GasSAXManager provides
 *                  the following search methods to find the gas stations
 *                  from the XML data-source based on the each Decision Factor.<br>
 *
 *                  findBrandStore(DF: brandName): finds all the stations with the Brand.<br>
 *                  findStoreHasGas(DF: Type of Gas): finds all the stations have the Type of gas.<br>
 *                  sortLowestGas(DF: price): sort the gas stations list from the lowest
 *                                            gas price to highest. <br>
 *                  sortShortestDistance(DF: distance): sort the gas stations list from the nearest one
 *                                                      to the farthest one. </p>
 *
 *                  This class extends 'spa.sax.ShopSAXManager' class and
 *                  shows how to extend and utilize the 'spa' package and its classes.
 *
 * <p><b>
 * Idea:</b>        As you see in this class, it is quite simple because the
 *                  super class (ShopSAXManager), which provides all the SPA
 *                  functionalities.  This class just simply provides the
 *                  instructions for the super class methods to do the work.
 *                  Because of its simplicity, we might be able to further develop
 *                  encapsulate all the complexities.  So, a simple XML instruction
 *                  would define each SPA module characteristic to perform
 *                  many different SPA processes.</p> 
 *                  
 *                  <p>
 *                  This XML instruction capability would bring new
 *                  possibilities.  Since XML is easy to develop and manage by
 *                  using a GUI window, a non-programmer might be able to develop
 *                  the SPA XML instruction by using the GUI.  Even a non programmer
 *                  might be able to program SPA application by simply using
 *                  the GUI windows.   </p>
 *					<p>
 *                  Moreover, there are several advantages using a SAX parser.  
 *                  It do not have to store the whole XML data in a local memory,
 *                  so a small computing device can use it.  Also, it can handle unlimited data, and 
 *                  can be in a use real-time data discovery application.  
 *                  </p>
 *
 * <p>
 * TODO             1. For the networking application, it needs to implement the Thread-runnerable,
 *                  wait(), and notify() for validating the network xml-parsing process.<br>
 *                  2. In the 'endOfDocument()' method, it should use java-thread to notify 
 *                   the finish process event to the called class.</p> 
 * 
 * <p>Copyright:    Copyright (c) 2001-2002 Chon Chung.</p>
 * <p>@author       Chon Chung</p>
 * <p>@version      Demo 0.5</p>
 * 
 * 
 * <p>          License: Lesser General Public License (LGPL)</p>
 *
 */


public class GasSAXManager extends ShopSAXManager {	

	  protected static GasTask task = new GasTask();
	  
	    
	  /** URL reference for reading the XML data information from a URL.openstream. */
	  static protected  URL url=null;
	  /** GasReadXML local reference */ 
	  static protected  GasSAXParser gasSAXParser; 
    
  /** Constructor, it calls the super.constructor to initialize its
   *  instance.  
   *  
   *  
   * @param _xmlSource  the XML data file source.
   * @param _shopNode    The main business entity XML node. i.e. '<store/>'
   */
  public GasSAXManager(String _xmlSource, String _shopNode){
    super(_xmlSource); 
    super.setTask(task, _shopNode);
    url=null; 

	gasSAXParser = new GasSAXParser(url, _xmlSource, task);  
  }



  /** Constructor, it calls the super.constructor to initialize its
   *  instance.  
   *  
   *  
   * @param _url        the URL for a remote XML data file location
   * @param _xmlSource  the XML data file source.
   * @param _shopNode    The main business entity XML node. i.e. '<store/>'
   */
  public GasSAXManager(URL _url, String _xmlSource, String _shopNode){
	  super(_xmlSource); 
	  super.setTask(task, _shopNode);
	  url= _url; 

	  gasSAXParser = new GasSAXParser(url, _xmlSource, task);  
  }	

  
  /** Resets this class instance with the given XML data-source.  This method
   *  is used to conduct a new search from the given new XML data-source.
   *
   * @param xmlSource  the XML data file source.
   * @param shopNode    The main business entity XML node. i.e. '<store/>' */
  public void reset(String _xmlSource, String _shopNode){
    super.resetForNewSearch(_xmlSource, _shopNode);
    gasSAXParser.reset(null, _xmlSource); 
    url=null;
    task.reset();     
  }

  /** Resets this class instance with the given XML data-source.  This method
   *  is used to conduct a new search from the given new XML data-source.
   *
    * @param url          the URL for a remote XML data-source
    * @param xmlSource    local XML data-file source for when a url connection is unavailable.  This is only for demo.  When a network connection is not available, this file is used for demo.
    * @param shopNode       The main business entity XML node. i.e. '<store/>' */
  public void reset(URL _url, String _xmlSource, String _shopNode){
    super.resetForNewSearch(_xmlSource, _shopNode);
    gasSAXParser.reset(_url, _xmlSource); 
    url = _url;    

    task.reset(); 
  }


  //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
  /** Finds all the stores that sell the given gas type.
   *
   *  Implementation detail: First, by calling the super.entityHasElement() method, it sets the
   *  the search terms (Finds all the elements that have the tagetNode(gas-type)), 
   *  then activates readXML_data(), which calls GasSAXParser's inherited read() method.  
   *  During the reading,
   *  the SAX-parser finds the shops, those match with the search terms, 
   *  and stores their id to the  shopList.  
   *  
   *  For example, when the parser find the gas-station
   *  IDs, it stores them to the shopList.  
   *
   * @param gasType   The name of gas type. i.e. 'unlead'
   * @return    	  A string array of gas-station IDs.
   */
  public String[] findShopHasGas(String _gasType){
    super.entityHasNode(_gasType);
    
    readXML_data(); 

        String[] duplicateArray = new String[task.shopList.size()];
        task.shopList.toArray(duplicateArray);
    return duplicateArray;
  }
  /** 
   * Finds all stores that sells the gas type from the given selectedShopList.
   *
   * @param gasType     The name of gas type. i.e. 'unlead'
   * @param _selectedShopList  The array of gas-stations' ID list.  Search will be conducted
   *                    from this list.
   * @return    	    A string array of gas-station Identification numbers.
   */
  public String[] findShopHasGas(String _gasType, String[] _selectedShopList){
    super.entityHasNode(_gasType,  _selectedShopList);
    readXML_data(); 

        String[] duplicateArray = new String[task.shopList.size()];
        task.shopList.toArray(duplicateArray);
    return duplicateArray;
  }
  //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
  /** Finds stores with a certain brand. ex) Chevron, Exxon, Shell, or Texaco */
  public String[] findBrandShops(String _brandName){
	super.entityHasNodeWithValue("brand", _brandName);
    readXML_data(); 
    
        String[] duplicateArray = new String[task.shopList.size()];
        task.shopList.toArray(duplicateArray);
    return duplicateArray;
  }
  /** Finds stores with a certain brand. ex) Chevron, Exxon, Shell, or Texaco */
  public String[] findBrandShops(String _brandName, String[] _selectedShopList){
    super.entityHasNodeWithValue("brand", _brandName, _selectedShopList);
    readXML_data(); 

        String[] duplicateArray = new String[task.shopList.size()];
        task.shopList.toArray(duplicateArray);
    return duplicateArray;
  }
  //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
  /** Sorts the gas stations lists from the lowest gas price 
   *  to the highest price. */
  public String[] sortLowestGas(String _gasType){
    super.entitySort(_gasType, true);
    readXML_data(); 

    return getShopList_idValuesArray();
  }
  /** Sorts the gas stations lists from the lowest gas price
   *  to the highest price. */
  public String[] sortLowestGas(String _gasType, String[] _selectedShopList){
    if(_selectedShopList.length == 0) return null;

    super.entitySort(_gasType, true, _selectedShopList);
    readXML_data(); 

    return getShopList_idValuesArray();
  }

  //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
  /** Finds and sorts gas stations lists from the shortest distance to the longest one. */
  public String[] sortShortestDistance(){
    super.entitySort("distance", true);
    readXML_data(); 

    return getShopList_idValuesArray();
  }

  /** Finds and sorts gas stations lists from the shortest distance to the longest one. */
  public String[] sortShortestDistance(String[] _selectedShopList){
    if(_selectedShopList.length == 0) return null;

    super.entitySort("distance", true, _selectedShopList);
    readXML_data(); 

    return getShopList_idValuesArray();
  }

  private String[] getShopList_idValuesArray(){
      String[] duplicateArray = new String[task.shopList_IdValues.size()];
          for (int i=0; i< task.shopList_IdValues.size(); i++)
              duplicateArray[i] = Integer.toString(((ShopVO)task.shopList_IdValues.elementAt(i)).getId());

      return duplicateArray;
  }
  //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
  /** Extract the shop information from the XML source. */
  public String getGasShopInfo(String _shopId){

	  task.gasShopInfo = new GasElement();//reset the Store
    super.entityInfo(_shopId);
    readXML_data(); 

    return task.gasShopInfo.toString();
  }

  /** Extract the shops' information from the XML source. 
   * 
   * @param _shopIdList        shop-id-list to get the each shopElememt information
   * @return ShopElement[]  	Returns ShopElement[] or null */
  public ShopElement[] getShopListInfo(String[] _shopIdList){

      if(_shopIdList.length == 0) return null; 

      super.entityListInfo(_shopIdList);
      readXML_data(); 
      
      ShopElement[] tempList = new GasElement[_shopIdList.length];
      task.shopList.toArray(tempList);

      return tempList;

  }

   /**
   *  Activate a gasSAXParser to read the business XML data from
   *  the specified file location.
   *  */
  public void readXML_data() {
	  
	  String selectedShopList_length = "0"; 
	  if(task.selectedShopList != null){
		  selectedShopList_length =  "" + task.selectedShopList.length;
      }
	  gasSAXParser.read();   
  }

  //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
}
