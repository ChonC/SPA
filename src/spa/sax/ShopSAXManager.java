package spa.sax;

import org.xml.sax.*;
import org.xml.sax.helpers.DefaultHandler;

import cityfind.gasfind.GasDecisionProcess;

import spa.ShopElement;
import spa.util.Sort;

import java.net.URL;
import java.util.ArrayList;
import java.util.Vector;

import static spa.sax.ShopSAX_Command.*; 


/**
 * Title:        ShopSAXManager<br/>
 *
 * Description:  ShopSAXManager provides search methods 
 *               to find business shops, which met the user decision-answer-choice, from
 *               a XML data file.  This is the class you can plug in 
 *               the specific handling functions of your search functions. <br/><br/>
 *               
 *               
 * Implementation detail:
 *               When a CityFinder mobile application
 *               requests a search task, the device would sends its current GPS 
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
	  protected String xmlSource;
	  	  
	  protected ShopTask task = null; 
	  
	  
  /** Constructor.   
   * @Param _xmlSource the XML source file location.  
   * @Param _shopNode the main shop entity XML tag name.  "<store>"*/
  public ShopSAXManager(String _xmlSource){
      xmlSource = _xmlSource;  
      //shopMainNode = _shopNode;      
  }
  
  protected void setTask(ShopTask _task, String _shopNode){
	  task = _task; 
      task.shopMainNode = _shopNode; 
      entityTotalShops();
  }
  
  /** Resets this class instance with a different XML data source.  This method
   *  is used to conduct a new search from a different XML data source. 
   * */
  protected void resetForNewSearch(String _xmlSource, String _shopNode){
      xmlSource = _xmlSource;
      task.resetForNewSearch(_shopNode); 

      entityTotalShops();
  }

  /** Resets this properties to their default values for the next search from the same XML data source.
   *  
   *  Note: It does not reset selectedShopList because it searches from the last searched list.  
   *  If you want do fresh-search, use resetForFreshSearch() method. 
   **/
  protected void resetForNextSearch(){
	  task.resetForNextSearch(); 	  
  }
  

  /** Resets this properties to their default values for the fresh new search from the same XML data source.
   *  It also reset selectedShopList (last searched list). 
   **/
  protected void resetForFreshSearch(){
	  task.resetForFreshSearch(); 	  
  }
  

  public ArrayList<Object> getShopList() {
  	return task.shopList;
  }
  public String getXmlSource() {
	return xmlSource;
  }
  public String[] getSelectedShopList() {
  	return task.selectedShopList;
  }

  public int getFindCriteria() {
  	return task.findCriteria;
  }

  public String getShopNode() {
  	return task.shopMainNode;
  }

  public String getCurrentID() {
  	return task.currentID;
  }

  public String getTargetNode() {
  	return task.targetNode;
  }

  public String getTargetValue() {
  	return task.targetValue;
  }

  public String getValueType() {
  	return task.valueType;
  }

  public int getMatchingCriteria() {
  	return task.matchingCriteria;
  }

  public int getTotalEntity() {
  	return task.totalEntity;
  }

  public int getEntityIndex() {
  	return task.entityIndex;
  }

  public int getTargetIndex() {
  	return task.targetIndex;
  }

  public boolean isFindLowest() {
  	return task.b_FindLowest;
  }

  public boolean isTargetEntity() {
  	return task.b_TargetEntity;
  }

  public boolean isTargetNode() {
  	return task.b_TargetNode;
  }

  public boolean isUseShopList() {
  	return task.b_UsingLastShopList;
  }

  public Vector getShopListValues() {
  	return task.shopList_IdValues;
  }


  /**
   * <p>
   * Finds the total number of shop-elements from the XML data file.  
   * 
   * Implementation detail: It sets the "ShopTask.findCriteria" property value to indicate the
   * current task, which is finding the total number of shop ('store') elements from a XML data.
   * <p>
   * A Search method (give a search term/direction).</p>
   * <p>
   * 
   * Implementation detail: 
   * First, Sub class (GasSAXManager) method calls a ShopSAXManager search method 
   * to set the search terms.  Then the sub-class-method calls readXML() method 
   * to start XML file reading process to find the Shops.
   * 
   *
   * <p><b>
   * Check the 'gasfind.sax.GasSAXManager' class's methods for the
   * usage examples.</p>
   * 
   * @see gasfind.sax.GasSAXManager
   */
  protected void entityTotalShops(){
	  task.findCriteria = C_FIND_TOTAL;
  }

  /**
   * It reads the given entity (element) information from the XML data source.<br><br>
   * 
   * A search method(give a search term/direction).
   */
  protected void entityInfo(String _entityId){
	  task.findCriteria = C_FIND_INFO;
	  task.targetValue = _entityId;
  }

  /**
   * From the given elements ID list, search/read the each elements properties' information from the XML data source.
   * Stores them as ShopElement[], and put into the ShopSAXManager.shopList. 
   * 
   * A search method(give a search term/direction).
   */
  protected void entityListInfo(String[] _entityIds){
        resetForNextSearch();

        task.findCriteria = C_FIND_SHOPLIST_INFO;
        task.selectedShopList = _entityIds;
  }

  /** Get Shop-Element of the List, and their properties' information. 
   *  Given the stores' ID list, it should return ShopElement objects, which have
   *  the store contact information such as name, address, phone number, 
   *  and etc.  This method should be used to present the stores' contact 
   *  information to a user.  It should further implement the 
   *  'entityListInfo()' method for ease of use. 
   *  
   * @param _storeIdList        store-id-list to get the each shopElememt information
   * @return ShopElement[]  	Returns ShopElement[] or null
   *  
   * TODO test this method ***************************************************************************************
   *  */
  protected ShopElement[] getShopListInfo(String[] _storeIdList){

      if(_storeIdList.length == 0) return null;

      entityListInfo(_storeIdList);
      ShopElement[] tempList = new ShopElement[_storeIdList.length];
      task.shopList.toArray(tempList);

      return tempList;
  }

  /**
   * Finds all the elements that have the tagetNode.<br><br>
   * 
   * 
   * A search method(give a search term/direction).
   *
   * For example, by using this method, we should be able to find all the Gas-stations,
   * which has '<diesel>' gas.
   *
   * @Param targetNode the target XML node for searching
   */
  protected void entityHasNode(String _targetNode){
        entityHasNode(_targetNode, null);
  }

  /**
   * Find the shops that have the target node from the given shop-elements list.<br><br>
   * A search method(give a search term/direction).<br><br>
   *
   * For example, by using this method, we should be able to find all the Gas-stations,
   * which have "diesel" gas from the given elements' list.<br><br>
   *
   * @Param _targetNode the target XML node 
   * @Param _selectedShopList previously-selected elements' list for further define search.
   */
  protected void entityHasNode(String _targetNode,
                                  String[] _selectedShopList){
        resetForNextSearch();
        task.findCriteria = C_FIND_ELEMENT;
        if (_selectedShopList != null) {
        	task.selectedShopList = _selectedShopList;
        }
        task.targetNode = _targetNode;
  }

  /**
   * Set the class parameters to find the elements 
   * that have the element node with the specified target-Value.<br><br>
   *
   * A search method(give a search term/direction).<br><br>
   * 
   * For example, by using this method, we should be able to find all the Gas-stations,
   * which have <brand> tag value equal to "Texaco".<br><br>
   *
   * @Param targetNode the target XML node for searching
   * @Param targetValue the value to match
   */
  protected void entityHasNodeWithValue(String _targetNode,
                                       String _targetValue){

        entityHasNodeWithValue(_targetNode, _targetValue, null, 
        		              C_VALUE_EXACT, null);
  }

  /**
   * Set the class parameters to find the elements that have the element node with the targetValue
   * from the given elements list.<br><br>
   *  
   * A search method(give a search term/direction).<br><br>
   *
   * For example, by using this method, we should be able to find all the Gas-stations,
   * which have <brand> tag value equal to "Texaco" from the given elements' list.<br><br>
   *
   * @Param targetNode the target XML node for searching
   * @Param targetValue the value to match
   * @Param _selectedShopList previously-selected elements' list for further define searching.
   */
  protected void entityHasNodeWithValue(String _targetNode,
                                      String _targetValue,
                                      String[] _selectedShopList){

        entityHasNodeWithValue(_targetNode, _targetValue, null, 
        		              C_VALUE_EXACT, _selectedShopList);
  }

 /**
   * Set this class properties to find the all the elements' list
   * that have the element node with the given value.<br><br>
   * 
   * A search method(give a search term/direction).<br><br>
   *
   * @Param targetNode 		the target XML node for searching
   * @Param targetValue 	the target value
   * @Param valueType 		indicate the type of targetValue.  i.e. String, integer, or etc.
   * @Param matchingCriteria
   * @Param _selectedShopList 		previously-selected elements' list for further define searching.
   */
  protected void entityHasNodeWithValue(String _targetNode,
                                    String _targetValue,
                                    String _valueType,//*** not for the gas finder
                                    int _matchingCriteria, /* VALUE_EXACT, VALUE_EQUAL_OR_BIGGER, VALUE_EQUAL_OR_SMALLER */
                                    String[] _selectedShopList){ //Vector should be iterator

        resetForNextSearch();
        //set the sorting properties so sax-parser can go through the XML list, and find the elements.
        task.findCriteria = C_FIND_ELEMENT_VALUE;

        if (_selectedShopList != null) {
        	task.selectedShopList = _selectedShopList;
        }

        task.targetNode = _targetNode;
        task.targetValue = _targetValue;
        task.valueType = _valueType;
        task.matchingCriteria = _matchingCriteria;
  }

  /**
   * Set the Task properties to find the all elements
   * that have the item, and sort the elements based on the item value.  
   * The elements' list will be either ascending or descending order based
   * on the 'findLowest' parameter value.<br><br>
   * 
   * 
   * A search method(give a search term/direction).<br><br>
   *
   * Implementation detail: when the SAX parser reached the end of document, the 'endDocument()' method
   * will be invoked and the method will initiate the sorting process to sort the
   * elements' list based on the item value.<br><br>
   *
   * i.e. By using this method, we should be able to find a list of 
   * all the Gas-stations, and sort them from the lowest to highest gas prices.
   *
    * @param item 			the elements' target item
    * @param b_FindLowest 	indicates to sort the elements' list from the lowest value.
   */
  protected void entitySort(String _item, boolean _findLowest){
        entitySort(_item, _findLowest, null);
  }

  /**
   * From the given selectedShopList, set the Task class properties to find all elements
   * that have the item, and sort the elements' list based on the item value.  
   * The elements' list will be either ascending or descending order based
   * on the 'findLowest' parameter value.<br><br>
   * 
   * A search method(give a search term/direction).<br><br>
   *
   * Implementation detail: when the SAX parser reached the end of document, the 'endDocument()' method
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
  protected void entitySort(String _item, boolean _findLowest, String[] _selectedShopList){

        resetForNextSearch();

        task.findCriteria = C_FIND_SORTED;
        if (_selectedShopList != null) {
          if (_selectedShopList.length == 0) return;

          task.selectedShopList = _selectedShopList;
        }
        task.shopList_IdValues = new Vector(20, 10);
        task.targetNode = _item;
        task.b_FindLowest = _findLowest;
  }

  public void fatalError (SAXParseException e) throws SAXException {
    System.out.println("ShopSAXManager-Error: " + e);
    throw e;
  }
  

  /**
   *TODO Must override this method in a sub class.  
   *  
   *  Activate a SAXParser class to read a XML data.
   *  */
  public void readXML_data() throws Exception{
	  throw new RuntimeException("ShopSAXManager.readXML_data(): you must override this method in a Subclass.\n" + 
			                     "So, a ShopSAXManager entended SubClass (GasSAXManger) reference can be passed to the SAXReader methods. ");
	  
	  //Example: 
	  //gasSAXParser.read();   
  }
}
