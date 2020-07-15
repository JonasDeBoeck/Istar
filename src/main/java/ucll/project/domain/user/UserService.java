package ucll.project.domain.user;

import ucll.project.db.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

public class UserService {
    private UserRepository userRepo;

    public UserService(){
        userRepo = new UserRepositoryDb();
    }

    public List<User> getUsers(){
        return userRepo.getAll();
    }

    public void addUsser(User user, String password){
        this.userRepo.createUser(user,password);
    }
    public User login (String email, String passwoord) throws InvalidLogin {
        return userRepo.loginUser(email, passwoord);
    }

    public void update(User user){
        this.userRepo.update(user);
    }

    public User get(String userName) {
        return userRepo.get(userName);
    }

    public void updateStars(int stars){
        userRepo.updateStars(stars);
    }

    public List<User> getUserWhoWantsEmails () {
        return userRepo.getUsersWhoWantsEmails();
    }
}
