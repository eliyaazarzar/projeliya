package projeliya.ver2.projeliya;
public class CheckCode 
{


    private String id;
    private   int [][] codeValidation;
    private int [][] codeValidationAnswers;
    private String bestRunTime;
 
    public CheckCode(int[][] codeValidation, int[][] codeValidationAnswers)
    {
        this.codeValidation = codeValidation;
        this.codeValidationAnswers = codeValidationAnswers;
    }

    public CheckCode() 
    {
    }


}

