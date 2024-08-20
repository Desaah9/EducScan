package fr.isen.perigot.educscan;

public class User {
    String name, email, username, password;
    Boolean isStudent;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public Boolean getIsStudent() {
        return isStudent;
    }
    public void setIsStudent(Boolean isStudent) {
        this.isStudent = isStudent;
    }
    public User(String name, String email, String username, String password, Boolean isStudent) {
        this.name = name;
        this.email = email;
        this.username = username;
        this.password = password;
        this.isStudent = isStudent;
    }
    public User() {
    }
}
