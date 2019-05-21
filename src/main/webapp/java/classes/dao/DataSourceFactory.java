package classes.dao;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import javax.naming.NamingException;
import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class DataSourceFactory {
    private static final DataSourceFactory dsf = new DataSourceFactory();
    private static DataSource ds;

    public static DataSourceFactory getInstance() {
        return dsf;
    }
    
    private DataSourceFactory() {
        ds = getMySQLDataSource();
    }

    public  DataSource getMySQLDataSource() {
        Properties props = new Properties();
        FileInputStream fis = null;
        MysqlDataSource mysqlDS = null;
        try {
            fis = new FileInputStream("/home/ann/IdeaProjects/first_laba/src/main/webapp/java/classes/dao/db.properties");
            props.load(fis);
            mysqlDS = new MysqlDataSource();
            mysqlDS.setURL(props.getProperty("MYSQL_DB_URL"));
            mysqlDS.setUser(props.getProperty("MYSQL_DB_USERNAME"));
            mysqlDS.setPassword(props.getProperty("MYSQL_DB_PASSWORD"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mysqlDS;

    }

    public Connection getConnection() throws SQLException {
        Connection connection = ds.getConnection();
        return connection;
    }

}
