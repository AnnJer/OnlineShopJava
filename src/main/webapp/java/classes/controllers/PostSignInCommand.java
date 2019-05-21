package classes.controllers;

import classes.dao.DAOFactory;
import classes.dao.SessionDAO;
import classes.dao.UserDAO;
import classes.models.Session;
import classes.models.User;
import classes.models.UserSession;
import com.fasterxml.jackson.databind.ObjectMapper;
import servlets.RestStatus;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class PostSignInCommand implements Command {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException {
        UserDAO userDAO = DAOFactory.getInstance().getUserDAO();
        ObjectMapper objectMapper = new ObjectMapper();

        String email = req.getParameter("email");
        String password = req.getParameter("password");

        System.out.println(email+" "+ password);
        User user = userDAO.getByEmailPassword(email, password);

        if(user != null){

            Session session = DAOFactory.getInstance().getSessionDAO().getByUserId(user.getId());

            if(session != null){

                return objectMapper.writeValueAsString(new UserSession(
                       user.getId(), user.isAdmin(), session.getToken()
                ));

            } else {
                session = Session.createSession(user.getId());
                DAOFactory.getInstance().getSessionDAO().insert(session);

                return objectMapper.writeValueAsString(new UserSession(
                        user.getId(), user.isAdmin(), session.getToken()
                ));
            }

        }
        else {
//TODO:complete
            return RestStatus.ERROR.toJson();
        }




    }
}
