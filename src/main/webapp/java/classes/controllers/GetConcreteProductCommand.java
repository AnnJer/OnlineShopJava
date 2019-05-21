package classes.controllers;

import classes.dao.DAOFactory;
import classes.dao.ProductDAO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class GetConcreteProductCommand implements Command {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException {

        ProductDAO productDAO = DAOFactory.getInstance().getProductDAO();

        resp.getWriter().println(req.getAttribute("id"));

        Long id = Long.parseLong((String) req.getAttribute("id"));

        ObjectMapper objectMapper = new ObjectMapper();

        return objectMapper.writeValueAsString(productDAO.get(id));

    }
}
