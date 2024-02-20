package eliyaa.projeliya;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
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
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import java.util.concurrent.Future;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import org.json.JSONArray;
import org.json.JSONObject;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.stmt.ForStmt;
import com.github.javaparser.ast.stmt.WhileStmt;

import org.springframework.data.mongodb.core.aggregation.DateOperators.Millisecond;
import org.springframework.stereotype.Service;

@Service
public class HomeService {
    private static Thread previousThread = null;
    private int testPassed = 0;
    private int numOfIterations = 50000;
    private int numOfItrationsSort = 10000;
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
    public String compilationErrors;
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
    private int timeForSteps = 5000;
    private int timeForTimeAndMemory = 8000;
    private String resultStringGpt;
    private ExecutorService executor;

    public HomeService() {

    }

    private HomeRepository Repo;

    public HomeService(HomeRepository repo) {

        this.Repo = repo;

        executor = Executors.newCachedThreadPool();

    }

    public double checkAnswer(String methodcode2, String NameOfFunc)
            throws IOException, ClassNotFoundException, NoSuchMethodException,
            SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException,
            NoSuchFieldException, InterruptedException {
        executor = Executors.newCachedThreadPool();
        cleanedMethod2 = cleanCode(methodcode2);
        System.out.println(cleanedMethod2 + "the code clean");
        // File creation
        Path filePath = Paths.get("src/eliyaa/projecteliya/RunPitaron.java");
        // Create directories if they don't exist
        Files.createDirectories(filePath.getParent());
        // Use Files.newBufferedWriter to write to the file
        // Create source code file
        readyCode = processCode(cleanedMethod2);
        System.out.println(fileName + readyCode);
        Thread t2 = biuldFileThread(fileName, cleanedMethod2);
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
            if (NameOfFunc.equals("SelectionSort")) {
                method = dynamicClass.getMethod("SelectionSort", int[].class);
                ChackFuncOfClient(method);
            }
            System.out.println("-------- " + testPassed + "--------------------------------");
            if (testPassed < 5) {
                System.out.println("im here!");
                successRateWeighted = 0.0;
                chatGptScoreWeighted = 0.5;
                stepsScoreWeighted = 0.5;
                memoryWeighted = 0.5;
                timeWeighted = 0.5;
                System.out.println(timeWeighted);
                System.out.println(memoryWeighted);
                System.out.println(chatGptScoreWeighted);
                System.out.println(stepsScoreWeighted);
                System.out.println(successRateWeighted);
                double combinedScore = 2.0;
                return combinedScore;
            }
            System.out.println("------------------  get res Started! ------------------");
            Thread manyStepsThread = getGradeForSteps();
            manyStepsThread.start();
            boolean result = countTime(manyStepsThread, timeForSteps);
            System.out.println("------------------  get res ended! ------------------");

            System.out.println("result: " + result);
            if (result) {
                System.out.println("Thread finished before the maximum time.");
            } else {
                System.out.println("Thread did not finish before the maximum time and was interrupted.");
            }

            Thread runManyThread = buildThreadForRunMany();
            Runtime runtime = Runtime.getRuntime();
            runtime.gc();
            runManyThread.start();
            System.out.println("------------------  get res started! ------------------");
            boolean resultForTimeScore = countTime(runManyThread, timeForTimeAndMemory);
            long memoryClient = runtime.totalMemory() - runtime.freeMemory();
            System.out.println("------------------  get res Ended! ------------------");
            if (resultForTimeScore) {
                System.out.println("Thread finished before the maximum time.");
            } else {
                System.out.println("Thread did not finish before the maximum time and was interrupted.");
            }
            System.out.println("Time taken by the Client method: " + memoryClient + " bytes");
            System.out.println("Memory used by the my method: " + efectiveMemory + " bytes");
            System.out.println("testPassed = " + testPassed);
            codeFronted = readyCode;
            System.out.println("time score is: " + resultForTimeScore);

            timeScore = 0;
            memoryScore = 0;
            long timeDifference = 0;
            double memoryDifference = 0;
            if (resultForTimeScore == true) {
                timeScore = 100;
            } else {
                timeScore = 0;
            }
            if (efectiveMemory >= memoryClient || efectiveMemory >= memoryClient - 200 && resultForTimeScore == true) {
                memoryScore = 100;
            } else {
                memoryScore = 0;
            }
            int baseScore = stepsForClient < 100000 ? 10 : 10000;
            System.out.println("averegeStepForClient: " + averegeStepForClient + ", averegeStepForEfective: "
                    + averegeStepForEfective);
            if (averegeStepForEfective >= averegeStepForClient && result == true) {
                stepsScore = 100;
            } else if ((averegeStepForClient - averegeStepForEfective + 1000) > 0) {
                stepsScore = 0;
            } else {
                stepsScore = 20;
            }
            // Get a score from chatGPT
            int chatGptScore = 0;
            try {
                System.out.println("im start the chatGPT Thread");
             //   Thread t = chatGptFuncThread(cleanedMethod2);
                //t.start();
                //t.join();
                System.out.println("end chatGpt Thread");

                System.out.println("---" + resultStringGpt + "---");

               // int find = resultStringGpt.indexOf(":");
                // if (find != -1) {
                //     System.out.println("find");
                //     resultStringGpt = resultStringGpt.substring(find + 1);
                // }
                // chatGptScore = Integer.parseInt(resultStringGpt);
            } catch (Exception e) {
                e.printStackTrace(); // This will print the exception details to the console
                resultStringGpt = "Error occurred";
                chatGptScore = 0;
            }

            System.out.println(timeScore + "timeScore");
            timeWeighted = timeScore * 0.1;
            System.out.println("timeWeighted: " + timeWeighted);
            memoryWeighted = memoryScore * 0.1;
            successRateWeighted = successRate * 0.3;
            stepsScoreWeighted = stepsScore * 0.4;
            chatGptScoreWeighted = chatGptScore * 0.1;

            System.out.println("timeScore after multiplication by 0.1: " + timeWeighted);
            System.out.println("memoryScore after multiplication by 0.2: " + memoryWeighted);
            System.out.println("successRate after multiplication by 0.3: " + successRateWeighted);
            System.out.println("stepsScore after multiplication by 0.3: " + stepsScoreWeighted);
            System.out.println("chatGptScore after multiplication by 0.1: " + chatGptScoreWeighted);
            double combinedScore = 0.0;
            combinedScore = ((timeScore * 0.1) + (memoryScore * 0.1) + (successRate * 0.4) + (stepsScore * 0.3)
                    + (chatGptScore * 0.1)) / 10;
            System.out.println("testPassed: " + testPassed);

            // Display the grade
            System.out.println("The grade of your method is: " + String.format("%.1f", combinedScore));

            // Add the grade to the UI
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

    public void calculateAndPrintSteps(Method method, int numOfIterationsSorted) throws IllegalAccessException,
            IllegalArgumentException, InvocationTargetException, NoSuchFieldException, SecurityException {
        long arrStepsFunc, clientFuncSteps;
        int index = 0;
        long[] arrDifference = new long[11];
        averegeStepForClient = 0;
        for (int v = 1000; v <= numOfItrationsSort; v += 1000) {
            clientFuncSteps = returnStepsForClient(method, v);
            arrStepsFunc = returnStepsForClient(method, (v + 1000));
            arrDifference[index] = ((arrStepsFunc - clientFuncSteps) / ((v + 1000) - v));
            averegeStepForClient = arrDifference[index] + averegeStepForClient;
            // System.out.println("(" + (index + 1) + ")steps:" + v + "-> ,clientFuncSteps =
            // " + clientFuncSteps
            // + " ,nextSteps = " + arrStepsFunc);
            // System.out.println("the slope is:" + calculateSlope(v, clientFuncSteps, (v +
            // 1000), arrStepsFunc));
            index++;
        }
        int counterOne = 0, counterN = 0, counterMarici = 0;
        long sum = arrDifference[1] - arrDifference[0];
        long sum2 = arrDifference[1] - arrDifference[0];
        // System.out.println("sum2 = " + sum2 + "sum = " + sum);
        long[] arrayRealDifference = new long[5];
        for (int i = 0; i < arrDifference.length - 6; i++) {
            arrayRealDifference[i] = arrDifference[i + 1] - arrDifference[i];
            // System.out.println(arrayRealDifference[i]);
        }
        // for (int j = 0; j < arrDifference.length - 1; j++) {
        // System.out.println("slopes" + arrDifference[j]);
        // }
        System.out.println("---go the the end part---");
        if (isAllZeros(arrDifference) && isAllZeros(arrayRealDifference)) {
            System.out.println("This is O(1)");
        } else if (areAllElementsEqual(arrDifference)) {
            System.out.println("This is O(n)");
        } else {
            System.out.println("This is O^k");

        }

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
        for (int i = 1; i < arr.length - 1; i++) {
            if (arr[i] != firstElement) {
                return false;
            }
        }
        return true;
    }

    public Thread getGradeForSteps() {

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
        executor.shutdown();
        ExecutorService executor = Executors.newSingleThreadExecutor();

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

    public static Boolean countTime(Thread thread, long maxTimeInMillis) 
    {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        boolean[] result = new boolean[1];
        executor.execute(() -> {
            try {
                thread.join(maxTimeInMillis);
                result[0] = !thread.isAlive();
                if (!result[0]) {
                    thread.interrupt(); // אם הוא עדיין חי אחרי הכמות זמן
                }
            } catch (InterruptedException e) {
                result[0] = false;
            }
        });
        executor.shutdown();
        try {
            executor.awaitTermination(maxTimeInMillis, java.util.concurrent.TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            executor.isTerminated();
            executor.shutdownNow();
        }
        return result[0];
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

    public void shutdownExecutor() {
        executor.shutdown();
    }

    public Thread biuldFileThread(String fileName, String stringMethodBeforeProcess) {
        Thread thread3 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    synchronized (lock) {

                        System.out.println(" ----------- Thread build the func start -----------------");
                        biuldFile(fileName, processCode(stringMethodBeforeProcess));
                        System.out.println("Thread build end!");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        return thread3;
    }

    public Boolean runNumOfChackArr(String num, String methodcode2)
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
        String readyCode = processCode(cleanedMethod2);

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

        // Redirect standard error to capture compilation errors
        ByteArrayOutputStream errorStream = new ByteArrayOutputStream();
        PrintStream originalErrorStream = System.err;
        System.setErr(new PrintStream(errorStream));

        int compileResult = compiler.run(null, null, null, fileName);

        // Restore the original error stream
        System.setErr(originalErrorStream);

        System.out.println("Compilation result: " + compileResult);

        // Print compilation errors if any
        compilationErrors = errorStream.toString();
        if (!compilationErrors.isEmpty()) {
            System.out.println("Compilation errors:\n" + compilationErrors);
        }

        return compileResult;
    }

    private static String truncateMessage(String message, int maxLength) {
        return message.substring(0, Math.min(message.length(), maxLength));
    }

    public void ChackFuncOfClient(Method mathod) throws InterruptedException {
        Thread threadTestFunc = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    synchronized (lock) {
                        System.out.println("Thread1 start");
                        runTestsAndCalculateSuccessRate(method);
                        System.out.println("Thread1 end!");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        Future<?> future = executor.submit(threadTestFunc);
        try {
            future.get(); // זה יחכה עד שהתהליך יסיים את הריצה
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
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

    public void runTestsAndCalculateSuccessRate(Method method) throws IllegalAccessException,
            IllegalArgumentException, InvocationTargetException {
        System.out.println("---------------------------------------------");
        int[][] inputsArray = {
                { 7, 5, 1, 2, 3 },
                { 10, 3 },
                { 9, 2 },
                { 3, 1 }, // Edge case: Same numbers
                { 5, 0 }, // Edge case: Zeroes
                { 15, 12 }, // Edge case: Larger numbers
                { -3, -6 }, // Edge case: Negative numbers
                { 100, 99 }, // Edge case: Large numbers
                { 10, 5, -10 },
                { 12, -2 } // Edge case: Negative and positive numbers
        };
        System.out.println("input array11:" + Arrays.toString(inputsArray[0]));

        int[][] expectedOutputsArray = {
                { 1, 2, 3, 5, 7 },
                { 3, 10 },
                { 2, 9 },
                { 1, 3 }, // Expected output for same numbers
                { 0, 5 }, // Expected output for zeroes
                { 12, 15 }, // Expected output for larger numbers
                { -6, -3 }, // Expected output for negative numbers
                { 99, 100 }, // Expected output for large numbers
                { -10, 5, 10 },
                { -2, 12 } // Expected output for negative and positive numbers
        };
        Object result = null;
        int counter = inputsArray.length;
        int totalTests = inputsArray.length;
        int errorCount = 0;

        for (int i = 0; i < totalTests; i++) {
            int[] resultArray = returnResultOfFuncClient(method, inputsArray[i]);
            System.out.println("Test Case " + (i + 1) + ":");
            System.out.println("Input Array: " + Arrays.toString(inputsArray[i]));
            System.out.println("Expected Output Array: " + Arrays.toString(expectedOutputsArray[i]));
            System.out.println("Actual Output Array: " + Arrays.toString(resultArray));
            if (!Arrays.equals(resultArray, expectedOutputsArray[i])) {
                errorCount--;
                System.out.println("Error: Output mismatch!");
                System.out.println("Actual Output Array: " + Arrays.toString(resultArray));
                System.out.println("Expected Output Array: " + Arrays.toString(expectedOutputsArray[i]));
            }
        }
        System.out.println("Total errors: " + errorCount + " totalTests " + totalTests);
        successRate = 0;
        counter = counter + errorCount;
        if (counter != 0) {
            successRate = ((double) (totalTests + errorCount) * 10);

            System.out.println(successRate);
        } else {
            successRate = 0;
        }

        testPassed = totalTests + errorCount;
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

        CompilationUnit cu = StaticJavaParser.parse(cleanCode(codeForParsing));
        String arrayName = "arr";
        int forLoopCount = cu.findAll(ForStmt.class).size();
        int whileLoopCount = cu.findAll(WhileStmt.class).size();
        int sizeOfLoops = forLoopCount + whileLoopCount;
        String classTitle = "public class RunPitaron {\n    public static long[] " + arrayName + " = new long["
                + sizeOfLoops + "];\n";
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
            writer.println(cleanedMethod2);
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
        HomeService.arrForFunc = arrForFunc;
    }

    public static void setSuccessRate(double successRate) {
        HomeService.successRate = successRate;
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

    public void setCompilationErrors(String compilationErrors) {
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
        HomeService.method = method;
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

    public String getCompilationErrors() {
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
