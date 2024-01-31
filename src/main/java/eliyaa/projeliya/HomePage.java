package eliyaa.projeliya;

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
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Stack;

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
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.router.Route;

@Route("/")
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
    private HomeService service;

    public HomePage(HomeService service) throws UnknownHostException {
        this.service = service;
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

            
                double grade = service.checkAnswer(answerTextArea.getValue());
                String formattedGrade = String.format("%.1f", grade);
                double roundedGrade = Double.parseDouble(formattedGrade);
                if(roundedGrade > 10)
                {
                    roundedGrade =10;
                }
                                if(service.getCompile() ==true){
               H1 respond =new H1("responed of your code:\t"+service.getCompile()+"\t"+"compilation");
               respond.getStyle().set("white-space", "pre-line");
                add(respond);
                add(new H2("final func:"+service.getCodeFronted()));
                add(new H2("succses Rate Of your Func:"+service.getSuccessRate()+"%"));
                add(new H2("memoryScore after multiplication by 0.1:"+service.getMemoryWeighted()));
                add(new H2("chatGptScore after multiplication by 0.1:"+service.getChatGptScoreWeighted()));
                add(new H2("successRate after multiplication by 0.3: "+service.getSuccessRateWeighted()));
                add(new H2("stepsScore after multiplication by 0.4: "+service.getStepsScoreWeighted()));
                add(new H2("timeScore after multiplication by 0.1: "+service.getTimeWeighted()));
                add(new H1("the grade for your func is :"+roundedGrade));
                }else
                { 
                    String errors ="responed of your code:\n "+false+" compilation\n";
                    String explainErrors = "errors of your code: \n " + service.getCompilationErrors()+"\n fix it and try again :)";
                    H1 err_h1 = new H1(errors);
                    H1 errExplained_h1 = new H1(explainErrors);
                    err_h1.getStyle().set("white-space", "pre-line");
                    errExplained_h1.getStyle().set("white-space", "pre-line");

                    add(err_h1);
                    add(errExplained_h1);

                }
            } catch (ClassNotFoundException | NoSuchMethodException | SecurityException | IllegalAccessException
                    | IllegalArgumentException | InvocationTargetException | IOException e) {
                e.printStackTrace();
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
        });

        add(answerTextArea, submitButton, answerParagraph);
    }

}
