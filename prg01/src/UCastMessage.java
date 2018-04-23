import java.io.*;

/**
 * CSCI320 - Networking & Distributed Computing
 * Spring 2018
 * Defines a unicast message containing a player's name, a question, and an answer.
 * @author Thyago Mota
 */
class UCastMessage extends Message {

    private String name;
    private String answer;

    public UCastMessage(String name, String question, String answer) {
        super();
        this.name     = name;
        this.question = question;
        this.answer   = answer;
    }

    public String getName() {
        return name;
    }

    public String getAnswer() {
        return answer;
    }

    @Override
    public String toString() {
        return "UCastMessage{" +
                "name='" + name + '\'' +
                ", question='" + question + '\'' +
                ", answer='" + answer + '\'' +
                '}';
    }
}
