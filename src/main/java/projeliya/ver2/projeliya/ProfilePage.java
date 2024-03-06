package projeliya.ver2.projeliya;


import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H6;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;

import java.util.List;
import java.util.stream.Collectors;

@Route("profile")
@PageTitle("Profile")
public class ProfilePage extends VerticalLayout {
    private static final String USER_SESSION_KEY = "user"; // Session attribute key for the user
    private final String checkString = "✅";
    private final String exString = "❌";
    private final UserService userService;
    private final QuestionService questionService;
    private Button toggleButton;
    private Button deleteSolution;
    private  Grid<Solution> grid;
    private SolutionService solutionService;

    public ProfilePage(SolutionService solutionService,UserService userService, QuestionService questionService) {
        this.userService = userService;
        this.questionService = questionService;
        this.solutionService = solutionService;
        String username = (String) VaadinSession.getCurrent().getAttribute("user");
            System.out.println(username);
        if(username == null) {
            System.out.println("-------- User NOT Authorized - can't use chat! --------");
            UI.getCurrent().getPage().setLocation("/"); // Redirect to login page (HomePage).
            return;        
        }
        grid = new Grid<>();
        grid.addColumn(Solution::getNameOfQuestion).setHeader("Question name");
        grid.addColumn(Solution::getFinalGrade).setHeader("Grade");
        refreshGrid();
        H1 title = new H1("Welcome to your profile, " + username + "!");
        grid.asSingleSelect().addValueChangeListener(event -> {
            User user  = userService.getUserByID(username);
            Solution selectedSolution = grid.asSingleSelect().getValue(); 
            if (user != null) 
            {                showAnswerDetails(selectedSolution);
            }
        });

        toggleButton = new Button("My Soultions", event -> toggleTableVisibility());
        Button changePasswordButton = new Button("Change Password", event -> openPasswordChangeDialog());
        Button logoutButton = new Button("Logout", event -> logout());

        add(title, toggleButton, grid, changePasswordButton, logoutButton);

        // Initially hide the table
        grid.setVisible(false);
    }

    private void deleteSolution(Solution soultion) 
    {
        solutionService.deleteSolution(soultion.getId());
    
    }

    private void refreshGrid() {
        String username = (String) VaadinSession.getCurrent().getAttribute("user");
        List<Solution> userAnswers = solutionService.showTheAnswersForUser(username);
        grid.setItems(userAnswers);
   
    }
    
    

    private void toggleTableVisibility() {
        grid.setVisible(!grid.isVisible());
    }

    private void openPasswordChangeDialog() {
        Dialog passwordChangeDialog = new Dialog();
        passwordChangeDialog.setModal(true);

        H1 title = new H1("Change Password");

        // Your password change form components (e.g., old password, new password, confirm password fields)
        TextField oldPasswordField = new TextField("Old Password");
        PasswordField newPasswordField = new PasswordField("New Password");
        PasswordField confirmPasswordField = new PasswordField("Confirm New Password");

        Button changeButton = new Button("Change", event -> {
            // Add your password change logic here
            String oldPassword = oldPasswordField.getValue();
            String newPassword = newPasswordField.getValue();
            String confirmPassword = confirmPasswordField.getValue();

            // Validate the fields and perform the password change operation
            // Example validation: check if new password matches the confirm password
            if (!newPassword.equals(confirmPassword)) {
                // Show an error message if passwords don't match
                Notification.show("New password and confirm password do not match.").setPosition(Notification.Position.MIDDLE);
                return;
            }
            String username2 = (String) VaadinSession.getCurrent().getAttribute("user");
            // Perform the password change operation
            boolean success = userService.updateUser(userService.getUserByID(username2),oldPassword+"", newPassword+"");

            if (success) {
                // Show a success message
                Notification.show("Password changed successfully.").setPosition(Notification.Position.MIDDLE);
                passwordChangeDialog.close();
            } else {
                // Show an error message if password change failed
                Notification.show("Failed to change password. Please try again.").setPosition(Notification.Position.MIDDLE);
            }
        });

        Button cancelButton = new Button("Cancel", event -> passwordChangeDialog.close());

        VerticalLayout layout = new VerticalLayout(title, oldPasswordField, newPasswordField, confirmPasswordField, changeButton, cancelButton);
        passwordChangeDialog.add(layout);
        passwordChangeDialog.open();
    }

    private void logout() {
        VaadinSession.getCurrent().close();
        UI.getCurrent().navigate("");
        return;
    }

    private Dialog openDialog; // Declare a member variable to store the currently open dialog

