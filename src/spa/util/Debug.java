package spa.util;



/**
 * Log out the debugging parameter and its value. This class is used during development.
 *
 * Note: before production deployment, search, remove, and clean all "spa.util.Debug".
 * 
 * @author Chon Chung
 *
 */
public class Debug {
	private static final Debug INSTANCE = new Debug();
	private static boolean isEnable;

    private Debug() {
    	isEnable = true;//by changing this value, it stops the out put. 
	}

	public static Debug getInstance(){
	      return INSTANCE;
	}

	/**
	 * to call
	 *
	 * spa.util.Debug.out(paramName, paramValue);//*** Debug
	 */
	public static void out(String paramName, String paramValue){

		if (isEnable){
			
            String outStr = "********************************************************************\n";
                   outStr += "" + paramName + ":"  + paramValue + "\n";
                   
            System.out.println(outStr);
		}
	}
	

	/**
	 * to call
	 *
	 * spa.util.Debug.out(paramName, paramValue);//*** Debug
	 */
	public static void out(String paramName, String paramValue, Boolean noOutline){

		if (isEnable){			
            String outStr = "" + paramName + ":"  + paramValue + "\n";
                   
            System.out.println(outStr);
		}
	}

	/**
	 * to call
	 *
	 * spa.util.Debug.out(paramName, paramValue);//*** Debug
	 */
	public static void out(String message, String paramName, String paramValue){

		if (isEnable){

            String outStr = "********************************************************************\n";
                   outStr += message +"::" + paramName + ":"  + paramValue + "\n";
                   
            System.out.println(outStr);
		}
	}
	

	/**
	 * to call
	 *
	 * spa.util.Debug.out(classReference, paramName, paramValue);//*** Debug
	 */
	public static void out(Object classReference, String paramName, String paramValue){
		String className = classReference.getClass().getName();  
		if (isEnable){

            String outStr = "********************************************************************\n";
                   outStr += className + ":" + paramName + ":"  + paramValue + "\n";
            System.out.println(outStr);
		}
	}
	

	/**
	 * to call
	 *
	 * spa.util.Debug.out(classReference, paramName, paramValue);//*** Debug
	 */
	public static void out(Object classReference, String message, String paramName, String paramValue){
		String className = classReference.getClass().getName();  
		if (isEnable){

            String outStr = "********************************************************************\n";
                   outStr += className + ":" + message + "::" + paramName + ":"  + paramValue + "\n";
            System.out.println(outStr);
		}
	}
}