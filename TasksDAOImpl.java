package todolist;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class TasksDAOImpl implements TasksDAO {
    
    private Connection connection;
    private static final Logger logger = Logger.getLogger(TasksDAOImpl.class.getName());
    private final String DB_DRIVER = "oracle.jdbc.driver.OracleDriver";
    private final String DB_URL = "jdbc:oracle:thin:@localhost:1521:xe";
    
    public void initialize() {
        
    }
    @Override
    public void getConnection() throws SQLException, ClassNotFoundException {
       
        try {
            Class.forName(DB_DRIVER);
            connection = DriverManager.getConnection(DB_URL, Main.getUserName(), Main.getPassword());
        }
        catch(ClassNotFoundException se) {
            logger.log(Level.SEVERE, se.getMessage());
        }
    }
    
    @Override
    public void endConnection() throws SQLException {
        
        try {
            if(connection != null && !connection.isClosed()) {
                connection.close();
            }
        }
        catch(SQLException se) {
            logger.log(Level.SEVERE, se.getMessage());
        }
    }

    private Tasks getTasksSet(ResultSet rs) throws SQLException {
        Tasks tasks = new Tasks();
        try {
       
            tasks.setTaskId(rs.getInt(1));
            tasks.setTaskDesc(rs.getString(2));
            tasks.setTaskAmt(rs.getDouble(3));
            tasks.setTaskDate(rs.getDate(4));
        }
        catch(SQLException se)
        {
            logger.log(Level.SEVERE, se.getMessage());
        }
        return tasks;
    }
    
    @Override
    public List<Tasks> loadTasks() throws SQLException, ClassNotFoundException {
       
        String queryStmt = "SELECT * FROM TASKS";
        List<Tasks> tasksList = new ArrayList<>();
        Statement stmt = null;
        ResultSet resultSet = null;
        try {
            getConnection();
            stmt = connection.createStatement();
            resultSet = stmt.executeQuery(queryStmt);
            while(resultSet.next()) {
                Tasks tasks = getTasksSet(resultSet);
                tasksList.add(tasks);
            }
        }
        catch(SQLException se) {
            logger.log(Level.SEVERE, se.getMessage());
        }
        finally {
            if(resultSet != null) {
            resultSet.close();
        }
            if(stmt != null) {
                stmt.close();
            }
        }
        endConnection();
     return tasksList;
    }
    
    
}
