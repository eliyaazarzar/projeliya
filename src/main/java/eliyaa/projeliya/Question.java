package eliyaa.projeliya;

import java.util.List;

public class Question 
{
    // מספר שאלה
      private int num;
    // תוכן השאלה
    private String question;
    // רשימה של כל התשובות
    private List<Answer> answers;
    private Answer bestAnswer;
    // Constructor with appropriate data types
    public Question(int num, String question, List<Answer> answers,Answer bestAnswer) 
    {
        this.num = num;
        this.question = question;
        this.answers = answers;
        this.bestAnswer =bestAnswer;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getNum() {
        return num;
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
