package classes.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.nio.charset.Charset;
import java.util.Random;

public class Session {
    private Long id;
    private Long user_id;
    private String token;
    public static final String characters =
            "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890";

    @JsonCreator
    public Session(@JsonProperty("id") Long id, @JsonProperty("user_id")Long user_id,
                   @JsonProperty("token") String token) {
        this.id = id;
        this.user_id = user_id;
        this.token = token;
    }
    public static Session createSession(Long user_id){
        Random random = new Random();
        char[] text = new char[5];
        for (int i = 0; i < 5; i++) {
            text[i] = characters.charAt(random.nextInt(characters.length()));
        }

        return new Session(null, user_id, new String(text));
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "Session{" +
                "id=" + id +
                ", user_id=" + user_id +
                ", token='" + token + '\'' +
                '}';
    }
}
