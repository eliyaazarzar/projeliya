package projeliya.ver2.projeliya;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.H6;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.Notification.Position;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.progressbar.ProgressBar;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;

import java.net.UnknownHostException;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.sound.sampled.Line;
import javax.swing.text.html.HTML;

import org.vaadin.pekkam.Canvas;

@Route(value = "/ChackYourCode", layout = AppMainLayout.class)
@PageTitle("ChackYourCode")
public class CheckYourCodePage extends VerticalLayout {
    private int counter = 0;
    private H1 questionTitle;
    List<String> efficiencyStepsScores = new ArrayList<>();
    List<String> memoryScores = new ArrayList<>();
    List<String> validationFuncsScores = new ArrayList<>();
    List<String> chatGptScores = new ArrayList<>();
    private int index = -1;
    private Double roundedGrade;
    private final String checkString = "✅";
    private final String exString = "❌";
    private TextArea answerTextArea;
    private H3 textForFronted;
    private Button submitButton;
    private Paragraph answerParagraph;
    private ServiceLogic service;
    private HorizontalLayout horizontalLayout;
    private HorizontalLayout horizontalLayoutButton;
    private Button showChart;
    private double grade;
    private Grid<gridClass> grid;
    Dialog dialogForIndcate;
    private H2 respond;
    private Notification notification;
    private VerticalLayout centerLayout;
    private Select<String> selectQuestion;
    private Button addSoultion;
    private QuestionService questionService;
    private String codeOfTheClient;
    private List<Solution> listOfSoulotins;
    private Paragraph textTest;
    private Button hideExplaintion;
    private HorizontalLayout topBar;
    ProgressBar progressBar;
    private SolutionService solutionService;
    public Dialog dialogForIndicate;
    private List<Integer> uploadList;
    private ErrorsReport report;
    private Dialog chartDialog;
    private Canvas canvas;

