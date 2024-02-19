package eliyaa.projeliya;

import com.mongodb.internal.logging.StructuredLogMessage.Component;
import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.html.Pre;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.progressbar.ProgressBar;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

@Route("/")
public class HomePage extends VerticalLayout {
    private Div codeBlockDiv;
    private int counter = 0;
    List<String> efficiencyStepsScores = new ArrayList<>();
    List<String> memoryScores = new ArrayList<>();
    List<String> validationFuncsScores = new ArrayList<>();
    List<String> chatGptScores = new ArrayList<>();
    private Double roundedGrade;
    private TextArea answerTextArea;
    private Button submitButton;
    private Paragraph answerParagraph;
    private HomeService service;
    private HorizontalLayout horizontalLayout;
    private HorizontalLayout horizontalLayoutButton;
    private double grade;
    private Grid<gridClass> grid;
    private List<String> complationList;
    private Pre code;
    private TextField text;
    private H2 respond;
    private ProgressBar progressBar;
    private Notification notification;
    private VerticalLayout centerLayout;

    public HomePage(HomeService service) throws UnknownHostException {
        
        progressBar = new ProgressBar();
        progressBar.setIndeterminate(true); // Set it to indeterminate to show the loading cycle
        progressBar = new ProgressBar();
        progressBar.setIndeterminate(true); // Set it to indeterminate to show the loading cycle

        // Create a layout to center the ProgressBar
        centerLayout = new VerticalLayout();
        centerLayout.setSizeFull(); // Make the layout take up the full available space
        centerLayout.setJustifyContentMode(JustifyContentMode.CENTER); // Center its content vertically
        centerLayout.setAlignItems(Alignment.CENTER); // Center its content horizontally

        notification = new Notification("", 3000); // Notification will disappear after 3 seconds

        respond = new H2();

        // Adjust the width as needed
        codeBlockDiv = new Div();
        horizontalLayoutButton = new HorizontalLayout();
        Pre code = new Pre();
        this.service = service;
        text = new TextField();
        text.setLabel("enter your n - Chack-yourCode!");
        answerTextArea = new TextArea("Your Answer");
        submitButton = new Button("Submit Answer");
        grade = 0;
        answerParagraph = new Paragraph();
        horizontalLayout = new HorizontalLayout();
        grid = new Grid<>(gridClass.class, false);
        List<gridClass> gridItems = new ArrayList<>();
        List<String> complationList = new ArrayList<>();
        List<String> timeScore = new ArrayList<>();
        horizontalLayoutButton.add(text, submitButton);
        grid.addColumn(gridClass::getCompilation).setHeader("Compilation Result");
        grid.addColumn(gridClass::getCounter).setHeader("test solution");
        grid.addColumn(gridClass::getTimeScore).setHeader("Time Score");
        grid.addColumn(gridClass::getEfficiencyStepsScore).setHeader("Efficiency Steps");
        grid.addColumn(gridClass::getMemoryScore).setHeader("Memory");
        grid.addColumn(gridClass::getValidationFuncsScore).setHeader("Validation Funcs");
        grid.addColumn(gridClass::getChatGptScore).setHeader("Chat GPT");
        grid.addColumn(gridClass::getFinalScore).setHeader("Final Score");
        codeBlockDiv.getStyle().set("font-family", "monospace");
        codeBlockDiv.getStyle().set("background-color", "#f2f2f2");
        codeBlockDiv.getStyle().set("padding", "10px");
        codeBlockDiv.getStyle().set("border-radius", "5px");
        codeBlockDiv.getStyle().set("overflow-x", "auto");
        codeBlockDiv.setWidth("100%");
        code.setSizeFull();
        codeBlockDiv.setSizeFull();
        codeBlockDiv.setHeightFull();
        initializeLayout();
        add(respond);
        horizontalLayout.add(answerTextArea);
        submitButton.addClickListener(event -> {
            add(centerLayout);

            try {

                counter++;
                if (!text.getValue().isEmpty()) {
                    Boolean result = service.runNumOfChackArr(text.getValue(), answerTextArea.getValue());
                    System.out.println("result is " + result);
                    System.out.println("compile is " + service.getCompile());

                    if (result == true && service.getCompile()) {

                        notification.show("compilation = true,n="+ text.getValue() + " your code is good!");  
                        respond.setText("your code (work):");
                        codeBlockDiv.setSizeFull();
                        codeBlockDiv.setHeightFull();
                        codeBlockDiv.setText(answerTextArea.getValue());
                    } else if ((result == false) && service.getCompile() == true) {
                        notification.show("compilation = true,n="+ text.getValue() + " fix your code!");  
                        respond.setText("your code(does not work):");
                        codeBlockDiv.setSizeFull();
                        codeBlockDiv.setHeightFull();
                        codeBlockDiv.setWidthFull();
                        codeBlockDiv.setText(answerTextArea.getValue());
                    } else if (service.getCompile() == false) {
                        notification.show("compilation = false,n="+ text.getValue() + " fix your code!");  
                        respond.setText("your errors");
                       codeBlockDiv.setSizeFull();
                        codeBlockDiv.setHeightFull();
                        codeBlockDiv.setWidthFull();
                        handleCompilationErrors();
                    }
                    return;
                } else {
                    String NameOfFun= "SelectionSort";
                    grade = service.checkAnswer(answerTextArea.getValue(),NameOfFun);
                    finishThetask();
                }
                if (service.getCompile() && text.isEmpty()) {
                    if (codeBlockDiv != null) {
                        codeBlockDiv.removeAll();
                    }
                    String formattedGrade = String.format("%.1f", grade);
                    roundedGrade = Double.parseDouble(formattedGrade);

                    if (roundedGrade > 10) {
                        roundedGrade = 10.0;
                    }
                    memoryScores.clear();
                    chatGptScores.clear();
                    validationFuncsScores.clear();
                    efficiencyStepsScores.clear();
                    complationList.clear();
                    complationList.add(String.valueOf(service.getCompile()));
                    memoryScores.add(service.getMemoryWeighted() + "");
                    efficiencyStepsScores.add(service.getStepsScoreWeighted() + "");
                    validationFuncsScores.add("" + service.getSuccessRateWeighted());
                    chatGptScores.add("" + service.getChatGptScoreWeighted());
                    timeScore.add("" + service.getTimeWeighted());
                    // Make sure the lists have the same size before creating gridClass instances
                    int size = efficiencyStepsScores.size();
                    // Clear the list before adding new items
                    // Add new items to the list
                    for (int i = 0; i < size; i++) {
                        gridItems.add(new gridClass(
                                counter,
                                complationList.get(i),
                                timeScore.get(i),
                                efficiencyStepsScores.get(i),
                                memoryScores.get(i),
                                validationFuncsScores.get(i),
                                chatGptScores.get(i),
                                roundedGrade + ""));
                    }
                        respond.setText("Your Code");
                    

                    System.out.println(gridItems.size()); // Verify the size before setting items
                    grid.setItems(gridItems);
                    code.setSizeFull();
                    code.setHeightFull();
                    code.setText(answerTextArea.getValue());

                } else {
                    respond.setText("your errors");
                    gridItems.add(new gridClass(
                            counter,
                            "false",
                            0 + "",
                            0 + "",
                            0 + "",
                            0 + "",
                            0 + "",
                            0 + ""));
                    grid.setItems(gridItems);
                    handleCompilationErrors();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        horizontalLayout.add(grid);
        horizontalLayoutButton.setAlignItems(Alignment.START);
        add(horizontalLayout);
        add(horizontalLayoutButton);
        codeBlockDiv.add(code);
        add(respond);
        add(codeBlockDiv);
        add(answerParagraph);

    }

    private void finishThetask() {
        UI.getCurrent().access(() -> {
            // Remove the ProgressBar when the task is finished
            remove(centerLayout);

            // Display the notification
            notification.setText("Task completed successfully!");
            notification.open();
        });

    }

    private Dialog createLoadingDialog() {
        Dialog loadingDialog = new Dialog();
        ProgressBar progressBar = new ProgressBar();
        progressBar.setIndeterminate(true);
        loadingDialog.add(progressBar);
        return loadingDialog;
    }

    private void initializeLayout() throws UnknownHostException 
    {
        setSizeFull();
        horizontalLayout.setSizeFull();
        horizontalLayoutButton.setSizeFull();
        answerTextArea.setHeight("300px"); // Adjust the height as needed
        answerTextArea.setWidth("500px");
        grid.setSizeFull();

        answerTextArea.setValue(
                "public static int[] SelectionSort(int[] array)\n {" + "\n\n\n\n\n\n\n\n\n" + " return array; \n}");
        String question = " בנה פונקציה שמקבלת מערך וממיינת אותו בסדר עולה ומחזירה את המערך הממויין - בהצלחה-דוגמה לא ממויין(11,1,4,3,5,6)";
        add(new H1("שאלה: " + question + "וממויין שאתה צריך להחזיר(1,3,4,5,6,11) "));
    }

    private void handleCompilationErrors() {
        codeBlockDiv.removeAll();
        respond.setText("see your Errors in the code");
        String explainErrors = "Errors in your code: \n" + service.getCompilationErrors() +
                "\nFix it and try again :)";
        Pre textErrors = new Pre();
        textErrors.setSizeFull();
        textErrors.setText(explainErrors);
        codeBlockDiv.add(textErrors);
        codeBlockDiv.setSizeFull();
        codeBlockDiv.setHeightFull();

    }
}
