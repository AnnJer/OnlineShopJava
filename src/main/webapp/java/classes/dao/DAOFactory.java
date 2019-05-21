package classes.dao;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class DAOFactory {
    private static DAOFactory ourInstance;

    public static DAOFactory getInstance() throws SQLException {
        if (ourInstance == null){
            ourInstance = new DAOFactory();
        }
        return ourInstance;
    }

    private DAOFactory() throws SQLException {

    }

    public UserDAO getUserDAO() throws SQLException {
        return new UserDAO(DataSourceFactory.getInstance().getConnection());
    }

    public ProductDAO getProductDAO() throws SQLException {
        return new ProductDAO(DataSourceFactory.getInstance().getConnection());
    }
    public OrderDAO getOrderDAO() throws SQLException {
        return new OrderDAO(DataSourceFactory.getInstance().getConnection());
    }
    public CategoryDAO getCategoryDAO() throws SQLException {
        return new CategoryDAO(DataSourceFactory.getInstance().getConnection());
    }
    public BlockListDAO getBlockListDAO() throws SQLException {
        return new BlockListDAO(DataSourceFactory.getInstance().getConnection());
    }

    public SessionDAO getSessionDAO() throws SQLException {
        return new SessionDAO(DataSourceFactory.getInstance().getConnection());
    }
}
