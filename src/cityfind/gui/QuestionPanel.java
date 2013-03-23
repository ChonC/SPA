package cityfind.gui;

import cityfind.CityFinderJWS;

import java.awt.*;

/**
 * <p>Description: Panel to display a question to collect a user decision choice. </p>
 * <p>Copyright: Copyright (c) 2001 - 2013 Chon Chung</p>
 * <p>Company: </p>
 * @author Chon Chung
 * @version Demo 0.7
 */

public class QuestionPanel extends Panel{

  BorderLayout borderLayout1 = new BorderLayout();
  private Button nextButton = new Button();
  Panel currentQuestion;
  /** The parent frame reference for notifying the received UI action event.  */
  CityFinderJWS parentFrame;

  /**
   * Constructor.
   *
   * @param parent                The main frame reference to pass the button event.
   * @param questionForDisplay    Question panel reference for display.
   * @param actionCommand         ActionCommand for the 'Next' button.
   */
  public QuestionPanel(CityFinderJWS parent,
                       Panel questionForDisplay,
                       String actionCommand) {
    this.parentFrame = parent;
          try {
              /** Set the GUI components. */
              this.setLayout(borderLayout1);
              currentQuestion = questionForDisplay;
              currentQuestion.setSize(242,300);
              this.add(currentQuestion, BorderLayout.CENTER);

              Panel nextButtonPan = new Panel();
              nextButtonPan.setLayout(null);


              nextButton.setBounds(new Rectangle(47, 22, 114, 31));
              nextButton.setFont(new java.awt.Font("Dialog", 1, 14));

              nextButton.setLabel("Next");
              nextButton.setActionCommand(actionCommand);
              //add the main-frame as the eventListenerObject, so the frame can receive this button event.
              nextButton.addActionListener(parent);
              nextButtonPan.add(nextButton);
              nextButtonPan.setSize(242,130);

              this.add(nextButtonPan, BorderLayout.SOUTH);


          }
          catch(Exception ex) {
              ex.printStackTrace();
          }
  }

  /** Removes the last question, and displays a new question on the panel. */
  public void setNextQuestion(Panel nextQuestion){
         this.remove(currentQuestion);
         currentQuestion = null;

         currentQuestion = nextQuestion;
         currentQuestion.setSize(242,300);
         this.add(currentQuestion, BorderLayout.CENTER);
         this.setVisible(true);
  }

  /**
   * Returns the currently display question reference.
   * @return    the currently display question reference
   */
  public Panel getQuestion(){
      return currentQuestion;
  }

  /**
   * Sets the Next button ActionCommand.
   * @param actionCommand         ActionCommand for the 'Next' button.
   */
  public void setNextButActionCommand(String actionCommand){
      nextButton.setActionCommand(actionCommand);
  }
}