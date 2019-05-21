package classes.dao;

import classes.models.BlockList;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class BlockListDAO implements DAO<BlockList> {

    private Connection connection;
    private PreparedStatement preparedStatement;
    private Statement statement;

    private static final String DELETE = "DELETE FROM block_list WHERE id=?";
    private static final String FIND_BY_ID = "SELECT * FROM block_list WHERE id=?";
    private static final String FIND_ALL = "SELECT * FROM block_list ORDER BY id";
    private static final String INSERT = "INSERT INTO block_list (user_id, reason) VALUES (?, ?)";
    private static final String UPDATE = "UPDATE block_list SET user_id=?, reason=? WHERE id=?";

    public BlockListDAO(Connection connection) {
        this.connection = connection;
    }


    @Override
    public Optional<BlockList> get(long id) throws SQLException {
        ResultSet resultSet = null;
        try {
            preparedStatement = this.connection.prepareStatement(FIND_BY_ID);
            preparedStatement.setLong(1, id);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return Optional.of(new BlockList(resultSet.getLong("id"),resultSet.getLong("user_id"),
                        resultSet.getString("reason")));

            } else {
                return Optional.empty();
            }
        } catch (SQLException ex){
            ex.printStackTrace();
        }finally{
            getFinally(resultSet, preparedStatement);
        }
        return Optional.empty();    }

    @Override
    public List<BlockList> getAll() {
        List<BlockList> blockLists = new LinkedList<BlockList>();
        ResultSet resultSet = null;
        try {
            statement = this.connection.createStatement();
            resultSet = statement.executeQuery(FIND_ALL);

            while (resultSet.next()) {
                long id = resultSet.getLong("id");
                long userId = resultSet.getLong("user_id");
                String reason = resultSet.getString("reason");

                BlockList blockList = new BlockList(id, userId, reason);

                blockLists.add(blockList);
            }

            return blockLists;
        } catch (SQLException ex){
            ex.printStackTrace();

        }finally{
            getFinally(resultSet, statement);
        }

        return null;
    }

    @Override
    public void insert(BlockList blockList) {
        try{
            preparedStatement = this.connection.prepareStatement(INSERT);
            preparedStatement.setLong(1, blockList.getUser_id());
            preparedStatement.setString(2, blockList.getReason());


            preparedStatement.executeUpdate();
            preparedStatement.close();
            System.out.println("BlockList with following details was inserted in DB: " + blockList.toString());


        } catch (SQLException ex) {
            ex.printStackTrace();
        }finally{
            getFinally(null, statement);
        }
    }

    @Override
    public void update(BlockList blockList) {
        try{
            preparedStatement = this.connection.prepareStatement(UPDATE);

            preparedStatement.setLong(1, blockList.getUser_id());
            preparedStatement.setString(2, blockList.getReason());
            preparedStatement.setLong(1, blockList.getId());

            preparedStatement.executeUpdate();
            preparedStatement.close();
            System.out.println("BlockList with following details was updated in DB: " + blockList.toString());


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

            System.out.println("BlockList with id: " + id + " was successfully deleted from DB.");

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
