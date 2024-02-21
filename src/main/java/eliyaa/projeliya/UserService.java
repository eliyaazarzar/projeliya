package eliyaa.projeliya;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
public class UserService 
{
    private UserRepository usersRepo;

    public UserService(UserRepository usersRepo) {
        this.usersRepo = usersRepo;
    }
   
    public boolean existsByName(String id) {
        return usersRepo.existsById(id);


    }
    private Boolean login(String id , String password)
    {
        Optional<User> user = usersRepo.findById(id);
        User userExists =  user.isPresent() ? user.get() : null;

        if (userExists!= null && userExists.getPassword().equals(password) ) 
        {
            System.out.println("the password is" + userExists.getPassword());
            return true;
        }
        return false;
    }
    public void saveUser(User user)
    {
        usersRepo.save(user);
    }
    public Boolean updateUser(User user,String password,String newPassword) 
    {
       boolean flag =  login(user.getId(),password);
       System.out.println("flag:"+flag);
        if(flag == true)
        {
        user.setPassword(newPassword);
        usersRepo.save(user);
            return true;
        }
    return false;
    }

    // Create new user if not already exists
    public boolean addUser(User user) {
        // Check if the user already exists
        if (usersRepo.existsById(user.getId())) {
            // User already exists, return false
            return false;
        }

        // User does not exist, proceed to insert
        try {
            usersRepo.insert(user); // insert new user into MongoDB
            return true;
        } catch (Exception e) {
            System.out.println("UserService.addUser() Error! " + e.getMessage());
            return false;
        }
    }

    // Read all users
    public ArrayList<User> getAllUsers() {
        return (ArrayList<User>) usersRepo.findAll();
    }

    public User getUserByID(String id) 
    {
        Optional<User> user = usersRepo.findById(id);
        User userExists =  user.isPresent() ? user.get() : null;
        return userExists;
    }

    // Delete user by ID
    public void deleteUserByID(String id) {
        usersRepo.deleteById(id);
    }
}
