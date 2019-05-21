package classes.controllers;

import classes.common.Auth;
import classes.dao.DAOFactory;
import classes.dao.ProductDAO;
import classes.models.Product;
import servlets.RestStatus;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class PostProductCommand implements Command{
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException {



        ProductDAO productDAO = DAOFactory.getInstance().getProductDAO();

        String name = req.getParameter("name");

        Long amount = Long.parseLong(req.getParameter("amount"));

        double price = Double.parseDouble(req.getParameter("price"));

        Long categoryId = Long.parseLong(req.getParameter("categoryId"));

        productDAO.insert(new Product(null, name, amount, price, categoryId));

        return RestStatus.SUCCESS.toJson();
    }
}
