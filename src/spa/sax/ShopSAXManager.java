package spa.sax;

import org.xml.sax.*;
import org.xml.sax.helpers.DefaultHandler;

import spa.ShopElement;
import spa.util.Sort;

import java.net.URL;
import java.util.Vector;


/**
 * Title:        ShopSAXManager<br/>
 *
 * Description:  ShopSAXManager provides search methods 
 *               to find business shops, which met the user decision-answer-choice, from
 *               a XML data file.  This is the place you can plug in 
 *               the specific handling functions of a process. <br/><br/>
 *               
 *               When a CityFinder mobile application
 *               requests a search task, the device sends its current GPS 
 *               location to a remote server.
 *               Based on the GPS location, the remote server system
 *               queries data of all the nearby available shops from a database, 
 *               and caches the data in an XML format in its (remote server's) local memory. 
 *               The remote server
 *               then informs CityFinder the availability of the XML data.  
 *               By using this class, 
 *               the CityFinder searches all appropriate business shops from 
 *               the remote target data file, and only stores their ID numbers 
 *               in a local memory (to save memory space in a mobile computer).
 *               The CityFinder continues to use the previous ID list to refine a search and
 *               narrows down the list.<br/><br/>
 * 
 *               This class call a ShopSAXParser for reading 
 *               an XML data source.  In addition to the original SAX methods, 
 *               this class provides additional, refined SPA-specific methods 
 *               to search, compare, and select business entity information from 
 *               an XML data source.<br/><br/>     
 *
 *				 <p>
 *               Moreover, there are several advantages using a SAX parser.  
 *               It does not have to store the whole XML data in a local memory,
 *               so a small computing device can use it.  Also, it can handle unlimited data, and 
 *               can be used in a real-time data discovery application.  
 *               </p>
 *
 * Copyright:    Copyright (c) 2001-2002 Chon Chung.
 * @author       Chon Chung
 * @version      Demo 0.1
 * 
 * <p>          License: Lesser General Public License (LGPL)</p>
 */
public class ShopSAXManager {


  /** the location of the XML shop data file (either a remote url, or local file).  */
  static protected String xmlSource;
  /** Shop list to store all the shop-elements (id) from the
   *  XML data file.  Shops-list those meet the current search criteria. */
  static protected Vector shopList;
  /** The current selected shop-elements ID lists for further searching or comparison. */
  static protected String[] selectedShopList;
  /** Indicates the current searching criteria. It's value can be one of the 
   * ShopSAX_Command class's searching status constant value: such as FIND_ELEMENT, 
   * FIND_ELEMENT_VALUE (ELEMENT with a specific value), or FIND_INFO...  */
  static protected int findCriteria;
  /** Indicates the main Shop Element XML node, 
   * all the shop properties' nodes belong to this node. e.g. '<store/>' */
  static protected String shopMainNode;
  /** The current shop-element Id (a numerical string format). */
  static protected String currentID;
  /** The target XML node for searching. i.e. If we are comparing all the gas stations with 
   *  their Unread gas prices, the target node would be '<unread>'. */
  static protected String targetNode;
  /** The value that we are looking for...  For example, if you are looking
   *  for all the gas stations with Texaco brand, the targetNode should be '<brand>',
   *  and the targetValue should be 'Texaco'*/
  static protected String targetValue;
  /** Indicates the target value type. e.g. String, float, integer, etc. */
  static protected String valueType;
  /** Target value matching criteria. It can be one of 
   *  the ShopSAX_Command class's matching status constant value:
   *  VALUE_EXACT, VALUE_EQUAL_OR_BIGGER, VALUE_EQUAL_OR_SMALLER or etc. */
  static protected int matchingCriteria;
  /** totalNumber of elements */
  static protected int totalEntity = 0;
  /** the index of current entity */
  static protected int entityIndex = 0;
  /** the index of target entity */
  static protected int targetIndex = 0;

  /** Indicates the default searching criteria is finding the business elements
   *  from the lowest value.  If false, the parser will find the elements
   *  from the highest value. */
  static protected boolean b_FindLowest = true;
  /** Indicates the current entity(Shop), which the SAX parser is currently reading, is the target entity. */
  static protected boolean b_TargetEntity = false;
  /** Indicates the current node, which the SAX parser is reading, is the target node. */
  static protected boolean b_TargetNode = false;
  /** Indicates the current search is conducted from the previously selected 'shopList'. */
  static protected boolean b_UsingLastShopList = false;
  /** A Vector list to store shopListVO objects, which consist of an shop ID, one of
   *  the shop's property values.  The list is usually used for a sorting task such
   *  as finding the gas station with the lowest gas price from the Vector list.  */
  static protected Vector shopList_IdValues;
  
