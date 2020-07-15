package ucll.project.model;

import ucll.project.domain.user.User;
import ucll.project.domain.user.UserRepositoryDb;
import ucll.project.domain.user.UserService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;
import java.util.Locale;

public class Star {
    private int id;
    private User receiver,sender;
    private String message;
    private LocalDate date;
    private List<Comment> comments;
    private List<Like> likes;
    private List<Tag> tags;

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public void setLikes(List<Like> likes) {
        this.likes = likes;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public Star() {}

    public Star(User receiver, User sender, String message, LocalDate date){
        this.receiver = receiver;
        this.sender = sender;
        this.message = message;
        this.date = date;
    }

    public void addComment(String comment,User sender){
        if (comment != null && !comment.isEmpty()){
            this.comments.add(new Comment(this,message,sender));
        }else{
            //throw domainexceptioin
        }
    }

    public void addLike(User like){
        if (like != null){
            this.likes.add(new Like(this,like));
        }
    }

    public void addTag (Tag tag) {
        if (tag != null) {
            this.tags.add(tag);
        }
    }

    public void removeTag(Tag tag){
        if (tag != null){
            this.tags.remove(tag);
        }
    }

    public LocalDate getDate(){
        return this.date;
    }

    public User getReceiver() {
        return receiver;
    }

    public User getSender() {
        return sender;
    }

    public String getMessage() {
        return message;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public List<Like> getLikes() {
        return likes;
    }

    public void setId(int id){
        this.id = id;
    }

    public int getId(){
        return this.id;
    }

    public List<Tag> getTags(){
        return this.tags;
    }

    @Override
    public String toString(){
        return this.message;
    }

    public void setReceiver(String receiver) {
        UserRepositoryDb userService = new UserRepositoryDb();
        if (userService.get(receiver) == null) {
            throw new IllegalArgumentException("This user doesn't exist!");
        } else if (receiver.isEmpty()) {
            throw new IllegalArgumentException("Receiver can't be empty");
        } else {
            this.receiver = userService.get(receiver);
        }
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public void setMessage(String message) {
        if (message.isEmpty()) {
            throw new IllegalArgumentException("Message can't be empty!");
        } else {
            this.message = message;
        }
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getTagList(){
        if (this.tags != null || this.tags.isEmpty()) {
            System.out.println(this.getMessage());
            String info = "";
            for (int i=0;i<this.tags.size();i++) {
                if (i<this.tags.size()-1) {
                    info += this.tags.get(i).getMessage() + ", ";
                }else{
                    info += this.tags.get(i).getMessage();
                }
            }
            return info.substring(0, info.length());
        }return "";
    }

    public String formatDate(){
        return  this.date.getDayOfMonth() + "-" + this.date.getMonth().toString();
    }
}