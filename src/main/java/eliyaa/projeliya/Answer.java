package eliyaa.projeliya;

public class Answer 
{
    private String nameOfWriter;
    private int numQuastion;
    private String Answer;
    private long miliSecondsRunTime;
    private int errorsCode;    
    public Answer(String nameOfWriter, int numQuastion, String answer, long miliSecondsRunTime, int errorsCode) {
        this.nameOfWriter = nameOfWriter;
        this.numQuastion = numQuastion;
        Answer = answer;
        this.miliSecondsRunTime = miliSecondsRunTime;
        this.errorsCode = errorsCode;
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
    public long getMiliSecondsRunTime() {
        return miliSecondsRunTime;
    }
    public void setMiliSecondsRunTime(long miliSecondsRunTime) {
        this.miliSecondsRunTime = miliSecondsRunTime;
    }
    public int getErrorsCode() {
        return errorsCode;
    }
    public void setErrorsCode(int errorsCode) {
        this.errorsCode = errorsCode;
    }
    public String getNameOfWriter() {
        return nameOfWriter;
    }
    public void setNameOfWriter(String nameOfWriter) {
        this.nameOfWriter = nameOfWriter;
    }
    

    




}
