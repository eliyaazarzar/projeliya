package projeliya.ver2.projeliya;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.tools.Diagnostic;
import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import org.json.JSONArray;
import org.json.JSONObject;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.server.VaadinSession;
import org.springframework.stereotype.Service;

@Service
public class ServiceLogic {
    private Point[] arrayOfPoints;
    private long[] arrayOfSlope;
    private long[] arrayOfDistance;
    private static Thread previousThread = null;
    private int testPassed = 0;
    private int numOfIterations = 50000;
    private int numOfItrationsSort = 50;
    private String codeForFronted;
    private int index = 0;
    private int sizeOfArrFunc = 0;
    private int incAndDec = 0;
    private long efectiveMemory = 49249784;
    private int countParmters = 0;
    private static int[] arrForFunc;
    public static double successRate = 0;
    private double timeWeighted;
    private double memoryWeighted;
    private double successRateWeighted;
    private double stepsScoreWeighted;
    private double chatGptScoreWeighted;
    private String codeFronted;
    private Boolean compile;
    private int timeScore = 0;
    private int memoryScore = 0;
    public StringBuilder compilationErrors;
    private static double clientTime = 0.0;
    private double theEffectiveTime = 10;
    public static Method method = null;
    private static int stepsScore = 0;
    private static long stepsForClient = 0;
    private static long efectiveFuncSteps = 0;
    private static final Object lock = new Object(); // יצירת אובייקט לנעילה
    private static int timer;
    private long averegeStepForClient = 0;
    private long averegeStepForEfective = 4949550;
    private String classTitle = "public class RunPitaron {";
    private String cleanedMethod2 = "";
    private String className = "RunPitaron";
    private String fileName = className + ".java";
    private String readyCode = "";
    private int timeForSteps = 1000000;
    private int timeForTimeAndMemory = 100000;
    private String stringRunTime;
    private String resultStringGpt;
    private QuestionService questionService;
    private HomeRepository Repo;
    private Button addMySoultion;
    private boolean result;
    private ExaminationService examinationService;
    private ErrorsReport errorsReport;
    private Examination examinationQuestion;

    public ServiceLogic(HomeRepository repo, QuestionService questionService, ExaminationService examinationService) {
        this.questionService = questionService;
        this.Repo = repo;
        this.examinationService = examinationService;
    }

    public ErrorsReport getErrorsReport() {
        return errorsReport;
    }

