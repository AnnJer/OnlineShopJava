package classes.dao;

import classes.models.Category;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class CategoryDAO implements DAO<Category> {
    private Connection connection;
    private PreparedStatement preparedStatement;
    private Statement statement;

    private static final String DELETE = "DELETE FROM categories WHERE id=?";
    private static final String FIND_BY_ID = "SELECT * FROM categories WHERE id=?";
    private static final String FIND_ALL = "SELECT * FROM categories ORDER BY id";
    private static final String INSERT = "INSERT INTO categories (name) VALUES (?)";
    private static final String UPDATE = "UPDATE categories SET name=? WHERE id=?";

    public CategoryDAO(Connection connection) {
        this.connection = connection;
    }


    @Override
    public Optional<Category> get(long id) throws SQLException {
        ResultSet resultSet = null;
        try {
            preparedStatement = this.connection.prepareStatement(FIND_BY_ID);
            preparedStatement.setLong(1, id);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return Optional.of(new Category(resultSet.getLong("id"),resultSet.getString("name")));

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
    public List<Category> getAll() {
        List<Category> categories = new LinkedList<Category>();
        ResultSet resultSet = null;
        try {
            statement = this.connection.createStatement();
            resultSet = statement.executeQuery(FIND_ALL);

            while (resultSet.next()) {
                long id = resultSet.getLong("id");
                String name = resultSet.getString("name");

                Category category = new Category(id, name);

                categories.add(category);
            }

            return categories;
        } catch (SQLException ex){
            ex.printStackTrace();

        }finally{
            getFinally(resultSet, statement);
        }

        return null;
    }

    @Override
    public void insert(Category category) {
        try{
            preparedStatement = this.connection.prepareStatement(INSERT);

            preparedStatement.setString(1, category.getName());


            preparedStatement.executeUpdate();
            preparedStatement.close();
            System.out.println("Category with following details was inserted in DB: " + category.toString());


        } catch (SQLException ex) {
            ex.printStackTrace();
        }finally{
            getFinally(null, statement);
        }
    }

    @Override
    public void update(Category category) {
        try{
            preparedStatement = this.connection.prepareStatement(UPDATE);

            preparedStatement.setString(1, category.getName());
            preparedStatement.setLong(2, category.getId());

            preparedStatement.executeUpdate();
            preparedStatement.close();
            System.out.println("Category with following details was updated in DB: " + category.toString());


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

            System.out.println("Category with id: " + id + " was successfully deleted from DB.");

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
