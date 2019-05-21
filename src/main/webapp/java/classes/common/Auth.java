package classes.common;

import classes.dao.DAOFactory;
import classes.dao.SessionDAO;
import classes.dao.UserDAO;
import classes.models.Session;
import classes.models.User;
import classes.models.UserSession;

import java.sql.SQLException;
import java.util.Optional;

public class Auth {
    private static Auth ourInstance = new Auth();

    public static Auth getInstance() {
        return ourInstance;
    }

    private Auth() {
    }


    public boolean isAdmin(String token) throws SQLException {
        if(token == null){
            return false;
        }
        SessionDAO sessionDAO = DAOFactory.getInstance().getSessionDAO();

        UserSession userSession = sessionDAO.getByToken(token);

        if(userSession != null){

            return userSession.getIsAdmin();

        }
        else {
            return false;
        }

    }
}
