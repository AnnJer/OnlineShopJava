package classes.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class User {
    private long id;
    private String name;
    private String email;
    private String pasword;
    private boolean isAdmin;
    private int emailConfirmation;

    @JsonCreator
    public User(@JsonProperty("id") long id, @JsonProperty("name") String name,
                @JsonProperty("email") String email, @JsonProperty("password") String pasword,
                @JsonProperty("isAdmin") boolean isAdmin, @JsonProperty("emailConfirmation") int emailConfirmation) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.pasword = pasword;
        this.isAdmin = isAdmin;
        this.emailConfirmation = emailConfirmation;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

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

    public String getPasword() {
        return pasword;
    }

    public void setPasword(String pasword) {
        this.pasword = pasword;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public int getEmailConfirmation() {
        return emailConfirmation;
    }

    public void setEmailConfirmation(int emailConfirmation) {
        this.emailConfirmation = emailConfirmation;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", pasword='" + pasword + '\'' +
                ", isAdmin=" + isAdmin +
                ", emailConfirmation=" + emailConfirmation +
                '}';
    }
}
