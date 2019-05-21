package classes.dao;

import classes.models.Order;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class OrderDAO implements DAO<Order>{
    private Connection connection;
    private PreparedStatement preparedStatement;
    private Statement statement;

    private static final String DELETE = "DELETE FROM orders WHERE id=?";
    private static final String FIND_BY_ID = "SELECT * FROM orders WHERE id=?";
    private static final String FIND_ALL = "SELECT * FROM orders ORDER BY id";
    private static final String INSERT = "INSERT INTO orders (client_id, product_id,  amount, payed) VALUES (?, ?, ?, ?)";
    private static final String UPDATE = "UPDATE orders SET  payed=? WHERE id=?";

    public OrderDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Optional<Order> get(long id) throws SQLException {
        ResultSet resultSet = null;
        try {
            preparedStatement = this.connection.prepareStatement(FIND_BY_ID);
            preparedStatement.setLong(1, id);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return Optional.of(new Order(resultSet.getLong("id"),resultSet.getLong("client_id"),
                        resultSet.getLong("product_id"), resultSet.getLong("amount"),
                        resultSet.getInt("payed") ));

            } else {
                return Optional.empty();
            }
        } catch (SQLException ex){
            ex.printStackTrace();
        }finally{
            getFinally(resultSet, preparedStatement);
        }
        return Optional.empty();
    }

    @Override
    public List<Order> getAll() {

        List<Order> orders = new LinkedList<Order>();
        ResultSet resultSet = null;
        try {
            statement = this.connection.createStatement();
            resultSet = statement.executeQuery(FIND_ALL);

            while (resultSet.next()) {
                long id = resultSet.getLong("id");
                long clientId = resultSet.getLong("client_id");
                long productId = resultSet.getLong("product_id");
                long amount = resultSet.getLong("amount");
                int payed =  resultSet.getInt("payed");

                Order order = new Order(id, clientId, productId, amount, payed);

                orders.add(order);
            }

            return orders;
        } catch (SQLException ex){
            ex.printStackTrace();

        }finally{
            getFinally(resultSet, statement);
        }

        return null;
    }

    @Override
    public void insert(Order order) {
        try{
            preparedStatement = this.connection.prepareStatement(INSERT);

            preparedStatement.setLong(1, order.getClientId());
            preparedStatement.setLong(2, order.getProductId());
            preparedStatement.setLong(3, order.getAmount());
            preparedStatement.setInt(4, order.getPayed());

            preparedStatement.executeUpdate();
            preparedStatement.close();
            System.out.println("Order with following details was inserted in DB: " + order.toString());


        } catch (SQLException ex) {
            ex.printStackTrace();
        }finally{
            getFinally(null, statement);
        }
    }

    @Override
    public void update(Order order) {
        try{
            preparedStatement = this.connection.prepareStatement(UPDATE);


            preparedStatement.setInt(1, order.getPayed());
            preparedStatement.setLong(2, order.getId());

            preparedStatement.executeUpdate();
            preparedStatement.close();
            System.out.println("Order with following details was updated in DB: " + order.toString());


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

            System.out.println("Order with id: " + id + " was successfully deleted from DB.");

        } catch (SQLException ex) {
            ex.printStackTrace();
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
