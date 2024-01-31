package eliyaa.projeliya;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.html.Pre;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.router.Route;

@Route("/")
public class HomePage extends VerticalLayout {
    private TextArea answerTextArea = new TextArea("Your Answer");
    private Button submitButton = new Button("Submit Answer");
    private Paragraph answerParagraph = new Paragraph();
    private int index = 0;
    private HomeService service;
    int counter = 0;

    public HomePage(HomeService service) throws UnknownHostException {
        this.service = service;

        // Set the height and width of the TextArea
        answerTextArea.setHeight("300px"); // Adjust the height as needed
        answerTextArea.setWidth("500px"); // Adjust the width as needed

        InetAddress localhost = InetAddress.getLocalHost();
        answerTextArea.setValue(
                "public static int[] SelectionSort(int[] array)\n {" + "\n\n\n\n\n\n\n\n\n" + " return array; \n}");

        // Print the IP address and port
        System.out.println("IP Address: " + localhost.getHostAddress() + " Port: 8080");

        // If you want to add it to your HTML content
        add(new H1("Localhost IP Address: " + localhost.getHostAddress() + " Port: 8080"));

        // Print the IP address and port
        answerTextArea.setMinHeight("150px");
        answerTextArea.setMinWidth("300px");

        submitButton.addClickListener(event -> {
            answerParagraph.setText("Submitted answer: " + answerTextArea.getValue());
            try {
                counter++;
                if (counter == 3) {
                    answerTextArea.setValue("public static int[] SelectionSort(int[] array)\n {" + "\n\n\n\n\n\n\n\n\n"
                            + " return array; \n}");
                }
                double grade = service.checkAnswer(answerTextArea.getValue());

                System.out.println(service.getCompile());
                if (service.getCompile()) {
                    String formattedGrade = String.format("%.1f", grade);
                    double roundedGrade = Double.parseDouble(formattedGrade);

                    if (roundedGrade > 10) {
                        roundedGrade = 10;
                    }

                    H1 respond = new H1("Response of your code: " + service.getCompile() + " (Compilation)");
                    respond.getStyle().set("white-space", "pre-line");
                    add(respond);
                    add(new H2("Final function:"));
                    Div codeBlockDiv = new Div(new Pre(answerTextArea.getValue()));
                    codeBlockDiv.getStyle().set("font-family", "monospace");
                    codeBlockDiv.getStyle().set("background-color", "#f2f2f2");
                    codeBlockDiv.getStyle().set("padding", "10px");
                    codeBlockDiv.getStyle().set("border-radius", "5px");
                    codeBlockDiv.getStyle().set("overflow-x", "auto");
                    codeBlockDiv.setWidth("100%"); // Adjust the width as needed

                    add(codeBlockDiv);
                    add(new H1("The grade for your function is: " + roundedGrade ));
                    add(new H1("Details of the grade: " ));
                    add(new OutputField("Success Rate of Your Function:", service.getSuccessRate(), "%"));
                    add(new OutputField("Memory Score after multiplication by 0.1:", service.getMemoryWeighted(), "%"));
                    add(new OutputField("ChatGPT Score after multiplication by 0.1:", service.getChatGptScoreWeighted(),
                            "%"));
                    add(new OutputField("Success Rate after multiplication by 0.3:", service.getSuccessRateWeighted(),
                            "%"));
                    add(new OutputField("Steps Score after multiplication by 0.4:", service.getStepsScoreWeighted(),
                            "%"));
                    add(new OutputField("Time Score after multiplication by 0.1:", service.getTimeWeighted(), "%"));
                } else 
                {
                    counter++;
                    H1 respond = new H1("Response of your code: " + service.getCompile() + " (Compilation)");
                    String explainErrors = "Errors in your code: \n" + service.getCompilationErrors() +
                            "\nFix it and try again :)";
                    Div codeBlockDiv = new Div(new Pre(explainErrors));
                    codeBlockDiv.getStyle().set("font-family", "monospace");
                    codeBlockDiv.getStyle().set("background-color", "#f2f2f2");
                    codeBlockDiv.getStyle().set("padding", "10px");
                    codeBlockDiv.getStyle().set("border-radius", "5px");
                    codeBlockDiv.getStyle().set("overflow-x", "auto");
                    codeBlockDiv.setWidth("100%"); // Adjust the width as needed
                    add(respond, codeBlockDiv);
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

    private static class CodeBlock extends Pre {
        public CodeBlock(String code, String language) {
            setText(code);
            getElement().getStyle().set("font-family", "monospace");
            getElement().getStyle().set("background-color", "#f2f2f2");
            getElement().getStyle().set("color", "#333333");
            getElement().getStyle().set("padding", "10px");
            getElement().getStyle().set("border-radius", "5px");
            getElement().getStyle().set("overflow-x", "auto");
        }
    }

    private static class OutputField extends VerticalLayout {
        public OutputField(String label, double value, String unit) {
            add(new H2(label));
            Paragraph valueParagraph = new Paragraph(String.format("%.1f", value) + unit);
            add(valueParagraph);
            valueParagraph.getStyle().set("color", "#007bff");
            valueParagraph.getStyle().set("font-size", "30px"); // Adjust the font size as needed
            valueParagraph.getStyle().setFont("Arial bold 10px");
        }
    }

}
