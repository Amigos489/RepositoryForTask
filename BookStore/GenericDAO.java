import java.sql.SQLException;
import java.util.List;

public interface GenericDAO<T> {
    public T getByID(int id);
    public List<T> getAll();

    public void save(T entity) throws SQLException;
    public void update(T entity) throws SQLException;

    public void delete(T entity);
    public void deleteByID(int id);
}