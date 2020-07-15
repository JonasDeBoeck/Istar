package ucll.project.model;

import ucll.project.domain.user.InvalidLogin;
import ucll.project.domain.user.User;

import java.util.List;

public interface StarServiceInterface {
    // CREATE
    void createStar(Star star);

    // READ ONE
    Star get(int starid);

    // READ ALL
    List<Star> getAll();

    List<Star> getAllByTagname(String tagname);

    List<Star> getAllByUsername(String username, String senderReceiver);

    int getLevel(String username,String tagname);

    // UPDATE
    void update(Star user);

    // DELETE
    void delete(int id);

}
