package spa.util;


/*
 * I (Chon) downloaded this algorithm from a Russian web site.  It seems originally
 * written by James Gosling himself and modified by other developers.  I tested it several times,
 * and very pleased with its performance.  However, it requires further testing!
 *
 *
 * @(#)QSortAlgorithm.java	1.3   29 Feb 1996 James Gosling
 *
 * Copyright (c) 1994-1996 Sun Microsystems, Inc. All Rights Reserved.
 *
 * Permission to use, copy, modify, and distribute this software
 * and its documentation for NON-COMMERCIAL or COMMERCIAL purposes and
 * without fee is hereby granted.
 * Please refer to the file http://www.javasoft.com/copy_trademarks.html
 * for further important copyright and trademark information and to
 * http://www.javasoft.com/licensing.html for further important
 * licensing information for the Java (tm) Technology.
 *
 * SUN MAKES NO REPRESENTATIONS OR WARRANTIES ABOUT THE SUITABILITY OF
 * THE SOFTWARE, EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED
 * TO THE IMPLIED WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
 * PARTICULAR PURPOSE, OR NON-INFRINGEMENT. SUN SHALL NOT BE LIABLE FOR
 * ANY DAMAGES SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING OR
 * DISTRIBUTING THIS SOFTWARE OR ITS DERIVATIVES.
 *
 * THIS SOFTWARE IS NOT DESIGNED OR INTENDED FOR USE OR RESALE AS ON-LINE
 * CONTROL EQUIPMENT IN HAZARDOUS ENVIRONMENTS REQUIRING FAIL-SAFE
 * PERFORMANCE, SUCH AS IN THE OPERATION OF NUCLEAR FACILITIES, AIRCRAFT
 * NAVIGATION OR COMMUNICATION SYSTEMS, AIR TRAFFIC CONTROL, DIRECT LIFE
 * SUPPORT MACHINES, OR WEAPONS SYSTEMS, IN WHICH THE FAILURE OF THE
 * SOFTWARE COULD LEAD DIRECTLY TO DEATH, PERSONAL INJURY, OR SEVERE
 * PHYSICAL OR ENVIRONMENTAL DAMAGE ("HIGH RISK ACTIVITIES").  SUN
 * SPECIFICALLY DISCLAIMS ANY EXPRESS OR IMPLIED WARRANTY OF FITNESS FOR
 * HIGH RISK ACTIVITIES.
 */


import java.util.Vector;


/**
 * A quick sort algorithm.  Modified for the SPA project.
 *
 * @author James Gosling
 * @author Kevin A. Smith
 * @version 	@(#)QSortAlgorithm.java	1.3, 29 Feb 1996
 *
 * Quick sort algorithm extended with TriMedian and InsertionSort by Denis Ahrens
 * with all the tips from Robert Sedgewick (Algorithms in C++).
 * It uses TriMedian and InsertionSort for lists shorts than 4.
 * <fuhrmann@cs.tu-berlin.de>
 */
class FastQSortAlgorithm {


        /** Sort method for an array, which contains stores' id.
         * @param a[]     a string array of business stores' ids  */
	static void sortArray(String a[])
	{
		QuickSort(a, 0, a.length - 1);
		InsertionSort(a,0,a.length-1); //InsertionSort for lists shorts than 4.
	}

        /** Sort method for a Vector, which contains multiful ShopVO objects.
         * @param _ShopList_IdValues     a Vector of ShopVO objects*/
	static void sortVector(Vector _ShopList_IdValues)
	{
		QuickSort(_ShopList_IdValues,0,_ShopList_IdValues.size()-1);
		InsertionSort(_ShopList_IdValues,0,_ShopList_IdValues.size()-1);
	}

      /**
        * I (Chon) modified this method to accept a String Array of business stores' ids.
        *
        * This is a generic version of C.A.R Hoare's Quick Sort
	* algorithm.  This will handle arrays that are already
	* sorted, and arrays with duplicate keys.<BR>
	*
	* If you think of a one dimensional array as going from
	* the lowest index on the left to the highest index on the right
	* then the parameters to this function are lowest index or
	* left and highest index or right.  The first time you call
	* this function it will be with the parameters 0, a.length - 1.
	*
	* @param a	   an integer array
	* @param l	 left boundary of array partition
	* @param r	 right boundary of array partition
	*/
	private static void QuickSort(String a[], int l, int r) //***throws Exception
        {

            int M = 4;
            int i;
            int j;
            int v;

              if ((r-l)>M)
              {
                      i = (r+l)/2;
                      if (Integer.parseInt(a[l])>Integer.parseInt(a[i])) swap(a,l,i);	// Tri-Median Methode!
                      if (Integer.parseInt(a[l])>Integer.parseInt(a[r])) swap(a,l,r);
                      if (Integer.parseInt(a[i])>Integer.parseInt(a[r])) swap(a,i,r);

                      j = r-1;
                      swap(a,i,j);
                      i = l;
                      v = Integer.parseInt(a[j]);
                      for(;;)
                      {
                              while(Integer.parseInt(a[++i])<v);
                              while(Integer.parseInt(a[--j])>v);
                              if (j<i) break;
                              swap (a,i,j);

                      }
                      swap(a,i,r-1);
                      QuickSort(a,l,j);
                      QuickSort(a,i+1,r);
              }
        }

