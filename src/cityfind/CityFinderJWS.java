package cityfind;

import cityfind.gasfind.GasDecisionProcess;
import cityfind.gui.*;


import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.lang.ClassLoader;
import javax.swing.ImageIcon;

import spa.DecisionProcess;
import spa.ShopElement;
import spa.question.Question;
import sun.awt.image.URLImageSource;
/**
 * <p>Title: CityFinder: Mobile city search demo application</p>
 *
 * <p>
 * <b>Description:</b>
 *
 *                 This demo application lets a user find the best 
 *                 gas stations in Houston and Austin city based on his/her saved SPA-decisions.</p>
 *                 <p>
 *                 It displays a map of Houston city downtown area, and
 *                 provides 4 questions to the user to collect his/her decisions.
 *                 After the initial interactions (training), the application
 *                 would record/store the user decisions for the one-click
 *                 automation. (For this simple demo purpose, this application conducts
 *                 the searches from the sample static XML data text files in a remote web server.)  </p>
 *
 *                 <p>
 *                 <b>CityFinder is powered by the SPA Algorithm:</b></p>
 *                 <p>
 *
 *                 SPA automation Algorithm:<br/><br/>
 *                 
 *                 SPA (Smart Process Automation) is the Fractal Object-Oriented Algorithm; 
 *                 it records the process and automates them.  
 *                 
 *                 It is based on the Decision Factor (DF), which is the basic building block 
 *                 that influences and defines the decision outcome.  For example, 
 *                 if we would like to locate the nearest gas station with the best price, 
 *                 there are 4 Decision Factors: type of gas, gas brand, distance, and price.
 *                 With each Decision Factor, we can create a Decision Question (DQ), 
 *                 which interactively asks a person for his/her decision process, step-by-step. 
 *                 By combining all of the DQ’s in a logical manner, we can develop an SPA 
 *                 that records the person's decision process for one-click automation.
 *                 
 *                 Moreover, SPA is a Fractal Intelligence Algorithm. By combining multiple SPA modules, 
 *                 we can solve complex decision tasks such as: finding the best place for gas, 
 *                 lunch and shopping, and then drive a route that includes all of them.
 *</p>
 *                 
 *<p><b>
 *Note:</b>        The <u>'spa'</u> and <u>'cityfind.gasfind'</u>
 *                 packages are the core parts of this application.
 *                 The <u>'spa'</u> package provides framework. And,
 *                 <u>'cityfind.gasfind'</u> provides a working example of
 *                 how to extend and utilize the super (<u>'spa'</u>) framework package.
 *                 GasSAXManager and GasDecisionProcess classes in the gasfind package are the good places
 *                 to start learning about this application.</p>
 *                 <p>
 *                 This CityFinderDemo class and 'cityfind.gui' package are
 *                 specifically designed for this demo application only, so you are welcome 
 *                 to change for more robust solutions.  Also, check out the oneClickAutomation()
 *                 method in this class, you will find how simple it is to use
 *                 the SPA framework. </p>
 *
 *<p> @todo        1. seperate the network connection related methods into a different class.<br/>
 *                 2. The undo process - a user should be able to go back to the steps. <br>
 *                 3. Check the final data quality.  If the final data fail to meet the
 *                 SPA data quality requirement, the application should do the more Decision processes.</p>
 *
 *
 *<p> Copyright:    Copyright (c) 2001-2004 Chon D. Chung.</p>
 *<p> @author       Chon D. Chung</p>
 *<p> @version      Demo 0.7</p>
 *
 * <p>          License: Lesser General Public License (LGPL)</p>
 */

public class CityFinderJWS extends Frame implements ActionListener{