    private void showAnswerDetails(Solution solution) 
    {
            Dialog dialog = new Dialog();
        StringBuilder details = new StringBuilder();
        if(solution !=null)
        {
        ErrorsReport report = solution.getReport();
        // Create a multiline text component to display details
        H6 text = new H6(); // Declare text variable outside the if block
        // Titles
        // Explanations

        details.append("<strong>הקוד:</strong>\n");
        details.append(solution.getAnswer()+"\n");
        details.append("<hr>");
        details.append("<strong>בדיקת הידור:</strong>\n")
                .append("בדיקה שקובעת האם הקוד עובר הידור \n")
                .append("הבדיקה עברה בהצלחה"+checkString);
        details.append("<hr>");
        details.append("<strong>בדיקת תקינות של הפעולה:</strong>(30%):\n");
        details.append("\n.הבדיקה נועדה לבדוק האם אתה מבצע את הפעולה הנדרשת בכל מצב.הבדיקה מורכבת מ 10 בדיקות של מערכים כל מערך בגודל 30 ומאוכסנים בו איברים בסדרים שיאתגרו את בניית הפונקציה שלך,אם חצי מהבדיקות נכשלו אז מבחינתי הפונקציה לא תקינה -אבל אם יותר ממחצית מהבדיקות נכונות אז בכל שגיאה יורד 10 נקודות מהציון הסופי\n");
                if (report.getErrorsArraysExplain().size() != 0) 
                {
            details.append("<strong>תוצאות הבדיקה:\n </strong>\n");
            for (int i = 0; i < report.getErrorsArraysExplain().size(); i++) {
                details.append("לא עמדת בבדיקה מספר" + ": " + i + " אשר הייתה מורכבת מ "
                        + report.getErrorsArraysExplain().get(i));
                details.append(exString);
                details.append("\n");
                        
            }
        } else {
            details.append("<strong>עמדת בכל הבדיקות שהיו"+checkString+"\n</strong>");
        }
        details.append("הסבר חישוב הציון:\n");
        details.append("100 - " + ("(כמות הטעויות*10)") + "= הציון של הבדיקה(עדיין לא הסופי)\n");
        details.append(" (המשקל בציון שקיבלנו מקודם * 0.3)=הציון הסופי לבדיקה הזאת\n");
        details.append("<hr>");
        details.append("להלן החישוב בבדיקה שלך:\n");
        details.append("שלב ראשון בחישוב:");
        details.append("100 - " + "("+(100 - +solution.getGrade().getScoreValidtion()) + ") = "+solution.getGrade().getScoreValidtion()+"\n");
        details.append("שלב שני וסופי בחישוב:");
        details.append("("+solution.getGrade().getScoreValidtion() + " * 0.3)="+ solution.getGrade().getScoreValidtion() * 0.3);
        details.append("<hr>");
        details.append("<strong> סדר גודל של הפונקציה +דרך :</strong> (40%):\n")
                .append("מציאת סדר גודל (זמן ריצה) של פונקציה ובדיקה האם הסדר גודל שנמצא הוא יעיל.")
                .append("אם אכן הוא יעיל מספיק אתה תקבל 100% מהציון ואם לא אז 0% \n").append(".")
                .append("את הסדר גודל אנחנו מוצאים על ידי מספר פעולות\n").append(".")
                .append(".ניצור את הנקודות דרך יצירת מערכים בלולאה מ10 עד 50 (בקפיצות של 10),בכל הפעלה של הפונקציה על מערך מוציאים את מספר הצעדים הגבוה ביותר שנעשתה לאחר שיש לנו את כמות הצעדים אז יוצרים נקודות.\n")
                .append("לאחר שמצאנו את הנקודות אפשר למצוא שיפועים ואז דרך השיפועים האלו אנחנו מוצאים את המרחקים בין שיפוע לשיפוע. ")
                .append("(x = גודל המערך,y= מספר הצעדים שנעשו).\n")
                .append("ולפי שלבים אלו ניתן למצוא את זמן הריצה של הפונקציה.");
        Point[] arrPoints = report.getArrayOfPoints();
        long[] arrSlopes = report.getArrayOfSlope();
        long[] arrDistanceOfSlopes = report.getArrayOfDistance();
        details.append("\n<hr>");
        if(arrPoints!=null)
        {
              int counter2 = 10;
            details.append("The all Points\n");
        for (int i = 0; i < arrPoints.length; i++) {
            if(arrPoints[i]!= null)
            {
            details.append("(" + (i + 1) + ")\n");
            details.append("n = " + counter2 + "\n");
            details.append(
                    "the Point is: " + "(" + arrPoints[i].getX() + "= X, " + arrPoints[i].getY() + "= Y)"
                            + "\n");
            counter2+=10;
            }
        }
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
        int counterDistance=0;
        for (int j = 0; j < arrDistanceOfSlopes.length; j++) {
            if (arrDistanceOfSlopes[j] != 0) {
                details.append("(" + (counterDistance + 1) + ")\t");
                details.append("the Distance of Slope is: " + arrDistanceOfSlopes[j] + "\n");
                counterDistance++;
            }
        }
        details.append("<hr>");
        if (report.getRunTimeOfFunc().equals("O(1)")) {
            details.append("רואים שהשיפועים והמרחקים שווים לאפס לכן מסיקים שזה O(1)");

        } else if (report.getRunTimeOfFunc().equals("N")) {
            details.append("רואים שהשיפועים שווים לכן זה O(N)");

        } else if (report.getRunTimeOfFunc().equals("N^2")) {
            details.append("לפי המרחקים שאנחנו רואים שהם שווים ושונים מאפס אז - זמן הריצה הוא N²");

        } else if (report.getRunTimeOfFunc().equals("N^3")) {
            details.append("לפי המרחקים ניתן לזהות שזמן הריצה של הפונקציה הוא :\n N³");
        }
    }else{
        System.out.println("בגלל שהפונקציה שלך לא תקינה -אין סיבה שנעשה את שאר הבדיקות.");
    }

    details.append("<hr>");
        details.append("<strong>בדיקת יעילות בזיכרון:</strong> (10%):\n").append(
                "בדיקה שמודדת את היעילות של הפונקציה   -פעולה לפונקציה לעשות מיון על מערך ענק(בגודל 50 אלף)  אם כמות הזיכרון שלקחה הפעולה עומד בכמות הזיכרון היעיל שהגדרנו (שאמור לקחת לפעולה הזאת). הבדיקה מקבלת 100% מהציון. ואם לא אז 0% \n");
                details.append("חישוב הציון:\n");
                details.append("(0.1*ציון הזיכרון)= הציון הסופי של");
                details.append("<hr>");
        details.append("<strong>בדיקת יעילות בפעולה:</strong>(10%):\n")
                .append("בדיקה שמודדת את היעילות של הפונקציה   - מדידה של הזמן שלוקח לפונקציה לעשות פעולה על מערך גדול(בגודל 50 אלף).\n  .אם הזמן שלקח עד לסיום הפעולה עומד בזמן שהגדרנו לפעולה. הבדיקה מקבלת 100% מהציון,ואם לא אז 0% \n");
                details.append("חישוב הציון:\n");
                details.append("ציון הזמן *0.1= הציון הסופי של ");
                // .append(report.getTimeRunTest());
        details.append("<hr>");
        details.append("<strong>שליחת הפונקציה אל ChatGpt: </strong>(10%):\n").append(
                "שליחת הפונקציה שלך לצאט ");
        details.append("לדירוג מ10 עד 100 על פי הפרמטרים לעיל.");
        details.append("חישוב הציון:\n");
                details.append("קבלת הציון מהצאט*0.1= הציון הסופי של ");
        details.append("<hr>");
        details.append("<strong>ציון סופי</strong>:\n").append("<hr>").append(
                "חישוב הציון הסופי הוא ((" + solution.getGrade().getScoreOfTime() + " * 0.1)   +("
                        + solution.getGrade().getScoreOfSteps() + "* 0.4) +   ("
                        + solution.getGrade().getScoreOfMemory() + "* 0.1) + ("
                        + solution.getGrade().getScoreOfChatGpt() + "* 0.1) + ("
                        + solution.getGrade().getScoreValidtion() + "* 0.3)) / 10\n ="
                        + solution.getGrade().getGrade());
        text.getElement().setProperty("innerHTML", details.toString());
        text.getElement().getStyle().set("text-align", "right");
        text.setText(details.toString());
        dialog.add(text);
        text.getStyle().set("white-space", "pre-line");
        text.getStyle().set("font-size", "20px");
        deleteSolution = new Button("DeleteSoultion", event ->{
            deleteSolution(solution);
            Notification.show("Your Souliton has been deleted");
            refreshGrid();
            dialog.close();
            System.out.println("closeDialog");
            return;
            
        });
        Button closeButton = new Button("Close", event -> dialog.close());
        dialog.add(closeButton);
        dialog.add(deleteSolution);
        dialog.open();
    }
    else{
        dialog.close();

    }
    }
    
    
}
