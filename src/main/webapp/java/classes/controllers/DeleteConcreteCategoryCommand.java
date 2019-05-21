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

public class DeleteConcreteCategoryCommand implements Command {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws SQLException, JsonProcessingException {

        if(!Auth.getInstance().isAdmin(req.getParameter("token"))){

            return RestStatus.ERROR.toJson();
        }

       CategoryDAO categoryDAO = DAOFactory.getInstance().getCategoryDAO();

       Long  id = Long.parseLong((String) req.getAttribute("id"));

       categoryDAO.delete(id);

       return RestStatus.SUCCESS.toJson();

    }
}