    public double checkAnswer(String methodcode2, String NameOfFunc, String questionName)
            throws IOException, ClassNotFoundException, NoSuchMethodException,
            SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException,
            NoSuchFieldException, InterruptedException {
        errorsReport = new ErrorsReport();
        compilationErrors = new StringBuilder();
        cleanedMethod2 = cleanCode(methodcode2);
        System.out.println(cleanedMethod2 + "the code clean");
        // File creation
        Path filePath = Paths.get("src/eliyaa/projecteliya/RunPitaron.jaa");
        // Create directories if they don't exist
        Files.createDirectories(filePath.getParent());
        // Use Files.newBufferedWriter to write to the file
        // Create source code file
        // System.out.println(fileName + readyCode);
        Thread t2 = biuldFileThread(fileName, processCode(cleanedMethod2));
        System.out.println("----------------------");
        t2.start();
        t2.join();
        System.out.println("----------------------"); // Compile the source file
        int compileResult = compileAndRunJavaFile(className, cleanedMethod2);
        System.out.println("Compilation result: " + compileResult);
        if (compileResult == 0) {
            System.out.println("the compile successfull");
            compile = true;
            Files.createDirectories(filePath.getParent());
            URLClassLoader classLoader = new URLClassLoader(new URL[] { new File("./").toURI().toURL() });
            Class<?> dynamicClass = Class.forName(className, true, classLoader);

            // Get the method and invoke it for each input
            examinationQuestion = examinationService.getById(questionName);
            int[][] arrForChack = examinationQuestion.getArr();
            if (questionName.equals("SortArray")) {
                method = dynamicClass.getMethod("SelectionSort", int[].class);

                ChackFuncOfClientSort(method, arrForChack);

            } else if (questionName.equals("removeDuplicates")) 
            {
                method = dynamicClass.getMethod("removeDuplicates", int[].class);
                FuncOfClientRemoveDuplicate(method, arrForChack);
            }

            if (testPassed < 5) {
                System.out.println("im here!");
                successRateWeighted = 0.0;
                chatGptScoreWeighted = 0.0;
                stepsScoreWeighted = 0.0;
                memoryWeighted = 0.0;
                timeWeighted = 0.0;
                System.out.println(timeWeighted);
                System.out.println(memoryWeighted);
                System.out.println(chatGptScoreWeighted);
                System.out.println(stepsScoreWeighted);
                System.out.println(successRateWeighted);
                double combinedScore = 2.0;
                return combinedScore;
            }
            Thread manyStepsThread = getGradeForSteps();
            System.out.println("Thared runTimeSteps Start!");
            manyStepsThread.start();
            manyStepsThread.join();
            System.out.println("Thared runTimeSteps end!");
            // Thread runManyThread = buildThreadForRunMany();
            // Runtime runtime = Runtime.getRuntime();
            // runtime.gc();
            // System.out.println("Thared runTime Start!");
            // boolean resultForTimeScore = countTime(runManyThread, timeForTimeAndMemory);
            // long memoryClient = runtime.totalMemory() - runtime.freeMemory();
            // System.out.println("------------------ get res Ended! ------------------");
            // if (resultForTimeScore)
            // {
            // System.out.println("Thread finished before the maximum time.");
            // } else {
            // System.out.println("Thread did not finish before the maximum time and was
            // interrupted.");
            // }
            // System.out.println("Time taken by the Client method: " + memoryClient + "
            // bytes");
            // System.out.println("Memory used by the my method: " + efectiveMemory + "
            // bytes");
            // System.out.println("testPassed = " + testPassed);
            // codeFronted = readyCode;
            // System.out.println("time score is: " + resultForTimeScore);

            timeScore = 0;
            memoryScore = 0;
            long timeDifference = 0;
            double memoryDifference = 0;
            // if (resultForTimeScore == true) {
            // timeScore = 100;
            // } else {
            // timeScore = 0;
            // }
            // if (efectiveMemory >= memoryClient || efectiveMemory >= memoryClient - 200 &&
            // resultForTimeScore == true) {
            // memoryScore = 100;
            // } else {
            // memoryScore = 0;
            // }
            String runTimeEfective = examinationQuestion.getRunTime();
            String AveregRunTime = examinationQuestion.getRunAvergeTime();
            System.out.println(result);
            System.out.println("effective RunTime = " + runTimeEfective);
            System.out.println("client RunTime =" + stringRunTime);
            System.out.println("success RunTime = " + successRate +"runTimeAverge = " + stringRunTime);
            if (runTimeEfective.equals(stringRunTime)) {
                System.out.println("the run time is size = " + stringRunTime);
                stepsScore = 100;
            } else if(stringRunTime.equals(AveregRunTime)&&successRate == 100)
             {
                stepsScore = 50;
            }else{
                stepsScore = 0;
            }
            // Get a score from chatGPT
            int chatGptScore = 0;
            try {
                System.out.println("im start the chatGPT Thread");
                // Thread t = chatGptFuncThread(cleanedMethod2);
                // t.start();
                // t.join();
                System.out.println("end chatGpt Thread");

                System.out.println("---" + resultStringGpt + "---");

                // int find = resultStringGpt.indexOf(":");
                // if (find != -1) {
                // System.out.println("find");
                // resultStringGpt = resultStringGpt.substring(find + 1);
                // }
                // chatGptScore = Integer.parseInt(resultStringGpt);
            } catch (Exception e) {
                e.printStackTrace(); // This will print the exception details to the console
                resultStringGpt = "Error occurred";
                chatGptScore = 0;
            }

            System.out.println(timeScore + "timeScore");
            System.out.println("timeWeighted: " + timeWeighted);
            if (successRate > 100) {
                successRate = 100;
            }
            successRateWeighted = successRate;
            stepsScoreWeighted = stepsScore;
            chatGptScoreWeighted = chatGptScore;
            memoryWeighted = memoryScore;
            System.out.println("----------------------------");
            System.out.println("timeScore after multiplication by 0.1: " + timeWeighted);
            System.out.println("memoryScore after multiplication by 0.2: " + memoryWeighted);
            System.out.println("successRate after multiplication by 0.3: " + successRateWeighted);
            System.out.println("stepsScore after multiplication by 0.4: " + stepsScoreWeighted);
            System.out.println("chatGptScore after multiplication by 0.1: " + chatGptScoreWeighted);
            System.out.println("----------------------------");

            double combinedScore = 0.0;
            combinedScore = ((timeScore * 0.1) + (memoryScore * 0.1) + (successRate * 0.3) + (stepsScore * 0.4)
                    + (chatGptScore * 0.1)) / 10;
            System.out.println("testPassed: " + testPassed);

            // Display the grade
            String username = (String) VaadinSession.getCurrent().getAttribute("user");

            System.out.println("The grade of your method is: " + String.format("%.1f", combinedScore));
            if (combinedScore > 0) {
                return combinedScore;
            } else {
                return 0.0;
            }

        } else {
            compile = false;
            System.out.println("Compilation failed. Cannot execute the method.");
            System.out.println("the grade is:1");
            double returnErrorFalse = 0.0;
            return returnErrorFalse;
        }
    }