  /** ShopSAX_Command class reference, which has commands constants for dictating a task for ShopSAXReader. */
  static protected ShopSAX_Command sax_command = null; 


  
  /** Constructor.   
   * @Param _xmlSource the XML source file location.  
   * @Param _shopNode the main shop entity XML tag name.  "<store>"*/
  public ShopSAXManager(String _xmlSource, String _shopNode){
      xmlSource = _xmlSource;
      shopMainNode = _shopNode;      
      shopList = new Vector(20, 10);// initialCapacity, capacityIncrement
      sax_command = new ShopSAX_Command(); 
      entityTotalShops();
  }
  
  /** Resets this class instance with a different XML data source.  This method
   *  is used to conduct a new search from a different XML data source. 
   * */
  static protected void resetParamsForNewSearch(String _xmlSource, String _shopNode){
      xmlSource = _xmlSource;
      shopMainNode = _shopNode;
      shopList = new Vector(20, 10);// initialCapacity, capacityIncrement
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
      matchingCriteria = sax_command.VALUE_NULL;
      entityIndex = 0;
      b_FindLowest = true;
      b_UsingLastShopList = false;
      shopList_IdValues = null;
      //~~~~~~~~~~~~~~~~~~~
      entityTotalShops();
  }

  /** Resets this class properties to their default values for the next search.  This method
   *  is used to conduct a different search from the same XML data source.
   **/
  static protected void resetParamsForNextSearch(){
      findCriteria = sax_command.FIND_NOTHING;
      shopList = new Vector(20, 10);
      currentID = null;
      selectedShopList = null; //*** Check this, it might erase the selectedShopList  *********************************************
      targetIndex = 0;
      targetNode = null;
      targetValue = null;
      b_TargetEntity = false;
      b_TargetNode = false;
      valueType = null;
      matchingCriteria = sax_command.VALUE_NULL;
      entityIndex = 0;
      b_FindLowest = true;
      b_UsingLastShopList = false;
      shopList_IdValues = null;
  }
  

  static public Vector getShopList() {
  	return shopList;
  }
  static public String getXmlSource() {
	return xmlSource;
  }
  static public String[] getSelectedShopList() {
  	return selectedShopList;
  }

  static public int getFindCriteria() {
  	return findCriteria;
  }

  static public String getShopNode() {
  	return shopMainNode;
  }

  static public String getCurrentID() {
  	return currentID;
  }

  static public String getTargetNode() {
  	return targetNode;
  }

  static public String getTargetValue() {
  	return targetValue;
  }

  static public String getValueType() {
  	return valueType;
  }

  static public int getMatchingCriteria() {
  	return matchingCriteria;
  }

  static public int getTotalEntity() {
  	return totalEntity;
  }

  static public int getEntityIndex() {
  	return entityIndex;
  }

  static public int getTargetIndex() {
  	return targetIndex;
  }

  static public boolean isFindLowest() {
  	return b_FindLowest;
  }

  static public boolean isTargetEntity() {
  	return b_TargetEntity;
  }

  static public boolean isTargetNode() {
  	return b_TargetNode;
  }

  static public boolean isUseShopList() {
  	return b_UsingLastShopList;
  }

  static public Vector getShopListValues() {
  	return shopList_IdValues;
  }

  static public ShopSAX_Command getSax_command() {
  	return sax_command;
  }

  /** Inserts the matching entity's ID to the 'shopList'.  When the search methods
   * find a matching business entity from the target data source of
   * a remote Web server, the search method stores the ID of
   * the business entity to the 'shopList' by using this method. */
  static protected void addEntityToShopList(String id){
    shopList.addElement(id);
  }

  /**
   * <p>
   * Finds the total number of shop-elements from the XML data file.  
   * It sets the "findCriteria" property value to indicate the
   * current task, which is finding the total number of shop ('store') elements from a XML data.
   * <p>
   * A Searching method (give a search term/direction).</p>
   * <p>
   * 
   * How a searching method is used: 
   * First, Sub class (GasSAXManager) method calls a ShopSAXManager search method 
   * to set the search terms.  Then the sub-class-method calls readXML() method 
   * to start XML file reading process and finding the Shops.
   * 
   *
   * <p><b>
   * Check the 'gasfind.sax.GasSAXManager' class's methods for the
   * usage examples.</p>
   */
  static  protected void entityTotalShops(){
        findCriteria = sax_command.FIND_TOTAL;
  }

