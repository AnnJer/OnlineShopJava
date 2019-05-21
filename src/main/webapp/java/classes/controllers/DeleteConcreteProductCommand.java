package classes.controllers;

import classes.common.Auth;
import classes.dao.DAOFactory;
import classes.dao.ProductDAO;
import servlets.RestStatus;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class DeleteConcreteProductCommand implements Command {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException {
        if(!Auth.getInstance().isAdmin(req.getParameter("token"))){

            return RestStatus.ERROR.toJson();
        }

        ProductDAO productDAO = DAOFactory.getInstance().getProductDAO();

        Long  id = Long.parseLong((String) req.getAttribute("id"));

        productDAO.delete(id);

        return RestStatus.SUCCESS.toJson();
    }
}
