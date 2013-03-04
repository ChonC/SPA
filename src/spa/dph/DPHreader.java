package spa.dph;

/**
 * <p>Title: DPHreader </p>
 * <p>Description: DPHreader is the SAX parser class that provides methods to read
 *                 the user's saved decision processes from a XML file.
 *
 *                 After the first interaction with a user, an SPA application saves
 *                 the user's Decision Processes history (DPH) as an XML file.  This
 *                 class provides methods to read the file, so the SPA
 *                 application can perform the task again, this time automatically complete the job.</p>
 *
 * @todo 			I could not encapsulate the whole XML reading process in
 *        			the BizSAXReader class search method, so I had to extends it to a sub-class.  
 *        			But, I am able to do the encapsulation of the reading process in this "DPHreader" class.  
 *                  Why? Find the differences and fix it.
 *        			1. It might be the small local DPH XML file size: which might cause errors if it
 *        			   reads a large file from a distant remote server.
 *                  2. Or the SAX Parser. 
 *
 * <p>Copyright: Copyright (c) 2001-2004 Chon Chung.</p>
 * @author Chon Chung
 * @version Demo 0.7
 * 
 * <p>          License: Lesser General Public License (LGPL)</p>
 */
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

import java.io.StringReader;
import java.util.ArrayList;

public class DPHreader extends DefaultHandler {

  /** The question number that we are looking for.  */
  protected String questionNum;
  /** A String of numerical ID of the Current question. */
  protected String questionID;
  /** Indicates that the current question should have multiple answers. */
  protected boolean mutiAnswers;
  /** The answer numbers. */
  protected int ansNum;
  /** The answer numbers. */
  protected ArrayList<String> ansNumList;

  /** singleton instance reference. */
  private static DPHreader INSTANCE;
  /** SAX XML Parser Factory */
  protected SAXParserFactory saxfactory; 
  /** SAX XML Parser */
  protected SAXParser saxParser; 
  

  /** Indicates the current question is the target question. In startElement() method,
   *  this value is set to "true" if the current reading question is the target question number 
   *  that we are looking for, 
   *  so characters() method knows which answer to collect.*/
  protected boolean isTargetQuestion;
  /** is the "answer" XML tag */
  protected boolean bAnswer;

  /**
   * Private constructor to enforce a Singleton instance.
   */
  private DPHreader() {
      questionNum = null;
      isTargetQuestion = false;
      bAnswer = false;
      questionID = null;
      mutiAnswers = false;
      ansNum = -1;
      ansNumList = new ArrayList<String>();
      try{
		saxfactory = SAXParserFactory.newInstance();
		saxParser = saxfactory.newSAXParser();
      } catch (Exception e) {
          e.printStackTrace();
      }
  }
  
  /* Creates and return the singleton instance.
   */
  public static DPHreader getInstance(){
    //if no instance is available, it calls the constructor to initialize this class
    if (INSTANCE == null){
        INSTANCE = new DPHreader();
    }
    return INSTANCE;
  }

  /** Resets the properties with their default values */
  protected void resetParams(){
      questionNum = null;
      isTargetQuestion = false;
      bAnswer = false;
      questionID = null;
      mutiAnswers = false;
      ansNum = -1;
      ansNumList.clear();
  }
  


  /** Getter for ansNum.  Returns ansNum */
    public int getAnsNum() {
        return ansNum;
  }

  /** Returns the size of AnsNumsList */
  public int getAnsNumsSize(){
      return ansNumList.size();
  }

  /** Returns the element of AnsNumsList */
  public int getAnsNumsElement(int index){
      return Integer.parseInt((String)ansNumList.get(index));
  }
  
  /** Reads the saved answer for the question.  This method is used when there
   *  is only one answer for the question*/
  public String readAnswer(String xml_DPH, int que){
    resetParams();
    mutiAnswers = false;
    questionNum = Integer.toString(que);
    readXMLInputString(xml_DPH);
    return Integer.toString(ansNum);
  }

  /** Reads the saved multiple answers for the question.  This method is used
   *  when there are more than one answer for the question.*/
  public ArrayList<String> readAnswers(String xml_DPH, int que){
    resetParams();
    mutiAnswers = true;
    questionNum = Integer.toString(que);
    readXMLInputString(xml_DPH);

    return ansNumList;
  }

  /**
   * Activates the SAX parser to read the user DPH XML information from the input string.
   *
   * @param xml_DPH     the DPH XML String
   */
  public void readXMLInputString(String xmlString) {
      try {
    	  saxParser.parse(new InputSource(new StringReader(xmlString)), DPHreader.getInstance());
      }
      catch (final SAXException e) {
        System.out.println("SAXException: " + e);
        e.printStackTrace();
      }
      catch (final Throwable e) {
        System.out.println("Other Exception: " + e);
        e.printStackTrace();
      }
  }

  /**
   * SAX parser built in method. Receives an event at the beginning of the each tag element.
   * If the XML tag is a question ID and it is the question we are looking for,
   * this method changes the value of "isTargetQuestion" to notify the characters()
   * methods to collect the answer.
   */
  public void startElement(final String namespaceURI,
                           final String localName,
                           final String qName,
                           final Attributes atts)
    throws SAXException
  {

		if (qName.equals(DPH_tag.QUESTION_TAG)) {
	          for (int i = 0; i < atts.getLength(); i++){
	              if (atts.getLocalName(i).equals("id")){
	                  questionID = atts.getValue(i);
	                      if (questionNum.equals(questionID)){
	                          isTargetQuestion = true;
	                          break;
	                      }
	              }
	          }
		}

		if (qName.equals(DPH_tag.ANSWER_TAG)) {
			bAnswer = true;
		}
		
  }

  /**
   * SAX parser built in method. Receive an event at the end of the each tag element.
   * When it reaches the end of the </question> tag, it resets the "isTargetQuestion"
   * values to the default value for a next search.
   */
  public void endElement(final String namespaceURI,
                         final String localName,
                         final String qName)
    throws SAXException
  {
	  
      if(isTargetQuestion && qName.equals(DPH_tag.QUESTION_TAG)){
          isTargetQuestion = false;
      }

	  if (qName.equals(DPH_tag.ANSWER_TAG)) {
		  bAnswer = false;
	  }

  }

  /**
   * SAX parser built in method. Receives an event at the beginning of each tag value.
   * This method collects the answer values of the current target question.
   */
  public void characters (char ch[], int start, int length) {
      if (isTargetQuestion && !mutiAnswers){
          ansNum = Integer.parseInt(new String(ch, start, length).trim());
          isTargetQuestion = false;
      }else if ((isTargetQuestion && mutiAnswers) && bAnswer){
          ansNumList.add(new String(ch, start, length).trim());
      }
  }

  public void fatalError (SAXParseException e) throws SAXException {
    System.out.println("DPHreader-Error: " + e);
    throw e;
  }



}