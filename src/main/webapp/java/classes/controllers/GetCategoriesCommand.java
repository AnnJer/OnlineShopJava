package classes.controllers;

import classes.dao.CategoryDAO;
import classes.dao.DAOFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.Map;

public class GetCategoriesCommand implements Command {


    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws SQLException, JsonProcessingException {

       ObjectMapper objectMapper = new ObjectMapper();

       return objectMapper.writeValueAsString(DAOFactory.getInstance().getCategoryDAO().getAll());

    }
}