  /** The start button event for the cityfind.gui.StartPanel.*/
  public static final String START_APP_EVENT = "Start";
  /** The Next button event for the cityfind.gui.QuestionPanel.*/
  public static final String QUE_NEXT_EVENT = "Next Question";
  /** The completion event of collecting user decisions. Used by cityfind.gui.QuestionPanel class*/
  public static final String QUE_DONE_EVENT = "Collection Done";
  /** the Austin one-click search button Event for the <u>cityfind.gui.ToNextCity.*/
  public static final String ONE_CLICK_EVENT = "ONE CLICK";

  /** 
   *  The JAR file name, it is used for accessing the local resource files (images and XMLs)
   *  if an Internet connection is not available. */
  public static final String JAR_NAME = "cityfinderdemo.jar";


  /** A panel displays the decision questions to collect a user decision answers. */
  private QuestionPanel quePanel;
  /** Reference of the SPA process. */
  private static DecisionProcess decisionProcess;
  /** Canvas to display a city map. */
  private MapCanvas mapCanv;
  /** A panel, it displays the Houston city search results and 'search Austin'
   *  button. */
  private ToNextCityPanel toNextCity;
  /** A panel displays the Austin city search progress status. */
  private OneClickStatusPanel austinSearch;
  /** The start panel that displays this application GUI. */
  private StartPanel startPanel;
  /** URL, internet connection validation status. */
  private boolean url_status;
  /** URL address of the resource files location*/
  private String urlStr;
  /** Houston Map name */
  private String houstonMap;
  /** Houston gas-stations data XML file */
  private String houstonXML;
  /** Austin Map name */
  private String austinMap;
  /** Austing gas-stations data XML file */
  private String austinXML;

  /**
   * Indicates this application is a Java Web Start application.
   *
   * To easy demo on web, I create this application as a Java Web Start application.
   * For debugging and testing run as a Java Application, it should be set to false.
   * 
   * Since a Java Web Start application use the 'ClassLoader' to access
   * local resource files from a JAR file, this variable is used 
   * to change the setting of file location for accessing the local resource files.
   * 
   * 
   * @Todo: Bug: the current setting does allows accessing the local resource files for a standard JAR application.  But, when I actually deploy it on the Java Web Start environment, I am not able to access the local resource files from the JAR folder.  It might caused by the security settion of a JWS application.   */
  private boolean isJavaWebStart = false; 									


  /**Default Constructor.  Initializes the properties, and displays
   * the starting window panel for the Houston city search.
   *
   * @param urlStr      URL address of the resource files location
   * @param houstonMap  Houston Map file name
   * @param houstonXML  Houston gas-stations information XML resource file
   * @param austinMap   Austin Map file name
   * @param austinXML   Austing gas-stations information XML resource file
   */
  public CityFinderJWS(String urlStr,
						String houstonMap, String houstonXML,
						String austinMap, String austinXML,
						boolean isJavaWebStart) {
	  this.urlStr = urlStr;
	  this.houstonMap = houstonMap;
	  this.houstonXML = houstonXML;
	  this.austinMap = austinMap;
	  this.austinXML = austinXML;
	  this.isJavaWebStart = isJavaWebStart;

	  setLayout(new FlowLayout(FlowLayout.LEFT));

	  //for the window closing button event
	  addWindowListener(new WindowAdapter()
	  { public void windowClosing(WindowEvent e)
		  { System.exit(0);
		  }
	  });

	  initializeHoustonSearch();
  }

