package classes.controllers;

import classes.dao.CategoryDAO;
import classes.dao.DAOFactory;
import classes.dao.ProductDAO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class GetProductsCommand implements Command{
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException {

       ProductDAO productDAO = DAOFactory.getInstance().getProductDAO();

       ObjectMapper objectMapper = new ObjectMapper();
       if(req.getAttribute("categoryName")!= null){

           return objectMapper.writeValueAsString(productDAO.getByCategory(req.getParameter("byCategoryName")));
       }
       else{
           return objectMapper.writeValueAsString(productDAO.getAll());

       }
    }
}
