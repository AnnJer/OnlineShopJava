package classes.controllers;

import classes.common.Auth;
import classes.dao.DAOFactory;
import classes.dao.OrderDAO;
import classes.models.Order;
import servlets.RestStatus;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class PutConcreteOrderCommand implements Command {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException {

        System.out.println(req.getParameter("token"));
        if(!Auth.getInstance().isAdmin(req.getParameter("token"))){

            return RestStatus.ERROR.toJson();
        }

        OrderDAO orderDAO = DAOFactory.getInstance().getOrderDAO();

        Long id = Long.parseLong((String) req.getAttribute("id"));


        Integer payed = Integer.parseInt(req.getParameter("payed"));

         orderDAO.update(new Order(id, null, null,  null, payed));

        return RestStatus.SUCCESS.toJson();
    }
}
