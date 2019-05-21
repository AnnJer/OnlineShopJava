package classes.dao;

import classes.models.Session;
import classes.models.UserSession;

import java.sql.*;
import java.util.List;
import java.util.Optional;

public class SessionDAO implements DAO<Session> {
    private Connection connection;
    private PreparedStatement preparedStatement;
    private Statement statement;

    private static final String DELETE_BY_TOKEN = "DELETE FROM sessions WHERE token=?";
    private static final String DELETE_BY_USERID = "DELETE FROM sessions WHERE user_id=?";
    private static final String INSERT = "INSERT INTO sessions (user_id, token) VALUES (?, ?)";
    private static final String FIND_BY_TOKEN = "SELECT * FROM sessions WHERE token=?";
    private static final String FIND_BY_USERID = "SELECT * FROM sessions WHERE user_id=?";
    private static final String JOIN_SESSION_USER = "SELECT * FROM sessions JOIN users ON sessions.user_id = users.id WHERE token = ?";
    public SessionDAO(Connection connection) {this.connection = connection; }


    @Override
    public Optional<Session> get(long id) throws SQLException {
        return Optional.empty();
    }

    @Override
    public List<Session> getAll() {
        return null;
    }
    public  Session getByUserId(Long user_id) {
        ResultSet resultSet = null;
        try {
            preparedStatement = this.connection.prepareStatement(FIND_BY_USERID);
            preparedStatement.setLong(1, user_id);
            resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                Long id = resultSet.getLong("id");
                Long userId = resultSet.getLong("user_id");
                String concreteToken = resultSet.getString("token");
                return new Session(id, userId, concreteToken);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public  UserSession getByToken(String token) {
        ResultSet resultSet = null;
        try {
            preparedStatement = this.connection.prepareStatement(JOIN_SESSION_USER);
            preparedStatement.setString(1, token);
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                Long user_id = resultSet.getLong("user_id");
                Boolean is_admin = resultSet.getBoolean("is_admin");
                String concreteToken = resultSet.getString("token");

                return new UserSession(user_id, is_admin, concreteToken);
            }


        } catch (SQLException e) {
            e.printStackTrace();

        }finally{
            getFinally(resultSet, preparedStatement);
        }
        return null;
    }

    public void deleteByToken(String token) throws Exception {


        try{
            preparedStatement = this.connection.prepareStatement(DELETE_BY_TOKEN);

            preparedStatement.setString(1, token);

            preparedStatement.executeUpdate();
            preparedStatement.close();

            System.out.println("Session with token: " + token + " was successfully deleted from DB.");

        } catch (SQLException ex) {
            ex.printStackTrace();
        }finally{
            getFinally(null, preparedStatement);
        }
    }

    @Override
    public void insert(Session session) {
        try{
            preparedStatement = this.connection.prepareStatement(INSERT);

            preparedStatement.setLong(1, session.getUser_id());
            preparedStatement.setString(2, session.getToken());


            preparedStatement.executeUpdate();
            preparedStatement.close();
            System.out.println("Session with following details was inserted in DB: " + session.toString());


        } catch (SQLException ex) {
            ex.printStackTrace();
        }finally{
            getFinally(null, preparedStatement);
        }
    }

    @Override
    public void update(Session session) {

    }

    @Override
    public void delete(long id) {

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
