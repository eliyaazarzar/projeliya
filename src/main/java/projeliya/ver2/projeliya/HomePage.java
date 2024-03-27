package projeliya.ver2.projeliya;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "/", layout = AppMainLayout.class)
@PageTitle("HomePage")
public class HomePage extends VerticalLayout {

    private static final String IMAGE_LOGO_URL = "https://programmaticponderings.files.wordpress.com/2023/04/shutterstock_680929729.jpg";

    public HomePage() {
        setupUI();
    }

    private void setupUI() {
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        setSizeFull();
        setJustifyContentMode(JustifyContentMode.CENTER);
        getStyle().set("background", "linear-gradient(to right, #000000, #808080)");

        addContent();
    }

    private void addContent() {
        H1 title = new H1("ברוכים הבאים לאתר שמטרתו מתן ציון לפתרונות על ידי בדיקות אוטומטיות");
        title.getStyle().set("color", "#FFFFFF");

        Paragraph description = new Paragraph("בפרוייקט זה ישנם שאלות המיועדים לתלמידי התיכון והם יכולים לענות עליהם ולקבל חיווי עד כמה הפתרונות שלהם טובים - בנוסף הם יכולים לראות פתרונות אחרים שיש במערכת וללמוד מהם");
        description.getStyle().set("color", "#FFFFFF");

        Image img = new Image(IMAGE_LOGO_URL, "photo");
        img.setHeight("300px");

        add(img, title, description);
    }
}
