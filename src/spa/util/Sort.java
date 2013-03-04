package spa.util;

import java.util.Vector;


/**
 * Title:        CityFinder (SPA Demo Applet)
 *
 * Description:  Sort is a sorting class for the BizSAXReader and its sub-classes.
 *               When a BizSAXReader class needs to sort the business entities
 *               based on the one of their property value, such as find the Gas-station
 *               with the cheapest gas price, this class will be utilized for the sorting task.
 *
 * Copyright:    Copyright (c) 2001-2002 Chon Chung.
 * @author       Chon Chung
 * @version      Demo 0.1
 * 
 * 
 * <p>          License: Lesser General Public License (LGPL)</p>
 */
public class Sort {

    /**
     * Sort a Vector list, which contains multiful BizVO objects.
     * 
     * 	@status: Under improvement!
     *  @todo 1. sorting-rank
     *  */
    public static Vector sortVector(Vector  bizVec){

        FastQSortAlgorithm.sortVector(bizVec);
//********************************************************************************************
//***@todo
//*** ((float[])bizVec.elementAt(minIndex))[2] <-- Should store the sorting-rank,
//                                                 if more than one entities have the SAME values.
//********************************************************************************************

        return bizVec;
    }


    /**
     * Sort the array list, which contains the business stores' ID from the 
     * XML data-source.
     *
     * Since a SAX XML parser reads a XML file from the beginning to end,
     * we have to sort the bizList array (which contains the stores' ID) in
     * an ascending order in order to read the XML file from its beginning.
     */
    public static String[] sortArray(String[]  bizIDs){

        FastQSortAlgorithm.sortArray(bizIDs);

        return bizIDs;
    }
}
















