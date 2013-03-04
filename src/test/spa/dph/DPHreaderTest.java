/*
 * Created on Sep 26, 2003
 *
 */
package test.spa.dph;

import spa.dph.DPHreader;
import junit.framework.TestCase;


/** * 
 * JUnit test class for the spa.dph.DPHreader class.  
 * @author Chon D. Chung 
 */
public class DPHreaderTest extends TestCase {

	private DPHreader dPHreader;
	private String xml_DPH_String;
	/*
	 * @see TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
		

		xml_DPH_String = "<?xml version=\"1.0\" encoding=\"ISO-8859-15\"?>" +
				          "<dph id=\"GasDecisionProcess::demo-0.1::12-9-2001\" >" +
									 "<question id=\"1\">1</question>" +
									 "<question id=\"2\">8</question>" +
									 "<question id=\"3\">" +
									 "	<answer>3</answer>" +
									 "	<answer>2</answer>" +
									 "	<answer>1</answer>" +
									 "</question>" +
									 "<question id=\"4\">3</question>" +
						  "</dph>";


		dPHreader = DPHreader.getInstance();
	}

	/*
	 * @see TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testReadAnswer() {
			System.out.println("testReadAnswer()************************************");
			dPHreader.readAnswer(xml_DPH_String, 2);
			assertEquals("Question 2 Answer validation:", 8, dPHreader.getAnsNum());
			
			dPHreader.readAnswer(xml_DPH_String, 4);
			assertEquals("Question 4 Answer validation:", 3, dPHreader.getAnsNum());
			
			
			dPHreader.readAnswer(xml_DPH_String, 1);
			assertEquals("Question 1 Answer validation:", 1, dPHreader.getAnsNum());
	}

	public void testReadAnswers() {
		System.out.println("testReadAnswers2()************************************");
		int defaultAnswer = 3; 
		dPHreader.readAnswers(xml_DPH_String, 3);
				for (int i=0; i < dPHreader.getAnsNumsSize(); i++){
					assertEquals("Question 3 - " + (i+1) + " Answer validation:", 
					               defaultAnswer - i, 
					               dPHreader.getAnsNumsElement(i));
					  
				}


	}

}
