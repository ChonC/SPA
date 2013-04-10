package cityfind.gasfind.sax;

import java.net.URL;
import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import cityfind.gasfind.GasElement;

import spa.sax.ShopTask;
import spa.sax.ShopSAXParser;

import static cityfind.gasfind.sax.GasSAX_Command.*;

/** 
 * GasSAXParser provides tools to read a XML file, 
 * and update the GasSAXManager properties based on the XML data. 
 * 
 * 
 * By calling 'read()' method, it will start reading XML data file.  
 * During the reading, if this parser encounter a new tag node 'startElement()' will be invoked.  
 * If the parser is reading between a tag node (tag value), 'characters()' will be invoked. 
 * 
 * GasSAXManager sets the search terms 
 * (e.g. findShopHasGas() search term = Finds all the shops that have the tagetNode(gas-type = plus-unlead)), 
 * and calls GasSAXManager.readXML_data(), which calls this (GasSAXParser) class’s 
 * inherited read() method. 
 * 
 * During the reading XML data, 
 * startElement() finds the shops, those matched with the search terms, 
 * and it inserts the store id to the GasSAXManager.shopList.  And characters() method 
 * stores one of the shop’s property value (like Gas-price).  
 **/
public class GasSAXParser extends ShopSAXParser{

	/** GasTask reference */
	static protected GasTask t; 

	/** current read tag name */
	private String curTagName = null;  
	/** current read tag value */
	private String charactersValue = ""; 
	
	/** last tag id: there is a Java SAX Bug, which calls endElement() twice 
	 * at the last Tag, so it will be used to check the condition.  
	 *  TODO find what causes the bug */ 
	private int lastTagId = GasXMLTag.TAG_ID_UNKNOWN; 
    
			
	/** Constructor.  
	 *  @param _url        the URL for a remote XML data file location
	 *  @param _xmlSource the XML source file location. 
	 *  @param _task  task reference*/
	public GasSAXParser(URL _url, String _xmlSource, GasTask _task){
		super(_url, _xmlSource, (ShopTask)_task); //"_gasSAXManager" is passed to the super.ShopSAXParser. So, ShopSAXParser would be able to set GasSAXManager property during the reading XML processing. 
				
		t = _task;	
	}

	/** Resets url and xmlSource for a new search.
	 *
	 *  @param _url        the URL for a remote XML data file location
	 *  @Param xmlSource the XML source file location. */
	public void reset(URL _url, String _xmlSource){
		super.reset(_url, _xmlSource); 
	}
	
	  /**
	   * SAX parser built in method. Receive the beginning of document event.
	   */
	@SuppressWarnings("unchecked")
	public void startDocument() {
	    super.startDocument();
	      
	    if (t.shopList != null) t.lastShopList = (ArrayList<Object>) t.shopList.clone();
	}

	   /**
		 * SAX parser built in method. <br>
		 * Receives an event at the end of a document.
		 *
		 * <p><b>
		 * Note:  This method can be use to notify the called-searching method to wake up from a thread sleep. </p>
		 * </p>
		 */
	   public void endDocument() {
		   super.endDocument(); 

	   }
		

	  /**
	   * SAX parser built in method. Receive a beginning of a tag element event.
	   * At the beginning of reading each element(node) tag, this method will be executed.
	   * 
	   * It finds the shops, those matched with the search terms, 
       * and stores their id to the GasSAXManager.shopList.
	   */
	  public void startElement(final String namespaceURI,
	                           final String localName,
	                           final String qName,
	                           final Attributes atts)
	    throws SAXException
	  {
	      super.startElement(namespaceURI, localName, qName, atts);
		  curTagName = qName; 
		  charactersValue = ""; //set at the each tag element

	      if(t.findCriteria == C_FIND_INFO && t.b_TargetEntity == true) {
	        t.theCurrentTagId = GasXMLTag.getTagId(qName);//theCurrentTagId will be used in the "characters()" method to store the current XML-tag value.
	      }else if(t.findCriteria == C_FIND_SHOPLIST_INFO && t.b_TargetEntity == true) {
	          for (int i = 0; i < atts.getLength(); i++){
	              if (atts.getQName(i).equals("id")){
	            	  t.shopList.add(new GasElement());

	              }
	          }
	          t.theCurrentTagId = GasXMLTag.getTagId(qName);//theCurrentTagId will be used in the "characters()" method to store the current XML-tag value.
	      }
	  }

	  /**
	   * SAX parser built in method. Receives a beginning of each tag value event.
	   * At each element(node) value, the SAX parser invokes this method.
	   *
	   * It stores one of the shop’s property value (like Gas-price).  
	   * Note: 1. characters() method maybe called several times in a single node element, 
	   *       so we have to sum the values together to get the whole value. 
	   *       2. do not use "if(gm.isTargetNode())" condition here, FIND_SHOPLIST_INFO task needs to store every nodes' value. 
	   *        
	   * 
	   */
	  public void characters (char ch[], int start, int length) {
	      super.characters(ch, start, length);
	      charactersValue  = charactersValue + new String(ch, start, length);  		  
	  }
	  
	  /**
	   * SAX parser built in method. <br>
	   * Receives an event at the end of each tag element.
	   *  
	   * If the tag value matches with the current searching criterion, this method
	   * stores the entity ID and value for the further analysis.
	   * 
	   * Also, t reaches the end of the </store>, it resets the targetEntity property
	   * value to the default value for a next search.
	   *
	   *
	   * @status: under development
	   * @Todo 1. (matchingCriteria != VALUE_EXACT ) needs implementation
	   *       2. There is a Java SAX Bug, which calls endElement() twice at the last "<y>" tag.  
	   *	   	  Find what cause the bug.
	   *
	   * @param namespaceURI
	   * @param localName
	   * @param qName
	   * @throws SAXException
	   */
	   public void endElement(final String namespaceURI,
	                          final String localName,
	                          final String qName) throws SAXException {
		   
			      super.endElement(namespaceURI, localName, qName);
	
			      //TODO compare with super characterValue and this charValue.  If need, add them together. 	
			      charactersValue = charactersValue.trim();
			      
			      if (t.findCriteria == C_FIND_INFO && t.b_TargetEntity == true){
			          if(t.theCurrentTagId == GasXMLTag.TAG_ID_NAME){//"name" is the first property tag-node of <store> 
			              t.gasShopInfo.setId(String.valueOf(t.currentID));//set Id once. 
			          }
			          t.gasShopInfo.setElementValue(t.theCurrentTagId, charactersValue);//add the store information by each XML items.
			      }else if (t.findCriteria == C_FIND_SHOPLIST_INFO && t.b_TargetEntity == true){
			          if(t.theCurrentTagId == GasXMLTag.TAG_ID_NAME){
			              ((GasElement)t.shopList.get(t.targetIndex -1)).setId(String.valueOf(t.currentID));
			          }

			    	  /**Note: there is a Java SAX Bug, which calls endElement() twice at the last tag.  
			    	           So, the following condition is a quick fix.  TODO find what causes the bug */
			          if (t.theCurrentTagId != lastTagId && !charactersValue.equals("")){
			        	  ((GasElement)t.shopList.get(t.targetIndex -1)).setElementValue(t.theCurrentTagId, charactersValue);
			          }
			      }
		      
		    	
		      charactersValue = ""; //reset at the end of each tag element
		      lastTagId = t.theCurrentTagId;       
		      
	   }

}
