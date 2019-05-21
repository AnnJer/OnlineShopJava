package servlets;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class RestException extends Exception {

    @JsonCreator
    public RestException(@JsonProperty("error") String message) {
        super(message);
    }
}
