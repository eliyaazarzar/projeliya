package eliyaa.projeliya;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
public class User {
    
    private String name;
    private long password;
    public User(String name, long password) {
        this.name = name;
        this.password = password;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public long getPassword() {
        return password;
    }
    public void setPassword(long password) {
        this.password = password;
    }
    
    
}
