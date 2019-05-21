package classes.controllers;

import classes.common.Auth;
import classes.dao.BlockListDAO;
import classes.dao.DAOFactory;
import servlets.RestStatus;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DeleteFromBlockListCommand implements Command {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException {

        System.out.println(req.getHeader("token"));
        if(!Auth.getInstance().isAdmin(req.getHeader("token"))){

            return RestStatus.ERROR.toJson();
        }

        BlockListDAO blockListDAO =  DAOFactory.getInstance().getBlockListDAO();

        Long  id = Long.parseLong((String) req.getAttribute("id"));

        blockListDAO.delete(id);

        return RestStatus.SUCCESS.toJson();

    }
}
