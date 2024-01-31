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
import java.lang.reflect.Modifier;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.Random;
import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import org.json.JSONArray;
import org.json.JSONObject;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.BinaryExpr;
import com.github.javaparser.ast.expr.UnaryExpr;
import com.github.javaparser.ast.stmt.ForStmt;
import com.github.javaparser.ast.stmt.WhileStmt;
import org.springframework.stereotype.Service;

@Service
public class HomeService {
    private int testPassed = 0;
    private int numOfItrations = 100000;
    private int numOfItrationsSort = 100;
    private String codeForFronted; 
    private int index = 0;
    private int sizeOfArrFunc = 0;
    private int incAndDec = 0;
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
    private double theEffectiveTime = 8;
    public static Method method = null;
    private static int stepsScore = 0;
    private static int stepsForClient = 0;
    private static int efectiveFuncSteps = 0;

    public HomeService() {

    }

    private HomeRepository Repo;

    public HomeService(HomeRepository repo) {
        this.Repo = repo;
    }

    public String getCodeFronted() {
        return codeFronted;
    }

    public Boolean getCompile() {
        return compile;
    }

    public double checkAnswer(String methodcode2) throws IOException, ClassNotFoundException, NoSuchMethodException,
            SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException,
            NoSuchFieldException {
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
        Thread thread3 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("Thread build the func start");
                    biuldFile(fileName, processCode(cleanedMethod2));
                    System.out.println("Thread build end!");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread3.start();
        // Compile the source file
        int compileResult = compileAndRunJavaFile(className, cleanedMethod2);
        System.out.println("Compilation result: " + compileResult);

        if (compileResult == 0) {
            System.out.println("the compile successfull");
            compile = true;
            // add(new H1(index + ":Compilation result: Sucssesfull"));
            Files.createDirectories(filePath.getParent());
            URLClassLoader classLoader = new URLClassLoader(new URL[] { new File("./").toURI().toURL() });
            Class<?> dynamicClass = Class.forName(className, true, classLoader);
            // Get the method and invoke it for each input
            // add(new H1(readyCode));
            System.out.println("ready code: " + readyCode);

            method = dynamicClass.getMethod("SelectionSort", int[].class);
            System.out.println("---------------------------------------------");
            System.out.println("Modifiers: " + Modifier.toString(method.getModifiers()));
            System.out.println("---------------------------------------------");
            System.out.println("---------------------------------------------");
            System.out.println("parmaters count:" + method.getParameterCount());
            System.out.println("---------------------------------------------");
            System.out.println("---------------------------------");
            String pitaronProg = "public class RunPitaron {" + cleanedMethod2 + "}";
            try {
                System.out.println("pitaron prog java parser work!!!  : " + pitaronProg);
                CompilationUnit cu = StaticJavaParser.parse(pitaronProg);

                int variablesCount = cu.findAll(VariableDeclarator.class).size();
                // int arithmeticExpressionsCount = cu.findAll(BinaryExpr.class).size();
                long arithmeticExpressionsCount = cu.findAll(BinaryExpr.class).stream()
                        .filter(expr -> expr.isBinaryExpr() && isArithmeticOperation(expr.getOperator().asString()))
                        .count();
                int forLoopCount = cu.findAll(ForStmt.class).size();
                int whileLoopCount = cu.findAll(WhileStmt.class).size();
                sizeOfArrFunc = forLoopCount + whileLoopCount;
                long incrementCount = cu.findAll(UnaryExpr.class).stream()
                        .filter(expr -> "++".equals(expr.getOperator().asString()))
                        .count();

                long decrementCount = cu.findAll(UnaryExpr.class).stream()
                        .filter(expr -> "--".equals(expr.getOperator().asString()))
                        .count();
                incAndDec = -(int) (incrementCount + decrementCount);
                countParmters = variablesCount;

                System.out.println("Count of 'for' loops: " + forLoopCount);
                System.out.println("Count of 'while' loops: " + whileLoopCount);
                System.out.println("Count of Variables: " + variablesCount);
                System.out.println("Count of Arithmetic Expressions: " + arithmeticExpressionsCount);
                // add(new Span("Count of 'while' loops: " + whileLoopCount));
                // add(new Span("Count of 'for' loops: " + forLoopCount));
                // add(new Span("Count of Variables: " + variablesCount));
                // add(new Span("Count of Arithmetic Expressions: " +
                // arithmeticExpressionsCount));
                // add(new Span("Count of increment(++): " + incrementCount));
                // add(new Span("Count of decrementCount(--) : " + decrementCount));

            } catch (Exception e) {
                System.out.println("Parsing failed. Reason: " + e.getMessage());
                e.printStackTrace();
            }

            int[] randomArray = new int[numOfItrationsSort];

            for (int j = 0; j < randomArray.length; j++) {
                randomArray[j] = numOfItrationsSort - j;
            }

            stepsForClient = returnStepsForClient(method, numOfItrationsSort);
            efectiveFuncSteps = sortReturnSteps(randomArray);
            System.out.println(efectiveFuncSteps + "the steps of my method");
            testPassed = 0;
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        System.out.println("Thread1 start");
                        testPassed = runTestsAndCalculateSuccessRate(method);
                        System.out.println("Thread1 end!");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            thread.start();
            long theEffectiveMemory = 0;
            method = dynamicClass.getMethod("SelectionSort", int[].class);
            Thread thread2 = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        System.out.println("Thread2 start");
                        runtenMethodMultipleTimes(method, numOfItrations);
                        System.out.println("Thread2 end!");
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        return;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            Runtime runtime = Runtime.getRuntime();
            // יצירת אובייקט Thread והפעלת התהליך
            runtime.gc(); // Explicitly calling garbage collector to clean up memory
            try {
                Instant start = Instant.now(); // שמירת הזמן הנוכחי

                thread2.start();

                Thread.sleep(10000); // לדוגמה, שהשהייה תהיה של 5 שניות

                // בדיקה אם הזמן חרף את ה-10 שניות
                if (Duration.between(start, Instant.now()).toSeconds() >= 10) {
                    System.out.println("im here intruupt!");
                    thread.interrupt();

                } else {
                    System.out.println("thread end before the interrupt");
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            theEffectiveMemory = runtime.totalMemory() - runtime.freeMemory();
            System.out.println("Time taken by the Client method: " + clientTime + " Seconds");
            System.out.println("Memory used by the Client method: " + theEffectiveMemory + " bytes");
            // Executing the second method and measuring its memory usage
            Method method2 = HomeService.class.getDeclaredMethod("sort", int[].class);
            Runtime runTimeMyFunc = Runtime.getRuntime();
            codeFronted = readyCode;
            runTimeMyFunc.gc();
            long memoryClient = runTimeMyFunc.totalMemory() - runTimeMyFunc.freeMemory();
            System.out.println("Time taken by the My method: " + theEffectiveTime + " Seconds");
            System.out.println("Memory used by the memoryClient method: " + memoryClient + " bytes");
            // Compare time and memory efficiency between the methods
            timeScore = 0;
            memoryScore = 0;
            long timeDifference = 0;
            double memoryDifference = 0;
            // Check time efficiency
            // Assuming theEffectiveTime and clientTime are variables representing time in
            // some unit
            System.out.println("time score is " + timeScore);
            if (clientTime == 0 || clientTime > 15) {
                timeScore = 0;

            } else {
                if (theEffectiveTime >= clientTime - 0.5) {
                    timeScore = 100;
                } else {
                    timeDifference = (long) (clientTime - theEffectiveTime);

                    if (timeDifference > 1000) {
                        // If the time difference is significant
                        System.out.println("time score:" + timeScore);
                        timeScore = 10;
                    } else {
                        timeScore = (int) ((1000 - timeDifference) * 40 / 1000.0);
                    }
                }
            }

            if (theEffectiveMemory >= memoryClient || theEffectiveMemory >= memoryClient - 200) {
                memoryScore = 100;
            } else {
                memoryDifference = theEffectiveMemory - memoryClient;

                if (memoryDifference > 0) {
                    memoryScore = (int) ((1000 - memoryDifference) * 90 / 1000.0);
                } else {
                    memoryScore = (int) ((memoryDifference / 1000) * -1);
                }

            }

            System.out.println("effective func Steps: " + efectiveFuncSteps);
            System.out.println("steps for client: " + stepsForClient);
            // Check steps efficiency

            int baseScore = stepsForClient < 100000 ? 10 : 10000;

            // int threshold = stepsForClient - randomArray.length * 2;
            if (efectiveFuncSteps >= stepsForClient || efectiveFuncSteps >= stepsForClient - randomArray.length) {
                stepsScore = 100;
            } else if (stepsForClient - efectiveFuncSteps + 1000 > 0) {
                stepsScore = 0;
            } else {
                stepsScore = Math.min((stepsForClient - efectiveFuncSteps) / stepsForClient < 100000 ? 10 : 10000, 0);
            }

            // Get a score from chatGPT
            String resultString;
            int chatGptScore = 0;
            try {
                resultString = chatGPT(truncateMessage(cleanCode(
                        "Give me a score from 10 to 100 (when you respond The goal of the code I'm sending you should be a sorted array, if it doesn't do that, deduct it from the score according to your opinion, provide only the score without explanations or words, just the number). Rate this function based on efficiency, code integrity, and memory usage. "
                                + cleanedMethod2),
                        4096));
                System.out.println("---" + resultString + "---");
                chatGptScore = Integer.parseInt(resultString);
            } catch (Exception e) {
                // Handle the exception, you can log it or perform other actions as needed
                e.printStackTrace(); // This will print the exception details to the console
                resultString = "Error occurred"; // You might want to provide a default result in case of an error
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
                combinedScore = 2.0;
                return combinedScore;
            }

            // Display the grade
            System.out.println("The grade of your method is: " + String.format("%.1f", combinedScore));

            // Add the grade to the UI
            if (combinedScore > 0) {
                return combinedScore;
                // add(new H2("The grade of your method is: " + String.format("%.1f",
                // combinedScore)));
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

    public int returnStepsForClient(Method method, int numIterations) throws IllegalAccessException,
            IllegalArgumentException, InvocationTargetException, NoSuchFieldException, SecurityException {
        int maxSteps = 0;
        int[] randomArray = new int[numIterations];
        for (int j = 0; j < numIterations; j++) {

            randomArray[j] = numIterations - j;
        }

        Object resultForFuncs = method.invoke(null, randomArray);
        Class<?> runPitaronClass = method.getDeclaringClass();
        Field arrField = runPitaronClass.getDeclaredField("arr");
        arrField.setAccessible(true);
        int[] arrFuncSteps2 = (int[]) (arrField.get(null));
        for (int j = 0; j < arrFuncSteps2.length; j++) {
            System.out.println("stpes client " + arrFuncSteps2[j]);
        }
        maxSteps = findMaxNumber(arrFuncSteps2);
        System.out.println("Max steps: " + maxSteps);
        return maxSteps;
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

    public static void runtenMethodMultipleTimes(Method method, int numIterations)
            throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, InterruptedException {
        long endTime;
        int[] randomArray = new int[numIterations];

        for (int j = 0; j < numIterations; j++) {
            randomArray[j] = numIterations - j;
        }
        long startTime = System.nanoTime();
        method.invoke(null, randomArray); // The method you want to check
        endTime = System.nanoTime();
        double time = (endTime - startTime) / 1e9; // Calculating time in seconds
        System.out.println("-----------" + time + " seconds");
        clientTime = time;
        System.out.println(clientTime + " seconds");

        return;
    }

    public static String cleanCode(String prog) {
        prog = prog.replace("\n", "");
        prog = prog.replace("\t", "");
        prog = prog.trim();
        return prog;
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

    public static int findMaxNumber(int[] array) {
        // במקרה של מערך ריק, נחזיר ערך שלילי או ערך ראשוני כלשהו
        if (array == null) {
            return -1;
        }

        if (array.length == 0) {
            return -1;
        }

        int maxNumber = array[0]; // נניח שהמספר הכי גדול הוא המספר הראשון במערך

        for (int i = 1; i < array.length; i++) {
            if (array[i] > maxNumber) {
                maxNumber = array[i];
                System.out.println(maxNumber);
            }
        }

        return maxNumber;
    }

    public int runTestsAndCalculateSuccessRate(Method method) throws IllegalAccessException,
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
                { 5, -10 },
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
                { -10, 5 },
                { -2, 12 } // Expected output for negative and positive numbers
        };
        Object result = null;
        int counter = inputsArray.length;
        int totalTests = inputsArray.length;
        int errorCount = 0;

        for (int i = 0; i < totalTests; i++) {
            result = method.invoke(null, inputsArray[i]);
            int[] resultArray = (int[]) result;
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

        return totalTests + errorCount;
    }

    public static int sortReturnSteps(int[] arr) {
        int[] arrForFunc = new int[2];
        for (int i = 0; i < arrForFunc.length; i++) {
            arrForFunc[i] = 0;
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

        for (int i = 0; i < arrForFunc.length; i++) {
            System.out.println(arrForFunc[i] + " steps!");
        }
        return findMaxNumber(arrForFunc);
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
        String classTitle = "public class RunPitaron {\n    public static int[] " + arrayName + " = new int["
                + sizeOfLoops + "];\n";
        String codeForRun = inputCode;

        System.out.println(sizeOfLoops + " size:");
        StringBuilder stringBuilder = new StringBuilder(codeForRun);
        int openCodeIndex;

        int forIndex = stringBuilder.indexOf("for");
        int index = 0;
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
        return numOfItrations;
    }

    public void setNumOfItrations(int numOfItrations) {
        this.numOfItrations = numOfItrations;
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
