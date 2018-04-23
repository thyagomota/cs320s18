import java.io.Serializable;

/**
 * CSCI320 - Networking & Distributed Computing
 * Spring 2018
 * Defines a message containing a question.
 * @author Thyago Mota
 */
abstract class Message implements Serializable {

    protected String question;

    Message() {
        this.question = "";
    }

    String getQuestion() {
        return question;
    }

    synchronized void setQuestion(String question) {
        this.question = question;
    }
}
