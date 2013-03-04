package spa.sax;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.util.Vector;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import cityfind.gasfind.GasDecisionProcess;

import spa.util.ShopVO;

/** ShopSAXParser provides tools to read a XML file, 
 *  and update the ShopSAXManager properties based on the XML data search result.  
 *  
 *  By calling 'read()' method, it will start reading XML data file.  
 *  During the reading, if this parser encounter a new tag node 'startElement()' will be invoked.  
 *  If the parser is reading between a tag node (tag value), 'characters()' will be invoked.    
 **/
public class ShopSAXParser  extends DefaultHandler {

	/** ShopSAX_Command local reference, which has commands constants for dictating SAX task. */
	static ShopSAX_Command sc; 
	/** SAX Parser Factory */
	static protected SAXParserFactory factory; 
	/** SAX parser */ 
	static protected SAXParser saxParser; 
	/** ShopSAX reference. the reference is used 
	 * to set ShopSAX (or its subclass) properties value based on the finding from XML file.  */
	static protected ShopSAXManager sm; // 
	/** remote-server-xml data location url */
	static URL url; 
	/** XML data file name */
	static String xmlSource; 
	
	/** Main Element XML tag name ('store') identifier booleans */ 
	static boolean bstore = false;
	/** current read tag name */
	private String currentTagName = null;  
	/** current read tag value */
    protected String charValue = "";
	
	/** Constructor.  
	 *  @param _url        the URL for a remote XML data file location
	 *  @Param xmlSource the XML source file location. */
	public ShopSAXParser(URL _url, String _xmlSource, ShopSAXManager _sm){
		try {
			factory = SAXParserFactory.newInstance();
			saxParser = factory.newSAXParser();			
			sc = new ShopSAX_Command(); 
		    url = _url;
		    xmlSource = _xmlSource;  
		    
		    sm = _sm; //To access and set ShopSAXManager or its sub-class property  
		}catch (Exception e) {
			e.printStackTrace();
        }
	}
	 
	/** Resets url and xmlSource for a new search.
	 *
	 *  @param _url        the URL for a remote XML data file location
	 *  @Param xmlSource the XML source file location. */
	protected void reset(URL _url, String _xmlSource){
	    url = _url;
	    xmlSource = _xmlSource;  
	}
	
	/**
	 * Reads a XML file.  It reads the XML file from a remote URL server, or a local
     * directory if network connection is not available (only for demo purpose)
	 */
	public void read(){		
		try {						
			if (url!=null){		
		       	//reads from a remote URL server
		       	InputSource is = new InputSource(url.openStream());
		       	is.setEncoding("ISO-8859-15");
		        saxParser.parse(is, this); 
		    }else{  	
		      	String xmlFile = System.getProperty("user.dir") + "\\src\\" + xmlSource; 
		      	
		       	//reads from a local storage/file 
			    InputStream inputStream= new FileInputStream(new File(xmlFile));
	
	    	    Reader reader = new InputStreamReader(inputStream,"UTF-8");
			   	InputSource is = new InputSource(reader);
			   	is.setEncoding("ISO-8859-15"); 
			   	//is.setEncoding("UTF-8");  	      
		        	
		        saxParser.parse(is, this);
		     }

		 } catch (Exception e) {
		       e.printStackTrace();
         }
	}


	/**
	 * SAX parser built in method. Receives an event at the beginning of the document.
	*/
	public void startDocument() {
		
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
	   if (sm.findCriteria == sc.FIND_TOTAL){
		   sm.totalEntity = sm.entityIndex;
	   }else if (sm.findCriteria == sc.FIND_SORTED){
		   sm.sortShopListValues();//execute the sorting method
	   }


		  String selectedShopList_length = "0"; 
		  if(sm.selectedShopList != null){
			  selectedShopList_length =  "" + sm.selectedShopList.length;
	      }
	}

