package projeliya.ver2.projeliya;

import java.util.ArrayList;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;

@Route(value = "/adminPage" ,layout = AppMainLayout.class)
public class AdminPage extends VerticalLayout {

    private UserService userService;
    private Grid<User> userGrid;
    private Button deleteUserButton;

    public AdminPage(UserService userService) {
        this.userService = userService;
     String user = (String) VaadinSession.getCurrent().getAttribute("user");
         if(user != null) 
        {
            User userBeAdmin = userService.getUserByID(user);
            if(userBeAdmin.isFlag() == false)
            {
                System.out.println("-------- User NOT Authorized - can't use chat! --------");
                UI.getCurrent().getPage().setLocation("/"); // Redirect to login page (HomePage).
                return;  
            }
            
        }else{
            System.out.println("-------- User NOT Authorized - can't use chat! --------");
            UI.getCurrent().getPage().setLocation("/"); // Redirect to login page (HomePage).
            return;        
        }


        userGrid = new Grid<>(User.class);
        // Set up grid configuration
        userGrid.setColumns("id", "flag"); // Change according to your User class
        userGrid.getColumnByKey("id").setHeader("Name Of User");
        userGrid.getColumnByKey("flag").setHeader("Admin");
        userGrid.setItems(getUsers());
        deleteUserButton = new Button("Delete User", event -> {
            User selectedUser = userGrid.asSingleSelect().getValue();
            if (selectedUser != null) {
                deleteUser(selectedUser.getId());
            }
        });

        add(userGrid, deleteUserButton);
    }

    private ArrayList<User> getUsers() {
        return new ArrayList<>(userService.getAllUsers());
    }

    private void deleteUser(String id) {
        userService.deleteUserByID(id);
        userGrid.setItems(getUsers());
    }
}
