package classes.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class BlockList {

    private Long id;
    private Long userId;
    private String reason;

    @JsonCreator
    public BlockList(@JsonProperty("id") Long id, @JsonProperty("user_id") Long user_id,
                     @JsonProperty("reason") String reason) {
        this.id = id;
        this.userId = user_id;
        this.reason = reason;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUser_id() {
        return userId;
    }

    public void setUser_id(Long user_id) {
        this.userId = user_id;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    @Override
    public String toString() {
        return "BlockListDAO{" +
                "id=" + id +
                ", user_id=" + userId +
                ", reason='" + reason + '\'' +
                '}';
    }
}
