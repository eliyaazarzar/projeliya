package eliyaa.projeliya;

import com.github.javaparser.TokenRange;
import com.github.javaparser.ast.AllFieldsConstructor;
import com.github.javaparser.ast.Generated;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.observer.ObservableProperty;
import com.github.javaparser.ast.visitor.CloneVisitor;
import com.github.javaparser.ast.visitor.GenericVisitor;
import com.github.javaparser.ast.visitor.VoidVisitor;
import com.github.javaparser.metamodel.BinaryExprMetaModel;
import com.github.javaparser.metamodel.JavaParserMetaModel;
import com.github.javaparser.printer.Stringable;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.BinaryExpr;
import com.github.javaparser.ast.expr.UnaryExpr;
import com.github.javaparser.ast.stmt.ForStmt;
import com.github.javaparser.ast.stmt.WhileStmt;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import javax.tools.*;
import com.github.javaparser.ast.expr.AssignExpr;
import org.json.JSONArray;
import org.json.JSONObject;
import org.objectweb.asm.Label;
import org.springframework.data.mongodb.core.aggregation.AccumulatorOperators.Max;

import static org.junit.Assert.assertArrayEquals;
import java.io.*;
import java.lang.reflect.*;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Route("")
public class HomePage extends VerticalLayout {
    private static int[] arrForFunc;

    private int sizeOfArrFunc;
    private int sizeOfArrFuncClient;
    private int countParmters;
    private int incAndDec;
    private int arithmeticCount;
    private TextArea answerTextArea = new TextArea("Your Answer");
    private Button submitButton = new Button("Submit Answer");
    private Paragraph answerParagraph = new Paragraph();
    private int index = 0;

