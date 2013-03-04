package cityfind.gasfind.sax;

import spa.sax.ShopXMLTag;

/**
 * Title:        GasXMLTag
 * 
 * Description:  GasXMLTag contains the gas-station XML tag information (tag name and key)
 *               for the GasSAXReader to read the XML data.  By utilizing this class,
 *               the GasSAXReader can identify a gas-station XML tags 
 *               during its reading process.
 * 
 * Copyright:    Copyright (c) 2001-2004 Chon Chung.
 * @author       Chon Chung
 * @version      Demo 0.5
 * 
 * <p>          License: Lesser General Public License (LGPL)</p>
 */
public class GasXMLTag extends ShopXMLTag{

  /** constant for a XML tag Id. Gas Brand tag */
  public static final int TAG_BRAND = 10;  //Start from 10, so it does not conflict with ShopXMLTag Tags. 
  /** constant for a XML tag Id. Unlead tag */
  public static final int TAG_UNLEAD = 11;
  /** constant for a XML tag Id. Plus unlead tag */
  public static final int TAG_PLUS_UNLEAD = 12;
  /** constant for a XML tag Id. Premium unlead tag */
  public static final int TAG_PREMIUM_UNLEAD = 13;
  /** constant for a XML tag Id. Diesel tag. */
  public static final int TAG_DIESEL = 14;

  

  //constants for a XML tag element's names:
  /** Name of the tag-brand. */
  protected static final String NAME_TAG_BRAND = "brand";
  /** Name of the tag-unlead. */
  protected static final String NAME_TAG_UNLEAD = "unlead";
  /** Name of the tag-plus-unlead. */
  protected static final String NAME_TAG_PLUS_UNLEAD = "plus-unlead";
  /** Name of the tag-premium-unlead. */
  protected static final String NAME_TAG_PREMIUM_UNLEAD = "premium-unlead";
  /** Name of the tag-diesel. */
  protected static final String NAME_TAG_DIESEL = "diesel"; 
  
  public GasXMLTag(){}

  /** Returns the name of xml tag. */
  public static String getTagName(int tag_node){
	  if (tag_node <= TAG_ID_Y){
		  return ShopXMLTag.getTagName(tag_node); 
	  }else{
	        switch(tag_node){            	
	            case TAG_BRAND:
	                return NAME_TAG_BRAND;
	            case TAG_UNLEAD:
	                return NAME_TAG_UNLEAD;
	            case TAG_PLUS_UNLEAD:
	                return NAME_TAG_PLUS_UNLEAD;
	            case TAG_PREMIUM_UNLEAD:
	                return NAME_TAG_PREMIUM_UNLEAD;
	            case TAG_DIESEL:
	                return NAME_TAG_DIESEL;
	            default:   return NAME_TAG_UNKNOWN;
	        }//End of switch
	  }
  }

  /** Returns the Id of the XML tag node. */
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
      }else if (tagName.equals(NAME_TAG_X)){
          return TAG_ID_X;
      }else if (tagName.equals(NAME_TAG_Y)){
          return TAG_ID_Y;
      }else if (tagName.equals(NAME_TAG_BRAND)){
          return TAG_BRAND;
      }else if (tagName.equals(NAME_TAG_UNLEAD)){
          return TAG_UNLEAD;
      }else if (tagName.equals(NAME_TAG_PLUS_UNLEAD)){
          return TAG_PLUS_UNLEAD;
      }else if (tagName.equals(NAME_TAG_PREMIUM_UNLEAD)){
          return TAG_PREMIUM_UNLEAD;
      }else if (tagName.equals(NAME_TAG_DIESEL)){
          return TAG_DIESEL;
      }else {
          return TAG_ID_UNKNOWN;
      }//End of if
  }

}