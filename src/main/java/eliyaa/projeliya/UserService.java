package eliyaa.projeliya;


import java.util.ArrayList;

import org.springframework.stereotype.Service;

@Service
public class UserService
{
    private UserRepository usersRepo;

    public UserService(UserRepository usersRepo)
    {
        this.usersRepo = usersRepo;
    }

    // Create new user
    public boolean addUser(User user)
    {
        User insertedUser = null;
        try
        {
            insertedUser = usersRepo.insert(user); // insert new user into mongoDB
        }
        catch (Exception e)
        {
            System.out.println("UserService.addUser() Error! " + e.getMessage());
        }
        // System.out.println("==========> insertedUser="+insertedUser);
        return insertedUser == null ? false : true;
    }

    // Read all users
    public ArrayList<User> getAllUsers()
    {
        return (ArrayList<User>)usersRepo.findAll();
    }

    public User getUserByID(Long unID)
    {
        return usersRepo.findById(unID).get();
    }

    // Delete user by ID
    public void deleteUserByID(Long unID)
    {
        usersRepo.deleteById(unID);
    }
}
