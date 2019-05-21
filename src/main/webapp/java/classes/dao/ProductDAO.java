package classes.dao;

import classes.models.Product;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class ProductDAO implements DAO<Product> {

    private Connection connection;
    private PreparedStatement preparedStatement;
    private Statement statement;

    private static final String DELETE = "DELETE FROM products WHERE id=?";
    private static final String FIND_BY_ID = "SELECT * FROM products WHERE id=?";
    private static final String FIND_BY_CATEGORY = "SELECT * FROM products where category_id = (SELECT id from categories where name = ? )";
    private static final String FIND_ALL = "SELECT * FROM products ORDER BY id";
    private static final String INSERT = "INSERT INTO products (name, amount,  price, category_id) VALUES (?, ?, ?, ?)";
    private static final String UPDATE = "UPDATE products SET name=?, amount=?, price=?, category_id=? WHERE id=?";


    public List<Product> getByCategory(String nameOfCondition) throws SQLException {
        List<Product> products = new LinkedList<Product>();
        ResultSet resultSet = null;
        try {
            preparedStatement = this.connection.prepareStatement(FIND_BY_CATEGORY);
            preparedStatement.setString(1, nameOfCondition);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                long id = resultSet.getLong("id");
                String name = resultSet.getString("name");
                long amount = resultSet.getLong("amount");
                double price =  resultSet.getDouble("price");
                long categoryId = resultSet.getLong("category_id");

                Product product = new Product(id, name, amount, price, categoryId);
                products.add(product);
            }

            return products;
        } catch (SQLException ex){
            ex.printStackTrace();
        }finally{
            getFinally(resultSet, preparedStatement);
        }
        return products;
    }

    public ProductDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Optional<Product> get(long id) throws SQLException {
        ResultSet resultSet = null;
        try {
            preparedStatement = this.connection.prepareStatement(FIND_BY_ID);
            preparedStatement.setLong(1, id);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return Optional.of(new Product(resultSet.getLong("id"), resultSet.getString("name"),
                        resultSet.getLong("amount"), resultSet.getDouble("price"),
                        resultSet.getLong("category_id") ));

            } else {
                return Optional.empty();
            }
        } catch (SQLException ex){
            ex.printStackTrace();
            return Optional.empty();
        }finally{
            getFinally(resultSet, preparedStatement);
        }

    }

    @Override
    public List<Product> getAll() {
        List<Product> products = new LinkedList<Product>();
        ResultSet resultSet = null;
        try {
            statement = this.connection.createStatement();
            resultSet = statement.executeQuery(FIND_ALL);

            while (resultSet.next()) {
                long id = resultSet.getLong("id");
                String name = resultSet.getString("name");
                long amount = resultSet.getLong("amount");
                double price =  resultSet.getDouble("price");
                long categoryId = resultSet.getLong("category_id");

                Product product = new Product(id, name, amount, price, categoryId);

                products.add(product);
            }

            return products;
        } catch (SQLException ex){
            ex.printStackTrace();

        }finally{
            getFinally(resultSet, statement);
        }

        return null;
    }

    @Override
    public void insert(Product product) {
        try{
            preparedStatement = this.connection.prepareStatement(INSERT);

            preparedStatement.setString(1, product.getName());
            preparedStatement.setLong(2, product.getAmount());
            preparedStatement.setDouble(3, product.getPrice());
            preparedStatement.setLong(4, product.getCategoryId());

            preparedStatement.executeUpdate();
            preparedStatement.close();
            System.out.println("Product with following details was inserted in DB: " + product.toString());


        } catch (SQLException ex) {
            ex.printStackTrace();
        }finally{
            getFinally(null, statement);
        }
    }

    @Override
    public void update(Product product) {
        try{
            preparedStatement = this.connection.prepareStatement(UPDATE);

            preparedStatement.setString(1, product.getName());
            preparedStatement.setLong(2, product.getAmount());
            preparedStatement.setDouble(3, product.getPrice());
            preparedStatement.setLong(4, product.getCategoryId());
            preparedStatement.setLong(5, product.getId());

            preparedStatement.executeUpdate();
            preparedStatement.close();
            System.out.println("Product with following details was updated in DB: " + product.toString());


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

            System.out.println("Product with id: " + id + " was successfully deleted from DB.");

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
