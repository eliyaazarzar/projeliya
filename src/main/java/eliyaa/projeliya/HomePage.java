package eliyaa.projeliya;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;

@Route("/")
@PageTitle("HomePage")
public class HomePage extends VerticalLayout implements BeforeEnterObserver {

    private static final String USER_SESSION_KEY = "user"; // Session attribute key for the user
    private static final String LOGIN_VIEW = "login"; // Path to the login view
    private static final String REGISTER_VIEW = "register"; // Path to the register view
    private static final String PROFILE_VIEW = "profile"; // Path to the profile view
    private static final String CHANGE_PASSWORD_VIEW = "changepassword"; // Path to the change password view

    private final UserService userService; 

    public HomePage(UserService userService) {
        this.userService = userService;
        setupUI();
    
    
    
    
    
    }

    private void setupUI() {
                
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        setSizeFull();
        setJustifyContentMode(JustifyContentMode.CENTER);
        getStyle().set("background", "linear-gradient(to right, #FFD700, #FFA500)");

        updateUIBasedOnAuthState();
    }

    private void updateUIBasedOnAuthState() {
        removeAll(); // Important: Clear the UI before adding components
        String loggedInUsername = (String) VaadinSession.getCurrent().getAttribute(USER_SESSION_KEY);
        if (loggedInUsername != null) {
            addUserView(loggedInUsername);
        } else {
            addGuestView();
        }
    }

    private void addGuestView() {
        H1 title = new H1("Welcome to My Website");
        title.getStyle().set("color", "#FFFFFF");
        Paragraph description = new Paragraph("Discover a world of possibilities.");
        description.getStyle().set("color", "#FFFFFF");
        
        Button signInButton = new Button("Login", e -> UI.getCurrent().navigate(LOGIN_VIEW));
        Button signUpButton = new Button("Register", e -> UI.getCurrent().navigate(REGISTER_VIEW));
        
        HorizontalLayout buttonsLayout = new HorizontalLayout(signInButton, signUpButton);
        buttonsLayout.setSpacing(true);
        
        add(title, description, buttonsLayout);
    }

    private void addUserView(String username) 
    {
        H1 welcomeMessage = new H1("Welcome back, " + username + "!");
        welcomeMessage.getStyle().set("color", "#FFFFFF");
        
        Button profileButton = new Button("Profile", e -> UI.getCurrent().navigate("/profile"));
        Button seeAllAnswersButton = new Button("allAnswers", e -> UI.getCurrent().navigate("/show"));

        Button changePasswordButton =new Button("ChackYourCode", e -> UI.getCurrent().navigate("/ChackYourCode"));

        Button logoutButton = new Button("Logout", e -> {
            VaadinSession.getCurrent().setAttribute(USER_SESSION_KEY, null);
            updateUIBasedOnAuthState(); // Refresh UI to reflect the logout
        });
        
        HorizontalLayout optionsLayout = new HorizontalLayout(profileButton, changePasswordButton,seeAllAnswersButton, logoutButton);
        optionsLayout.setSpacing(true);
        
        add(welcomeMessage, optionsLayout);
    }
    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        // Optional: Redirect logic based on auth state can be added here
    }
}
