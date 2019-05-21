package servlets;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public enum  RestStatus {
    ERROR("user not found"),
    SUCCESS("success");


    private String status;

    @JsonCreator
    RestStatus(@JsonProperty("status") String status) {
        this.status = status;
    }

    public String toJson() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();

        return objectMapper.writeValueAsString(this);
    }
}
