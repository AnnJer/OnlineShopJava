package classes.controllers;

import classes.common.Auth;
import classes.dao.DAOFactory;
import classes.dao.OrderDAO;
import com.fasterxml.jackson.databind.ObjectMapper;
import servlets.RestStatus;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class GetOrdersCommand implements Command {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException {
        if(!Auth.getInstance().isAdmin(req.getParameter("token"))){

            return RestStatus.ERROR.toJson();
        }

        OrderDAO orderDAO = DAOFactory.getInstance().getOrderDAO();

        ObjectMapper objectMapper = new ObjectMapper();

        return objectMapper.writeValueAsString(orderDAO.getAll());

    }
}
