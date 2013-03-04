/*
 * Created on Sep 27, 2003
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package test.spa.dph;

import spa.dph.DPHreader;
import spa.dph.DPHwriter;
import junit.framework.TestCase;


/**
 * 
 * JUnit test class for the spa.dph.DPHwriter class.
 */
public class DPHwriterTest extends TestCase {
	private String id;
	private String userDPH;
	private DPHreader dPHreader;
	/*
	 * @see TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();

		id = "GasDecisionProcess::demo-0.1::12-9-2001";
	  	userDPH = DPHwriter.newDPH(id);
		dPHreader = DPHreader.getInstance();
	}

	/*
	 * @see TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	/**
	 * 
	 *
	 */
	public void testAddAnswer() {

		//add an answer
		userDPH = DPHwriter.addAnswer(userDPH, "1", "3");
		
		//read the answer
		dPHreader.readAnswer(userDPH, 1);
		assertEquals("Question 1 Answer validation:", 3, dPHreader.getAnsNum());
		
		//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		
		//add an answer
		userDPH = DPHwriter.addAnswer(userDPH, "2", "8");
		
		//read the answer
		dPHreader.readAnswer(userDPH, 2);
		assertEquals("Question 2 Answer validation:", 8, dPHreader.getAnsNum());		
	}
	
	/**
	 * 
	 *
	 */
	public void testAddAnswers() {
		//add answers 
		String[] answers = {"3", "2", "1" };
		userDPH = DPHwriter.addAnswers(userDPH, "3", answers);
		
		//read the answers
		int defaultAnswer = 3; 
		dPHreader.readAnswers(userDPH, 3);
				for (int i=0; i < dPHreader.getAnsNumsSize(); i++){
					  assertEquals("Question 3 - " + (i+1) + " Answer validation:", 
								   defaultAnswer - i, 
								   dPHreader.getAnsNumsElement(i));
				}		
	}

}
