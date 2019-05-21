package classes.controllers;

import classes.dao.DAOFactory;
import classes.dao.SessionDAO;
import servlets.RestStatus;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class PostSignOutCommand  implements Command{
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException {
        SessionDAO sessionDAO = DAOFactory.getInstance().getSessionDAO();

        String token = req.getParameter("token");
        System.out.println(token);
        try {
            sessionDAO.deleteByToken(token);
            return RestStatus.SUCCESS.toJson();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return RestStatus.ERROR.toJson();
    }
}
