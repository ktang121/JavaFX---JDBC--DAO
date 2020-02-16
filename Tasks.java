package todolist;
import java.sql.Date;
import javafx.beans.property.*;


public class Tasks {
    
    private IntegerProperty taskId;
    private StringProperty taskDescription;
    private DoubleProperty taskAmount;
    private SimpleObjectProperty<Date> taskDate;
    
    public Tasks() {
        
        this.taskId = new SimpleIntegerProperty();
        this.taskDescription = new SimpleStringProperty();
        this.taskAmount = new SimpleDoubleProperty();
        this.taskDate = new SimpleObjectProperty<>();
    }
    
    public void setTaskId(int taskId) {
        this.taskId.set(taskId);
    }
    public int getTaskId() {
        return taskId.get();
    }
    public IntegerProperty taskIdProperty() {
        return taskId;
    }
    
    public void setTaskDesc(String taskDescription) {
        this.taskDescription.set(taskDescription);
    }
    public String getTaskDesc() {
        return taskDescription.get();
    }
    public StringProperty taskDescProperty() {
        return taskDescription;
    }
    
    public void setTaskAmt(double taskAmount) {
        this.taskAmount.set(taskAmount);
    }
    public double getTaskAmt() {
        return taskAmount.get();
    }
    public DoubleProperty taskAmtProperty() {
        return taskAmount;
    }
    
    public void setTaskDate(Date taskDate) {
        this.taskDate.set(taskDate);
    }
    public Date getTaskDate() {
        return taskDate.get();
    }
    public SimpleObjectProperty<Date> taskDateProperty() {
        return taskDate;
    }
}