    public HomePage() throws UnknownHostException {

        // Set the height and width of the TextArea
        answerTextArea.setHeight("300px"); // Adjust the height as needed
        answerTextArea.setWidth("500px"); // Adjust the width as needed
        InetAddress localhost = InetAddress.getLocalHost();
        answerTextArea.setValue("public static int[] SelectionSort(int[] array)\n {" +"\n\n\n\n\n\n\n\n\n"+" return array; \n}");   
        // Print the IP address and port
        System.out.println("IP Address: " + localhost.getHostAddress() + " Port: 8080");
        
        // If you want to add it to your HTML content
        add(new H1("Localhost IP Address: " + localhost.getHostAddress() + " Port: 8080"));        // Print the IP address and port
        answerTextArea.setMinHeight("150px");
        answerTextArea.setMinWidth("300px");

        submitButton.addClickListener(event -> {
            answerParagraph.setText("Submitted answer: " + answerTextArea.getValue());
            try {
                checkAnswer(answerTextArea.getValue());
            } catch (ClassNotFoundException | NoSuchMethodException | SecurityException | IllegalAccessException
                    | IllegalArgumentException | InvocationTargetException | IOException e) {
                e.printStackTrace();
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
        });

        add(answerTextArea, submitButton, answerParagraph);
    }

    public void checkAnswer(String methodcode2) throws IOException, ClassNotFoundException, NoSuchMethodException,
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
        biuldFile(fileName, processCode(cleanedMethod2));
        // Compile the source file
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        int compileResult = compiler.run(null, null, null, fileName);
        System.out.println("Compilation result: " + compileResult);

        if (compileResult == 0) {
            index++;
            add(new H1(index + ":Compilation result: Sucssesfull"));
            Files.createDirectories(filePath.getParent());
            URLClassLoader classLoader = new URLClassLoader(new URL[] { new File("./").toURI().toURL() });
            Class<?> dynamicClass = Class.forName(className, true, classLoader);
            // Get the method and invoke it for each input
            add(new H1(readyCode));
            Method method = dynamicClass.getMethod("SelectionSort", int[].class);
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
                add(new Span("Count of 'while' loops: " + whileLoopCount));
                add(new Span("Count of 'for' loops: " + forLoopCount));
                add(new Span("Count of Variables: " + variablesCount));
                add(new Span("Count of Arithmetic Expressions: " + arithmeticExpressionsCount));
                add(new Span("Count of  increment(++): " + incrementCount));
                add(new Span("Count of decrementCount(--) : " + decrementCount));

            } catch (Exception e) {
                System.out.println("Parsing failed. Reason: " + e.getMessage());
                e.printStackTrace();
            }
            System.out.println("---------------------------------------------");
            int[][] inputsArray = {
                    { 7,5,1,2,3},
                    {  10,3 },
                    { 9,2 },
                    { 3,1 }, // Edge case: Same numbers
                    { 5,0 }, // Edge case: Zeroes
                    {  15,12}, // Edge case: Larger numbersד
                    { -3, -6 }, // Edge case: Negative numbers
                    { 100, 99 }, // Edge case: Large numbers
                    { 5, -10 }, 
                    { 12, -2 } // Edge case: Negative and positive numbers
            };
            System.out.println("input array11:"+Arrays.toString(inputsArray[0]));

            int[][] expectedOutputsArray = {
                    { 1,2,3,5,7 },
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
            Type[] parameters = method.getGenericParameterTypes();
            int counter = inputsArray.length;
            int totalTests = inputsArray.length;
            int errorCount = 0;

            for (int i = 0; i < totalTests; i++) 
            {
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

            System.out.println("Total errors: " + errorCount + "totalTests" + totalTests);
            double successRate = 0;
            counter = counter + errorCount;
            if (counter != 0)

            {
                successRate = ((double) (totalTests + errorCount) * 10);
                System.out.println(successRate);
            } else {
                successRate = 0;
            }

            System.out.println("Success rate: " + successRate + "%" + counter + "/" + totalTests);
            String rate = "Success rate: " + successRate + "%" + counter + "/" + totalTests;
            add(new H1(rate));
            Runtime runtime = Runtime.getRuntime();
            Double clientTime = runtenMethodMultipleTimes(method, inputsArray, 10000000);
            runtime.gc(); // Explicitly calling garbage collector to clean up memory
            long theEffectiveMemory = runtime.totalMemory() - runtime.freeMemory();
            System.out.println("Time taken by the Client method: " + clientTime + " Seconds");
            System.out.println("Memory used by the Client method: " + theEffectiveMemory + " bytes");
            add(new H2("Time taken by the Client method: " + clientTime + " Seconds"));
            add(new H2("Memory used by the Client method: " + theEffectiveMemory + " bytes"));
            // Executing the second method and measuring its memory usage

            Method method2 = HomePage.class.getDeclaredMethod("sort", int[].class);
            Runtime runTimeMyFunc = Runtime.getRuntime();
            Double theEffectiveTime = runtenMethodMultipleTimes(method2, inputsArray, 10000000);
            System.out.println("effective time:" + theEffectiveTime);
            runTimeMyFunc.gc();
            long memoryClient = runTimeMyFunc.totalMemory() - runTimeMyFunc.freeMemory();
            System.out.println("Time taken by the My method: " + theEffectiveTime + " Seconds");
            System.out.println("Memory used by the memoryClient method: " + memoryClient + " bytes");
            add(new H2("Time taken by the effective method: " + theEffectiveTime + " Seconds"));
            add(new H2("Memory used by the memoryClient method: " + memoryClient + " bytes"));
            // Compare time and memory efficiency between the methods
            int timeScore = 0;
            int memoryScore = 0;

            // Calculate the combined score based on time and memory efficiency
            long timeDifference = 0;
            double memoryDifference = 0;

            int testPassed = totalTests + errorCount;

            // Check time efficiency
            // Assuming theEffectiveTime and clientTime are variables representing time in
            // some unit
            if (theEffectiveTime >= clientTime - 0.5) {
                // The user's function is considered highly effective if the effective time is
                // recent
                timeScore = 100;
            } else {
                timeDifference = (long) (clientTime - theEffectiveTime);

                if (timeDifference > 1000) {
                    // If the time difference is significant
                    timeScore = 10;
                } else {
                    timeScore = (int) ((1000 - timeDifference) * 90 / 1000.0);
                }
            }

            if (theEffectiveMemory >= memoryClient || theEffectiveMemory >= memoryClient - 200) {
                memoryScore = 100;
            } else {
                memoryDifference = theEffectiveMemory - memoryClient;

                if (memoryDifference > 0) {
                    memoryScore = (int) ((1000 - memoryDifference) * 90 / 1000.0);
                } else {
                    memoryScore = (int) ((memoryDifference / 100.0) * -1);
                }

            }

            int[] arr = {1,22,3,4,5,6,7};
           
            Object resultForFuncs;
            resultForFuncs = method.invoke(null, arr);
            Class<?> runPitaronClass = method.getDeclaringClass(); // Use RunPitaron.class instead of method.getClass()
            Field arrField = runPitaronClass.getDeclaredField("arr");
            arrField.setAccessible(true);
            int[] arrFuncSteps = (int[]) (arrField.get(null));
            int stepsScore=0;

            int efectiveFuncSteps = sortReturnSteps(arr);
            int stepsForClient=0;
            if(arrFuncSteps.length !=0 )
            {
             stepsForClient = findMaxNumber(arrFuncSteps);
            
            System.out.println("effective func Steps:" + efectiveFuncSteps);
            System.out.println("steps for client:" + stepsForClient);
            // Check steps efficiency
            int baseScore = stepsForClient < 100000 ? 10 : 10000;
            int threshold = stepsForClient - arr.length * 2;
            if (efectiveFuncSteps >= stepsForClient || efectiveFuncSteps >= threshold) {
                stepsScore = 100;
            } else {
                stepsScore = Math.min((stepsForClient - efectiveFuncSteps) / baseScore, 0);
            }
        }else
        {
            stepsForClient = 0;
        }
            // Get a score from chatGPT
          String resultString;
          int chatGptScore=0;
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
    chatGptScore=0;
}

// Continue with the rest of your code

          

            // Calculate the combined score
         
            double timeWeighted = timeScore * 0.1;
            double memoryWeighted = memoryScore * 0.2;
            double successRateWeighted = successRate * 0.3;
            double stepsScoreWeighted = stepsScore * 0.3;
            double chatGptScoreWeighted = chatGptScore * 0.1;

            System.out.println("timeScore after multiplication by 0.1: " + timeWeighted);
            System.out.println("memoryScore after multiplication by 0.2: " + memoryWeighted);
            System.out.println("successRate after multiplication by 0.3: " + successRateWeighted);
            System.out.println("stepsScore after multiplication by 0.3: " + stepsScoreWeighted);
            System.out.println("chatGptScore after multiplication by 0.1: " + chatGptScoreWeighted);

            double combinedScore = ((timeScore * 0.1) + (memoryScore * 0.1) + (successRate * 0.4) + (stepsScore * 0.3)
                    + (chatGptScore * 0.1)) / 10;
                    if(testPassed<5)
                    {
                        combinedScore=3;
                    }
            // Display the grade
            System.out.println("The grade of your method is: " + String.format("%.1f", combinedScore));

            // Add the grade to the UI
            if (combinedScore > 0) {
                add(new H2("The grade of your method is: " + String.format("%.1f", combinedScore)));
            } else if (testPassed > 5) {
                add(new H2("The grade of your method is: 4"));
            } 
        } else {
            add(new H1("Compilation result: failed"));
            System.out.println("Compilation failed. Cannot execute the method.");
            System.out.println("the grade is:1");
        }
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

    public static double runtenMethodMultipleTimes(Method method, Object[] inputsArray, int numIterations) {
        long startTime = System.nanoTime();
        long endTime;

        for (int i = 0; i < numIterations; i++) {
            try {
                for (int j = 0; j < inputsArray.length; j++) {
                    method.invoke(null, inputsArray[j]); // הפעולה שאתה רוצה לבדוק
                }
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        endTime = System.nanoTime();

        double time = (endTime - startTime) / 1e9; // חישוב הזמן בשניות
        return time;
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
        if (array.length == 0) {
            return Integer.MIN_VALUE;
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

    public static int sortReturnSteps(int[] arr) {
        arrForFunc = new int[2];
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
        while (forIndex != -1)
         {
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
}
