package eliyaa.projeliya;

import java.util.Date;

public class Answer {
    private int AnswerNumber = 0;
    private String nameOfWriter;
    private int numQuastion;
    private String Answer;
    private Date answerDate;
    private double grade;

    public Answer(String nameOfWriter, int numQuastion, String answer, double grade, Date date, int counterOfTheList) {
        this.nameOfWriter = nameOfWriter;
        this.numQuastion = numQuastion;
        this.Answer = answer;
        this.grade = grade;
        this.answerDate = date;
        this.AnswerNumber = counterOfTheList;
    }

    public int getAnswerNumber() {
        return AnswerNumber;
    }

    public void setAnswerNumber(int answerNumber) {
        AnswerNumber = answerNumber;
    }

    public Date getAnswerDate() {
        return answerDate;
    }

    public void setAnswerDate(Date answerDate) {
        this.answerDate = answerDate;
    }

    public double getGrade() {
        return grade;
    }

    public void setGrade(double grade) {
        this.grade = grade;
    }

    public int getNumQuastion() {
        return numQuastion;
    }

    public void setNumQuastion(int numQuastion) {
        this.numQuastion = numQuastion;
    }

    public String getAnswer() {
        return Answer;
    }

    public void setAnswer(String answer) {
        Answer = answer;
    }

    public String getNameOfWriter() {
        return nameOfWriter;
    }

    public void setNameOfWriter(String nameOfWriter) {
        this.nameOfWriter = nameOfWriter;
    }
}
