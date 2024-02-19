package eliyaa.projeliya;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route("/test")
public class testPage extends VerticalLayout {

    public testPage() {
        Button button = new Button("Start Task");
        button.addClickListener(event -> {
            UI ui = UI.getCurrent();
            if (ui != null) {
                ui.setPollInterval(100000); // Set a polling interval of 500 milliseconds
             
                // Stop the polling interval after 3 minutes (180000 milliseconds)
                new java.util.Timer().schedule(new java.util.TimerTask() {
                    @Override
 
                    public void run() {
                        ui.setPollInterval(-1); // Stop the polling interval
                    }
                }, 180000); // 3 minutes
            }
        });

        add(button);
    }
}