  /**
   * It reads the given entity (element) information from the XML data source.<br><br>
   * 
   * A Searching method (give a search term/direction).
   */
  static  protected void entityInfo(String _entityId){
        findCriteria = sax_command.FIND_INFO;
        targetValue = _entityId;
  }

  /**
   * From the given elements ID list, search/read the each elements properties' information from the XML data source.<br><br>
   * 
   * A Searching method (give a search term/direction).
   */
  static  protected void entityListInfo(String[] _entityIds){
        resetParamsForNextSearch();

        findCriteria = sax_command.FIND_SHOPLIST_INFO;
        selectedShopList = _entityIds;
  }

  /** Get Shop-Element of the List, and their properties' information. 
   *  Given the stores' ID list, it should return ShopElement objects, which have
   *  the store contact information such as name, address, phone number, 
   *  and etc.  This method should be used to present the stores' contact 
   *  information to a user.  It should further implement the 
   *  'entityListInfo()' method for ease of use. 
   *  
   *  @todo test this method ***************************************************************************************
   *  */
  protected ShopElement[] getShopListInfo(String[] _storeIdList){

      if(_storeIdList.length == 0) return null;

      entityListInfo(_storeIdList);
      ShopElement[] tempList = new ShopElement[_storeIdList.length];
      shopList.copyInto(tempList);

      return tempList;
  }

  /**
   * Finds all the elements that have the tagetNode.<br><br>
   * 
   * 
   * A Searching method (give a search term/direction).
   *
   * For example, by using this method, we should be able to find all the Gas-stations,
   * which has '<diesel>' gas.
   *
   * @Param targetNode the target XML node for searching
   */
  static  protected void entityHasNode(String _targetNode){
        entityHasNode(_targetNode, null);
  }

  /**
   * Find the shops that have the target node from the given shop-elements list.<br><br>
   * A Searching method (give a search term/direction).<br><br>
   *
   * For example, by using this method, we should be able to find all the Gas-stations,
   * which have "diesel" gas from the given elements' list.<br><br>
   *
   * @Param _targetNode the target XML node 
   * @Param _selectedShopList previously-selected elements' list for further define search.
   */
  static  protected void entityHasNode(String _targetNode,
                                  String[] _selectedShopList){
        resetParamsForNextSearch();
        findCriteria = sax_command.FIND_ELEMENT;
        if (_selectedShopList != null) {
          selectedShopList = _selectedShopList;
        }
        targetNode = _targetNode;
  }

  /**
   * Set the class parameters to find the elements 
   * that have the element node with the specified target-Value.<br><br>
   *
   * A Searching method (give a search term/direction).<br><br>
   * 
   * For example, by using this method, we should be able to find all the Gas-stations,
   * which have <brand> tag value equal to "Texaco".<br><br>
   *
   * @Param targetNode the target XML node for searching
   * @Param targetValue the value to match
   */
  static  protected void entityHasNodeWithValue(String _targetNode,
                                       String _targetValue){

        entityHasNodeWithValue(_targetNode, _targetValue, null, 
        		              sax_command.VALUE_EXACT, null);
  }

  /**
   * Set the class parameters to find the elements that have the element node with the targetValue
   * from the given elements list.<br><br>
   *  
   * A Searching method (give a search term/direction).<br><br>
   *
   * For example, by using this method, we should be able to find all the Gas-stations,
   * which have <brand> tag value equal to "Texaco" from the given elements' list.<br><br>
   *
   * @Param targetNode the target XML node for searching
   * @Param targetValue the value to match
   * @Param _selectedShopList previously-selected elements' list for further define searching.
   */
  static  protected void entityHasNodeWithValue(String _targetNode,
                                      String _targetValue,
                                      String[] _selectedShopList){

        entityHasNodeWithValue(_targetNode, _targetValue, null, 
        		              sax_command.VALUE_EXACT, _selectedShopList);
  }

