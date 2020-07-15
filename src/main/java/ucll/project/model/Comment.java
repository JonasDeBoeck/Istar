package ucll.project.model;

import ucll.project.domain.user.User;

public class Comment {
    private User sender;
    private String message;
    private Star star;

    public Comment(Star star,String message,User sender){
        this.star = star;
        this.message = message;
        this.sender = sender;
    }

    public User getSender() {
        return sender;
    }

    public String getMessage() {
        return message;
    }

    public Star getStar() {
        return star;
    }
}
