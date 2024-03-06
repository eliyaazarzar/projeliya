package projeliya.ver2.projeliya;

import java.util.Date;

import org.springframework.data.mongodb.core.annotation.Collation;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.vaadin.flow.component.template.Id;
@Document(collection = "Soultions")
public class Solution 
{
    @Id
    private int id;
    private int SoultionNumber;
    private String nameOfWriter;
    private String nameOfQuestion;
    private String Answer;
    private Date answerDate;
    private Grade grade;
    private ErrorsReport report;
    private String errorsInCode;
    private Boolean compile;
    private double finalGrade;
    public Solution() {
    }  
    public Solution(int id,int answerNumber, String nameOfWriter, String nameOfQuestion, String answer, Date answerDate,
            Grade grade, ErrorsReport report, String errorsInCode, Boolean compile, double finalGrade) {
       
       
            this.id= id;
         SoultionNumber = answerNumber;
        this.nameOfWriter = nameOfWriter;
        this.nameOfQuestion = nameOfQuestion;
        Answer = answer;
        this.answerDate = answerDate;
        this.grade = grade;
        this.report = report;
        this.errorsInCode = errorsInCode;
        this.compile = compile;
        this.finalGrade = finalGrade;
    }
    public Solution(int answerNumber, String nameOfWriter, String nameOfQuestion, String answer, Date answerDate,
            Grade grade, ErrorsReport report, String errorsInCode, Boolean compile, double finalGrade) {
        SoultionNumber = answerNumber;
        this.nameOfWriter = nameOfWriter;
        this.nameOfQuestion = nameOfQuestion;
        Answer = answer;
        this.answerDate = answerDate;
        this.grade = grade;
        this.report = report;
        this.errorsInCode = errorsInCode;
        this.compile = compile;
        this.finalGrade = finalGrade;
    }
    public Solution(String Answer) 
    {
        this.Answer = Answer;
    }
    public int getId() 
    {
		return id;
	}
    
    public ErrorsReport getReport() {
		return report;
	}
	public void setReport(ErrorsReport report) {
		this.report = report;
	}
	public Solution(int answerNumber, String nameOfWriter, String answer,String numOfQuestion, Date answerDate, Grade grade, String errorsInCode, Boolean compile) 
{
    this.SoultionNumber = answerNumber;
    this.nameOfWriter = nameOfWriter;
    this.Answer = answer;
    this.nameOfQuestion = nameOfQuestion;
    this.answerDate = answerDate;
    this.grade = grade;
    this.errorsInCode = errorsInCode;
    this.compile = compile;
    this.finalGrade = grade.getScoreOfSteps();
}

public void setId(int id) {
		this.id = id;
	}
public double getFinalGrade() {
        return finalGrade;
    }
    public void setFinalGrade(double finalGrade) 
    {
        this.finalGrade = finalGrade;
    }
public Boolean getCompile() {
        return compile;
    }
    public void setCompile(Boolean compile) {
        this.compile = compile;
    }
public Solution(String nameOfWriter, String numQuastion, String answer, Grade grade, Date date, int counterOfTheList) {
    this.nameOfWriter = nameOfWriter;
    this.nameOfQuestion = numQuastion;
    this.Answer = answer;
    this.grade = grade;
    this.answerDate = date;
    this.SoultionNumber = counterOfTheList;
    this.compile =null;
}

    public Solution(String nameOfWriter, String numQuastion, String answer, Grade grade, Date date, int counterOfTheList,String errorsInCode) {
        this.nameOfWriter = nameOfWriter;
        this.nameOfQuestion = numQuastion;
        this.Answer = answer;
        this.grade = grade;
        this.answerDate = date;
        this.SoultionNumber = counterOfTheList;
        this.errorsInCode = errorsInCode;
        this.compile =null;
    }
    public String getErrorsInCode() {
        return errorsInCode;
    }
    public void setErrorsInCode(String errorsInCode) {
        this.errorsInCode = errorsInCode;
    }

    public int getSoultionNumber() {
        return SoultionNumber;
    }

    public void setSoultionNumber(int answerNumber) {
        SoultionNumber = answerNumber;
    }

    public Date getAnswerDate() {
        return answerDate;
    }

    public void setAnswerDate(Date answerDate) {
        this.answerDate = answerDate;
    }

    public Grade getGrade() {
        return grade;
    }

    public void setGrade(Grade grade) {
        this.grade = grade;
    }

    public String getNameOfQuestion() {
        return nameOfQuestion;
    }

    public void setNameOfQuestion(String numQuastion) {
        this.nameOfQuestion = numQuastion;
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
