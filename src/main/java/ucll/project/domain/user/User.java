package ucll.project.domain.user;

import org.json.JSONObject;

import javax.xml.bind.DatatypeConverter;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class User {
    private int userId;
    private String userName;
    private String firstName;
    private String lastName;
    private String email;
    private Gender gender;
    private Role role;
    private int stars;
    private LocalDate birthdate;
    private String level = "";
    private boolean receivingEmails = false;
    private boolean updated = false;

    // hashed password
    private transient String hashedPassword;

    public User() {
    }

    public User(String userName, String firstName, String lastName, String email, Gender gender, Role role, int stars,LocalDate date) {
        setUserName(userName);
        setFirstName(firstName);
        setLastName(lastName);
        setEmail(email);
        setGender(gender);
        setRole(role);
        setStars(stars);
        setBirthDate(date);

    }

    private void setBirthDate(LocalDate date) {
        if (date != null && date.isBefore(LocalDate.now())){
            this.birthdate = date;
        }
    }

    public void hashAndSetPassword(String password) {
        if (password.length() < 4) {
            throw new IllegalArgumentException("Too short password!");
        }
        String hashed = getPasswordToHashedPassword(password);
        setHashedPassword(hashed);
    }

    // This function will hash the password
    protected String getPasswordToHashedPassword(String password) {
        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException();
        }
        digest.update(password.getBytes(StandardCharsets.UTF_8));
        String hash = DatatypeConverter.printHexBinary(digest.digest()).toUpperCase();
        return hash;
    }

    public boolean isValidPassword(String password) {
        if (getHashedPassword() == null) {
            return false;
        }
        return getPasswordToHashedPassword(password).equals(getHashedPassword());
    }

    public LocalDate getBirthdate(){
        return this.birthdate;
    }

    // Getters and setters and toString
    public int getUserId() {
        return this.userId;
    }

    public String getUserName() {
        return this.userName;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public String getEmail() {
        return this.email;
    }

    public Gender getGender() {
        return this.gender;
    }

    public Role getRole() {
        return this.role;
    }

    public String getHashedPassword() {
        return this.hashedPassword;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        Pattern pattern = Pattern.compile("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}");
        Matcher mat = pattern.matcher(email);
        if (email.trim().isEmpty() || !mat.matches()) throw new IllegalArgumentException("invalid email");
        this.email = email;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    public void setStars(int stars){
        if(stars < 0){
            throw new IllegalArgumentException("invalid stars!");
        }else {
            this.stars = stars;
        }
    }

    public Integer getStars(){
        return stars;
    }

    public String toString() {
        return "User(userId=" + this.getUserId() + ", userName=" + this.getUserName() + ", firstName=" + this.getFirstName() + ", lastName=" + this.getLastName() + ", email=" + this.getEmail() + ", gender=" + this.getGender() + ", role=" + this.getRole() + ", hashedPassword=" + this.getHashedPassword() + ")";
    }

    public void setBirthdate(LocalDate date){
        this.birthdate = date;
    }

    public void setLevel(String level){
        this.level = level;
    }

    public void subtractStar() {
        this.stars --;
    }

    public String getLevel() {
        return level;
    }

    public String toJsonString(){
        JSONObject user = new JSONObject(this);
        return user.toString();
    }

    public void setReceivingEmails(boolean state){
        this.receivingEmails = state;
    }

    public boolean isReceivingEmails() {
        return receivingEmails;
    }
    public boolean getUpdated(){
        return this.updated;
    }

    public void setUpdated(boolean updated){
        this.updated = updated;
    }

    public String formatDate(){
        return  this.birthdate.getDayOfMonth() + "-" + this.birthdate.getMonth().toString();
    }
}
