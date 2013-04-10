package spa.question;

/**
 * Title:        CityFinder (SPA Demo Applet)
 * Description:  Question is used for the questioning-processes for collecting decisions.
 *               To collect decisions, we create a series of questions to ask a user 
 *               by implementing this Question Interface. 
 *
 *
 * Copyright:    Copyright (c) 2001-2002 Chon Chung.
 * @author Chon Chung
 * @version Demo 0.1
 * 
 * <p>          License: Lesser General Public License (LGPL)</p>
 */

public interface Question {
    /** Identification number. */
    public static final int ID = -1;
    /** Decision Factor for the question */
    public static final String DF = "Decision Factor";
    /** returns this Question's Identification number. */
    int getID();
    /** Based on the user response to the current question, executes actions. */
    void doAction();
    /** Based on the last saved user DIH, executes actions. */
    void doAutoAction();
    /** Set the next question. */
    void setNextQuestion();
}