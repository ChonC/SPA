SPA Personal Automation Algorithm 
==============================
Record your Smart Decision Processes, store them in a computing device, and automate them.  A computing device can do the work much faster for you.  

![Smart Personal-Process Automation - SPA](http://chon.techliminal.com/cityfind/images/3icons.png)

1. <strong>Record</strong>: XML or JSON are good tools to record processes in an Object oriented manner.  SPA provides tools to record the processes in a XML file. 
2. <strong>Process</strong>: Every Decision Process has two basic elements: Decision Factor and Decision Question.  By using a SPA algorithm, we can develop generic classes for the Decision Factor and Decision Question to collect a user decision answers. 
3. <strong>Automation</strong>:  SPA provides the framework and tool to easily build the SPA automation module.  By using it, we can create tools to collect the process information, record them, and automate the decision tasks in a computing device. 

<iframe width="853" height="480" src="http://www.youtube.com/embed/rzE1LVDXpsQ" frameborder="0" allowfullscreen></iframe>

SPA Personal Automation Algorithm
------------------------
SPA (Smart Personal-Process Automation) is the Fractal Object-Oriented Algorithm; it records the process and automates them.  It is based on the Decision Factor (DF), which is the basic building block that influences and defines the decision outcome.  For example, if we would like to locate the nearest gas station with the best price, there are 4 Decision Factors: type of gas, gas brand, distance, and price.
With each Decision Factor, we can create a Decision Question (DQ), which interactively asks a person for his/her decision process, step-by-step. By combining all of the DQ�s in a logical manner, we can develop an SPA that records the person's decision process for one-click automation.

Moreover, SPA is a Fractal Intelligence Algorithm. By <strong>combining multiple SPA modules</strong>, we can solve <strong>complex decision tasks</strong> such as: finding the best place for gas, lunch and shopping, and then drive a route that includes all of them.  Also, a Fractal Intelligence Algorithm requires a less memory and process power than other Intelligence Algorithms for a small computing device.    



<table width="780" border="0">
  <tr>
    <td width="297" align="center">
    <img src="http://chon.techliminal.com/cityfind/images/spa.png" width="251">
    </td>
    <td width="473" align="left">
    SPA <br/><br/>

    1. Divide a task into multiple decision processes based on the DF (Decision Factor). 
<br/><br/>
    2. In each process, interact with a Decision Maker to solve the process. 
<br/><br/>
    3. Record the solved decision processes for automation. 
    </td>
  </tr>
</table>



Programming Instructions
------------------------

Step I:
-------
Identify the Decision Factors of the task.  For example, if we want to develop a Movie Theater Finder module, our Decision Factors could be movie title, time, theater location distance, ticket price, and so on.

 
![Movie Threater Finder](http://chon.techliminal.com/cityfind/images/movie_df.png)

Step II:
--------
Use each Decision Factor to develop a Decision Question that asks a user his/her preferences of the Decision Factor.  Record the user Decision Answers and search the XML data to find all appropriate theaters that meet the user's decision. 

The <strong>Question</strong> interface in the SPA.question package provides the basic structure, and we can easily develop the Decision Question, by simply extending it. 



![question interface](http://chon.techliminal.com/cityfind/images/question.png)


Step III:
---------
After we developed the Decision Questions, we logically place them in the DecisionProcess class. The <strong>DecisionProcess</strong> class should be able to interact with a user to collect his/her decision processes by presenting the questions and collecting answers in a logical manner. By extending the super classes, we can develop the DecisionProcess classes.


![Decision Process](http://chon.techliminal.com/cityfind/images/DecisionProcess.png)
 

Step IV:
--------
To support the Movie-Theater-SAXparser's XML reading process, we need an XMLTag and Movie-Theater-Element classes. The XMLTag class provides the XML tag to read information from the movie XML data file. MovieTheaterElement class is a Data Object class that has the same properties as the movie theater properties of the XML data file, which is used to stored the Theaters' information in a local memory. We can develop the two classes by extending the super ShopElement and ShopXMLTag classes.

 
![elements](http://chon.techliminal.com/cityfind/images/element.png)

Step V:
-------
To support the Decision Process search, we need to develop SAX-XML package and its classes with the search methods that find all appropriate movie theaters from the XML data file. By extending the super SPA-SAX package, we can easily develop the Movie SAX package.


![spa.sax](http://chon.techliminal.com/cityfind/images/spa.sax.png)


Step VI:
--------
Finally, after we collect all the user's decision choices, we can record and store them by using the DPHwriter class in the super SPA package. Also, by utilizing the DPHreader, we can read the recorded decision choices for automation.

 
![Decision process History](http://chon.techliminal.com/cityfind/images/dph.png)




Vision: 
-------
<table width="847" border="0">
  <tr>
    <td width="345">
    
       <img src="http://chon.techliminal.com/cityfind/images/fractal_intelligence_algorithm.png" width="340" height="324" />
    </td>
    <td width="492">
SPA goal/vision is to create a true Fractal Process Automation Algorithm to automate all kinds of Processes. 

Fractal Intelligence Algorithm is designed to mimic a human being&#39;s subconscious mind.  For example, a human child learns to walk through practice,  and after he/she has learned to walk, the knowledge-processes are stored in the child's subconscious memory. So, the child can walk without consciously thinking about walking all the time.  Also, a subconscious intelligence is much faster and efficient because it does not require the 80% learning process.    

However, to solve such complex process automation, we need a single generic SPA module that is able to automatically adapt to different Decision tasks.  So, we can instantiate hundreds of SPA modules at runtime, without manual programming; by simply feeding task artifact (characteristic) files to a generic SPA module. As you can see, the sub-SPA task package classes are pretty simple, which makes it possible to create a true Fractal SPA module.  A single/simple fractal algorithm to handle complex tasks' automation. 

    
    </td>
  </tr>
</table>


<br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/>
Copyright &copy; 2001-2013 Chon Chung. License: Lesser General Public License (LGPL)

<br/>
I discovered/created SPA algorithm in year 2001.  I hope it to be used to benefit us: make fun stuff, and create more jobs.  So, I release it as an open source project. 





  


