package classes.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface DAO<T> {
    Optional<T> get(long id) throws SQLException;

    List<T> getAll();

    void insert(T t);

    void update(T t);

    void delete(long id);
}
