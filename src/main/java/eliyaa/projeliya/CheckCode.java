package eliyaa.projeliya;
import java.lang.reflect.*;

import javassist.compiler.MemberResolver.Method;

public class CheckCode 
{
    private Method addCheckCode; // Corrected variable name
    private int num;
    private   int [][] codeValidation;
    private int [][] codeValidationAnswers;
    private Answer bestAnswer;
 

    
    public CheckCode(Method addCheckCode, int num, int[][] codeValidation, int[][] codeValidationAnswers,
            Answer bestAnswer)
             
            {
        this.addCheckCode = addCheckCode;
        this.num = num;
        this.codeValidation = codeValidation;
        this.codeValidationAnswers = codeValidationAnswers;
        this.bestAnswer = bestAnswer;
    }

    public CheckCode() 
    {
    }

    public Method getAddCheckCode() {
        return addCheckCode;
    }

    public void setAddCheckCode(Method addCheckCode) {
        this.addCheckCode = addCheckCode;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}

