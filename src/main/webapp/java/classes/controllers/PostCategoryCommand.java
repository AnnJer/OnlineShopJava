package classes.controllers;

import classes.common.Auth;
import classes.dao.CategoryDAO;
import classes.dao.DAOFactory;
import classes.models.Category;
import com.fasterxml.jackson.core.JsonProcessingException;
import servlets.RestStatus;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;

public class PostCategoryCommand implements Command {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws SQLException, JsonProcessingException {

        if(!Auth.getInstance().isAdmin(req.getParameter("token"))){

            return RestStatus.ERROR.toJson();
        }

        CategoryDAO categoryDAO = DAOFactory.getInstance().getCategoryDAO();

        String name = req.getParameter("name");

        categoryDAO.insert(new Category(null, name));

        return RestStatus.SUCCESS.toJson();

    }
}
