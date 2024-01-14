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
import static org.junit.Assert.assertArrayEquals;
import java.io.*;
import java.lang.reflect.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Route("")
public class HomePage extends VerticalLayout {

    private TextArea answerTextArea = new TextArea("Your Answer");
    private Button submitButton = new Button("Submit Answer");
    private Paragraph answerParagraph = new Paragraph();

    public HomePage() {
        submitButton.addClickListener(event -> {
            answerParagraph.setText("Submitted answer: " + answerTextArea.getValue());
            try {
                checkAnswer(answerTextArea.getValue());
            } catch (ClassNotFoundException | NoSuchMethodException | SecurityException | IllegalAccessException
                    | IllegalArgumentException | InvocationTargetException | IOException e) {
                e.printStackTrace();
            }
        });

        add(answerTextArea, submitButton, answerParagraph);
    }

    public void checkAnswer(String methodcode2) throws IOException, ClassNotFoundException, NoSuchMethodException,
            SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {

        // String methodcode2 =
        // "public static int[] sortArray(int[] arr) {\n" +
        // " int len = arr.length;\n" +
        // "\n" +
        // " for (int i = 0; i < len - 1; i++) {\n" +
        // " for (int j = 0; j < len - i - 1; j++) {\n" +
        // " if (arr[j] > arr[j + 1]) {\n" +
        // " int temp = arr[j];\n" +
        // " arr[j] = arr[j + 1];\n" +
        // " arr[j + 1] = temp;\n" +
        // " }\n" +
        // " }\n" +
        // " }\n" +
        // "\n" +
        // " return arr;\n" +
        // "}";
        // String methodCode = "public static int add(int a, int b) { return a + b; }";
        // String cleanMethod1 = cleanCode(methodCode);

        String cleanedMethod2 = cleanCode(methodcode2);

        String packageName = "package eliyaa.projecteliya;";
        String classTitle = "public class RunPitaron {";
        // main(String[] args) { ";
        String pitaronProg = classTitle + cleanedMethod2 + "}";

        // File creation
        Path filePath = Paths.get("src/eliyaa/projecteliya/RunPitaron.java");

        // Create directories if they don't exist
        Files.createDirectories(filePath.getParent());

        // Use Files.newBufferedWriter to write to the file
        try (BufferedWriter writer = Files.newBufferedWriter(filePath, StandardOpenOption.CREATE,
                StandardOpenOption.TRUNCATE_EXISTING)) {
            writer.write(pitaronProg);
        }

        // Create source code file
        String className = "RunPitaron";
        String fileName = className + ".java";
        try (PrintWriter writer = new PrintWriter(fileName)) {
            writer.println("public class " + className + " {");
            writer.println(cleanedMethod2);
            writer.println("}");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        // Compile the source file
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        int compileResult = compiler.run(null, null, null, fileName);
        System.out.println("Compilation result: " + compileResult);

        if (compileResult == 0) 
        {
            add(new H1("Compilation result: Sucssesfull"));

            URLClassLoader classLoader = new URLClassLoader(new URL[] { new File("./").toURI().toURL() });
            Class<?> dynamicClass = Class.forName(className, true, classLoader);
            // Get the method and invoke it for each input
            Method method = dynamicClass.getMethod("SelectionSort", int[].class);

            System.out.println("---------------------------------------------");
            System.out.println("Modifiers: " + Modifier.toString(method.getModifiers()));
            System.out.println("---------------------------------------------");
            System.out.println("---------------------------------------------");
            System.out.println("parmaters count:" + method.getParameterCount());
            System.out.println("---------------------------------------------");
            System.out.println("---------------------------------");
            String pitaron = "public static int[] SelectionSort(int[] arr) {\n" +
                    "    int n = arr.length;\n" +
                    "    int i = 0;\n" +
                    "    int min_idx = 0;\n" +
                    "    int j = 0;\n" +
                    "    int temp = 0;\n" +
                    "    for (i = 0; i < n - 1; i++) {\n" +
                    "        min_idx = i;\n" +
                    "        for (j = i + 1; j < n; j++) {\n" +
                    "            if (arr[j] < arr[min_idx]) {\n" +
                    "                min_idx = j;\n" +
                    "            }\n" +
                    "        }\n" +
                    "        temp = arr[min_idx];\n" +
                    "        arr[min_idx] = arr[i];\n" +
                    "        arr[i] = temp;\n" +
                    "    }\n" +
                    "    return arr;";

            try {
                CompilationUnit cu = StaticJavaParser.parse(pitaronProg);

                int variablesCount = cu.findAll(VariableDeclarator.class).size();
                // int arithmeticExpressionsCount = cu.findAll(BinaryExpr.class).size();
                long arithmeticExpressionsCount = cu.findAll(BinaryExpr.class).stream()
                        .filter(expr -> expr.isBinaryExpr() && isArithmeticOperation(expr.getOperator().asString()))
                        .count();
                int forLoopCount = cu.findAll(ForStmt.class).size();
                int whileLoopCount = cu.findAll(WhileStmt.class).size();

                long incrementCount = cu.findAll(UnaryExpr.class).stream()
                        .filter(expr -> "++".equals(expr.getOperator().asString()))
                        .count();

                long decrementCount = cu.findAll(UnaryExpr.class).stream()
                        .filter(expr -> "--".equals(expr.getOperator().asString()))
                        .count();
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
                    { 7, 5 },
                    { 10, 3 },
                    { 2, 9 },
                    { 3, 1 }, // Edge case: Same numbers
                    { 5, 0 }, // Edge case: Zeroes
                    { 15, 12 }, // Edge case: Larger numbersד
                    { -3, -6 }, // Edge case: Negative numbers
                    { 100, 99 }, // Edge case: Large numbers
                    { 5, -10 } // Edge case: Negative and positive numbers
            };

            int[][] expectedOutputsArray = 
            {
                    { 5, 7 },
                    { 3, 10 },
                    { 2, 9 },
                    { 1, 3 }, // Expected output for same numbers
                    { 0, 5 }, // Expected output for zeroes
                    { 12, 15 }, // Expected output for larger numbers
                    { -6, -3 }, // Expected output for negative numbers
                    { 99, 100 }, // Expected output for large numbers
                    { -10, 5 } // Expected output for negative and positive numbers
            };
            Object result = null;
            Type[] parameters = method.getGenericParameterTypes();
            System.out.println("Number of parameters: " + parameters.length);
            System.out.println("Parameter type: " + parameters[0].getTypeName());
            int counter =  inputsArray.length;
            int totalTests = inputsArray.length;
            int errorCount = 0;

            for (int i = 0; i < totalTests; i++) 
            {
                result = method.invoke(null,inputsArray[i]);
                int[] resultArray = (int[]) result;

                System.out.println("Test Case " + (i + 1) + ":");
                System.out.println("Input Array: " + Arrays.toString(inputsArray[i]));
                System.out.println("Expected Output Array: " + Arrays.toString(expectedOutputsArray[i]));
                System.out.println("Actual Output Array: " + Arrays.toString(resultArray));

                for (int v = 0; v < resultArray.length; v++) 
                {
                    if (resultArray[v] != expectedOutputsArray[i][v]) 
                    {
                        errorCount--;
                        System.out.println(resultArray[v]+"good arr:"+expectedOutputsArray[i][v]);
                    }
                }

                System.out.println();
            }

            System.out.println("Total errors: " + errorCount);
            double successRate = ((double) (totalTests + errorCount) / totalTests) * 100;
            counter = counter + errorCount;
            System.out.println("Success rate: " + successRate + "%" + counter + "/" + totalTests);
            String rate = "Success rate: " + successRate + "%" + counter + "/" + totalTests;
            add(new H1(rate));

            Runtime runtime = Runtime.getRuntime();
            // Measuring memory usage for the first method
            Double clientTime = runtenMethodMultipleTimes(method, inputsArray, 10000000);
            runtime.gc(); // Explicitly calling garbage collector to clean up memory
            long memory = runtime.totalMemory() - runtime.freeMemory();
            System.out.println("Time taken by the Client method: " + clientTime + " Seconds");
            System.out.println("Memory used by the Client method: " + memory + " bytes");
            add(new H2("Time taken by the Client method: " + clientTime + " Seconds"));
            add(new H2("Memory used by the Client method: " + memory + " bytes"));
            // Executing the second method and measuring its memory usage
            Method method2 = HomePage.class.getDeclaredMethod("sort", int[].class);
            Runtime runTimeMyFunc = Runtime.getRuntime();
            Double theEffectiveTime = runtenMethodMultipleTimes(method2, inputsArray, 10000000);
            System.out.println("effective time:" + theEffectiveTime);
            runTimeMyFunc.gc();
            long memory2 = runTimeMyFunc.totalMemory() - runTimeMyFunc.freeMemory();
            System.out.println("Time taken by the My method: " + theEffectiveTime + " Seconds");
            System.out.println("Memory used by the My method: " + memory2 + " bytes");
            add(new H2("Time taken by the effective method: " + theEffectiveTime + " Seconds"));
            add(new H2("Memory used by the My method: " + memory2 + " bytes"));

            // Compare time and memory efficiency between the methods
            double timeScore = (theEffectiveTime < clientTime) ? 10 : 10 * (clientTime / theEffectiveTime);
            double memoryScore = (memory2 < memory) ? 10 : 10 * (memory / memory2);

            // Calculate the combined score based on time and memory efficiency
            double combinedScore = (timeScore + memoryScore) / 2;

            // Display the grade
            System.out.println("The grade of your method is: " + String.format("%.1f", combinedScore));
            add(new H2("The grade of your method is: " + String.format("%.1f", combinedScore)));

        } else {
            add(new H1("Compilation result: failed"));
            System.out.println("Compilation failed. Cannot execute the method.");
            System.out.println("the grade is:2");
        }
    }

    private static String truncateMessage(String message, int maxLength) 
    {
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
                        return responseReturn;
                    } else {
                        System.out.println("Error: 'choices' array is empty.");
                    }
                }
            } else {
                System.out.println("Error: " + responseCode);
            }

        } catch (IOException e) 
        {
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

    private static boolean isArithmeticOperation(String operator) {
        return operator.equals("+") || operator.equals("-") || operator.equals("*") || operator.equals("/");
    }
}
