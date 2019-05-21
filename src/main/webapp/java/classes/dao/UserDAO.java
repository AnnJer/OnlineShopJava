package classes.dao;
import classes.models.User;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class UserDAO implements DAO<User> {

    private Connection connection;
    private PreparedStatement preparedStatement;
    private Statement statement;

    private static final String AUTHENTICATE = "SELECT * FROM users WHERE email=? and password=?";
    private static final String DELETE = "DELETE FROM users WHERE id=?";
    private static final String FIND_BY_ID = "SELECT * FROM users WHERE id=?";
    private static final String FIND_BY_PARAMS = "SELECT * FROM users WHERE email=? AND password=?";
    private static final String FIND_ALL = "SELECT * FROM users ORDER BY id";
    private static final String INSERT = "INSERT INTO users (name, email,  password, is_admin, email_confirmation) VALUES (?, ?, ?, ?, ?)";
    private static final String UPDATE = "UPDATE users SET name=?, email=?, password=?, is_admin=?, email_confirmation=? WHERE id=?";

    public UserDAO(Connection connection) {
        this.connection = connection;
    }


    @Override
    public Optional<User> get(long id) throws SQLException {
        ResultSet resultSet = null;

        try {
            preparedStatement = this.connection.prepareStatement(FIND_BY_ID);
            preparedStatement.setLong(1, id);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return Optional.of(new User(resultSet.getInt("id"), resultSet.getString("name"),
                        resultSet.getString("email"),resultSet.getString("password"),
                        resultSet.getBoolean("is_admin"), resultSet.getInt("email_confirmation") ));

            } else {
                return Optional.empty();
            }
        } catch (SQLException ex){
            ex.printStackTrace();
        } finally{
            getFinally(resultSet, preparedStatement);
        }
        return Optional.empty();
    }



    @Override
    public List<User> getAll() {
        List<User> users = new LinkedList<User>();
        ResultSet resultSet = null;
        try {
            statement = this.connection.createStatement();
            resultSet = statement.executeQuery(FIND_ALL);

            while (resultSet.next()) {
                long id = resultSet.getLong("id");
                String name = resultSet.getString("name");
                String email = resultSet.getString("email");
                String password = resultSet.getString("password");
                boolean is_admin =  resultSet.getBoolean("is_admin");
                int email_confirmation = resultSet.getInt("email_confirmation");

                User user = new User(id, name, email, password, is_admin, email_confirmation);

                users.add(user);
            }

            return users;
        } catch (SQLException ex){
            ex.printStackTrace();

        }finally{
            getFinally(resultSet, statement);
        }
        return null;

    }

    public User getByEmailPassword(String email, String password){

        ResultSet resultSet = null;
        try {
            preparedStatement = this.connection.prepareStatement(FIND_BY_PARAMS);
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);

            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return new User(resultSet.getInt("id"), resultSet.getString("name"),
                        resultSet.getString("email"),resultSet.getString("password"),
                        resultSet.getBoolean("is_admin"), resultSet.getInt("email_confirmation") );

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;

    }

    @Override
    public void insert(User user) {

        try{
            preparedStatement = this.connection.prepareStatement(INSERT);

            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.setString(3, user.getPasword());
            preparedStatement.setBoolean(4, user.isAdmin());
            preparedStatement.setInt(5, user.getEmailConfirmation());

            preparedStatement.executeUpdate();
            preparedStatement.close();
            System.out.println("User with following details was inserted in DB: " + user.toString());


        } catch (SQLException ex) {
            ex.printStackTrace();
        }finally{
            getFinally(null, preparedStatement);
        }

    }


    @Override
    public void update(User user) {

        try{
            preparedStatement = this.connection.prepareStatement(UPDATE);

            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.setString(3, user.getPasword());
            preparedStatement.setBoolean(4, user.isAdmin());
            preparedStatement.setInt(5, user.getEmailConfirmation());
            preparedStatement.setLong(6, user.getId());

            preparedStatement.executeUpdate();
            preparedStatement.close();
            System.out.println("User with following details was updated in DB: " + user.toString());


        } catch (SQLException ex) {
            ex.printStackTrace();
        }finally{
            getFinally(null, statement);
        }
    }

    @Override
    public void delete(long id) {
        try{
            preparedStatement = this.connection.prepareStatement(DELETE);

            preparedStatement.setLong(1, id);

            preparedStatement.executeUpdate();
            preparedStatement.close();

            System.out.println("User with id: " + id + " was sucesfully deleted from DB.");

        } catch (SQLException ex) {
            ex.printStackTrace();
        }finally{
            getFinally(null, statement);
        }
    }


    private void getFinally(ResultSet resultSet, Statement statement) {
        try {
            if(resultSet != null) resultSet.close();
            if(statement != null) statement.close();
            if(connection != null) connection.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }


    private void getFinally(ResultSet resultSet, PreparedStatement preparedStatement) {
        try {
            if(resultSet != null) resultSet.close();
            if(preparedStatement != null) preparedStatement.close();
            if(connection != null) connection.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
