package eliyaa.projeliya;

import java.sql.Date;

public class Answer 
{
   private int AnswerNumber =0 ;
    private String nameOfWriter;
    private int numQuastion;
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
    private String Answer;
    private Date answerDate;
    private double grade;    
    /**
     * 
     * @param nameOfWriter name of writer
     * @param numQuastion number of questions
     * @param answer answer of the question
     * @param miliSecondsRunTime number of seconds
     * @param errorsCode number of errors
     */
    public Answer(String nameOfWriter, int numQuastion, String answer, double grade,Date date, int counterOfTheList) 
    {
        this.nameOfWriter = nameOfWriter;
        this.numQuastion = numQuastion;
        Answer = answer;
        this.grade = grade;
        this.answerDate = date;
        this.AnswerNumber = counterOfTheList;
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
