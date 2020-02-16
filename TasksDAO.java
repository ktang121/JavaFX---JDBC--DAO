package todolist;
import java.sql.SQLException;
import java.util.List;


public interface TasksDAO extends DAO {
    public List<Tasks> loadTasks() throws SQLException, ClassNotFoundException;
}
