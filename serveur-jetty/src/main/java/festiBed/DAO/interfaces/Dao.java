package festiBed.DAO.interfaces;

import java.sql.SQLException;
import java.util.List;

public interface Dao<T> {
    
    public int save(T bObject) throws SQLException;
    public void update(T bObject) throws SQLException;
    public void update(int id) throws SQLException;
    public T getById(int id) throws SQLException;
    public List<T> getAll() throws SQLException;
    public List<T> getAll(int limit, int offset) throws SQLException;
    public void destroy(T bObject) throws SQLException;
    public void destroy(int id) throws SQLException;
}