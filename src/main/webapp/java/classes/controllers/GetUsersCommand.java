package classes.controllers;

import classes.common.Auth;
import classes.dao.DAOFactory;
import classes.dao.UserDAO;
import com.fasterxml.jackson.databind.ObjectMapper;
import servlets.RestStatus;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class GetUsersCommand implements Command {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException {

        if(!Auth.getInstance().isAdmin(req.getParameter("token"))){

            return RestStatus.ERROR.toJson();
        }

        UserDAO userDAO = DAOFactory.getInstance().getUserDAO();

        ObjectMapper objectMapper = new ObjectMapper();

        return objectMapper.writeValueAsString(userDAO.getAll());

    }
}