  /**
   * Gets the shop XML data file and initializes the SPA process for the
   * Houston city search.
   *
   * @param cityShopXML   the city gas-stations information XML source
   */
  private void setSPA_Houston(String cityShopXML){
	  URL url=null;
	  if (decisionProcess == null){
			if (url_status){//if network connection is available
				  try {
						  url = new URL (urlStr + cityShopXML);
						  decisionProcess = new GasDecisionProcess(url, cityShopXML);
						  startPanel.appendText(">> Received the business XML data file... \n" +
												">> Successfully initialized.  Click the Start button.");
						  startPanel.enableStartButton();
				  }catch(MalformedURLException em) {
						  try{
								// Get the local file instead.
								if (isJavaWebStart){
									  url = this.getXML_JAR_URLpath(cityShopXML, startPanel);
									  decisionProcess = new GasDecisionProcess(url, cityShopXML);
								}else{
									decisionProcess = new GasDecisionProcess(cityShopXML);									
								}
								startPanel.appendText(">> Using a local data file... \n" +
                                                      ">> Successfully initialized.  Click the Start button.");
								startPanel.enableStartButton();
						  }catch(Exception ex){
							printError(startPanel, ex, url.toString());
								  return;
						  }
				  }catch (Exception  e ) {
					printError(startPanel, e, url.toString());
				  }
			}else{
				  try{
						// Get the local file instead.
						if (isJavaWebStart){
							  url = this.getXML_JAR_URLpath(cityShopXML, startPanel);
							  decisionProcess = new GasDecisionProcess(url, cityShopXML);
						}else{
							decisionProcess = new GasDecisionProcess(cityShopXML);									
						}
						startPanel.appendText(">> Using a local data file... \n" +
                                              ">> Successfully initialized.  Click the Start button.");
						startPanel.enableStartButton();
				  }catch(Exception exc){
					printError(startPanel, exc, url.toString());
				  }
			}
	  }
  }
  
  /**
   * Gets the Austin city shop XML file, and resets the SPA process for Automation
   * search in Austin city.
   *
   * @param cityShopXML   the city gas-stations information XML source
   */
  private void setSPA_Austin(String cityShopXML){
	  URL url=null;

			if (url_status){
				  try {
						  url = new URL (urlStr + cityShopXML);
						  ((GasDecisionProcess)decisionProcess).reset(url, cityShopXML);
						  austinSearch.appendText(">> Received the business XML file from the remote URL... \n" +
											 ">> Received the data XML file.");
				  }catch(MalformedURLException em) {
						  try{
								// If the remote file is not avaiable, Get the local file instead.
								if (isJavaWebStart){
									  url = this.getXML_JAR_URLpath(cityShopXML, austinSearch);
									((GasDecisionProcess)decisionProcess).reset(url,cityShopXML);
								}else{
									((GasDecisionProcess)decisionProcess).reset(cityShopXML);									
								}
								austinSearch.appendText(">> Received the data XML file.");
						  }catch(Exception ex){
							printError(austinSearch, ex, url.toString());
								return;
						  }
				  }catch (Exception  e ) {
					printError(austinSearch, e, url.toString());
				  }
			}else{
				  try{
						// If the remote file is not avaiable, Get the local file instead.
						if (isJavaWebStart){
							  url = this.getXML_JAR_URLpath(cityShopXML, austinSearch);
							((GasDecisionProcess)decisionProcess).reset(url,cityShopXML);
						}else{
							((GasDecisionProcess)decisionProcess).reset(cityShopXML);									
						}
					  	austinSearch.appendText(">> Received the data XML file.");
				  }catch(Exception exc){
					printError(austinSearch, exc, url.toString());
				  }
			}
  }

  /**
   * By using user's recorded decisions, performs an auto search in
   * Austin city area, and displays the results.
   */
  public void oneClickAutomation(){
	  Question question;
	  austinSearch.appendText("\n\n>>One click auto search:");

	  while(decisionProcess.getTaskStatus() != DecisionProcess.TASK_DONE) {
		  question = decisionProcess.getNextQuestion();
		  question.doAutoAction();
		  austinSearch.appendText("\n>>" + question.toString());
		  austinSearch.increaseProgressBar(20);//increase the bar size to indicate the progress
	  }
	  austinSearch.increaseProgressBar(100);//increase the bar size to indicate the task completion.
	  displayAustinResults();
  }
  

  /** Returns the SPA Decision Process reference. */
  public static DecisionProcess getDecisionProcessInstance(){
	return decisionProcess;
  }
  