  /**
   * SAX parser built in method. <br>
   * Receive an event at the beginning of the each tag element.
   * If the XML tag is the target entity ID, this method changes the values of this class properties 
   * to notify other methods (such as the characters()) to collect the entity's properties information.
   *
   * @Todo need to improve speed by rearranging the if-else statements and their orders
   */
  public void startElement(final String namespaceURI,
                           final String localName,
                           final String qName,
                           final Attributes atts) throws SAXException {
	  
	  currentTagName = qName; 
      charValue = ""; //set at the each tag element
       
      if (currentTagName.equals(sm.shopMainNode)) {//check if it is inside <store> tag.  endElement() will check it again and reset this value.  
			bstore = true;//inside <store> tag
	  }

      if(sm.findCriteria == sc.FIND_TOTAL){
    	  //if Finding the total number of 'store', count
    	  if (bstore && currentTagName.equals(sm.shopMainNode)){//if it is inside <'store'> tag, only count once  
    		  sm.entityIndex++;  
    	  }
      }else if(sm.findCriteria != sc.FIND_DONE){
    	    
    	  if (bstore && currentTagName.equals(sm.shopMainNode)){//if it is inside <'store'> tag, only execute once (other child(store properties) tags are = false). 
	          for (int i = 0; i < atts.getLength(); i++){
	
	              if (atts.getQName(i).equals("id")){//get the store id
		            	  
	            	  sm.currentID = atts.getValue(i); //At the beginning of the each new <store> tag, sets the current-shop-ID
	                      if(sm.findCriteria == sc.FIND_SORTED){//sorting task
	                          if(sm.selectedShopList != null && sm.targetIndex < sm.selectedShopList.length){
	                                if (sm.selectedShopList[sm.targetIndex].equals(sm.currentID)){
	                                	sm.targetIndex++;
	                                	sm.b_TargetEntity = true;
	                                }
	                          }else if(sm.selectedShopList == null){
	                        	  sm.b_TargetEntity = true;
	                          }
	                      }else if(sm.findCriteria == sc.FIND_INFO){
	                          if (sm.b_TargetEntity != true && sm.targetValue.equals(sm.currentID)){
	                        	  sm.b_TargetEntity = true;
	                          }else if (sm.b_TargetEntity == true && !sm.targetValue.equals(sm.currentID)){
	                        	  sm.b_TargetEntity = false;
	                        	  sm.findCriteria = sc.FIND_DONE; //indicates the task have been done.
	                              break;//*** needs a better way
	                          }
	                      }else if(sm.findCriteria == sc.FIND_SHOPLIST_INFO){
	                          if(sm.selectedShopList != null && sm.targetIndex < sm.selectedShopList.length){
	                              if (sm.selectedShopList[sm.targetIndex].equals(sm.currentID)){
	                            	  sm.targetIndex++;
	                            	  sm.b_TargetEntity = true;//<-- changed by 'endElement()' method
	                              }
	                          }else{
	                        	  sm.findCriteria = sc.FIND_DONE;
	                          }
	                      }//End of "if(findCriteria == FIND_SORTED) and else..."
	              }
	          }//End of the for-loop
          }//End of 'if (bstore)'
      
          if((sm.findCriteria != sc.FIND_INFO || sm.findCriteria != sc.FIND_SHOPLIST_INFO)&& currentTagName.equals(sm.targetNode)){
        	  
        	  if (sm.findCriteria == sc.FIND_ELEMENT){
                          if(sm.selectedShopList != null && sm.targetIndex < sm.selectedShopList.length){
                                if (sm.selectedShopList[sm.targetIndex].equals(sm.currentID)){
                                	sm.targetIndex++;
                                	sm.addEntityToShopList(sm.currentID);
                                }
                          }else if(sm.selectedShopList == null){
                        	  sm.addEntityToShopList(sm.currentID);
                          }
//                          else{
//                            //**** Throw error?????????????????????????
//                          }
              }else if(sm.findCriteria == sc.FIND_ELEMENT_VALUE){
                          if(sm.selectedShopList != null && sm.targetIndex < sm.selectedShopList.length){
                                if (sm.selectedShopList[sm.targetIndex].equals(sm.currentID)){
                                	sm.targetIndex++;
                                	sm.b_TargetEntity = true;
                                	sm.b_TargetNode = true; //to notify the 'characters(') method to collect the entity properties' values.
                                }
                          }else if(sm.selectedShopList == null){
                        	  sm.b_TargetEntity = true;
                        	  sm.b_TargetNode = true; //to notify the 'characters()' method to collect the entity properties' values.
                          }
              }else if(sm.findCriteria == sc.FIND_SORTED & sm.b_TargetEntity){
            	  sm.b_TargetNode = true; //to notify the 'characters()' method to collect the entity properties' values.
              }
          }
      }//End of the 'else if'
  }

   
  /**
   * SAX parser built in method. <br>
   * Receives an event at the beginning of each tag value.
   * If the current tag matches with the current searching criterion, this method
   * stores the characters-value, which will be used in endElement() method.
   *
   * note: do not use "if(gm.isTargetNode())" condition here, FIND_SHOPLIST_INFO task needs to store every nodes' value.  
   */
  public void characters (char ch[], int start, int length) {
	      charValue  = charValue + new String(ch, start, length);  
  }
  

