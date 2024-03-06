package projeliya.ver2.projeliya;

public class Grade {
    private double scoreOfTime;
    private double scoreOfSteps;
    private double scoreOfMemory;
    private double scoreOfChatGpt;
    private double scoreValidtion;
    private double grade;
    public Grade(double scoreOfTime, double scoreOfSteps, double scoreOfMemory, double scoreOfChatGpt,
            double scoreValidtion, double grade) {
        this.scoreOfTime = scoreOfTime;
        this.scoreOfSteps = scoreOfSteps;
        this.scoreOfMemory = scoreOfMemory;
        this.scoreOfChatGpt = scoreOfChatGpt;
        this.scoreValidtion = scoreValidtion;
        this.grade = grade;
    }
    public double getScoreOfTime() {
        return scoreOfTime;
    }
    public void setScoreOfTime(double scoreOfTime) {
        this.scoreOfTime = scoreOfTime;
    }
    public double getScoreOfSteps() {
        return scoreOfSteps;
    }
    public void setScoreOfSteps(double scoreOfSteps) {
        this.scoreOfSteps = scoreOfSteps;
    }
    public double getScoreOfMemory() {
        return scoreOfMemory;
    }
    public void setScoreOfMemory(double scoreOfMemory) {
        this.scoreOfMemory = scoreOfMemory;
    }
    public double getScoreOfChatGpt() {
        return scoreOfChatGpt;
    }
    public void setScoreOfChatGpt(double scoreOfChatGpt) {
        this.scoreOfChatGpt = scoreOfChatGpt;
    }
    public double getScoreValidtion() {
        return scoreValidtion;
    }
    public void setScoreValidtion(double scoreValidtion) {
        this.scoreValidtion = scoreValidtion;
    }
    public double getGrade() {
        return grade;
    }
    public void setGrade(double grade) {
        this.grade = grade;
    }
    


    
}
