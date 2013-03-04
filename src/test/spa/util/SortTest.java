/*
 *
 * Created on Sep 29, 2003
 */
package test.spa.util;

import junit.framework.TestCase;
import java.util.Vector;

import spa.util.ShopVO;
import spa.util.Sort;


/**
 * JUnit test case for testing Sort and FastQSortAlgorithm classes. 
 * @author Chon D. Chung *
 */
public class SortTest extends TestCase {

	/*
	 * @see TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
	}

	/*
	 * @see TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testSortVector() {
		Vector shopListValues = new Vector(34);
			shopListValues.addElement(new ShopVO(1001001, new Float(0.68).floatValue()));
			shopListValues.addElement(new ShopVO(1001002, new Float(1.3).floatValue()));
			shopListValues.addElement(new ShopVO(1001003, new Float(1.5).floatValue()));
			shopListValues.addElement(new ShopVO(1001004, new Float(1.7).floatValue()));
			shopListValues.addElement(new ShopVO(1001005, new Float(2.4).floatValue()));
			shopListValues.addElement(new ShopVO(1001006, new Float(2.5).floatValue()));
			shopListValues.addElement(new ShopVO(1001007, new Float(3.0).floatValue()));
			shopListValues.addElement(new ShopVO(1002001, new Float(0.61).floatValue()));
			shopListValues.addElement(new ShopVO(1002002, new Float(0.67).floatValue()));
			shopListValues.addElement(new ShopVO(1002003, new Float(0.69).floatValue()));
			shopListValues.addElement(new ShopVO(1002004, new Float(0.86).floatValue()));
			shopListValues.addElement(new ShopVO(1002005, new Float(1.3).floatValue()));
			shopListValues.addElement(new ShopVO(1002006, new Float(1.4).floatValue()));
			shopListValues.addElement(new ShopVO(1002007, new Float(1.5).floatValue()));
			shopListValues.addElement(new ShopVO(1002008, new Float(1.5).floatValue()));
			shopListValues.addElement(new ShopVO(1002009, new Float(1.8).floatValue()));
			shopListValues.addElement(new ShopVO(1002010, new Float(2.3).floatValue()));
			shopListValues.addElement(new ShopVO(1003001, new Float(0.02).floatValue()));
			shopListValues.addElement(new ShopVO(1003002, new Float(0.23).floatValue()));
			shopListValues.addElement(new ShopVO(1003003, new Float(1.0).floatValue()));
			shopListValues.addElement(new ShopVO(1003004, new Float(1.2).floatValue()));
			shopListValues.addElement(new ShopVO(1003005, new Float(1.4).floatValue()));
			shopListValues.addElement(new ShopVO(1003006, new Float(1.4).floatValue()));
			shopListValues.addElement(new ShopVO(1003007, new Float(1.6).floatValue()));
			shopListValues.addElement(new ShopVO(1003008, new Float(2.2).floatValue()));
			shopListValues.addElement(new ShopVO(1003009, new Float(2.3).floatValue()));
			shopListValues.addElement(new ShopVO(1004001, new Float(0.31).floatValue()));
			shopListValues.addElement(new ShopVO(1004002, new Float(0.87).floatValue()));
			shopListValues.addElement(new ShopVO(1004003, new Float(1.3).floatValue()));
			shopListValues.addElement(new ShopVO(1004004, new Float(1.3).floatValue()));
			shopListValues.addElement(new ShopVO(1004005, new Float(1.5).floatValue()));
			shopListValues.addElement(new ShopVO(1004006, new Float(1.5).floatValue()));
			shopListValues.addElement(new ShopVO(1004007, new Float(1.6).floatValue()));
			shopListValues.addElement(new ShopVO(1004008, new Float(1.6).floatValue()));
			
		shopListValues = Sort.sortVector(shopListValues);

		// Check the returned Vector size
		assertEquals(34,shopListValues.size());
		
		// check if the bizValues list has been sorted
		for (int i = 1; i < shopListValues.size(); i++){		  
			assertTrue(((ShopVO)shopListValues.elementAt(i-1)).getPropertyValue() <= ((ShopVO)shopListValues.elementAt(i)).getPropertyValue());
//			System.out.println("Property Value " + (i -1) + ": " + ((ShopVO)bizValues.elementAt(i-1)).getProp());//*** for debugging only
//			System.out.println("Property Value " + i + ": " + ((ShopVO)bizValues.elementAt(i)).getProp());//*** for debugging only
		}
	}
	
	/** Test the 'sortArray()' method */
	public void testSortArray() {
		
		String[] bizIDs = new String[34];

		bizIDs[0] =  "1003001";
		bizIDs[1] =  "1003002";
		bizIDs[2] =  "1004001";
		bizIDs[3] =  "1002001";
		bizIDs[4] =  "1002002";
		bizIDs[5] =  "1001001";
		bizIDs[6] =  "1002003";
		bizIDs[7] =  "1002004";
		bizIDs[8] =  "1004002";
		bizIDs[9] =  "1003003";
		bizIDs[10] =  "1003004";
		bizIDs[11] =  "1002005";
		bizIDs[12] =  "1001002";
		bizIDs[13] =  "1004003";
		bizIDs[14] =  "1004004";
		bizIDs[15] =  "1002006";
		bizIDs[16] =  "1003005";
		bizIDs[17] =  "1003006";
		bizIDs[18] =  "1004006";
		bizIDs[19] =  "1001003";
		bizIDs[20] =  "1002007";
		bizIDs[21] =  "1002008";
		bizIDs[22] =  "1004005";
		bizIDs[23] =  "1003007";
		bizIDs[24] =  "1004008";
		bizIDs[25] =  "1004007";
		bizIDs[26] =  "1001004";
		bizIDs[27] =  "1002009";
		bizIDs[28] =  "1003008";
		bizIDs[29] =  "1003009";
		bizIDs[30] =  "1002010";
		bizIDs[31] =  "1001005";
		bizIDs[32] =  "1001006";
		bizIDs[33] =  "1001007";
	
		bizIDs = Sort.sortArray(bizIDs);

		// Check the returned array size
		assertEquals(34, bizIDs.length);

		// Check if the bizIDs list has been sorted
		for (int i = 1; i < bizIDs.length; i++){//*** for debugging only
			assertTrue(Integer.parseInt(bizIDs[i-1]) <= Integer.parseInt(bizIDs[i]));
//		    System.out.println("bizIDs[" + (i-1) + "]: " + bizIDs[i-1]);
//			System.out.println("bizIDs[" + i + "]: " + bizIDs[i]);
		}
	}

}
