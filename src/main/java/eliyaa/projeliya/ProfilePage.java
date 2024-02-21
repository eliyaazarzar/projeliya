package eliyaa.projeliya;

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

@Route("profile")
@PageTitle("Profile")
public class ProfilePage extends VerticalLayout {
    private static final String USER_SESSION_KEY = "user"; // Session attribute key for the user

    private final UserService userService;
    private final QuestionService questionService;
    private Grid<Answer> answersGrid;
    private Button toggleButton;

    public ProfilePage(UserService userService, QuestionService questionService) {
        this.userService = userService;
        this.questionService = questionService;

        String username = (String) VaadinSession.getCurrent().getAttribute("user");

        if (username == null) {
            UI.getCurrent().navigate(""); // Redirect to home page if user is not logged in
            return;
        }

        H1 title = new H1("Welcome to your profile, " + username + "!");
        answersGrid = createAnswersGrid(username);

        answersGrid.asSingleSelect().addValueChangeListener(event -> {
            User user  = userService.getUserByID(username);
            if (user != null) {
                for(Answer answer : user.getAnswerList())
                showAnswerDetails(answer);
            }
        });

        toggleButton = new Button("My Answers", event -> toggleTableVisibility());

        Button changePasswordButton = new Button("Change Password", event -> openPasswordChangeDialog());
        Button logoutButton = new Button("Logout", event -> logout());

        add(title, toggleButton, answersGrid, changePasswordButton, logoutButton);

        // Initially hide the table
        answersGrid.setVisible(false);
    }

    private Grid<Answer> createAnswersGrid(String username) {
        Grid<Answer> grid = new Grid<>();
        grid.addColumn(Answer::getNumQuestion).setHeader("Question Number");
        grid.addColumn(Answer::getAnswer).setHeader("Answer");
        grid.addColumn(Answer::getGrade).setHeader("Grade");

        List<Answer> userAnswers = userService.getUserByID(username).getAnswerList();
        grid.setItems(userAnswers);

        return grid;
    }

    private void toggleTableVisibility() {
        answersGrid.setVisible(!answersGrid.isVisible());
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

    private void showAnswerDetails(Answer answer) {
        // Close the currently open dialog if any
        if (openDialog != null && openDialog.isOpened()) {
            openDialog.close();
        }
        
        // Create a new dialog
        Dialog dialog = new Dialog();
        dialog.setModal(true);
        dialog.setCloseOnOutsideClick(false); // Prevent dialog from closing on outside click
    
        H1 title = new H1("Answer Details");
        H6 userName = new H6("User name: " + answer.getNameOfWriter());
        H6 questionNumber = new H6("Question number: " + answer.getNumQuestion());
        H6 answerText = new H6("Answer: " + answer.getAnswer());
        H6 grade = new H6("Grade: " + answer.getGrade());
    
        // Create a close button
        Button closeButton = new Button("Close", event -> dialog.close());
    
        // Add all components to the dialog
        dialog.add(title, userName, questionNumber, answerText, grade, closeButton);
    
        // Open the dialog
        dialog.open();
        
        // Set the current dialog as the open dialog
        openDialog = dialog;
    }
    
    
    
}
