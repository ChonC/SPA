/*
 * Created on Sep 27, 2003
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package test.spa.dph;

import junit.framework.Test;
import junit.framework.TestSuite;
import test.spa.dph.DPHreaderTest;
import test.spa.dph.DPHwriterTest;

/**
 * @author Chon Chung
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class AllTests_DPH {

	public static Test suite() {
		TestSuite suite = new TestSuite("Test for com.rvrw.idss.dih package.");
		//$JUnit-BEGIN$
		suite.addTestSuite(DPHreaderTest.class);
		suite.addTestSuite(DPHwriterTest.class);
		//$JUnit-END$
		return suite;
	}
}
