package spa.sax;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import cityfind.gasfind.sax.GasXMLTag;

import spa.util.ShopVO;
import static spa.sax.ShopSAX_Command.*;

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
	/** ShopTask reference.  */
	static protected ShopTask t; // 
	/** remote-server-xml data location url */
	static URL url; 
	/** XML data file name */
	static String xmlSource; 
	
	/** Main Element XML tag name ('store') identifier booleans */ 
	static boolean b_store = false;
	/** current read tag name */
	private String currentTagName = null;  
	/** current read tag value */
    protected String charValue = "";
    /** InputSource for XML data reading */
   	InputSource is;
    

	/** last tag id: there is a Java SAX Bug, which calls endElement() twice 
	 * at the last Tag, so it will be used to check the condition.  
	 *  TODO find what causes the bug */ 
	private int lastTagId = GasXMLTag.TAG_ID_UNKNOWN; 
	
	/** Constructor.  
	 *  @param _url        the URL for a remote XML data file location
	 *  @Param xmlSource the XML source file location. */
	public ShopSAXParser(URL _url, String _xmlSource, ShopTask _a){
		try {
			factory = SAXParserFactory.newInstance();
			saxParser = factory.newSAXParser();			
		    url = _url;
		    xmlSource = _xmlSource;  
		    
		    t = _a; //To access and set ShopSAXManager or its sub-class property  
		}catch (Exception e) {
			e.printStackTrace();
        }
	}
	 
	/** Resets url, saxParser and xmlSource for a new search.
	 *
	 *  @param _url        	the URL for a remote XML data file location
	 *  @Param xmlSource 	the XML source file location. */
	protected void reset(URL _url, String _xmlSource){
	    url = _url;
	    xmlSource = _xmlSource;  
	    saxParser.reset(); 
	}
	
	/**
	 * Reads a XML file.  It reads the XML file from a remote URL server, or a local
     * directory if network connection is not available (only for demo purpose)
	 */
	public void read(){		
		try {						
			if (url!=null){		
		       	//reads from a remote URL server
		       	is = new InputSource(url.openStream());
		       	is.setEncoding("ISO-8859-15");
		        saxParser.parse(is, this); 
		    }else{  	
		      	String xmlFile = System.getProperty("user.dir") + "\\src\\" + xmlSource; 
		      	
		       	//reads from a local storage/file 
			    InputStream inputStream= new FileInputStream(new File(xmlFile));
	
	    	    Reader reader = new InputStreamReader(inputStream,"UTF-8");
			   	is = new InputSource(reader);
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
	   if (t.findCriteria == C_FIND_TOTAL){
		   t.totalEntity = t.entityIndex;
	   }else if (t.findCriteria == C_FIND_SORTED){
		   t.sortShopListValues();//execute the sorting method
	   }


		  String selectedShopList_length = "0"; 
		  if(t.selectedShopList != null){
			  selectedShopList_length =  "" + t.selectedShopList.length;
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
       
      if (currentTagName.equals(t.shopMainNode)) {//check if it is inside <store> tag.  endElement() will check it again and reset this value.  
			b_store = true;//inside <store> tag
	  }

      if(t.findCriteria == C_FIND_TOTAL){
    	  //if Finding the total number of 'store', count
    	  if (b_store && currentTagName.equals(t.shopMainNode)){//if it is inside <'store'> tag, only count once  
    		  t.entityIndex++;  
    	  }
      }else if(t.findCriteria != C_FIND_DONE){
    	    
    	  if (b_store && currentTagName.equals(t.shopMainNode)){//if it is inside <'store'> tag, only execute once (other child(store properties) tags are = false). 
	          for (int i = 0; i < atts.getLength(); i++){
	
	              if (atts.getQName(i).equals("id")){//get the store id
		            	  
	            	  t.currentID = atts.getValue(i); //At the beginning of the each new <store> tag, sets the current-shop-ID
	                      if(t.findCriteria == C_FIND_SORTED){//sorting task
	                          if(t.selectedShopList != null && t.targetIndex < t.selectedShopList.length){
	                                if (t.selectedShopList[t.targetIndex].equals(t.currentID)){
	                                	t.targetIndex++;
	                                	t.b_TargetEntity = true;
	                                }
	                          }else if(t.selectedShopList == null){
	                        	  t.b_TargetEntity = true;
	                          }
	                      }else if(t.findCriteria == C_FIND_INFO){
	                          if (t.b_TargetEntity != true && t.targetValue.equals(t.currentID)){
	                        	  t.b_TargetEntity = true;
	                          }else if (t.b_TargetEntity == true && !t.targetValue.equals(t.currentID)){
	                        	  t.b_TargetEntity = false;
	                        	  t.findCriteria = C_FIND_DONE; //indicates the task have been done.
	                              break;//*** needs a better way
	                          }
	                      }else if(t.findCriteria == C_FIND_SHOPLIST_INFO){
	                          if(t.selectedShopList != null && t.targetIndex < t.selectedShopList.length){
	                              if (t.selectedShopList[t.targetIndex].equals(t.currentID)){
	                            	  t.targetIndex++;
	                            	  t.b_TargetEntity = true;//<-- changed by 'endElement()' method
	                              }
	                          }else{
	                        	  t.findCriteria = C_FIND_DONE;
	                          }
	                      }//End of "if(findCriteria == FIND_SORTED) and else..."
	              }
	          }//End of the for-loop
          }//End of 'if (bstore)'
      
          if((t.findCriteria != C_FIND_INFO || t.findCriteria != C_FIND_SHOPLIST_INFO)&& currentTagName.equals(t.targetNode)){
        	  
        	  if (t.findCriteria == C_FIND_ELEMENT){
                          if(t.selectedShopList != null && t.targetIndex < t.selectedShopList.length){
                                if (t.selectedShopList[t.targetIndex].equals(t.currentID)){
                                	t.targetIndex++;
                                	t.addEntityToShopList(t.currentID);
                                }
                          }else if(t.selectedShopList == null){
                        	  t.addEntityToShopList(t.currentID);
                          }
                          else{
                            throw new RuntimeException("This portion of ShopSAXParser.startElement() is not yet implemented"); 
                          }
              }else if(t.findCriteria == C_FIND_ELEMENT_VALUE){
                          if(t.selectedShopList != null && t.targetIndex < t.selectedShopList.length){
                                if (t.selectedShopList[t.targetIndex].equals(t.currentID)){
                                	t.targetIndex++;
                                	t.b_TargetEntity = true;
                                	t.b_TargetNode = true; //to notify the 'characters(') method to collect the entity properties' values.
                                }
                          }else if(t.selectedShopList == null){
                        	  t.b_TargetEntity = true;
                        	  t.b_TargetNode = true; //to notify the 'characters()' method to collect the entity properties' values.
                          }
              }else if(t.findCriteria == C_FIND_SORTED & t.b_TargetEntity){
            	  t.b_TargetNode = true; //to notify the 'characters()' method to collect the entity properties' values.
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
	      
	      if (t.findCriteria == C_FIND_ELEMENT_VALUE && t.b_TargetEntity && t.b_TargetNode){
	          if (t.matchingCriteria == C_VALUE_EXACT ){
	        	  
	              if (charValue.equals(t.targetValue)){
	            	  t.addEntityToShopList(t.currentID);
	              }
  	  
	          }else{
	            //*** Under construction ***
	            /**
	             * TODO Implements for these conditions:
	             * if (matchingCriteria == VALUE_BIGGER )
	             * if (matchingCriteria == VALUE_EQUAL_OR_BIGGER )
	             * if (matchingCriteria == VALUE_SMALLER )
	             * if (matchingCriteria == VALUE_EQUAL_OR_SMALLER )
	             * */
	        	throw new RuntimeException("ShopSAXParser.endElement() not yet fully implemented for these conditions. "); 
	          }
	      }else if (t.findCriteria == C_FIND_SORTED && t.b_TargetNode ){
	    	  
	    	  t.shopList_IdValues.addElement(new ShopVO(Integer.parseInt(t.currentID), 
	    			                          Float.parseFloat(charValue)));
	          
	      }
      	  
      if (qName.equals(t.shopMainNode)) {
    	t.b_TargetEntity = false;    	  
  		b_store = false;//reset 
  	  }
      charValue = ""; //reset at the end of each tag element
	  t.b_TargetNode = false; 
  }
	
}
