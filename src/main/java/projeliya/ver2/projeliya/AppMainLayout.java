package projeliya.ver2.projeliya;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.router.Location;
import com.vaadin.flow.server.VaadinSession;

import java.util.ArrayList;
import java.util.List;

public class AppMainLayout extends AppLayout {
    private static final String LOGIN_VIEW = "/login";
    private static final String REGISTER_VIEW = "/register";
    private static final String PROFILE_VIEW = "/profile";
    private static final String CHECK_YOUR_CODE_VIEW = "/ChackYourCode";
    private static final String ADMIN_PAGE_VIEW = "/adminPage";
    private static final String SOLUTIONS_PAGE_VIEW = "/show";
    private static final String HOME_PAGE_VIEW = "/";

    private UserService userService;
    private Button selectedButton;
    private List<Button> buttons = new ArrayList<>();

    public AppMainLayout(UserService userService) {
        this.userService = userService;
        createHeader();
    }

    private void createHeader() {
        H3 logo = new H3("Rate your solutions");
        logo.getStyle().setColor("blue");

        String userName = (String) VaadinSession.getCurrent().getAttribute("user");
        Span userSpan = new Span("👤 Welcome to Our Website: " + (userName != null ? userName : "Guest"));

        HorizontalLayout header = new HorizontalLayout();
        header.add(logo, userSpan);
        header.setFlexGrow(1, logo); // Ensuring the logo takes as much space as it needs

        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.setSpacing(true); // Adding some space between buttons

        if (userName != null) {

            Button btnProfile = createButton("Profile", PROFILE_VIEW);
            Button chackYourCode = createButton("Check Your Code", CHECK_YOUR_CODE_VIEW);
            Button btnAllAnswers = createButton("Solutions Page", SOLUTIONS_PAGE_VIEW);
            Button btnHomePage = createButton("Home Page", HOME_PAGE_VIEW);

            buttons.add(btnProfile);
            buttons.add(chackYourCode);
            buttons.add(btnAllAnswers);
            buttons.add(btnHomePage);

            User user = userService.getUserByID(userName);
            if (user != null && user.isFlag()) {
                Button btnAdminPage = createButton("Admin Page", ADMIN_PAGE_VIEW);
                buttons.add(btnAdminPage);
                buttonLayout.add(btnAdminPage);
            }

            Button btnLogout = new Button("Logout", e -> {
                VaadinSession.getCurrent().getSession().invalidate();
                UI.getCurrent().navigate(LOGIN_VIEW);
            });

            Location location = UI.getCurrent().getActiveViewLocation();

            btnLogout.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_SMALL);
            btnLogout.getStyle().setColor("black");
            btnLogout.getStyle().setBackgroundColor("red");

            buttonLayout.add(btnHomePage, chackYourCode, btnAllAnswers, btnProfile, btnLogout);
            buttonLayout.setAlignItems(FlexComponent.Alignment.BASELINE);
            String path = "/" + location.getPath();

        } else {
            Button btnLogin = createButton("Login", LOGIN_VIEW);
            Button btnRegister = createButton("Register", REGISTER_VIEW);
            buttonLayout.add(btnLogin, btnRegister);
        }

        buttonLayout.setPadding(true);
        header.add(buttonLayout);
        header.setWidthFull();
        header.setAlignItems(FlexComponent.Alignment.CENTER); // Align elements vertically centered
        header.setJustifyContentMode(FlexComponent.JustifyContentMode.END); // Align buttons to the right

        addToNavbar(header);
        selectButtonBasedOnPath();
    }

    private Button createButton(String caption, String route) {
        Button button = new Button(caption);
        button.addClickListener(e -> {
            UI.getCurrent().navigate(route);
            selectButton(button);
        });
        button.setId(route);
        System.out.println(button.getId());
        return button;
    }

    private void selectButton(Button button) {
        if (selectedButton != null) {
            selectedButton.removeThemeVariants(ButtonVariant.LUMO_PRIMARY);
        }
        button.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        selectedButton = button;
    }

    private void selectButtonBasedOnPath() {
        Location location = UI.getCurrent().getActiveViewLocation();
        String path = "/" + location.getPath();
        System.out.println("CURRENT path is:"+path);
        for (Button button : buttons) {
            String buttonPath = button.getId().orElse("");
            System.out.println("path is:"+buttonPath);

            if (buttonPath.equals(path)) {
                System.out.println("the chosse path is:"+buttonPath);
                selectButton(button);
                return;
            }
        }
    }
    
}
