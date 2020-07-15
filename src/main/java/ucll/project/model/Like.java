package ucll.project.model;
import ucll.project.model.Star;
import ucll.project.domain.user.User;

public class Like {
    private Star star;
    private User sender;

    public Like(Star star,User sender){
        this.star= star;
        this.sender = sender;
    }
}