    public void runTestsAndCalculateSuccessRate(Method method, int[][] inputsArray) throws IllegalAccessException,
            IllegalArgumentException, InvocationTargetException {
        successRate = 0;
        ArrayList<String> listErrors = new ArrayList<>();
        System.out.println("---------------------------------------------");

        System.out.println("input array1:" + Arrays.toString(inputsArray[0]));

        int[][] expectedOutputsArray = new int[inputsArray.length][];

        for (int i = 0; i < inputsArray.length; i++) {
            int[] inputCopy = Arrays.copyOf(inputsArray[i], inputsArray[i].length);
            Arrays.sort(inputCopy);
            expectedOutputsArray[i] = inputCopy;
        }

        int counter = inputsArray.length;
        int totalTests = inputsArray.length;
        int errorCount = 0;

        for (int i = 0; i < totalTests; i++) {
            int[] resultArray = returnResultOfFuncClient(method, inputsArray[i]);
            System.out.println("Test Case " + (i + 1) + ":");
            // System.out.println("Input Array: " + Arrays.toString(inputsArray[i]));
            // System.out.println("Expected Output Array: " +
            // Arrays.toString(expectedOutputsArray[i]));
            // System.out.println("Actual Output Array: " + Arrays.toString(resultArray));
            if (!Arrays.equals(resultArray, expectedOutputsArray[i])) {
                errorCount--;
                listErrors.add(examinationQuestion.getExplainTheArrays().get(i));
                System.out.println("Error: Output mismatch!");
                // System.out.println("Actual Output Array: " + Arrays.toString(resultArray));
                // System.out.println("Expected Output Array: " +
                // Arrays.toString(expectedOutputsArray[i]));
            }
        }

        System.out.println("Total errors: " + errorCount + " out of " + totalTests);
        successRate = 100.0 - (totalTests * errorCount);
        System.out.println("Success rate: " + successRate + "%");
        errorsReport.setErrorsArraysExplain(listErrors);
        

        testPassed = totalTests + errorCount;
    }

    public void runTestsAndCalculateSuccessDuclicates(Method method, int[][] inputsArray) throws IllegalAccessException,
            IllegalArgumentException, InvocationTargetException {
        successRate = 0;
        ArrayList<String> listErrors = new ArrayList<>();
        System.out.println("---------------------------------------------");

        System.out.println("input array11:" + Arrays.toString(inputsArray[0]));

        int[][] expectedOutputsArray = new int[inputsArray.length][];

        for (int i = 0; i < inputsArray.length; i++) {
            int[] inputCopy = Arrays.copyOf(inputsArray[i], inputsArray[i].length);
            int[] arrayAfter = removeDuplicates(inputCopy);
            expectedOutputsArray[i] = arrayAfter;
        }

        int counter = inputsArray.length;
        int totalTests = inputsArray.length;
        int errorCount = 0;

        for (int i = 0; i < totalTests; i++) {
            int[] resultArray = returnResultOfFuncClient(method, inputsArray[i]);
            System.out.println("Test Case " + (i + 1) + ":");
            // System.out.println("Input Array: " + Arrays.toString(inputsArray[i]));
            // System.out.println("Expected Output Array: " +
            // Arrays.toString(expectedOutputsArray[i]));
            // System.out.println("Actual Output Array: " + Arrays.toString(resultArray));
            if (!Arrays.equals(resultArray, expectedOutputsArray[i])) {
                errorCount--;
                listErrors.add(examinationQuestion.getExplainTheArrays().get(i));
                System.out.println("Error: Output mismatch!");
                // System.out.println("Actual Output Array: " + Arrays.toString(resultArray));
                // System.out.println("Expected Output Array: " +
                // Arrays.toString(expectedOutputsArray[i]));
            }
        }

        System.out.println("Total errors: " + errorCount + " out of " + totalTests);
        double successRate2 = 100.0 * (totalTests + errorCount) / totalTests;
        System.out.println("Success rate: " + successRate2 + "%");
        errorsReport.setErrorsArraysExplain(listErrors);
        if (testPassed == totalTests) {
            successRate = 100;
        } else {
            int errors = totalTests + errorCount;
            successRate = 100 + (errors * 5);
        }

        testPassed = totalTests + errorCount;
    }

