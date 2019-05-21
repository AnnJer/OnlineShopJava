package classes.models;

public class UserSession {

    private Long user_id;
    private boolean is_admin;
    private String token;

    public UserSession(Long user_id, boolean is_admin, String token) {
        this.user_id = user_id;
        this.is_admin = is_admin;
        this.token = token;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public boolean getIsAdmin() {
        return is_admin;
    }

    public void setIs_admin(boolean is_admin) {
        this.is_admin = is_admin;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
