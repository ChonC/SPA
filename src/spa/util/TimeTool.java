package spa.util;

import java.util.Date;
import java.text.SimpleDateFormat;

/**
* TimeTool utility. */
public class TimeTool {
	private long startTime;
	private long finishTime; 

	/** Return the current time in "MMM dd,yyyy HH:mm:ss" format. */
	public static String getCurrentTime(){
	        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd,yyyy HH:mm:ss");
	        return sdf.format(new Date().getTime());
	}
	
	public void setStartTime(){
		startTime = System.nanoTime();
	}
	public void setFinishTime(){
		finishTime = System.nanoTime();
	}
	/** Returns the elapsed(finishTime - startTime) time in this format("mm:ss:SSS") */ 
	public String getElapsedTime(){
		
		long elapsedTime = ((finishTime - startTime)/1000000); 
		
		String elaspString = (new SimpleDateFormat("mm:ss:SSS")).format(new Date(elapsedTime)); 

		return elaspString;
	}
	
	public static void main (String[] args){
		TimeTool timeTool = new TimeTool(); 
		timeTool.setStartTime(); 
		
		try{
			Thread.sleep(3400);
		}catch(Exception e){}
		timeTool.setFinishTime(); 
		
		
		System.out.println("ElapsedTime: " +  timeTool.getElapsedTime()); 
	}

}