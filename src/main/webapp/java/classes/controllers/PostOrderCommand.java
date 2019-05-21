package classes.controllers;

import classes.common.Auth;
import classes.dao.DAOFactory;
import classes.dao.OrderDAO;
import classes.models.Order;
import classes.models.Product;
import servlets.RestStatus;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class PostOrderCommand implements Command {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException {

        OrderDAO orderDAO = DAOFactory.getInstance().getOrderDAO();

        Long client_id = Long.parseLong(req.getParameter("client_id"));

        Long product_id = Long.parseLong(req.getParameter("product_id"));

        Long amount = Long.parseLong(req.getParameter("amount"));


        orderDAO.insert(new Order(null, client_id, product_id, amount, 0 ));

        return RestStatus.SUCCESS.toJson();
    }
}