    public static int[] removeDuplicates(int[] array) {
        int count = 0;
        int[] uniqueArray = new int[array.length];

        for (int i = 0; i < array.length; i++) {
            boolean isDuplicate = false;
            for (int j = 0; j < count; j++) {
                if (array[i] == uniqueArray[j]) {
                    isDuplicate = true;
                    break;
                }
            }
            if (!isDuplicate) {
                uniqueArray[count] = array[i];
                count++;
            }
        }
        int[] finalArray = new int[count];
        for (int i = 0; i < count; i++) {
            finalArray[i] = uniqueArray[i];
        }

        return finalArray;
    }
    public void calculateAndPrintSteps(Method methodClient, int numOfIterationsSorted) throws IllegalAccessException,
            IllegalArgumentException, InvocationTargetException, NoSuchFieldException, SecurityException {
        int stepsSize = 10;
        long arrStepsFunc = 0, clientFuncSteps;
        int index = 0;
        Point[] points = new Point[6];
        long[] arrSlopes = new long[11];
        averegeStepForClient = 0;
        for (int v = stepsSize; v <= numOfItrationsSort; v += stepsSize) {
            clientFuncSteps = returnStepsForClient(methodClient, v);
            System.out.println("n=" + v + "," + clientFuncSteps);
            arrStepsFunc = returnStepsForClient(methodClient, (v + stepsSize));
            System.out.println("n=" + (v + stepsSize) + "," + arrStepsFunc);
            points[index] = new Point(clientFuncSteps, v);
            points[index + 1] = new Point(arrStepsFunc, (v + stepsSize));
            arrSlopes[index] = ((arrStepsFunc - clientFuncSteps) / ((v + stepsSize) - v));
            averegeStepForClient = arrSlopes[index] + averegeStepForClient;
            System.out.println("arr slpoe" + arrSlopes[index]);
            index++;
        }
        for (int i = 0; i < points.length; i++) {
            System.out.println("--------------------------------");
            if (points[i] != null) {

                System.out.println(points[i].getX() + points[i].getY());
            }
            System.out.println("--------------------------------");
        }
        errorsReport.setArrayOfPoints(points);
        System.out.println("size of Points arr= " + points.length);
        errorsReport.setArrayOfSlope(arrSlopes);
        System.out.println("size of arrSlopes= " + arrSlopes.length);

        int counterOne = 0, counterN = 0, counterMarici = 0;
        long sum = arrSlopes[1] - arrSlopes[0];
        long sum2 = arrSlopes[1] - arrSlopes[0];
        // System.out.println("sum2 = " + sum2 + "sum = " + sum);
        long[] arrayRealDifference = new long[5];
        System.out.println(arrSlopes.length);
        System.out.println("start distance calculation");
        for (int i = 0; i < arrSlopes.length - 6; i++) 
        {
            System.out.println("in here in the slpeols distances");
            long sumOfDistance = arrSlopes[i + 1] - arrSlopes[i];
            if (sumOfDistance >= 0) 
            {
                arrayRealDifference[i] = arrSlopes[i + 1] - arrSlopes[i];
                System.out.println("distance is: " + arrayRealDifference[i]);
            }
        
        }
        System.out.println("end distance calculation");
        System.out.println("size of arrSlopes= " + arrayRealDifference.length);

        errorsReport.setArrayOfDistance(arrayRealDifference);

        System.out.println("---go the the end part---");
        System.out.println("reslt"+areAllElementsEqual(arrSlopes));
        if (isAllZeros(arrSlopes) && isAllZeros(arrayRealDifference)) {
            stringRunTime = "O(1)";
            System.out.println("This is O(1)");
        } else if (areAllElementsEqual(arrSlopes)) 
        {
            stringRunTime = "O(N)";
            errorsReport.setRunTimeOfFunc(stringRunTime);

            System.out.println("This is O(N)");
            return;

        } else {
            if (((arrayRealDifference[1] - arrayRealDifference[0])) == 0) {
                stringRunTime = "N^2";
                errorsReport.setRunTimeOfFunc(stringRunTime);
                System.out.println("This is O(N^2)");
                result = true;
                return;

            } else {
                result = false;
                long chackN3 = arrayRealDifference[1] - arrayRealDifference[0];
                for (int i = 10; i < 10000; i = i + 10) {
                    if (chackN3 == ((stepsSize * 30))) {

                        stringRunTime = "N^3";
                        errorsReport.setRunTimeOfFunc(stringRunTime);

                        System.out.println("This is O^3");
                        return;
                    }
                    chackN3 = chackN3 / 10;
                    System.out.println(chackN3 + " =?" + (stepsSize * 30));
                }
            }
            if ((arrayRealDifference[1] - arrayRealDifference[0]) == ((stepsSize * 40))) {
                stringRunTime = "O^4";
                errorsReport.setRunTimeOfFunc(stringRunTime);

                System.out.println("This is O^4");
                return;

            }

        }

    }

    public Point[] getArrayOfPoints() {
        return arrayOfPoints;
    }

    public void setArrayOfPoints(Point[] arrayOfPoints) {
        this.arrayOfPoints = arrayOfPoints;
    }

    public long[] getArrayOfSlope() {
        return arrayOfSlope;
    }

    public void setArrayOfSlope(long[] arrayOfSlope) {
        this.arrayOfSlope = arrayOfSlope;
    }

    public long[] getArrayOfDistance() {
        return arrayOfDistance;
    }

    public void setArrayOfDistance(long[] arrayOfDistance) {
        this.arrayOfDistance = arrayOfDistance;
    }

    public boolean isAllZeros(long[] arr) {
        for (long num : arr) {
            if (num != 0) {
                return false;
            }
        }
        return true;
    }

    public boolean areAllElementsEqual(long[] arr) {
        if (arr.length <= 1) {
            return true;
        }
        long firstElement = arr[0];
        for (int i = 1; i < arr.length - 6; i++) {
            System.out.println("(+"+ i+"):\t"+arr[i]);
            if (arr[i] != firstElement) 
            {
                System.out.println("arr=" + arr[i]);
                return false;
            }
        }
        return true;
    }
    
    public Thread getGradeForSteps()
     {

        Thread thread10 = new Thread(new Runnable() {
            @Override

            public void run() {

                try {
                    synchronized (lock) {

                        System.out.println("Thread10 start");
                        calculateAndPrintSteps(method, numOfItrationsSort);
                        System.out.println("Thread10 end!");
                    }
                } catch (Exception e) {
                    Thread.currentThread().interrupt();
                    System.out.println("overTime Steps Bad");
                    e.printStackTrace();
                }
            }
        });

        return thread10;
    }

