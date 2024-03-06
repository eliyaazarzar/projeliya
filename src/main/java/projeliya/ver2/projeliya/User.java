package projeliya.ver2.projeliya;
import java.util.List;
import org.springframework.data.mongodb.core.mapping.Document;
import com.vaadin.flow.component.template.Id;

@Document(collection = "users")
public class User {
    @Id
    private String id;
    private String password;
    private boolean flag;
    private List<Solution> answerList;

    public User() {
        // Default constructor needed for Spring Data MongoDB
    }
   
    public boolean isFlag() {
        return flag;
    }

    
    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public List<Solution> getAnswerList() {
        return answerList;
    }

    public void setAnswerList(List<Solution> answerList) {
        this.answerList = answerList;
    }

    public User(User user) {
    }
    public User(String id, String password, boolean flag, List<Solution> answerList) {
        this.id = id;
        this.password = password;
        this.flag = flag;
        this.answerList = answerList;
    }

    public User(String id, String password) {
        this.id = id;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
