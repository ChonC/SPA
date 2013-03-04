/*
 * Created on Oct 1, 2003
 */
package test.cityfind.gasfind.sax;

import java.net.URL;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import junit.framework.TestCase;
import cityfind.gasfind.sax.GasSAXManager;
import cityfind.gasfind.sax.GasXMLTag;
import spa.util.*;

/**
 * JUnit test class for the 'GasSAXManager'.  
 * @author Chon D. Chung
 * 
 * 
 *<p>@toDo:        1. Need to implement the methods that accept the sample list (String[] targetList) <br>
 *                 2. Update the equal() method in the GasSAXManager class..</p>
 */
public class GasSAXManagerTest extends TestCase {
	private static String xmlDataSource;
	private GasSAXManager gasSAXManager; 
	
	/*
	 * Set up the instance with a sample xml-data file
	 * for this testing.  
	 * 
	 * Note: The XML data location might vary based 
	 * on your environment.  I used the Eclipse IDE, and 
	 * my XML data file located under the 'src' directory.   
	 *  
	 * @see TestCase#setUp()
	 */
	@Before
	protected void setUp() throws Exception {
		super.setUp();
		try {
		
			gasSAXManager  = new GasSAXManager(new URL("http://chon.techliminal.com/cityfind/houstonXML.txt"), "houstonXML.txt", 
					                                                     GasXMLTag.getTagName(GasXMLTag.TAG_ID_STORE));
//			
//
//			gasSAXManager  = new GasSAXManager(null, "houstonXML.txt", 
//					                                                     GasXMLTag.getTagName(GasXMLTag.TAG_STORE));
		} catch (Exception e) {
		       e.printStackTrace();
		}
	}

	/*
	 * @see TestCase#tearDown()
	 */
	@After
	protected void tearDown() throws Exception {
		super.tearDown();
	}

//	/*
//	 * Class to test for GasSAXManager getInstance(String, String)
//	 *//***** I might need to update the equal() in the GasSAXManager class.
//	 */
//	public void testCreatInstanceStringString() {
//		assertEquals(gasSAXManager, GasSAXManager.getInstance());
//	}
//
//	public void testGetInstance() {
//		assertNotNull(GasSAXManager.getInstance());
//	}

	/*
	 * Class to test for String[] findStoreHasGas(String)
	 */
	@Test
	public void testFindStoreHasGasString() {
		String[] stores = gasSAXManager.findShopHasGas(GasXMLTag.getTagName(GasXMLTag.TAG_DIESEL));
        
		//store lists that sell a disel gas
		String[] diselStores = new String[15];
		diselStores[0] = "1001003";
		diselStores[1] = "1001005";
		diselStores[2] = "1001006";
		diselStores[3] = "1001007";
		diselStores[4] = "1002004";
		diselStores[5] = "1002005";
		diselStores[6] = "1002006";
		diselStores[7] = "1002008";
		diselStores[8] = "1003004";
		diselStores[9] = "1003006";
		diselStores[10] = "1003008";
		diselStores[11] = "1004003";
		diselStores[12] = "1004004";
		diselStores[13] = "1004005";
		diselStores[14] = "1004007";

		System.out.println("testFindStoreHasGasString----------(Start)--------------------------------");
			for (int i=0; i < stores.length; i++){
				System.out.println("stores[" + i + "] = \"" + stores[i] + "\";");

				//Check if the two array objects are equal 
				assertEquals(diselStores[i].toString(), stores[i].toString());
			}
		System.out.println("testFindStoreHasGasString-----------(End)---------------------------------");
	}

	/*
	 * Class to test for String[] findStoreHasGas(String, String[])
	 */
	@Test
	public void testFindStoreHasGasStringStringArray() {
	}