 /**
   * Set this class properties to find the all the elements' list
   * that have the element node with the given value.<br><br>
   * 
   * A Searching method (give a search term/direction).<br><br>
   *
   * @Param targetNode 		the target XML node for searching
   * @Param targetValue 	the target value
   * @Param valueType 		indicate the type of targetValue.  i.e. String, integer, or etc.
   * @Param matchingCriteria
   * @Param _selectedShopList 		previously-selected elements' list for further define searching.
   */
  static  protected void entityHasNodeWithValue(String _targetNode,
                                    String _targetValue,
                                    String _valueType,//*** not for the gas finder
                                    int _matchingCriteria, /* VALUE_EXACT, VALUE_EQUAL_OR_BIGGER, VALUE_EQUAL_OR_SMALLER */
                                    String[] _selectedShopList){ //Vector should be iterator

        resetParamsForNextSearch();
        //set the sorting properties so sax-parser can go through the XML list, and find the elements.
        findCriteria = sax_command.FIND_ELEMENT_VALUE;

        if (selectedShopList != null) {
          selectedShopList = _selectedShopList;
        }

        targetNode = _targetNode;
        targetValue = _targetValue;
        valueType = _valueType;
        matchingCriteria = _matchingCriteria;
  }

  /**
   * Set this class properties to find the all elements
   * that have the item, and sort the elements based on the item value.  
   * The elements' list will be either ascending or descending order based
   * on the 'findLowest' parameter value.<br><br>
   * 
   * 
   * A Searching method (give a search term/direction).<br><br>
   *
   * When the SAX parser reached the end of document, the 'endDocument()' method
   * will be invoked and the method will initiate the sorting process to sort the
   * elements' list based on the item value.<br><br>
   *
   * i.e. By using this method, we should be able to find a list of 
   * all the Gas-stations, and sort them from the lowest to highest gas prices.
   *
    * @param item 			the elements' target item
    * @param b_FindLowest 	indicates to sort the elements' list from the lowest value.
   */
  static  protected void entitySort(String _item, boolean _findLowest){
        entitySort(_item, _findLowest, null);
  }

  /**
   * From the given selectedShopList, set this class properties to find all elements
   * that have the item, and sort the elements' list based on the item value.  
   * The elements' list will be either ascending or descending order based
   * on the 'findLowest' parameter value.<br><br>
   * 
   * A Searching method (give a search term/direction).<br><br>
   *
   * When the SAX parser reached the end of document, the 'endDocument()' method
   * will be invoked and the method will initiate the sorting process to sort the
   * elements' list based on the item value.<br><br>
   *
   * i.e. By using this method, we should be able to find a list of
   * all the Gas-stations, and sort them from the lowest to highest gas prices.
   *
   * @param item 			the elements' target item
   * @param b_FindLowest 		indicates to sort the elements' list from the lowest value.
   * @param _selectedShopList 		previously-selected elements list for further define sorting.
   */
  static  protected void entitySort(String _item, boolean _findLowest, String[] _selectedShopList){

        resetParamsForNextSearch();

        findCriteria = sax_command.FIND_SORTED;
        if (_selectedShopList != null) {
          if (_selectedShopList.length == 0) return;

          selectedShopList = _selectedShopList;
        }
        shopList_IdValues = new Vector(20, 10);
        targetNode = _item;
        b_FindLowest = _findLowest;
  }

  /**
   * Sorts the ShopList Vector.
   *
   * @todo 1. sort the array from the highest value to the lowest.
   *       2. Status: under construction: requires further implementation.
   *
   */
  static public void sortShopListValues(){
      if (b_FindLowest){
          //sort the shopListValues from the the lowest value to the highest
          shopList_IdValues = Sort.sortVector(shopList_IdValues);
      }else {
          //*** Under construction ***
          /**@todo Implement this procedures to sort the list from the highest value to the lowest one*/
          //throw new java.lang.UnsupportedOperationException("ShopSAXManager.sortTempShopList() not yet fully implemented for this situation."); <-- Not in JDK 1.1
          System.out.println("ShopSAXManager.sortTempShopList() not yet fully implemented for this situation.");
          //shopListValues = Sort.selectSort(shopListValues);
      }
      //*** after the sorting insert it into the shopList, and notify the DataSet that it is sorted.
  }

  static public void fatalError (SAXParseException e) throws SAXException {
    System.out.println("ShopSAXManager-Error: " + e);
    throw e;
  }
  

  /**
   *TODO Must override this method in a sub class.  
   *  
   *  Activate a SAXParser class to read a XML data.
   *  */
  static public void readXML_data() throws Exception{
	  throw new Exception("ShopSAXManager.readXML_data(): you must override this method in a Subclass.\n" + 
			              "So, a ShopSAXManager entended SubClass (GasSAXManger) reference can be passed to the SAXReader methods. ");
	  
	  //Example: 
	  //gasSAXParser.read();   
  }
}
