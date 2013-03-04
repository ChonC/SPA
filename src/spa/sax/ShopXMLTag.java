package spa.sax;

/**
 * Title:        ShopXMLTag
 * Description:  ShopXMLTag provides the Shop-XML-tag information for ShopSAXParser class
 *               to read a XML data file.  By using this class,
 *               the ShopSAXManager can identify the shop XML tags 
 *               during its reading process. <br/><br/>
 *
 *               This is the super class that provides the structural foundation 
 *               for its subclasses.
 *
 * Copyright:    Copyright (c) 2001-2004 Chon Chung.
 *
 * @author       Chon Chung
 * @version      Demo 0.5
 *
 * 
 * <p>          License: Lesser General Public License (LGPL)</p>
 */

public class ShopXMLTag {

  //constants for a XML tag element's id:
  /** Unknown tag-Id. */
  public static final int TAG_ID_UNKNOWN = -1;
  /** Store(Shop) tag-Id. */
  public static final int TAG_ID_STORE = 0;
  /** Name tag-Id. */
  public static final int TAG_ID_NAME = 1;
  /** ID tag-Id. */
  public static final int TAG_ID_ID = 2;
  /** Phone tag-Id. */
  public static final int TAG_ID_PHONE = 3;
  /** Address tag-Id. */
  public static final int TAG_ID_ADDRESS = 4;
  /** Distance tag-Id. */
  public static final int TAG_ID_DISTANCE = 5;
  /** latitude tag-Id. */
  public static final int TAG_ID_LATITUDE = 6;
  /** longitude tag-Id. */
  public static final int TAG_ID_LONGITUDE = 7;  
  /** X location tag-Id. */
  public static final int TAG_ID_X = 8;
  /** Y location tag-Id. */
  public static final int TAG_ID_Y = 9;


  //constants for a XML tag element's names:
  /** Name of the tag-unknown. */
  public static final String NAME_TAG_UNKNOWN = "unknown";
  /** Name of the tag-store. */
  public static final String NAME_TAG_STORE = "store"; 
  /** Name of the tag-name. */
  public static final String NAME_TAG_NAME = "name"; 
  /** Name of the tag-id. */
  public static final String NAME_TAG_ID = "id";
  /** Name of the tag-phone. */
  public static final String NAME_TAG_PHONE = "phone";
  /** Name of the tag-address. */
  public static final String NAME_TAG_ADDRESS = "address";
  /** Name of the tag-distance. */
  public static final String NAME_TAG_DISTANCE = "distance";
  /** Name of the tag-latitude. */
  public static final String NAME_TAG_LATITUDE = "latitude";
  /** Name of the tag-longitude. */
  public static final String NAME_TAG_LONGITUDE = "longitude";  
  /** Name of the tag-x. */
  public static final String NAME_TAG_X = "x";
  /** Name of the tag-y. */
  public static final String NAME_TAG_Y = "y";


  /** Returns the name of the selected XML tag element.*/
  public static String getTagName(int tag_node){
	  switch(tag_node){
	      case TAG_ID_STORE:
	    	  return NAME_TAG_STORE; 
	      case TAG_ID_NAME:
	          return NAME_TAG_NAME;
	      case TAG_ID_ID: 
	      	  return NAME_TAG_ID; 
	      case TAG_ID_PHONE:
	          return NAME_TAG_PHONE;
	      case TAG_ID_ADDRESS:
	          return NAME_TAG_ADDRESS;
	      case TAG_ID_DISTANCE:
	          return NAME_TAG_DISTANCE;
	      case TAG_ID_LATITUDE:
	          return NAME_TAG_LATITUDE;
	      case TAG_ID_LONGITUDE:
	          return NAME_TAG_LONGITUDE;        
	      case TAG_ID_X:
	          return NAME_TAG_X;
	      case TAG_ID_Y:
	          return NAME_TAG_Y;
	      default:   
	    	  return NAME_TAG_UNKNOWN;
	  }
  }

  /** Returns the Id of the XML tag node state.*/
  public static int getTagId(String tagName){
	  if (tagName.equals(NAME_TAG_STORE)){
          return TAG_ID_STORE;
      }else if (tagName.equals(NAME_TAG_NAME)){
          return TAG_ID_NAME;
      }else if (tagName.equals(NAME_TAG_ID)){
          return TAG_ID_ID;
      }else if (tagName.equals(NAME_TAG_PHONE)){
          return TAG_ID_PHONE;
      }else if (tagName.equals(NAME_TAG_ADDRESS)){
          return TAG_ID_ADDRESS;
      }else if (tagName.equals(NAME_TAG_DISTANCE)){
          return TAG_ID_DISTANCE;
      }else if (tagName.equals(NAME_TAG_LATITUDE)){
          return TAG_ID_LATITUDE;
      }else if (tagName.equals(NAME_TAG_LONGITUDE)){
          return TAG_ID_LONGITUDE;          
      }else if (tagName.equals(NAME_TAG_X)){
          return TAG_ID_X;
      }else if (tagName.equals(NAME_TAG_Y)){
          return TAG_ID_Y;
      }else {
          return TAG_ID_UNKNOWN;
      }
  }
}