        /** QuickSort algorithm for a Vector of ShopVO object.
        * @param shopValuesList      a Vector of ShopVO objects
	* @param left	  left boundary of array partition
	* @param right	  right boundary of array partition*/
        private static void QuickSort(Vector shopValuesList, int left, int right) {
            int M = 4;
            int i;
            int j;
            ShopVO t; //temp reference

                if ((right-left)>M){
                        i = (right+left)/2;
                        if (((ShopVO)shopValuesList.elementAt(left)).getPropertyValue() > ((ShopVO)shopValuesList.elementAt(i)).getPropertyValue())
                             swap(shopValuesList,left,i);	// Tri-Median Methode!
                        if (((ShopVO)shopValuesList.elementAt(left)).getPropertyValue() > ((ShopVO)shopValuesList.elementAt(right)).getPropertyValue())
                             swap(shopValuesList,left,right);
                        if (((ShopVO)shopValuesList.elementAt(i)).getPropertyValue() > ((ShopVO)shopValuesList.elementAt(right)).getPropertyValue())
                             swap(shopValuesList,i,right);

                        j = right-1;
                        swap(shopValuesList,i,j);
                        i = left;
			t = (ShopVO)shopValuesList.elementAt(j);
                        for(;;)
                        {
                                while(((ShopVO)shopValuesList.elementAt(++i)).getPropertyValue() < t.getPropertyValue());
                                while(((ShopVO)shopValuesList.elementAt(--j)).getPropertyValue() > t.getPropertyValue());
                                if (j<i) break;
                                swap (shopValuesList,i,j);
                        }
                        swap(shopValuesList,i,right-1);
                        QuickSort(shopValuesList,left,j);
                        QuickSort(shopValuesList,i+1,right);
                }
        }

        /** Insertion Sort algorithm for a String Array.
	* @param a	 an String array
	* @param l	 left boundary of array partition
	* @param r	 right boundary of array partition*/
	private static void InsertionSort(String a[], int l, int r) //***throws Exception
	{
		int i;
		int j;
		int v;

		for (i=l+1;i<=r;i++)
		{
			v = Integer.parseInt(a[i]);
			j=i;
			while ((j>l) && (Integer.parseInt(a[j-1])>v))
			{
				a[j] = a[j-1];
				j--;
			}
			a[j] = String.valueOf(v);
	 	}
	}

        /** Insertion Sort algorithm for a Vector or ShopVO object.
        * @param shopList_IdValues      a Vector of ShopVO objects
	* @param left	  left boundary of array partition
	* @param right	  right boundary of array partition */
	private static void InsertionSort(Vector shopList_IdValues, int left, int right)
	{
		int i; //loop counter
		int j;
		ShopVO t; //temp reference

		for (i=left+1;i<=right;i++)
		{
			t = (ShopVO)shopList_IdValues.elementAt(i);
			j=i;
			while ((j>left) && (((ShopVO)shopList_IdValues.elementAt(j-1)).getPropertyValue() > t.getPropertyValue()))
			{
                                shopList_IdValues.setElementAt(shopList_IdValues.elementAt(j-1), j);
				j--;
			}
                        shopList_IdValues.setElementAt(t, j);
	 	}
	}

        /** Swap method for a String Array.
	* @param a       an String array
	* @param i	 the current array element index
	* @param j	 the target array index for swap*/
	private static void swap(String a[], int i, int j)
	{
		String T;
		T = a[i];
		a[i] = a[j];
		a[j] = T;
	}