    public CheckYourCodePage(SolutionService solutionService, ServiceLogic service, QuestionService questionService)
            throws UnknownHostException {
                 canvas = new Canvas(10,10);
                canvas.setVisible(false);
                canvas.setId("myChart");
                canvas.getStyle().set("right", "50px"); // Adjust as needed
                canvas.getStyle().set("top", "50px"); // Adjust as needed
                chartDialog= new Dialog();

                 this.solutionService = solutionService;
        uploadList = new ArrayList<Integer>();
        questionTitle = new H1("בחר שאלה שאתה רוצה לפתור");
        questionTitle.getStyle().set("align-self", "flex-end");
        hideExplaintion = new Button("Hide Explanations");
        hideExplaintion.setEnabled(false);
        textTest = new Paragraph("");
        textForFronted = new H3("");
        listOfSoulotins = new ArrayList<>();
        this.questionService = questionService;
        addSoultion = new Button("Uplaod My Soultion");
        addSoultion.setEnabled(false);
        showChart = new Button("SoultionChart");
        showChart.setEnabled(false);
        showChart.addClickListener(event -> {
            Solution solution = listOfSoulotins.get(index);
            if(solution.getReport().getArrayOfPoints() != null)
            {
            showChart(solution.getReport().getArrayOfPoints());
            }
        });
        String user = (String) VaadinSession.getCurrent().getAttribute("user");
        System.out.println(user);
        if (user == null) {
            System.out.println("-------- User NOT Authorized - can't use chat! --------");
            UI.getCurrent().getPage().setLocation("/"); // Redirect to login page (HomePage).
            return;
        }
        addSoultion.addClickListener(event -> {
            Solution solution = listOfSoulotins.get(index);
            System.out.println("index add to the list of uplload" + index);
            uploadList.add(index);
            openUploadAnswerDialog(solution);
            addSoultion.setEnabled(false);
        });

        hideExplaintion.addClickListener(event -> {
            clearAll();
        });

        // if (user == null) {
        // UI.getCurrent().navigate("/"); // Redirect to home page if user is not logged
        // in
        // return;
        // }
        dialogForIndcate = new Dialog();
        progressBar = new ProgressBar();
        progressBar.setIndeterminate(true);
        chartDialog.add(canvas);
        topBar = new HorizontalLayout();
        topBar.setWidthFull(); // Set the width to full to occupy the entire width of the page
        topBar.setJustifyContentMode(FlexComponent.JustifyContentMode.END); // Align components to the end (right)
        selectQuestion = new Select<>();
        selectQuestion.setLabel("Select Question");
        selectQuestion.setItems("SortArray", "removeDuplicates", "3", "4", "5");
        selectQuestion.setPlaceholder("Select Question");

        topBar.add(selectQuestion); // Add selectQuestion to the top bar
        add(topBar); // Add topBar to the layout
        // Adjust the spacing between the components to push selectQuestion to the right
        topBar.setSpacing(true);

        topBar.add(selectQuestion); // Add selectQuestion to the top bar
        add(topBar); // Add the top bar to the layout

        centerLayout = new VerticalLayout();
        centerLayout.setSizeFull(); // Make the layout take up the full available space
        centerLayout.setJustifyContentMode(JustifyContentMode.CENTER); // Center its content vertically
        centerLayout.setAlignItems(Alignment.CENTER); // Center its content horizontally
        respond = new H2();
        // Adjust the width as needed
        horizontalLayoutButton = new HorizontalLayout();
        this.service = service;
        answerTextArea = new TextArea("Your Soultion");
        submitButton = new Button("Submit Soultion");
        grade = 0;
        horizontalLayout = new HorizontalLayout();
        grid = new Grid<>(gridClass.class, false);
        List<gridClass> gridItems = new ArrayList<>();
        List<String> complationList = new ArrayList<>();
        List<String> timeScore = new ArrayList<>();
        grid.setWidth("1350px"); // להגדיר רוחב הגריד ל-400 פיקסלים

        horizontalLayoutButton.add( submitButton, hideExplaintion, addSoultion,showChart);
        grid.addColumn(gridClass::getCounter).setHeader("");
        grid.addColumn(gridClass::getCompilation).setHeader("Compilation");
        grid.addColumn(gridClass::getValidationFuncsScore).setHeader("Validation Funcs");
        grid.addColumn(gridClass::getEfficiencyStepsScore).setHeader("Efficiency Steps");
        grid.addColumn(gridClass::getTimeScore).setHeader("Time Score");
        grid.addColumn(gridClass::getMemoryScore).setHeader("Memory");
        grid.addColumn(gridClass::getChatGptScore).setHeader("Chat GPT");
        grid.addColumn(gridClass::getFinalScore).setHeader("Final Score");
        selectQuestion.addValueChangeListener(event -> {
            String selectedValue = event.getValue(); // Get the newly selected value
            try {
                initializeLayout(selectedValue);
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
            System.out.println("Selected question: " + selectedValue);
        });
        add(questionTitle);
        add(respond);

        horizontalLayout.add(answerTextArea);
        // לחיצת על הכפתור
        submitButton.addClickListener(event -> {
            add(centerLayout);
            try {
                Boolean result = null;
                if(selectQuestion.isEmpty() == false)
                    {
                    Question question = questionService.findById(selectQuestion.getValue());
                    String NameOfFun = question.getNameOfFunc();
                    String selectedQuestion = selectQuestion.getValue();
                    codeOfTheClient = answerTextArea.getValue();
                    counter++;
                    grade = service.checkAnswer(codeOfTheClient, NameOfFun, selectedQuestion);
                }       
                if(grade == -1)
                {
                    return;
                }        
                Notification.show("Your Solution got Grade!", 2000, Notification.Position.TOP_CENTER);

                if (service.getCompile() && selectQuestion.isEmpty()==false) {
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
                    memoryScores.add(service.getMemoryWeighted() * 0.1 + "");
                    efficiencyStepsScores.add(service.getStepsScoreWeighted() * 0.4 + "");
                    validationFuncsScores.add("" + service.getSuccessRateWeighted() * 0.3);
                    chatGptScores.add("" + service.getChatGptScoreWeighted() * 0.1);
                    timeScore.add("" + service.getTimeWeighted() * 0.1);
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

                    System.out.println(gridItems.size()); // Verify the size before setting items
                    grid.setItems(gridItems);
                    Grade grade = new Grade(service.getTimeWeighted(), service.getStepsScoreWeighted(),
                            service.getMemoryWeighted(), service.getChatGptScoreWeighted(),
                            service.getSuccessRateWeighted(), roundedGrade);
                    String nameOfQuestion = selectQuestion.getValue();
                    System.out.println(nameOfQuestion);
                    report = service.getErrorsReport();
                    Date currentDate = new Date(size); // This will set the current date and time
                    String errorsAsString = String.join(", ", service.getCompilationErrors().toString()); // Convert
                    Boolean success = service.getCompile();
                    Solution solution = new Solution(listOfSoulotins.size(), user, nameOfQuestion,
                            answerTextArea.getValue(), currentDate, grade, report, errorsAsString,
                            success, grade.getGrade());
                    listOfSoulotins.add(solution);

                } else {
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
                    roundedGrade = 0.0;
                    Grade grade = new Grade(service.getTimeWeighted(), service.getStepsScoreWeighted(),
                            service.getMemoryWeighted(), service.getChatGptScoreWeighted(),
                            service.getSuccessRateWeighted(), roundedGrade);
                    Boolean success = service.getCompile();
                    String nameOfQuestion = selectQuestion.getValue();

                    Solution solution = new Solution(listOfSoulotins.size(), user, answerTextArea.getValue(),
                            nameOfQuestion, new Date(1), grade,
                            service.getCompilationErrors().toString() + "", success);

                    listOfSoulotins.add(solution);
             
               
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        horizontalLayout.add(grid);
        horizontalLayoutButton.setAlignItems(Alignment.START);
        add(horizontalLayout);
        add(horizontalLayoutButton);
        add(textTest);
        grid.asSingleSelect().addValueChangeListener(event -> {
            gridClass item = event.getValue();
            if (item != null) {
                Solution soultion = listOfSoulotins.get(item.getCounter() - 1);
                answerTextArea.setValue(soultion.getAnswer());
                showSoultionDetails((item.getCounter() - 1)); // Use item directly to get the counter value
                for (int i = 0; i < uploadList.size(); i++) {
                    if (item.getCounter() - 1 == uploadList.get(i)) {
                        System.out.println("here is upload");
                        addSoultion.setEnabled(false);
                        break;
                    }
                }
                if(soultion.getReport().getArrayOfPoints()!=null)
                {
            showChart.setEnabled(true);
                }else{
                    showChart.setEnabled(false);

                }
                  
        }

        });

    }

    private void openUploadAnswerDialog(Solution solution) {
        System.out.println("go func upload");
        if (selectQuestion.getValue() == null) {
            System.out.println("null1");
        }
        if (solution == null) {
            System.out.println("null2");
        }
        solution.setAnswerDate(new java.util.Date());
        solutionService.addSolution(solution);
        Notification.show("the Answer is added");

    }

    private void showSoultionDetails(int indexForList) {

        Solution solution = listOfSoulotins.get(indexForList);
        if (solution.getGrade().getGrade() > 6 && solution.getCompile()) {
            addSoultion.setEnabled(true);
            System.out.println("im here true button add");
        } else {
            System.out.println("false here  button add");
            addSoultion.setEnabled(false);
        }
        StringBuilder details = new StringBuilder();
        ErrorsReport report = solution.getReport();
        // Create a multiline text component to display details
        H6 text = new H6(); // Declare text variable outside the if block
        index = indexForList;
        // Titles
        System.out.println("until here 1");
        // Explanations
        if (solution.getCompile()) {
            details.append("<strong>(Complation)בדיקת הידור:</strong>\n")
                    .append("בדיקה שקובעת האם הקוד עובר הידור \n");
            if (solution.getCompile() == true) {
                details.append("הבדיקה עברה בהצלחה" + checkString);
            }
            details.append("<hr>");
            details.append("<strong>(Validation)בדיקת תקינות של הפעולה:</strong>(30%):\n");
            details.append(
                    "\n.הבדיקה נועדה לבדוק האם אתה מבצע את הפעולה הנדרשת בכל מצב.הבדיקה מורכבת מ 10 בדיקות של מערכים כל מערך בגודל 30 ומאוכסנים בו איברים בסדרים שיאתגרו את בניית הפונקציה שלך,אם חצי מהבדיקות נכשלו אז מבחינתי הפונקציה לא תקינה -אבל אם יותר ממחצית מהבדיקות נכונות אז בכל שגיאה יורד 10 נקודות מהציון הסופי\n");
            if (report.getErrorsArraysExplain().size() != 0) {
                details.append("<strong>תוצאות הבדיקה:\n </strong>\n");
                for (int i = 0; i < report.getErrorsArraysExplain().size(); i++) {
                    details.append("לא עמדת בבדיקה מספר" + ": " + i + " אשר הייתה מורכבת מ "
                            + report.getErrorsArraysExplain().get(i));
                    details.append(exString);
                    details.append("\n");

                }
            } else {
                details.append("<strong>עמדת בכל הבדיקות שהיו" + checkString + "\n</strong>");
            }
            System.out.println("until here 2");

            details.append("הסבר חישוב הציון:\n");
            details.append("100 - " + ("(כמות הטעויות*10)") + "= הציון של הבדיקה(עדיין לא הסופי)\n");
            details.append(" (המשקל בציון שקיבלנו מקודם * 0.3)=הציון הסופי לבדיקה הזאת\n");
            details.append("<hr>");
            details.append("להלן החישוב בבדיקה שלך:\n");
            details.append("שלב ראשון בחישוב:");
            System.out.println("1" + solution.getGrade().getScoreValidtion() + "2" + report.getArrayOfPoints() + "3");

            details.append("100 - " + "(" + (100 - +solution.getGrade().getScoreValidtion()) + ") = "
                    + solution.getGrade().getScoreValidtion() + "\n");
            details.append("שלב שני וסופי בחישוב:");

            details.append("(" + solution.getGrade().getScoreValidtion() + " * 0.3)="
                    + solution.getGrade().getScoreValidtion() * 0.3);
            details.append("<hr>");
            System.out.println("@22");
            Point[] arrPoints = report.getArrayOfPoints();
            if (arrPoints == null) {

                details.append("הפונקציה שלך לא תקינה לכן לא תבצע את שאר הבדיקות.\n");
                details.append(" <strong> לכן יהיו רק הסברים של הבדיקות הבאות.</strong>\n\n");
                text.getElement().setProperty("innerHTML", details.toString());
                text.getElement().getStyle().set("text-align", "right");
                text.setText(details.toString());
                text.getStyle().set("white-space", "pre-line");
                textTest.removeAll();
                textTest.add(text);
                return;
            }
            System.out.println("until here 3");

            details.append("<strong> (Efficiency Steps)סדר גודל של הפונקציה + דרך :</strong> (40%):\n")
                    .append("מציאת סדר גודל (זמן ריצה) של פונקציה ובדיקה האם הסדר גודל שנמצא הוא יעיל.")
                    .append("אם אכן הוא יעיל מספיק אתה תקבל 100% מהציון ואם לא אז 0% \n").append(".")
                    .append("את הסדר גודל אנחנו מוצאים על ידי מספר פעולות\n").append(".")
                    .append(".ניצור את הנקודות דרך יצירת מערכים בלולאה מ10 עד 50 (בקפיצות של 10),בכל הפעלה של הפונקציה על מערך מוציאים את מספר הצעדים הגבוה ביותר שנעשתה לאחר שיש לנו את כמות הצעדים אז יוצרים נקודות.\n")
                    .append("לאחר שמצאנו את הנקודות אפשר למצוא שיפועים ואז דרך השיפועים האלו אנחנו מוצאים את המרחקים בין שיפוע לשיפוע. ")
                    .append("(x = גודל המערך,y= מספר הצעדים שנעשו).\n")
                    .append("ולפי שלבים אלו ניתן למצוא את זמן הריצה של הפונקציה.");
            long[] arrSlopes = report.getArrayOfSlope();
            long[] arrDistanceOfSlopes = report.getArrayOfDistance();
            details.append("\n<hr>");
            if (arrPoints != null) {
                int[] yValue = new int[arrPoints.length];
                int[] xValue = new int[arrPoints.length];
                System.out.println(arrPoints.length);

                int counter2 = 10;
                details.append("The all Points\n");
                for (int i = 0; i < arrPoints.length; i++) {
                    if (arrPoints[i] != null) {

                        xValue[i] = (int) arrPoints[i].getX();
                        yValue[i] = (int) arrPoints[i].getY();
                        details.append("(" + (i + 1) + ")\n");
                        details.append("n = " + counter2 + "\n");
                        details.append(
                                "the Point is: " + "(" + arrPoints[i].getX() + "= X, " + arrPoints[i].getY() + "= Y)"
                                        + "\n");
                        counter2 += 10;
                    }
                }
                System.out.println("until here 4");

                details.append("\nThe all slpoes\n");
                int counterForSlpoes = 0;
                for (int j = 0; j < arrSlopes.length; j++) {
                    if (arrSlopes[j] != 0) {
                        details.append("(" + (counterForSlpoes + 1) + ")");
                        details.append("the Slope is: " + arrSlopes[j] + "\t\t");
                        counterForSlpoes++;
                        details.append("\n");
                    }
                }
                details.append("The all the distances:\n");
                int counterDistance = 0;
                System.out.println("arr size= " + arrDistanceOfSlopes.length);
                for (int j = 0; j < arrDistanceOfSlopes.length - 1; j++) {
                    if (arrDistanceOfSlopes[j] >= 0) {

                        details.append("(" + (counterDistance + 1) + ")\t");
                        details.append("the Distance of Slope is: " + arrDistanceOfSlopes[j] + "\n");

                        counterDistance++;
                        System.out.println("details Distance");
                    }
                }

                // Create some example data

                // Convert the data to a string
           
                System.out.println("until here 5");

                details.append("<hr>");
                if (report.getRunTimeOfFunc().equals("O(1)")) {
                    details.append("רואים שהשיפועים והמרחקים שווים לאפס לכן מסיקים שזה O(1)");

                } else if (report.getRunTimeOfFunc().equals("O(N)")) {
                    details.append("רואים שהשיפועים שווים לכן זה O(N)");

                } else if (report.getRunTimeOfFunc().equals("N^2")) {
                    details.append("לפי המרחקים שאנחנו רואים שהם שווים ושונים מאפס אז - זמן הריצה הוא N²");

                } else if (report.getRunTimeOfFunc().equals("N^3")) {
                    details.append("לפי המרחקים ניתן לזהות שזמן הריצה של הפונקציה הוא :N³\n");
                    if (solution.getGrade().getScoreOfSteps() == 50) {
                        details.append(
                                "אבל מיכוון שביצעת את כל הפעולה בשלמות תקבל 50 אחוז מהציון למרות שהזמן הריצה שעשית הוא לא האולטימטיבי!\n");

                    }
                }
            }
            System.out.println("until here 6");

            details.append("<hr>");
            details.append("<strong>(TimeScore)בדיקת יעילות בזיכרון:</strong> (10%):\n").append(
                    "בדיקה שמודדת את היעילות של הפונקציה   -פעולה לפונקציה לעשות מיון על מערך ענק(בגודל 50 אלף)  אם כמות הזיכרון שלקחה הפעולה עומד בכמות הזיכרון היעיל שהגדרנו (שאמור לקחת לפעולה הזאת). הבדיקה מקבלת 100% מהציון. ואם לא אז 0% \n");
            details.append("חישוב הציון:\n");
            details.append("\n(0.1*ציון הזיכרון)= הציון הסופי של");
            if (report.getMemoryTest() == true) {
                details.append("חישוב מעשי:\n " + "100" + "*0.1 =" + (solution.getGrade().getScoreOfMemory() / 10));
                details.append(" הבדיקה שלך עברה בהצלחה - קיבלת את מלוא הניקוד" + checkString);
            } else {
                details.append("חישוב מעשי: \n" + "0" + "*0.1 =" + solution.getGrade().getScoreOfMemory());

                details.append("הבדיקה שלך לא עברה בהצלחה " + exString);
            }
            details.append("<hr>");
            details.append("<strong>(MemoryScore)בדיקת יעילות בפעולה:</strong>(10%):\n")
                    .append("בדיקה שמודדת את היעילות של הפונקציה   - מדידה של הזמן שלוקח לפונקציה לעשות פעולה על מערך גדול(בגודל 50 אלף).\n  .אם הזמן שלקח עד לסיום הפעולה עומד בזמן שהגדרנו לפעולה. הבדיקה מקבלת 100% מהציון,ואם לא אז 0% \n");
            details.append("חישוב הציון:\n");
            details.append("\nציון הזמן *0.1= הציון הסופי של \n");
            if (report.getTimeRunTest() == true) {
                details.append("חישוב מעשי:\n " + "100" + "*0.1 =" + (solution.getGrade().getScoreOfTime() / 10));
                details.append(" הבדיקה שלך עברה בהצלחה - קיבלת את מלוא הניקוד" + checkString);
            } else {
                details.append("חישוב מעשי: " + "0" + "*0.1 =" + (solution.getGrade().getScoreOfTime() / 10));

                details.append("הבדיקה שלך לא עברה בהצלחה " + exString);
            }
            details.append("<hr>");
            details.append("<strong>(ChatGptScore)שליחת הפונקציה אל ChatGpt: </strong>(10%):\n").append(
                    "שליחת הפונקציה שלך לצאט ");
            details.append("לדירוג מ10 עד 100 על פי הפרמטרים לעיל.");
            details.append("חישוב הציון:\n");
            details.append("קבלת הציון מהצאט*0.1= הציון הסופי של ");
            details.append("<hr>");
            details.append("<strong>(FinalScore)ציון סופי</strong>:\n").append("<hr>").append(
                    "חישוב הציון הסופי הוא ((" + solution.getGrade().getScoreOfTime() + " * 0.1)   +("
                            + solution.getGrade().getScoreOfSteps() + "* 0.4) +   ("
                            + solution.getGrade().getScoreOfMemory() + "* 0.1) + ("
                            + solution.getGrade().getScoreOfChatGpt() + "* 0.1) + ("
                            + solution.getGrade().getScoreValidtion() + "* 0.3)) / 10\n ="
                            + solution.getGrade().getGrade());
            hideExplaintion.setEnabled(true);
            text.getElement().setProperty("innerHTML", details.toString());
            text.getElement().getStyle().set("text-align", "right");
            text.setText(details.toString());
            text.getStyle().set("white-space", "pre-line");
            textTest.removeAll();
            textTest.getStyle().set("white-space", "pre-line");
            textTest.add(text);
        } else {
            details.append("<strong>(Complation)בדיקת הידור:</strong>\n")
                    .append("בדיקה שקובעת האם הקוד עובר הידור \n");
            details.append("הבדיקה לא עברה קומפלציה" + exString + "\n");
            details.append("<strong>השגיאות שעליך לתקן:</strong> \n").append(solution.getErrorsInCode()).append("\n");
            hideExplaintion.setEnabled(true);
            text.getElement().setProperty("innerHTML", details.toString());
            text.getElement().getStyle().set("text-align", "right");
            text.setText(details.toString());
            text.getStyle().set("white-space", "pre-line");
            textTest.removeAll();
            textTest.getStyle().set("white-space", "pre-line");
            textTest.add(text);
            HTML html = new HTML();
            

        }

    }
    public void showChart(Point[]arrPointsForChart)
    { 
          int[] yValue = new int[arrPointsForChart.length];
        int[] xValue = new int[arrPointsForChart.length];
        System.out.println(arrPointsForChart.length);

        int counter2 = 10;
        for (int i = 0; i < arrPointsForChart.length; i++) {

                xValue[i] = (int) arrPointsForChart[i].getX();
                yValue[i] = (int) arrPointsForChart[i].getY();
            }
        String yValueString = convertPointsToString(yValue);
        String xValueString = convertPointsToString(xValue);

        // Construct the JavaScript code with the dynamic data
        String jsCode = "const script = document.createElement('script');" +
                "script.src = 'https://cdn.jsdelivr.net/npm/chart.js';" +
                "script.onload = () => {" +
                "    const xValues = " + xValueString + ";" +
                "    new Chart('myChart', {" +
                "        type: 'line'," +
                "        data: {" +
                "            labels: xValues," +
                "            datasets: [{" +
                "                data: " + yValueString + "," +
                "                borderColor: 'red'," +
                "                fill: false" +
                "            }]" +
                "        }," +
                "        options: {" +
                "            legend: { display: false }" +
                "        }" +
                "    });" +
                "};" +
                "document.head.appendChild(script);";

        // Execute the JavaScript code
        UI.getCurrent().getPage().executeJs(jsCode);
        canvas.setVisible(true);
        Button closeButton = new Button("Close");
        closeButton.addClickListener(event -> chartDialog.close());
        // Open the dialog
        chartDialog.open();
    }


    public boolean areAllElementsEqual(long[] arr) {
        if (arr.length <= 1) {
            return true;
        }
        long firstElement = arr[0];
        for (int i = 1; i < arr.length; i++) {
            System.out.println("(+" + i + "):\t" + arr[i]);
            if (arr[i] != firstElement) {
                System.out.println("arr=" + arr[i]);
                return false;
            }
        }
        return true;
    }

    private void showTheExplain(int indexForList) {
        Span boldTitles;
        Span boldTexts;
        Solution solution = listOfSoulotins.get(indexForList);
        // Create a multiline text component to display details
        H6 text = new H6(); // Declare text variable outside the if block
        StringBuilder details = new StringBuilder();
        String titles = "";
        if (solution != null) {

            ErrorsReport report = solution.getReport();
            index = indexForList;
            // Titles
            // Explanations
            details.append("<strong>בדיקת הידור:</strong>\n")
                    .append("בדיקה שקובעת האם הקוד עובר הידור\n");
            details.append("<hr>");
            details.append("<strong>בדיקת תקינות של הפעולה:</strong>(30%):\n").append(
                    ".הבדיקה נועדה לבדוק האם אתה מבצע את הפעולה הנדרשת בכל מצב.הבדיקה מורכבת מ 10 בדיקות של מערכים כל מערך בגודל 100 ומאוכסנים בו איברים בסדרים שיאתגרו את בניית הפונקציה שלך,אם חצי מהבדיקות נכשלו אז מבחינתי הפונקציה לא תקינה -אבל אם יותר ממחצית מהבדיקות נכונות אז בכל שגיאה יורד 10 נקודות מהציון הסופי\n");
            details.append("<hr>");
            details.append("<strong>מציאת סדר גודל:</strong> (40%):\n")
                    .append("מציאת סדר גודל (זמן ריצה) של פונקציה ובדיקה האם הסדר גודל שנמצא הוא יעיל.")
                    .append("אם אכן הוא יעיל מספיק אתה תקבל 100% מהציון ואם לא אז 0% \n").append(".")
                    .append("את הסדר גודל אנחנו מוצאים על ידי מספר פעולות\n").append(".")
                    .append(".ניצור את הנקודות דרך יצירת מערכים בלולאה מ10 עד 50 (בקפיצות של 10),בכל הפעלה של הפונקציה על מערך מוציאים את מספר הצעדים הגבוה ביותר שנעשתה לאחר שיש לנו את כמות הצעדים אז יוצרים נקודות.\n")
                    .append("לאחר שמצאנו את הנקודות אפשר למצוא שיפועים ואז דרך השיפועים האלו אנחנו מוצאים את המרחקים בין שיפוע לשיפוע. ")
                    .append("(x = גודל המערך,y= מספר הצעדים שנעשו).\n")
                    .append("ולפי שלבים אלו ניתן למצוא את זמן הריצה של הפונקציה.\n");
        }
        details.append("<strong>בדיקת יעילות בזיכרון:</strong> (10%):\n").append(
                "בדיקה שמודדת את היעילות של הפונקציה   -פעולה לפונקציה לעשות מיון על מערך ענק(בגודל 50 אלף)  אם כמות הזיכרון שלקחה הפעולה עומד בכמות הזיכרון היעיל שהגדרנו (שאמור לקחת לפעולה הזאת). הבדיקה מקבלת 100% מהציון. ואם לא אז 0% \n");

        details.append("<strong>בדיקת יעילות בפעולה:</strong>(10%):\n")
                .append("בדיקה שמודדת את היעילות של הפונקציה   - מדידה של הזמן שלוקח לפונקציה לעשות פעולה על מערך גדול(בגודל 50 אלף).\n  .אם הזמן שלקח עד לסיום הפעולה עומד בזמן שהגדרנו לפעולה. הבדיקה מקבלת 100% מהציון,ואם לא אז 0% \n");

        details.append("<hr>");
        details.append("<strong>שליחת הפונקציה אל ChatGpt: </strong>(10%):\n").append(
                "שליחת הפונקציה שלך לצאט ");
        details.append("לדירוג מ10 עד 100 על פי הפרמטרים לעיל.\n");
        details.append("<hr>");
        details.append("<strong>ציון סופי:</strong>:\n").append("<hr>").append(
                "חישוב הציון הסופי הוא ((" + "ציון הזמן" + " * 0.1)   +("
                        + "ציון סדר גודל(זמן ריצה)" + "* 0.4) +   ("
                        + "ציון זיכרון" + "* 0.1) + ("
                        + "ציון של הצאט GPT" + "* 0.1) + ("
                        + "ציון של תקינות הקוד" + "* 0.3)\n )/10 ="
                        + "לציון הסופי");
        hideExplaintion.setEnabled(true);

        // Set the text contents
        textTest.removeAll();
        textTest.getStyle().set("white-space", "pre-line");
        boldTitles = new Span(titles);
        boldTexts = new Span(details.toString());
        boldTitles.getElement().setProperty("innerHTML", titles);
        boldTexts.getElement().setProperty("innerHTML", details.toString());
        boldTitles.getElement().getStyle().set("text-align", "right");
        boldTexts.getElement().getStyle().set("text-align", "right");
        textTest.getStyle().set("text-align", "right");
        textTest.add(boldTitles, boldTexts);
    }

    public void clearAll() {
        textTest.removeAll();
        index = -1;
        System.out.println("clear All");
        addSoultion.setEnabled(false);
        hideExplaintion.setEnabled(false);
        System.out.println("the dialog is false right now");
        canvas.setVisible(false);
        showChart.setEnabled(false);
        grid.deselectAll();

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

    public String convertPointsToString(int[] points) {
        StringBuilder dataString = new StringBuilder("[");
        for (int i = 0; i < points.length; i++) {
            dataString.append(points[i]);
            if (i < points.length - 1) {
                dataString.append(", ");
            }
        }
        dataString.append("]");
        return dataString.toString();
    }

    private void initializeLayout(String value) throws UnknownHostException {
        setSizeFull();
        horizontalLayout.setSizeFull();
        horizontalLayoutButton.setSizeFull();
        answerTextArea.setHeight("300px"); // Adjust the height as needed
        answerTextArea.setWidth("500px");
        grid.setSizeFull();
        System.out.println("value= " + value);
        Question question = questionService.findById(value);
        answerTextArea.setValue(question.getOpenQuestion());
        questionTitle.setText("שאלה: " + question.getQuestion());

    }

}
