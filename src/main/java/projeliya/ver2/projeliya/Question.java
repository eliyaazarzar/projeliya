package projeliya.ver2.projeliya;

import java.lang.reflect.Array;
import java.util.List;
import org.springframework.data.mongodb.core.mapping.Document;

import com.vaadin.flow.component.template.Id;

@Document(collection = "questions")
public class Question 
 {   
    @Id
    private String id;  
    // תוכן השאלה
    private String question;
    private int numOfQuestion;
    //
    String nameOfFunc;
    //התחלת השאלה
    private String openQuestion;
    public int getNumOfQuestion() {
        return numOfQuestion;
    }

    public void setNumOfQuestion(int numOfQuestion) {
        this.numOfQuestion = numOfQuestion;
    }

    // רשימה של כל התשובות
    private List<Solution> answers;
    private Solution bestAnswer;
    // Constructor with appropriate data types
    public Question(String id,String nameOfFunc, String question,int numOfQuestion,String openQuestion, List<Solution> answers, Solution bestAnswer) {
        this.id = id;
        this.nameOfFunc = nameOfFunc;
        this.openQuestion = openQuestion;
        this.numOfQuestion = numOfQuestion;
        this.question = question;
        this.answers = answers;
        this.bestAnswer = bestAnswer;
    }

    public String getNameOfFunc() {
        return nameOfFunc;
    }

    public Question(String id, String question,int numOfQuestion,String openQuestion, List<Solution> answers, Solution bestAnswer) {
        this.id = id;
        this.openQuestion = openQuestion;
        this.numOfQuestion = numOfQuestion;
        this.question = question;
        this.answers = answers;
        this.bestAnswer = bestAnswer;
    }

    public String getOpenQuestion() {
        return openQuestion;
    }

    public void setOpenQuestion(String openQuestion) {
        this.openQuestion = openQuestion;
    }

    public Solution getBestAnswer() {
        return bestAnswer;
    }

    public void setBestAnswer(Solution bestAnswer) {
        this.bestAnswer = bestAnswer;
    }

    public Question() {
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public List<Solution> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Solution> answers) {
        this.answers = answers;
    }

    // Getters and setters...
    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }    
}