    public Thread buildThreadForRunMany() {
        int[] randomArray = new int[numOfIterations];

        // Populate the randomArray
        for (int j = 0; j < numOfIterations; j++) {
            randomArray[j] = numOfIterations - j;
        }

        // Create and return a new Thread
        return new Thread(() -> {
            try {
                System.out.println("Thread start run Multiple");
                returnMethodMultipleTimes(method, randomArray);
                System.out.println("finished");
            } catch (Exception e) {
                // Handle exceptions gracefully\
                System.out.println("its end");
                System.err.println("An error occurred during method execution: " + e.getMessage());
                Thread.currentThread().interrupt();

            }
            System.out.println("Thread end");
        });
    }

    public Thread chatGptFuncThread(String cleanedMethod2) throws InterruptedException {
        // Create a new ExecutorService
        // Define the task
        Runnable task = () -> {
            synchronized (lock) {
                resultStringGpt = chatGPT(truncateMessage(cleanCode(
                        "Give me a score from 10 to 100 (when you respond The goal of the code I'm sending you should be a sorted array, if it doesn't do that, deduct it from the score according to your opinion, provide only the score without explanations or words, just the number). Rate this function based on efficiency, code integrity, and memory usage. "
                                + cleanedMethod2),
                        4096));
            }
        };

        // Submit the task to the executor
        Thread thread = new Thread(task);
        return thread;
    }

   
    // public static Boolean countTime(Thread longRunningThread, int seconds) {

    // longRunningThread.start();

    // try {
    // // Sleep for 15 seconds to simulate the long-running action
    // Thread.sleep((seconds*1000));
    // } catch (InterruptedException e) {
    // e.printStackTrace();
    // }
    // // thread
    // if (longRunningThread.isAlive())
    // {
    // System.out.println("Long-running action taking too long, interrupting...");
    // longRunningThread.interrupt();

    // Thread.currentThread().interrupt();
    // return false;
    // }

    // return true;
    // }

    private static void clearFile(String fileName) throws IOException {
        // פתח את הקובץ לכתיבה ומחק את תוכנו
        PrintWriter writer = new PrintWriter(fileName);
        writer.print("");
        writer.close();
    }

