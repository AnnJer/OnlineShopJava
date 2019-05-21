package classes.controllers;

import classes.common.Auth;
import classes.dao.BlockListDAO;
import classes.dao.DAOFactory;
import classes.models.BlockList;
import servlets.RestStatus;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class PostBlockListCommand implements Command {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException {
        if(!Auth.getInstance().isAdmin(req.getParameter("token"))){

            return RestStatus.ERROR.toJson();
        }
        BlockListDAO blockListDAO =  DAOFactory.getInstance().getBlockListDAO();

        Long user_id = Long.valueOf(req.getParameter("user_id"));

        blockListDAO.insert(new BlockList(null, user_id, null));

        return RestStatus.SUCCESS.toJson();
    }
}
