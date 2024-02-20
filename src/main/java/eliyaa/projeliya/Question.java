package eliyaa.projeliya;

import java.util.List;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "questions")
public class Question 
{   

    private Long id;  
    // תוכן השאלה
    private String question;
    // רשימה של כל התשובות
    private List<Answer> answers;
    private Answer bestAnswer;

    // Constructor with appropriate data types
    public Question(Long num, String question, List<Answer> answers, Answer bestAnswer) {
        this.id = num;
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
