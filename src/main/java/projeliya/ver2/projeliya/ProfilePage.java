package projeliya.ver2.projeliya;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H6;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.vaadin.pekkam.Canvas;
@Route(value = "/profile" ,layout = AppMainLayout.class)
@PageTitle("Profile")
public class ProfilePage extends VerticalLayout {
    private static final String USER_SESSION_KEY = "user"; // Session attribute key for the user
    private final String checkString = "✅";
    private final String exString = "❌";
    private final UserService userService;
    private final QuestionService questionService;
    private SolutionService solutionService;

    public ProfilePage(SolutionService solutionService, UserService userService, QuestionService questionService) {
        this.userService = userService;
        this.questionService = questionService;
        this.solutionService = solutionService;
        String username = (String) VaadinSession.getCurrent().getAttribute("user");
        System.out.println(username);
        if (username == null) {
            System.out.println("-------- User NOT Authorized - can't use chat! --------");
            UI.getCurrent().getPage().setLocation("/"); // Redirect to login page (HomePage).
            return;
        }
        H1 title = new H1("Welcome to your profile, " + username + "!");

        Button changePasswordButton = new Button("Change Password", event -> openPasswordChangeDialog());
        Button logoutButton = new Button("Logout", event -> logout());


        add(title, changePasswordButton, logoutButton);

        

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


    private void openPasswordChangeDialog() {
        Dialog passwordChangeDialog = new Dialog();
        passwordChangeDialog.setModal(true);

        H1 title = new H1("Change Password");

        // Your password change form components (e.g., old password, new password,
        // confirm password fields)
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
                Notification.show("New password and confirm password do not match.")
                        .setPosition(Notification.Position.MIDDLE);
                return;
            }
            String username2 = (String) VaadinSession.getCurrent().getAttribute("user");
            // Perform the password change operation
            boolean success = userService.updateUser(userService.getUserByID(username2), oldPassword + "",
                    newPassword + "");

            if (success) {
                // Show a success message
                Notification.show("Password changed successfully.").setPosition(Notification.Position.MIDDLE);
                passwordChangeDialog.close();
            } else {
                // Show an error message if password change failed
                Notification.show("Failed to change password. Please try again.")
                        .setPosition(Notification.Position.MIDDLE);
            }
        });

        Button cancelButton = new Button("Cancel", event -> passwordChangeDialog.close());

        VerticalLayout layout = new VerticalLayout(title, oldPasswordField, newPasswordField, confirmPasswordField,
                changeButton, cancelButton);
        passwordChangeDialog.add(layout);
        passwordChangeDialog.open();
    }

    private void logout() {
        VaadinSession.getCurrent().close();
        UI.getCurrent().navigate("");
        return;
    }

}
