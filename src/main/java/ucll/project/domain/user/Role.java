package ucll.project.domain.user;

public enum Role {
    USER("user"),
    SUPER("super"),
    ADMIN("admin");

    private String role;

    Role(String role) {
        this.role = role;
    }

    public String getRole() {
        return this.role;
    }
}