	/*
	 * Class to test for String[] findBrandStore(String)
	 */
	@Test
	public void testFindBrandStoreString() {
		String[] chevronStores = gasSAXManager.findBrandShops("Chevron");
		assertNotNull(chevronStores);
		System.out.println("testFindStoreHasGasStringString:Chevron----------(Start)--------------------------------");
			for (int i=0; i < chevronStores.length; i++){
				System.out.println("Chevron Stores[" + i + "] = " + chevronStores[i] );
				//Since the ID of Chevron station start with "1001", verify the all Chevron stations' ID
				assertEquals("1001", chevronStores[i].substring(0,4));
			}
		System.out.println("testFindStoreHasGasStringString:Chevron-----------(End)---------------------------------");
		try {
		gasSAXManager.reset(new URL("http://chon.techliminal.com/cityfind/houstonXML.txt"), "houstonXML.txt", 
                GasXMLTag.getTagName(GasXMLTag.TAG_ID_STORE));
		}catch (Exception e){}
		
		String[] exxonStores = gasSAXManager.findBrandShops("Exxon");
		assertNotNull(exxonStores);
		System.out.println("testFindStoreHasGasStringString:Exxon----------(Start)--------------------------------");
			for (int i=0; i < exxonStores.length; i++){
				System.out.println("Exxon Stores[" + i + "] = " + exxonStores[i] );
				assertEquals("1002", exxonStores[i].substring(0,4));
			}
		System.out.println("testFindStoreHasGasStringString:Exxon-----------(End)---------------------------------");

		String[] shellStores = gasSAXManager.findBrandShops("Shell");
		assertNotNull(shellStores);
		System.out.println("testFindStoreHasGasStringString:Shell----------(Start)--------------------------------");
			for (int i=0; i < shellStores.length; i++){
				System.out.println("Shell Stores[" + i + "] = " + shellStores[i] );
				assertEquals("1003", shellStores[i].substring(0,4));
			}
		System.out.println("testFindStoreHasGasStringString:Shell-----------(End)---------------------------------");
		

		String[] texacoStores = gasSAXManager.findBrandShops("Texaco");
		assertNotNull(texacoStores);
		System.out.println("testFindStoreHasGasStringString:Texaco----------(Start)--------------------------------");
			for (int i=0; i < texacoStores.length; i++){
				System.out.println("Texaco Stores[" + i + "] = " + texacoStores[i] );
				assertEquals("1004", texacoStores[i].substring(0,4));
			}
		System.out.println("testFindStoreHasGasStringString:Texaco-----------(End)---------------------------------");
	}

	/*
	 * Class to test for String[] findBrandStore(String, String[])
	 */
	@Test
	public void testFindBrandStoreStringStringArray() {
	}

	/*
	 * Class to test for String[] sortLowestGas(String)
	 */
	@Test
	public void testSortLowestGasString() {//Stop Here: Oct 1/ 2003**************************************************************************************************************
		
		String[] stores = gasSAXManager.sortLowestGas(GasXMLTag.getTagName(GasXMLTag.TAG_DIESEL));
		
		String[] lowestGas = new String[15];
		lowestGas[0] = "1002004";
		lowestGas[1] = "1004004";
		lowestGas[2] = "1004005";
		lowestGas[3] = "1001007";
		lowestGas[4] = "1001003";
		lowestGas[5] = "1001006";
		lowestGas[6] = "1002005";
		lowestGas[7] = "1001005";
		lowestGas[8] = "1003008";
		lowestGas[9] = "1004007";
		lowestGas[10] = "1004003";
		lowestGas[11] = "1003004";
		lowestGas[12] = "1002008";
		lowestGas[13] = "1003006";
		lowestGas[14] = "1002006";

		System.out.println("testSortLowestGasString----------(Start)--------------------------------");
			for (int i=0; i < stores.length; i++){
				System.out.println("lowestGas[" + i + "] = \"" + stores[i] + "\";");

				//Check if the new array is matched with the existing array. 
				assertEquals(lowestGas[i].toString(), stores[i].toString());
			}
		System.out.println("testSortLowestGasString-----------(End)---------------------------------");
	}

	/*
	 * Class to test for String[] sortLowestGas(String, String[])
	 */
	@Test
	public void testSortLowestGasStringStringArray() {
	}