  /** Initialized the starting panel and its properties for the
   *  Houston search.
   */
  private void initializeHoustonSearch(){
	  startPanel = new StartPanel(this);
	  startPanel.setSize(710,465);
	  add(startPanel);
	  this.pack();
	  setTitle("CityFinder (current task: finds gas-stations near Galleria Mall in Houston, Texas U.S.A)");

	  //check the URL connection availability status by validating the destination image.
	  url_status = this.URL_ImageStatus(this.urlStr + this.houstonMap);
	  //get Houston map
	  boolean hasMap = setHoustonMap(houstonMap);
	  //if successfully got the map, loads the Houston business XML datasource
	  if (hasMap) setSPA_Houston(houstonXML);
  }

  /** Gets the Houston city map, and puts it on a Canvas to display. */
  private boolean setHoustonMap(String mapName){
	  Image mapImage = null;
	  ImageIcon imageIcon;
	  boolean hasFile = false;

	  //for displaying message on the panel.
	  startPanel.appendText(">> Connecting... \n" +
							">> Destination: " + urlStr + " \n");

	  if (url_status){//If URL Internet connection is available, download the image.
			try {
					URL url = new URL(urlStr + mapName); //get the Hoston map from a remote Server
					imageIcon = new ImageIcon(url);
					mapImage = imageIcon.getImage();
					startPanel.appendText(">> Received Houston city Map... \n");
					hasFile = true;
			}catch(MalformedURLException em) {
					try{
							// If the network connection is not available, get the local image file.
							startPanel.appendText(">> Network resources are unavailable...  \n" +
												  ">> Use the local file instead \n");
							mapImage = getLocalImage(mapName, startPanel);
							hasFile = true;
					}catch(Exception ex){
						printError(startPanel, ex, mapName);
						return false;
					}
			}catch(Exception e){
				printError(startPanel, e, mapName);
			}
	  }else{//If URL connection is not available, use the local image file.
					try{
							// Get the local image instead.
							startPanel.appendText(">> Network resource file are not available...  \n" +
											      ">> Use the local files instead \n");
							mapImage = getLocalImage(mapName, startPanel);
							hasFile = true;
					}catch(Exception exc){
						printError(startPanel, exc, mapName);
					}

	  }

	  if (hasFile){
		  mapCanv = new MapCanvas(mapImage);
		  return true;
	  }
	  return false;
  }

  /** Gets the Austin city map, and displays it on the Canvas. */
  private boolean setAustinMap(String mapName){
	  Image mapImage = null;
	  ImageIcon imageIcon;
	  boolean hasFile = false;


	  austinSearch.appendText(">> Connecting... \n" +
							  ">> Destination: " + urlStr + " \n");

	  if (url_status){//If URL connection is available, download the image.
			try {
					URL url = new URL(urlStr + mapName); //get the Hoston map from the Server
					imageIcon = new ImageIcon(url);
					mapImage = imageIcon.getImage();
					austinSearch.appendText(">> Received the city Map from the remote URL server... \n");
					hasFile = true;
			}catch(MalformedURLException em) {
					try{
							//If URL connection is unavailable, get the local image instead.
							austinSearch.appendText(">> Network resource file are unavailable...  \n" +
												 ">> Use the local file instead \n");
							mapImage = getLocalImage(mapName, austinSearch);
							hasFile = true;
					}catch(Exception ex){
							printError(austinSearch, ex, mapName);
							return false;
					}
			}catch(Exception e){
				printError(austinSearch, e, mapName);
			}
	  }else{//If URL connection is unavailable, use the local image file.
					try{
							// Get the local image instead.
							mapImage = getLocalImage(mapName, austinSearch);
							austinSearch.appendText(">> Network resource file are unavailable...  \n" +
											   ">> Use the local file instead \n");
							mapImage = getLocalImage(mapName, austinSearch);
							hasFile = true;
					}catch(Exception exc){
						printError(austinSearch, exc, mapName);
					}

	  }

	  if (hasFile){
		  mapCanv.reset(mapImage);  //resets the canvas with the new map
		  return true;
	  }
	  return false;
  }
  /** Displays the error on the screen. */
  private void printError(I_MsgDisplay msgScreen, Exception e, String fileLocation){
	msgScreen.printError(">> Exception: " + e + "\n" +
				         ">> Fatal error occurred : Can not access the resource files  \n" +
					     ">> File location: " + fileLocation +  "\n" +
					     ">> Please report the error \n");				  
  }

  
  /** Returns the image from the Java Web Start application JAR folder. */
  public Image getLocalImage(String imgName, I_MsgDisplay msgScreen) throws Exception{  
  	URL url;  
	String jarPath;
	ImageIcon imageIcon;
	ClassLoader classLoader; 
	
		
		if (isJavaWebStart){
			jarPath = ClassLoader.getSystemResource(CityFinderJWS.JAR_NAME).toString();	
			msgScreen.appendText(">>Image Source = Jar:"+ jarPath + "!/" + imgName + "\n");   
			url = new URL("jar:" + jarPath + "!/" + imgName);
			imageIcon = new ImageIcon(url);		
		}else{
			msgScreen.appendText(">>Image Source: imgName = "+ imgName + "\n");
			imageIcon = new ImageIcon(imgName);
		}	
	return imageIcon.getImage();  
  }
  
