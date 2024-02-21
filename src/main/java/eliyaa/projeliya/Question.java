package eliyaa.projeliya;

import java.lang.reflect.Array;
import java.util.List;
import org.springframework.data.mongodb.core.mapping.Document;

import com.vaadin.flow.component.template.Id;

@Document(collection = "questions")
public class Question 
 {   
    @Id
    private Long id;  
    // תוכן השאלה
    private String question;
    // רשימה של כל התשובות
    private List<Answer> answers;
    private Answer bestAnswer;
    // Constructor with appropriate data types
    
    public Question(Long id, String question, List<Answer> answers, Answer bestAnswer) {
        this.id = id;
        this.question = question;
        this.answers = answers;
        this.bestAnswer = bestAnswer;
    }

    public Question() {
    }

    public void setId(Long num) {
        this.id = num;
    }

    public Long getId() {
        return id;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
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
