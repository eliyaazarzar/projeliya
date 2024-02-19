package eliyaa.projeliya;

public class Answer 
{
    private String nameOfWriter;
    private int numQuastion;
    private String Answer;
    private double grade;    
    /**
     * 
     * @param nameOfWriter name of writer
     * @param numQuastion number of questions
     * @param answer answer of the question
     * @param miliSecondsRunTime number of seconds
     * @param errorsCode number of errors
     */
    public Answer(String nameOfWriter, int numQuastion, String answer, double grade) {
        this.nameOfWriter = nameOfWriter;
        this.numQuastion = numQuastion;
        Answer = answer;
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
