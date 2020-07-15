package ucll.project.model;

public class Tag {
    private Star star;
    private String message;

    public Tag(Star star,String message){
        this.star = star;
        this.message = message;
    }

    public Star getStar() {
        return star;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString(){
        return this.message + ", " + this.star.getId();
    }
}
