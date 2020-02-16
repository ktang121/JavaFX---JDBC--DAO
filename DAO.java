package todolist;
import java.sql.SQLException;

public interface DAO {
    
    public void getConnection() throws SQLException, ClassNotFoundException;
    public void endConnection() throws SQLException;
    
}
