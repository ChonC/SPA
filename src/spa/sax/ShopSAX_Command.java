package spa.sax;

/**
 * ShopSAX_Command contains Shop-Find-task commands constant fields, which are 
 * used by ShopSAXManager class and its sub-classes. 
 * 
 * Copyright:    Copyright (c) 2001-2002 Chon Chung.
 * @author       Chon Chung
 * @version      Demo 0.1
 * 
 * <p>          License: Lesser General Public License (LGPL)</p>
 */
public class ShopSAX_Command {

	  //Find-command constant fields:
	  /** Indicates the current task is finding the total number of elements (the number of available shops). */
	  public static final int C_FIND_TOTAL = 1;
	  /** Indicates the current task is finding all the elements that contain the given XML tag. */
	  public static final int C_FIND_ELEMENT = 2;
	  /** Indicates the current task is finding all the elements that contain the XML tag, and its value is equal to the given value. */  
	  public static final int C_FIND_ELEMENT_VALUE = 3;
	  /** Indicates the current task is sorting the elements based on one of their property value. */
	  public static final int C_FIND_SORTED = 4;
	  /** Indicates the current task is reading the selected element information. */
	  public static final int C_FIND_INFO = 5;
	  /** Indicates the current task is reading the information of all the given shop-elements in the lists. */
	  public static final int C_FIND_SHOPLIST_INFO = 6;
	  /** Indicates the current task has been completed. */
	  public static final int C_FIND_DONE = 7;
	  /** initial default value. */
	  public static final int C_FIND_NOTHING = -1;

	  // Match-command constant fields:
	  /**   Finding the all matching elements that have the exact value. */
	  public static final int C_VALUE_EXACT = 21;
	  /**   Finding all matching elements that have bigger value. */
	  public static final int C_VALUE_BIGGER = 22;
	  /**   Finding all matching nodes that have equal or bigger value. */
	  public static final int C_VALUE_EQUAL_OR_BIGGER = 23;
	  /**   Finding all matching nodes that have smaller value. */
	  public static final int C_VALUE_SMALLER = 24;
	  /**   Finding all matching nodes that have equal or smaller value. */
	  public static final int C_VALUE_EQUAL_OR_SMALLER = 25;
	  /**   initial default value. */
	  public static final int C_VALUE_NULL = -21;
	  
	  
	  /** Returns the name of the Find command name.*/
	  public static String getC_FineCommandName(int _findCommand){
		  switch(_findCommand){
		      case C_FIND_TOTAL:
		    	  return "FIND_TOTAL"; 
		      case C_FIND_ELEMENT:
		          return "FIND_ELEMENT";
		      case C_FIND_ELEMENT_VALUE: 
		      	  return "FIND_ELEMENT_VALUE"; 
		      case C_FIND_SORTED:
		          return "FIND_SORTED";
		      case C_FIND_INFO:
		          return "FIND_INFO";
		      case C_FIND_SHOPLIST_INFO:
		          return "FIND_SHOPLIST_INFO";
		      case C_FIND_DONE:
		          return "FIND_DONE";
		      case C_FIND_NOTHING:
		          return "FIND_NOTHING";
		      default:   
		    	  return "WRONG_COMMAND";
		  }
	  }
	  

	  /** Returns the name of the Matching command name.*/
	  public static String getC_MatchingCommandName(int _MatchingCommand){
		  switch(_MatchingCommand){
		      case C_VALUE_EXACT:
		    	  return "VALUE_EXACT"; 
		      case C_VALUE_BIGGER:
		          return "VALUE_BIGGER";
		      case C_VALUE_EQUAL_OR_BIGGER: 
		      	  return "VALUE_EQUAL_OR_BIGGER"; 
		      case C_VALUE_SMALLER:
		          return "VALUE_SMALLER";
		      case C_VALUE_EQUAL_OR_SMALLER:
		          return "VALUE_EQUAL_OR_SMALLER";
		      case C_VALUE_NULL:
		          return "VALUE_NULL";
		      default:   
		    	  return "WRONG_COMMAND";
		  }
	  }

}
