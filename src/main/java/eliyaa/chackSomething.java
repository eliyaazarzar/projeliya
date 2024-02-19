package eliyaa;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.springframework.scheduling.config.Task;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.stmt.ForStmt;
import com.github.javaparser.ast.stmt.WhileStmt;

public class chackSomething {

     public static void main(String[] args) {
        // Create a new thread for the long-running action
        Thread longRunningThread = new Thread(() -> {
            for (int i = 0; i < 1000000; i++) {
                // Simulate the long-running action
                System.out.println("im doing my task " + i);
                // Check for interruption and exit if interrupted
                if (Thread.currentThread().isInterrupted()) {
                    System.out.println("Long-running action interrupted.");
                    return;
                }
            }
        });

        // Start the long-running thread
        longRunningThread.start();

        try {
            // Sleep for 15 seconds to simulate the long-running action
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // After 15 seconds, if the long-running action hasn't finished, interrupt the thread
        if (longRunningThread.isAlive()) {
            System.out.println("Long-running action taking too long, interrupting...");
            longRunningThread.interrupt();
        }

        // Continue with other things
        System.out.println("Continue with other things");
    }
    public static String processCode(String inputCode) 
    {

        int start = inputCode.indexOf("{");
        int end = 0, end2 = -1;
        String codeForParsing = "public class RunPitaron {" + inputCode + "}";

        CompilationUnit cu = StaticJavaParser.parse((codeForParsing));
        String arrayName = "arr";
        int forLoopCount = cu.findAll(ForStmt.class).size();
        int whileLoopCount = cu.findAll(WhileStmt.class).size();
        int sizeOfLoops = forLoopCount + whileLoopCount;
        String classTitle = "public class RunPitaron {\n    public static long[] " + arrayName + " = new long["
                + sizeOfLoops + "];\n";
        String codeForRun = inputCode;

        System.out.println(sizeOfLoops + " size:");
       codeForRun = codeForRun.substring(18);
        StringBuilder stringBuilder = new StringBuilder("public static long[]"+codeForRun);
        int openCodeIndex;

        int forIndex = stringBuilder.indexOf("for");
        int index = 0;
        System.out.println("here "+stringBuilder.indexOf("int"));



        while (forIndex != -1) {
            int openBracketIndex = stringBuilder.indexOf("{", forIndex);
            stringBuilder.insert(openBracketIndex + 1, "\n        " + arrayName + "[" + index + "]++;\n");
            index++;
            forIndex = stringBuilder.indexOf("for", openBracketIndex);
        }
        forIndex = stringBuilder.indexOf("while");
        while (forIndex != -1) {
            int openBracketIndex = stringBuilder.indexOf("{", forIndex);
            stringBuilder.insert(openBracketIndex + 1, "\n        " + arrayName + "[" + index + "]++;\n");
            forIndex = stringBuilder.indexOf("while", openBracketIndex);
            index++;
        }
        openCodeIndex = stringBuilder.indexOf("{");
        stringBuilder.insert(openCodeIndex + 1,
                " for (int i = 0; i < " + sizeOfLoops + "; i++) {" + arrayName + "[i] = 0; }");

        return (classTitle + stringBuilder + "}");
    }
    
    public static long sortReturnSteps(int size) {
        long [] arrForFunc = new long[2];
    
        for (int i = 0; i < arrForFunc.length; i++) {
            arrForFunc[i] = 0;
        }
    
        int[] arr = new int[size];
        for (int i = 0; i < size; i++) {
            arr[i] = size - 1;
        }
    
        int n = arr.length;
    
        for (int i = 0; i < n - 1; i++) {
            arrForFunc[0]++;
            for (int j = 0; j < n - i - 1; j++) {
                arrForFunc[1]++;
                if (arr[j] > arr[j + 1]) {
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }
            // Output the steps for debugging
        for (int i = 0; i < arrForFunc.length; i++) {
          //  System.out.println(arrForFunc[i] + " steps!" + " for n = " + arr.length);
        }
            return findMaxNumber(arrForFunc);
    }
    
  
    public static int[] sort(int[] arr) 
    {
        int n = arr.length;
        for (int i = 0; i < n - 1; i++) 
        {
            for (int j = 0; j < n - i - 1; j++) 
            {
                if (arr[j] > arr[j + 1]) {
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }
        return arr;
    }
    public static long findMaxNumber(long[] array) 
    {
        // במקרה של מערך ריק, נחזיר ערך שלילי או ערך ראשוני כלשהו
        if (array == null) {
            return -1;
        }

        if (array.length == 0) {
            return -1;
        }

        long maxNumber = array[0]; // נניח שהמספר הכי גדול הוא המספר הראשון במערך

        for (int i = 1; i < array.length; i++) {
            if (array[i] > maxNumber) {
                maxNumber = array[i];
            }
        }

        return maxNumber;
    }





}


