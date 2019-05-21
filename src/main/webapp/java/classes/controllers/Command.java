package classes.controllers;
import com.fasterxml.jackson.core.JsonProcessingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

public interface Command {

public String execute(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException;
}