    public Thread biuldFileThread(String fileName, String stringMethodBeforeProcess) {
        Thread thread3 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    synchronized (lock) {

                        System.out.println(" ----------- Thread build the func start -----------------");
                        biuldFile(fileName, stringMethodBeforeProcess);
                        System.out.println("Thread build end!");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        return thread3;
    }

    public Boolean runNumOfChackArrForRemoveDuplicates(String num, String methodcode2,String question)
            throws IOException, ClassNotFoundException, NoSuchMethodException, SecurityException,
            IllegalAccessException, IllegalArgumentException, InvocationTargetException, InterruptedException {
        String classTitle = "public class RunPitaron {";
        // main(String[] args) { ";

        Question questionForCheack = questionService.findById(question);
        String cleanedMethod2 = cleanCode(methodcode2);
        System.out.println(cleanedMethod2 + "the code clean");
        // File creation
        Path filePath = Paths.get("src/eliyaa/projecteliya/RunPitaron.java");
        // Create directories if they don't exist
        Files.createDirectories(filePath.getParent());
        // Use Files.newBufferedWriter to write to the file
        // Create source code file
        String className = "RunPitaron";
        String fileName = className + ".java";
        biuldFile(fileName, classTitle + cleanedMethod2+"}");
        int compile2 = compileAndRunJavaFile(className, cleanedMethod2);
        System.out.println(compile2);
        if (compile2 == 0) {
            System.out.println("im here");
            compile = true;
            System.out.println(cleanedMethod2 + "the code clean");
            System.out.println("the compile successfull");
            Files.createDirectories(filePath.getParent());
            URLClassLoader classLoader = new URLClassLoader(new URL[] { new File("./").toURI().toURL() });
            Class<?> dynamicClass = Class.forName(className, true, classLoader);
            method = dynamicClass.getMethod(questionForCheack.getNameOfFunc(), int[].class);
            int numForArr = Integer.parseInt(num);
            int[] arr =new int[numForArr];
            arr[0]=1;
            for(int i=1;i<arr.length;i++)
        {
            arr[i] = arr[i-1];                    
           }
            int[] resultArr = (int[]) method.invoke(null, arr);
            System.out.println("im here 2");
            for (int i = 0; i < resultArr.length; i++) {
                System.out.print(resultArr[i] + ",");
            }
            System.out.println("end -  need to sort arr");
            int[] expectedResult = removeDuplicates(arr);
            // Check if the result array is equal to the sorted array
            if (Arrays.equals(expectedResult, expectedResult)) {
                return true;
            } else {
                compile = true;
                return false;
            }
        } else {
            compile = false;
            return false;
        }
    }

    public Boolean runNumOfChackArrForSort(String num, String methodcode2)
            throws IOException, ClassNotFoundException, NoSuchMethodException, SecurityException,
            IllegalAccessException, IllegalArgumentException, InvocationTargetException, InterruptedException {
        String packageName = "package eliyaa.projecteliya;";
        String classTitle = "public class RunPitaron {";
        // main(String[] args) { ";
        String cleanedMethod2 = cleanCode(methodcode2);
        System.out.println(cleanedMethod2 + "the code clean");
        // File creation
        Path filePath = Paths.get("src/eliyaa/projecteliya/RunPitaron.java");
        // Create directories if they don't exist
        Files.createDirectories(filePath.getParent());
        // Use Files.newBufferedWriter to write to the file
        // Create source code file
        String className = "RunPitaron";
        String fileName = className + ".java";

        biuldFile(fileName, classTitle + cleanedMethod2+"}");
        int compile2 = compileAndRunJavaFile(className, cleanedMethod2);
        System.out.println(compile2);
        if (compile2 == 0) {
            System.out.println("im here");
            compile = true;
            System.out.println(cleanedMethod2 + "the code clean");
            System.out.println("the compile successfull");
            Files.createDirectories(filePath.getParent());
            URLClassLoader classLoader = new URLClassLoader(new URL[] { new File("./").toURI().toURL() });
            Class<?> dynamicClass = Class.forName(className, true, classLoader);
            method = dynamicClass.getMethod("SelectionSort", int[].class);
            int size = Integer.parseInt(num);
            int[] arr = new int[size];
            System.out.println("size = " + size);
            for (int i = 0; i < arr.length; i++) {
                arr[i] = size - i;
            }
            int[] resultArr = (int[]) method.invoke(null, arr);
            System.out.println("im here 2");
            for (int i = 0; i < resultArr.length; i++) {
                System.out.print(resultArr[i] + ",");
            }
            System.out.println("end -  need to sort arr");

            // Use Arrays.sort to sort the original array for comparison
            int[] sortedArr = new int[size];
            for (int v = 0; v < sortedArr.length; v++) {
                sortedArr[v] = size - v;
            }
            Arrays.sort(sortedArr);
            for (int j = 0; j < sortedArr.length; j++) {
                System.out.print(sortedArr[j] + ",");
            }
            System.out.println("end soretd 100% arr");
            Arrays.toString(sortedArr);

            // Check if the result array is equal to the sorted array
            if (Arrays.equals(resultArr, sortedArr)) {
                return true;
            } else {
                compile = true;
                return false;
            }
        } else {
            compile = false;
            return false;
        }
    }

    public int getNumOfItrationsSort() {
        return numOfItrationsSort;
    }

    public String getCodeForFronted() {
        return codeForFronted;
    }

    public static int getStepsScore() {
        return stepsScore;
    }

    public static long getStepsForClient() {
        return stepsForClient;
    }

    public static long getEfectiveFuncSteps() {
        return efectiveFuncSteps;
    }

    public static Object getLock() {
        return lock;
    }

    public long returnStepsForClient(Method method, int numIterationsSortFunc) throws IllegalAccessException,
            IllegalArgumentException, InvocationTargetException, NoSuchFieldException, SecurityException {
        long sum = 0;
        int[] randomArray = new int[numIterationsSortFunc];
        for (int j = 0; j < numIterationsSortFunc; j++) {

            randomArray[j] = numIterationsSortFunc - j;
        }

        Object resultForFuncs = method.invoke(null, randomArray);
        Class<?> runPitaronClass = method.getDeclaringClass();
        Field arrField = runPitaronClass.getDeclaredField("arr");
        arrField.setAccessible(true);
        long[] arrFuncSteps2 = (long[]) (arrField.get(null));
        sum = findMaxNumber(arrFuncSteps2);

        return sum;
    }

    private int compileAndRunJavaFile(String className, String code) throws IOException {
        String fileName = className + ".java";
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();

        // Create a diagnostic collector to capture compilation errors
        DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<>();

        // Use a custom file manager to capture compilation output
        StandardJavaFileManager fileManager = compiler.getStandardFileManager(diagnostics, null, null);
        Iterable<? extends JavaFileObject> compilationUnits = fileManager
                .getJavaFileObjectsFromStrings(Arrays.asList(fileName));

        // Compilation task
        JavaCompiler.CompilationTask task = compiler.getTask(null, fileManager, diagnostics, null, null,
                compilationUnits);

        // Perform the compilation
        boolean success = task.call();

        // StringBuilder to accumulate error messages
        StringBuilder errors = new StringBuilder();

        // Print compilation errors if any
        if (!success) {
            errors.append("Compilation failed:\n");
            for (Diagnostic<? extends JavaFileObject> diagnostic : diagnostics.getDiagnostics()) {
                if (diagnostic.getKind() == Diagnostic.Kind.ERROR) { // Check if it's an error
                    compilationErrors.append("Error on line ").append(diagnostic.getLineNumber()).append(": ");
                    compilationErrors.append(diagnostic.getMessage(null)).append("\n");
                }
            }
        }

        // Close the file manager
        fileManager.close();

        return success ? 0 : 1;
    }

    private static String truncateMessage(String message, int maxLength) {
        return message.substring(0, Math.min(message.length(), maxLength));
    }

    public void ChackFuncOfClientRemoveDuplicate(Method mathod, int[][] arraysForFunc) throws InterruptedException {
        Thread threadTestFunc = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    synchronized (lock) {
                        runTestsAndCalculateSuccessRate(method, arraysForFunc);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        System.out.println("thared start!");
        threadTestFunc.start();
        threadTestFunc.join();
        System.out.println("thared  end!");
    }


    public void FuncOfClientRemoveDuplicate(Method mathod, int[][] arraysForFunc) throws InterruptedException {
        Thread threadTestFunc = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    synchronized (lock) {
                        runTestsAndCalculateSuccessDuclicates(method, arraysForFunc);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        System.out.println("thared start!");
        threadTestFunc.start();
        threadTestFunc.join();
        System.out.println("thared  end!");
    }

    public void ChackFuncOfClientSort(Method mathod, int[][] arraysForFunc) throws InterruptedException {
        Thread threadTestFunc = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    synchronized (lock) {
                        runTestsAndCalculateSuccessRate(method, arraysForFunc);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        System.out.println("thared start!");
        threadTestFunc.start();
        threadTestFunc.join();
        System.out.println("thared  end!");
    }

    public static String chatGPT(String message) {
        String url = "https://api.openai.com/v1/chat/completions";
        String apiKey = "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";
        String model = "gpt-3.5-turbo";

        try {
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Authorization", "Bearer " + apiKey);
            con.setRequestProperty("Content-Type", "application/json");
            con.setDoOutput(true);

            // Build the JSON payload
            String jsonInputString = "{\"model\": \"" + model
                    + "\", \"messages\": [{\"role\": \"system\", \"content\": \"You are a chatbot.\"}, {\"role\": \"user\", \"content\": \""
                    + message + "\"}]}";

            System.out.println("Request JSON: " + jsonInputString); // Log the request for debugging

            try (OutputStream os = con.getOutputStream()) {
                byte[] input = jsonInputString.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            int responseCode = con.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                try (BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
                    StringBuilder response = new StringBuilder();
                    String inputLine;

                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }

                    // Log the entire response for debugging
                    String responseReturn = response.toString();
                    // Parse the JSON response
                    JSONObject jsonResponse = new JSONObject(response.toString());

                    // Extract and print the generated text
                    JSONArray choices = jsonResponse.getJSONArray("choices");
                    if (choices.length() > 0) {
                        String generatedText = choices.getJSONObject(0).optJSONObject("message")
                                .optString("content", "").trim();
                        System.out.println("The grade for the code is: " + generatedText);
                        return generatedText;
                    } else {
                        System.out.println("Error: 'choices' array is empty.");
                    }
                }
            } else {
                System.out.println("Error: " + responseCode);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void returnMethodMultipleTimes(Method method, int[] randomArray)
            throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        synchronized (lock) {
            method.invoke(null, randomArray); // The method you want to check 100K
        }
    }

    public static String cleanCode(String prog) {
        prog = prog.replace("\n", "");
        prog = prog.replace("\t", "");
        prog = prog.trim();

        return prog;
    }

    public static double calculateSlope(long input1, long StepsX, long input2, long stepsY) {
        return (stepsY - StepsX) / (input2 - input1);
    }

    public static int[] sort(int[] arr) {
        int n = arr.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }
        return arr;
    }

    public static long findMaxNumber(long[] array) {
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

    public int[] returnResultOfFuncClient(Method method, int[] arrayTest)
            throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Object result;
        result = method.invoke(null, arrayTest);
        return (int[]) result;
    }

    public int[] generateRandomArray() {
        Random random = new Random();
        int[] array = new int[100];
        for (int i = 0; i < 100; i++) {
            array[i] = random.nextInt(201) - 100; // Generate random numbers between -100 and 100
        }
        return array;
    }

    public long sortReturnSteps(int numOfIterationsSort) {
        long[] arrForFunc = new long[2];

        long sum = 0;

        for (int t = 0; t < arrForFunc.length; t++) {
            arrForFunc[t] = 0;
        }

        int[] arr;
        arrForFunc[0] = 0;
        arrForFunc[1] = 0;

        arr = new int[numOfIterationsSort];
        for (int j = 0; j < numOfIterationsSort; j++) {
            arr[j] = numOfIterationsSort - j;
        }
        int n = arr.length;
        for (int v = 0; v < n - 1; v++) {
            arrForFunc[0]++;
            for (int j = 0; j < n - v - 1; j++) {
                arrForFunc[1]++;
                if (arr[j] > arr[j + 1]) {
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }
        sum = findMaxNumber(arrForFunc);

        return sum;
    }

    public static String processCode(String inputCode) {

        int start = inputCode.indexOf("{");
        int end = 0, end2 = -1;
        String codeForParsing = "public class RunPitaron {" + inputCode + "}";

        String arrayName = "arr";

        int sizeOfLoops = 1;

        String codeForRun = inputCode;

        System.out.println(sizeOfLoops + " size:");
        StringBuilder stringBuilder = new StringBuilder(codeForRun);
        int openCodeIndex;

        int forIndex = stringBuilder.indexOf("for");
        int index = 0;
        while (forIndex != -1) {

            int openBracketIndex = stringBuilder.indexOf("{", forIndex);
            System.out.println(stringBuilder.indexOf("int", openBracketIndex));
            stringBuilder.insert(openBracketIndex + 1, "\n        " + arrayName + "[" + index + "]++;\n");
            index++;
            sizeOfLoops++;
            forIndex = stringBuilder.indexOf("for", openBracketIndex);
        }
        forIndex = stringBuilder.indexOf("while");
        while (forIndex != -1) {
            sizeOfLoops++;
            int openBracketIndex = stringBuilder.indexOf("{", forIndex);
            stringBuilder.insert(openBracketIndex + 1, "\n        " + arrayName + "[" + index + "]++;\n");
            forIndex = stringBuilder.indexOf("while", openBracketIndex);
            index++;
        }
        openCodeIndex = stringBuilder.indexOf("{");
        stringBuilder.insert(openCodeIndex + 1,
                " for (int i = 0; i < " + sizeOfLoops + "; i++) {" + arrayName + "[i] = 0; }");
        String classTitle = "public class RunPitaron {\n    public static long[] " + arrayName + " = new long["
                + sizeOfLoops + "];\n";
        return cleanCode(classTitle + stringBuilder + "}");
    }

    public static void biuldFile1(String fileName, String className, String cleanedMethod2) {
        try (PrintWriter writer = new PrintWriter(fileName)) {
            writer.println(cleanedMethod2);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void biuldFile(String fileName, String cleanedMethod2) {
        try (PrintWriter writer = new PrintWriter(fileName)) {
            writer.println(cleanedMethod2) ;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static boolean isArithmeticOperation(String operator) {
        return operator.equals("+") || operator.equals("-") || operator.equals("*") || operator.equals("/");
    }

    public int getIndex() {
        return index;
    }

    public int getTestPassed() {
        return testPassed;
    }

    public void setTestPassed(int testPassed) {
        this.testPassed = testPassed;
    }

    public int getNumOfItrations() {
        return numOfIterations;
    }

    public void setNumOfItrations(int numOfItrations) {
        this.numOfIterations = numOfItrations;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public void setSizeOfArrFunc(int sizeOfArrFunc) {
        this.sizeOfArrFunc = sizeOfArrFunc;
    }

    public void setIncAndDec(int incAndDec) {
        this.incAndDec = incAndDec;
    }

    public void setCountParmters(int countParmters) {
        this.countParmters = countParmters;
    }

    public static void setArrForFunc(int[] arrForFunc) {
        ServiceLogic.arrForFunc = arrForFunc;
    }

    public static void setSuccessRate(double successRate) {
        ServiceLogic.successRate = successRate;
    }

    public void setTimeWeighted(double timeWeighted) {
        this.timeWeighted = timeWeighted;
    }

    public void setMemoryWeighted(double memoryWeighted) {
        this.memoryWeighted = memoryWeighted;
    }

    public void setSuccessRateWeighted(double successRateWeighted) {
        this.successRateWeighted = successRateWeighted;
    }

    public void setStepsScoreWeighted(double stepsScoreWeighted) {
        this.stepsScoreWeighted = stepsScoreWeighted;
    }

    public void setChatGptScoreWeighted(double chatGptScoreWeighted) {
        this.chatGptScoreWeighted = chatGptScoreWeighted;
    }

    public void setCodeFronted(String codeFronted) {
        this.codeFronted = codeFronted;
    }

    public void setCompile(Boolean compile) {
        this.compile = compile;
    }

    public int getTimeScore() {
        return timeScore;
    }

    public String getCodeFronted() {
        return codeFronted;
    }

    public Boolean getCompile() {
        return compile;
    }

    public void setTimeScore(int timeScore) {
        this.timeScore = timeScore;
    }

    public int getMemoryScore() {
        return memoryScore;
    }

    public void setMemoryScore(int memoryScore) {
        this.memoryScore = memoryScore;
    }

    public void setCompilationErrors(StringBuilder compilationErrors) {
        this.compilationErrors = compilationErrors;
    }

    public double getClientTime() {
        return clientTime;
    }

    public void setClientTime(double clientTime) {
        this.clientTime = clientTime;
    }

    public double getTheEffectiveTime() {
        return theEffectiveTime;
    }

    public void setTheEffectiveTime(double theEffectiveTime) {
        this.theEffectiveTime = theEffectiveTime;
    }

    public static Method getMethod() {
        return method;
    }

    public static void setMethod(Method method) {
        ServiceLogic.method = method;
    }

    public void setRepo(HomeRepository repo) {
        Repo = repo;
    }

    public int getSizeOfArrFunc() {
        return sizeOfArrFunc;
    }

    public int getIncAndDec() {
        return incAndDec;
    }

    public int getCountParmters() {
        return countParmters;
    }

    public StringBuilder getCompilationErrors() {
        return compilationErrors;
    }

    public HomeRepository getRepo() {
        return Repo;
    }

    public static int[] getArrForFunc() {
        return arrForFunc;
    }

    public static double getSuccessRate() {
        return successRate;
    }

    public double getTimeWeighted() {
        return timeWeighted;
    }

    public double getMemoryWeighted() {
        return memoryWeighted;
    }

    public double getSuccessRateWeighted() {
        return successRateWeighted;
    }

    public double getStepsScoreWeighted() {
        return stepsScoreWeighted;
    }

    public double getChatGptScoreWeighted() {
        return chatGptScoreWeighted;
    }
}