  /** Swap method for a Vector.
    * @param shopList_IdValues     a Vector of ShopVO objects
	* @param i	 the current array element index
	* @param j	 the target array index for swap*/
	private static void swap(Vector shopList_IdValues, int i, int j)
	{
		Object T;
		T = shopList_IdValues.elementAt(i);
                shopList_IdValues.setElementAt(shopList_IdValues.elementAt(j), i);
                shopList_IdValues.setElementAt(T, j);
	}

//******************************************************************************
//  Original source code
//~~~~~~~~~~~~~~~~~~~~~~~
//~~~~~~~~~~~~~~~~~~~~~~~
//
//
///*
// * @(#)QSortAlgorithm.java	1.3   29 Feb 1996 James Gosling
// *
// * Copyright (c) 1994-1996 Sun Microsystems, Inc. All Rights Reserved.
// *
// * Permission to use, copy, modify, and distribute this software
// * and its documentation for NON-COMMERCIAL or COMMERCIAL purposes and
// * without fee is hereby granted.
// * Please refer to the file http://www.javasoft.com/copy_trademarks.html
// * for further important copyright and trademark information and to
// * http://www.javasoft.com/licensing.html for further important
// * licensing information for the Java (tm) Technology.
// *
// * SUN MAKES NO REPRESENTATIONS OR WARRANTIES ABOUT THE SUITABILITY OF
// * THE SOFTWARE, EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED
// * TO THE IMPLIED WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
// * PARTICULAR PURPOSE, OR NON-INFRINGEMENT. SUN SHALL NOT BE LIABLE FOR
// * ANY DAMAGES SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING OR
// * DISTRIBUTING THIS SOFTWARE OR ITS DERIVATIVES.
// *
// * THIS SOFTWARE IS NOT DESIGNED OR INTENDED FOR USE OR RESALE AS ON-LINE
// * CONTROL EQUIPMENT IN HAZARDOUS ENVIRONMENTS REQUIRING FAIL-SAFE
// * PERFORMANCE, SUCH AS IN THE OPERATION OF NUCLEAR FACILITIES, AIRCRAFT
// * NAVIGATION OR COMMUNICATION SYSTEMS, AIR TRAFFIC CONTROL, DIRECT LIFE
// * SUPPORT MACHINES, OR WEAPONS SYSTEMS, IN WHICH THE FAILURE OF THE
// * SOFTWARE COULD LEAD DIRECTLY TO DEATH, PERSONAL INJURY, OR SEVERE
// * PHYSICAL OR ENVIRONMENTAL DAMAGE ("HIGH RISK ACTIVITIES").  SUN
// * SPECIFICALLY DISCLAIMS ANY EXPRESS OR IMPLIED WARRANTY OF FITNESS FOR
// * HIGH RISK ACTIVITIES.
// */
//
///**
// * A quick sort demonstration algorithm
// * SortAlgorithm.java
// *
// * @author James Gosling
// * @author Kevin A. Smith
// * @version 	@(#)QSortAlgorithm.java	1.3, 29 Feb 1996
// * extended with TriMedian and InsertionSort by Denis Ahrens
// * with all the tips from Robert Sedgewick (Algorithms in C++).
// * It uses TriMedian and InsertionSort for lists shorts than 4.
// * <fuhrmann@cs.tu-berlin.de>
// */
//public class FastQSortAlgorithm extends SortAlgorithm
//{
//	/** This is a generic version of C.A.R Hoare's Quick Sort
//	* algorithm.  This will handle arrays that are already
//	* sorted, and arrays with duplicate keys.<BR>
//	*
//	* If you think of a one dimensional array as going from
//	* the lowest index on the left to the highest index on the right
//	* then the parameters to this function are lowest index or
//	* left and highest index or right.  The first time you call
//	* this function it will be with the parameters 0, a.length - 1.
//	*
//	* @param a	   an integer array
//	* @param lo0	 left boundary of array partition
//	* @param hi0	 right boundary of array partition
//	*/
//	private void QuickSort(int a[], int l, int r) throws Exception
//   {
//	int M = 4;
//	int i;
//	int j;
//	int v;
//
//	if ((r-l)>M)
//	{
//		i = (r+l)/2;
//		if (a[l]>a[i]) swap(a,l,i);	// Tri-Median Methode!
//		if (a[l]>a[r]) swap(a,l,r);
//		if (a[i]>a[r]) swap(a,i,r);
//
//		j = r-1;
//		swap(a,i,j);
//		i = l;
//		v = a[j];
//		for(;;)
//		{
//			while(a[++i]<v);
//			while(a[--j]>v);
//			if (j<i) break;
//			swap (a,i,j);
//			pause(i,j);
//                        if (stopRequested) {
//                            return;
//                        }
//		}
//		swap(a,i,r-1);
//		pause(i);
//		QuickSort(a,l,j);
//		QuickSort(a,i+1,r);
//	}
//}
//
//	private void swap(int a[], int i, int j)
//	{
//		int T;
//		T = a[i];
//		a[i] = a[j];
//		a[j] = T;
//	}
//
//	private void InsertionSort(int a[], int lo0, int hi0) throws Exception
//	{
//		int i;
//		int j;
//		int v;
//
//		for (i=lo0+1;i<=hi0;i++)
//		{
//			v = a[i];
//			j=i;
//			while ((j>lo0) && (a[j-1]>v))
//			{
//				a[j] = a[j-1];
//				pause(i,j);
//				j--;
//			}
//			a[j] = v;
//	 	}
//	}
//
//	public void sort(int a[]) throws Exception
//	{
//		QuickSort(a, 0, a.length - 1);
//		InsertionSort(a,0,a.length-1);
//		pause(-1,-1);
//	}
//}
//******************************************************************************

}
