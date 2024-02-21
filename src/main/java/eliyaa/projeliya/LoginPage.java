package eliyaa.projeliya;

import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;

@PageTitle("LoginPage")
@Route("/login")
public class LoginPage extends VerticalLayout {

    private UserService userService;

    public LoginPage(UserService userService) {
        this.userService = userService;
        

        // Create login form
        LoginForm loginForm = new LoginForm();
        loginForm.setForgotPasswordButtonVisible(false); // Hide the "Forgot Password" link

        // Set login form properties
        loginForm.setForgotPasswordButtonVisible(false);

        // Register a listener for the LoginEvent
        loginForm.addLoginListener(e -> {
            String username = e.getUsername();
            String password = e.getPassword();

            // Now you can compare the username and password with the database
            User userExists = userService.getUserByID(username);

            if (userExists != null && userExists.getPassword().equals(password)) {
                Notification.show("Login Successful!");
                UI.getCurrent().getSession().setAttribute("user", username);
                UI.getCurrent().navigate("/");
            } else if (userExists != null) {
                Notification.show("Invalid username or password. Please try again.");
                loginForm.setEnabled(true); // Disable login form after an unsuccessful attempt
            } else {
                Notification.show("Please register first.");
                loginForm.setEnabled(true); // Disable login form after an unsuccessful attempt

            }
        });

        // Create a button to navigate to the register page
        RouterLink registerLink = new RouterLink(" to Register", RegisterPage.class);

        // Set alignment and background
        setAlignItems(FlexComponent.Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);
        setWidth("100%");
        setHeight("100vh");
        getStyle().set("background", "linear-gradient(to right, #FFD700, #FFA500)");

        // Add components to the layout
        add(loginForm, registerLink);
    }
}