	/*
	 * Class to test for String[] sortShortestDistance()
	 */
	@Test
	public void testSortShortestDistance() {
		String[] stores = gasSAXManager.sortShortestDistance();
		
		//Already Sorted array to compare the sortShortestDistance() method result
		String[] sortedArray = new String[34];
		sortedArray[0] = "1003001";
		sortedArray[1] = "1003002";
		sortedArray[2] = "1004001";
		sortedArray[3] = "1002001";
		sortedArray[4] = "1002002";
		sortedArray[5] = "1001001";
		sortedArray[6] = "1002003";
		sortedArray[7] = "1002004";
		sortedArray[8] = "1004002";
		sortedArray[9] = "1003003";
		sortedArray[10] = "1003004";
		sortedArray[11] = "1002005";
		sortedArray[12] = "1001002";
		sortedArray[13] = "1004003";
		sortedArray[14] = "1004004";
		sortedArray[15] = "1002006";
		sortedArray[16] = "1003005";
		sortedArray[17] = "1003006";
		sortedArray[18] = "1004006";
		sortedArray[19] = "1001003";
		sortedArray[20] = "1002007";
		sortedArray[21] = "1002008";
		sortedArray[22] = "1004005";
		sortedArray[23] = "1003007";
		sortedArray[24] = "1004008";
		sortedArray[25] = "1004007";
		sortedArray[26] = "1001004";
		sortedArray[27] = "1002009";
		sortedArray[28] = "1003008";
		sortedArray[29] = "1003009";
		sortedArray[30] = "1002010";
		sortedArray[31] = "1001005";
		sortedArray[32] = "1001006";
		sortedArray[33] = "1001007";
		
		System.out.println("stores----------(Start)--------------------------------");
			for (int i=0; i < stores.length; i++){
				System.out.println("tores[" + i + "] = " + stores[i]);

				//Check if the array-list has been sorted. 
				assertEquals(sortedArray[i].toString(), stores[i].toString());
			}
		System.out.println("stores-----------(End)---------------------------------");
	}

	/*
	 * Class to test for String[] sortShortestDistance(String[])
	 */
	@Test
	public void testSortShortestDistanceStringArray() {
		
		//The sample array for the testing
		String[] sampleStores = new String[16];
		sampleStores[0] = "1001001";
		sampleStores[1] = "1001002";
		sampleStores[2] = "1001003";
		sampleStores[3] = "1001004";
		sampleStores[4] = "1001005";
		sampleStores[5] = "1001006";
		sampleStores[6] = "1001007";
		sampleStores[7] = "1003001";
		sampleStores[8] = "1003002";
		sampleStores[9] = "1003003";
		sampleStores[10] = "1003004";
		sampleStores[11] = "1003005";
		sampleStores[12] = "1003006";
		sampleStores[13] = "1003007";
		sampleStores[14] = "1003008";
		sampleStores[15] = "1003009";		

		//The sorted array list to compare the result
		String[] sortedArray = new String[16];
		sortedArray[0] = "1003001";
		sortedArray[1] = "1003002";
		sortedArray[2] = "1001001";
		sortedArray[3] = "1003003";
		sortedArray[4] = "1003004";
		sortedArray[5] = "1001002";
		sortedArray[6] = "1003005";
		sortedArray[7] = "1003006";
		sortedArray[8] = "1001003";
		sortedArray[9] = "1003007";
		sortedArray[10] = "1001004";
		sortedArray[11] = "1003008";
		sortedArray[12] = "1003009";
		sortedArray[13] = "1001005";
		sortedArray[14] = "1001006";
		sortedArray[15] = "1001007";
		
		//Sorted the array
		String[] stores = gasSAXManager.sortShortestDistance(sampleStores);
		
		
		System.out.println("stores----------(Start)--------------------------------");
			for (int i=0; i < stores.length; i++){
				System.out.println("stores[" + i + "] = \"" + stores[i] + "\";");

				//Check if the array-list has been sorted. 
				assertEquals(sortedArray[i].toString(), stores[i].toString());
			}
		System.out.println("stores-----------(End)---------------------------------");
	}
	@Test
	public void testGetStoreInfo() {
		String storeInfo = gasSAXManager.getGasShopInfo("1001001");

		System.out.println("gasSAXManager.getShopList().size():" + gasSAXManager.getShopList().size());
//		assertEquals(" Name: Chevron (id: 1001001)", storeInfo.substring(0,28));
		System.out.println(":" + storeInfo.substring(0,28) + ":");
	}

}
