package spa.dph;

/**
 * <p>Title: DPHwriter </p>
 * <p>Description: DPHwriter provides methods to write/record the DPH
 *                 (Decision Process History) as an XML format. </p>
 * 
 *
 * <p>Copyright: Copyright (c) 2001-2004 Chon Chung.</p>
 * @author Chon Chung
 * @version Demo 0.7
 * 
 * <p>          License: Lesser General Public License (LGPL)</p>
 */

public class DPHwriter {

  public static final String CLOSING_TAG = "\n</dph>";

  public DPHwriter() {}

  /** Returns the new DPH (Decision Process History) file as an XML format. */
  public static String newDPH(String id){
    return "<dph id=\"" + id + "\">" + CLOSING_TAG;
  }

 /** Adds the current user's answer to the DPH (Decision Process History) file
  *  as an XML format.  This saved DPH can be used for the one-click
  *  auto-processing.*/
  public static String addAnswer(String userDPH,
                                 String questionId,
                                 String answer){

    userDPH = userDPH.substring(0, (userDPH.length() - 7));//remove the CLOSING_TAG
    return userDPH + "\n<" + DPH_tag.QUESTION_TAG + " id=\"" + questionId + "\">" + 
           answer + "</" + DPH_tag.QUESTION_TAG + ">" +
           CLOSING_TAG;
  }

 /** Adds multiple answers to the DPH (Decision Process History) file.
  *  When the question has more than one answer, this method is used.*/
  public static String addAnswers(String userDPH,
                                  String questionId,
                                  String[] answers){

    userDPH = userDPH.substring(0, (userDPH.length() - 7));//remove the CLOSING_TAG
    userDPH += "\n<" + DPH_tag.QUESTION_TAG + " id=\"" + questionId + "\">\n";

    for (int i=0; i < answers.length; i++){
       userDPH += "\t<" + DPH_tag.ANSWER_TAG + ">" + answers[i] + "</" + DPH_tag.ANSWER_TAG + ">\n";
    }

    return userDPH + "</" + DPH_tag.QUESTION_TAG + ">" + CLOSING_TAG;
  }
}