  /**
   * SAX parser built in method. <br>
   * Receives an event at the end of each tag element.
   *  
   * If the tag value matches with the current searching criterion, this method
   * stores the entity ID and value for the further analysis.
   * 
   * Also, if it reaches the end of the </store>, it resets the targetEntity property
   * value to the default value for a next search.
   *
   *
   * @status: under development
   * @Todo (matchingCriteria != VALUE_EXACT ) require further development
   * @Note if(findCriteria == FIND_INFO | findCriteria == FIND_SHOPLIST_INFO)  --> should
   *       implemented in the sub-SAXParser class based on the sub package requirement.
   *       
   * @param namespaceURI
   * @param localName
   * @param qName
   * @throws SAXException
   */
   public void endElement(final String namespaceURI,
                          final String localName,
                          final String qName)
    throws SAXException {
	   
	      charValue = charValue.trim(); 
	      
	      if (sm.findCriteria == sc.FIND_ELEMENT_VALUE && sm.b_TargetEntity && sm.b_TargetNode){
	          if (sm.matchingCriteria == sc.VALUE_EXACT ){
	        	  
	              if (charValue.equals(sm.targetValue)){
	            	  sm.addEntityToShopList(sm.currentID);
	              }
  	  
	          }else{
	            //*** Under construction ***
	            /**
	             * @Todo Implements for the following conditions:
	             * if (matchingCriteria == VALUE_BIGGER )
	             * if (matchingCriteria == VALUE_EQUAL_OR_BIGGER )
	             * if (matchingCriteria == VALUE_SMALLER )
	             * if (matchingCriteria == VALUE_EQUAL_OR_SMALLER )
	             * */
	            //throw new java.lang.UnsupportedOperationException("ShopSAXParser.endElement() not yet fully implemented for this situation."); // <-- Not in JDK 1.1 (This class initially developed for a Personal Java environment of my Sharp-Zaurus PDA device) 
	            System.out.println("ShopSAXParser.endElement() not yet fully implemented for this situation. Todo it. ");
	          }
	      }else if (sm.findCriteria == sc.FIND_SORTED && sm.b_TargetNode ){
	    	  
	    	  sm.shopList_IdValues.addElement(new ShopVO(Integer.parseInt(sm.currentID), 
	    			                          Float.parseFloat(charValue)));
	          
	      }
      	  
      if (qName.equals(sm.shopMainNode)) {
    	sm.b_TargetEntity = false;    	  
  		bstore = false;//reset 
  	  }
      charValue = ""; //reset at the end of each tag element
	  sm.b_TargetNode = false; 
  }
	
//	public static void main(String args[]) {
//		try {
//			 URL url = new URL("http://chon.techliminal.com/cityfind/houstonXML.txt");
//			 ShopSAXManager shopSAXManager = new ShopSAXManager("houstonXML.txt", ShopXMLTag.getTagName(ShopXMLTag.TAG_STORE)); 
//			 ShopSAXParser shopSAXParser = new ShopSAXParser(url, "houstonXML.txt", shopSAXManager); 
//			 shopSAXParser.read(); 
//		} catch (Exception e) {
//		       e.printStackTrace();
//		}
//	 
//	}

}