  /** Returns the local XML data file URL path in the JAR folder. */
  public URL getXML_JAR_URLpath(String xmlName, I_MsgDisplay msgScreen) throws Exception{  
	URL url = new URL("jar:" + 
	               ClassLoader.getSystemResource(CityFinderJWS.JAR_NAME).toString() + 
                   "!/" + xmlName);   
		msgScreen.appendText(">>XML Source = JAR URL: " + url.toString() + "\n");
	return url;                   
  }

  /** Simple way to validate a URL connection availability status by checking the remote image.  */
  private boolean URL_ImageStatus(String imageURL){//**************************************************************************
		   try{
			   URL url = new URL(imageURL);
			   return url.getContent() instanceof URLImageSource;
		   }catch(Exception e){}
		 return false;
  }

  /**Based on the receives actionEvents, do the next step. */
  public void actionPerformed(ActionEvent evt){
	//receives the start button event from the startPanel
	if (evt.getActionCommand().equals(START_APP_EVENT)) {
		displayHoustonSearch();

	//receives the next button event from the QuestionPanel
	}else if (evt.getActionCommand().equals(QUE_NEXT_EVENT)){
		((Question) quePanel.getQuestion()).doAction();
		quePanel.setNextQuestion(((GasDecisionProcess)decisionProcess).getNextPanelQuestion());

        //if it reached the final question.
		if (((Question) quePanel.getQuestion()).getID() == 4)
			  quePanel.setNextButActionCommand(QUE_DONE_EVENT);

	//receives the final next button event from the QuestionPanel
	}else if (evt.getActionCommand().equals(QUE_DONE_EVENT)){
		((Question) quePanel.getQuestion()).doAction();
		displayHoustonResults();

	//received the austin search button event
	}else if (evt.getActionCommand().equals(ONE_CLICK_EVENT)){
	  austinSearch = new OneClickStatusPanel();
	  this.remove(toNextCity);
	  austinSearch.setSize(255,430);
	  this.add(austinSearch);
	  this.pack();
	  boolean hasMap = setAustinMap(austinMap);
	  austinSearch.increaseProgressBar(10); //increase the progress bar size

	  //if successfully got the map, loads the Austin business XML file
	  if (hasMap) {
		  setSPA_Austin(austinXML);
		  austinSearch.increaseProgressBar(10);
		  setTitle("CityFinder (current task: finds gas-stations near the UT in Austin, Texas)");
		  oneClickAutomation();
	  }
	}
  }






