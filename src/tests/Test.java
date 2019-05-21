import classes.dao.DAOFactory;
import classes.dao.UserDAO;
import classes.models.Category;
import classes.models.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.tomcat.jdbc.pool.DataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class Test {



    @org.junit.Test
    public void test() {

        DataSource ds = new DataSource();
        ds.setDriverClassName("com.mysql.cj.jdbc.Driver");
        ds.setUrl("jdbc:mysql://localhost:3306/online_shop");
        ds.setUsername("root");
        ds.setPassword("54321_cvb");
        ds.setInitialSize(3);
        ds.setMinIdle(3);
        ds.setMaxIdle(5);
        ds.setMaxActive(30);
        try {
            Connection connection = ds.getConnection();
            Statement statement = connection.createStatement();
            statement.execute("select  * from users (name, email, password, is_admin, email_confirmation) values ('slava','annjer@gmal.com', '1111', 1, 1 )");


            } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @org.junit.Test
    public void ts() throws SQLException, JsonProcessingException {

//        Category category = new Category(4, "masha");
//        ObjectMapper objectMapper = new ObjectMapper();
//
//        System.out.println(objectMapper.writeValueAsString(category));

    }

}
