package ucll.project.domain.user;

import java.util.List;

public interface UserRepository {

    // CREATE
    void createUser(User user, String password);

    // READ ONE
    User get(String userName);

    // READ ALL
    List<User> getAll();

    // LOGIN (= GET but with password check)
    User loginUser(String username, String password) throws InvalidLogin;

    // UPDATE
    void update(User user);

    // DELETE
    void delete(User user);

    User getUser(int id);

    void updateStars(int stars);

    List<User> getUsersWhoWantsEmails ();
}