  /** Add a store location on the Map as a red dot.
   *  @param x x-position
   *  @param y y-position
   *  @param rank rank of the store based on the user's preference
   *  @param id store id */
  private void addStoreToMap(int x, int y, int rank, int id){
	  mapCanv.addStore(x,y,rank,id);
	  
//	  spa.util.Debug.out(this, "addStoreToMap() - StoreInfo: x: " + x + " y: " + y + " rank: " + rank + " id: " + id, ""); //*** Debug
  }

  /** Displays the Houston map and questions for the Houston search. */
  private void displayHoustonSearch(){
	  remove(startPanel);
	  mapCanv.setSize(450,429);
	  add(mapCanv);
	  displayQuestion();
  }

  /** Displays the question panel on the right side of the window frame.
   *  To collect a user's answers. */
  private void displayQuestion(){
	  quePanel = new QuestionPanel(this,
								   ((GasDecisionProcess)decisionProcess).getNextPanelQuestion(),
								   QUE_NEXT_EVENT);
	  quePanel.setSize(242,430);
	  this.add(quePanel);
	  this.pack();
  }

  /** Displays the Houston city search results.  It displays the matched
   *  gas stations on the Houston map as red dots, the rank number next to the each dot,
   *  and displays the store's contact information on the TextArea of the right panel.
   **/
  private void displayHoustonResults(){
	  ShopElement[] finalShopListInfo= decisionProcess.getShopListInfo();
	  String storeInfo = "Total number of Stores: " + finalShopListInfo.length + "\n";
	  
	  for (int i=0;i<finalShopListInfo.length;i++){
		  storeInfo += "\n " + (i+1) + "). Store: \n=============================\n" +
						finalShopListInfo[i].toString() + "\n";
			 
		  addStoreToMap(Integer.parseInt(finalShopListInfo[i].x),
						Integer.parseInt(finalShopListInfo[i].y),
						i+1,
						Integer.parseInt(finalShopListInfo[i].id));
	  }

	  mapCanv.repaint();
	  toNextCity = new ToNextCityPanel(this);
	  toNextCity.setText(storeInfo);
	  this.remove(quePanel);
	  toNextCity.setSize(255,430);
	  this.add(toNextCity);
	  this.pack();

  }

  /** Displays the Austin city search results.  **/
  private void displayAustinResults(){

	  ShopElement[] bizInfoList= decisionProcess.getShopListInfo();
	  String storeInfo = "Total number of Stores: " + bizInfoList.length + "\n";

	  for (int i=0;i<bizInfoList.length;i++){
		  storeInfo += "\n " + (i+1) + "). Store: \n=============================\n" +
						bizInfoList[i].toString() + "\n";
		  addStoreToMap(Integer.parseInt(bizInfoList[i].x),
						Integer.parseInt(bizInfoList[i].y),
						i+1,
						Integer.parseInt(bizInfoList[i].id));
	  }

	  mapCanv.repaint();
	  austinSearch.appendText("\n\n================================\n" +
							  "Austin one-click Seach Result\n");
	  austinSearch.appendText(storeInfo);
	  austinSearch.displayFinalText();
  }

  /** Returns a string description */
  public String toString(){
	return "CityFinder (mobile navigation search engine) demo application. \n SPA: \n " +
		   decisionProcess.toString();
  }

  /** Start this CityFinder Demo application.  This application can be run as a regular application
   *  on a desktop computer or JavaWebStart application on web.  */
  public static void main(String args[]){
	CityFinderJWS cityFinder = new CityFinderJWS("http://chon.techliminal.com/cityfind/",
												   "houstonMap.gif",
												   "houstonXML.txt",
												   "austinMap.gif",
												   "austinXML.txt", 
												   false);//To run as a JavaWebStart application, sets the 'isJavaWebStart' value to be 'true'.
		
	cityFinder.setSize(740,510);
	cityFinder.show();
  }
}
