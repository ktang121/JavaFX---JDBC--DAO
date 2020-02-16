package todolist;
import javafx.scene.input.MouseEvent;
import java.sql.Date;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;


/**
 *
 * @author Kenny
 */
public class Main extends Application {

    private static final Logger logger = Logger.getLogger(Main.class.getName());
    private final TasksDAOImpl tasksDAO = new TasksDAOImpl();
    private final GridPane gridPane= new GridPane();
    private final BorderPane borderPane = new BorderPane();
    private static final TextField userNameTxt = new TextField();
    private static final PasswordField passwordTxt = new PasswordField();
    private ObservableList<Tasks> observableTasks;
    private TableView tasksTableView;
    private final TableColumn<Tasks, Integer> taskIdCol = new TableColumn("ID");
    private final TableColumn<Tasks, String> taskDescCol = new TableColumn("Description");
    private final TableColumn<Tasks, Double> taskAmtCol = new TableColumn("Amount");
    private final TableColumn<Tasks, Date> taskDateCol = new TableColumn("Date");
    private final DropShadow shadow = new DropShadow();
            
    
   @Override
   public void start(Stage primaryStage) {
       Scene scene = new Scene(logInPane(), 500, 500);
       scene.getStylesheets().add(getClass().getResource("Style.css").toExternalForm());
       primaryStage.setScene(scene);
       primaryStage.setTitle("To Do List Sample");
       primaryStage.show();
   }
    public static String getUserName() {
       return userNameTxt.getText().trim();
   }
   public static String getPassword() {
       return passwordTxt.getText().trim();
   }
   
   private GridPane logInPane() {
       gridPane.setHgap(7); gridPane.setVgap(10);
        
        Label connectLabel = new Label("Login Sample");
        connectLabel.setId("label-connect");
        gridPane.add(connectLabel, 1, 0);
        GridPane.setMargin(connectLabel, new Insets(10, 10, 40, 20));
        
        Label userNameLabel = new Label("Username");
        userNameLabel.setId("label-user");
        gridPane.add(userNameLabel,0,1);
        
        gridPane.add(userNameTxt, 1,1);
        GridPane.setMargin(userNameTxt, new Insets(10,10,5,20));
        
        Label passwordLabel = new Label("Password");
        passwordLabel.setId("label-password");
        gridPane.add(passwordLabel, 0, 2);
        
        gridPane.add(passwordTxt, 1, 2);
        GridPane.setMargin(passwordTxt, new Insets(5,10,10,20));
        
        Button connectButton = new Button("Connect");
        connectButton.setId("button-connect");
        connectButton.setOnAction(new ConnectButtonHandler());
        connectButton.setOnMouseEntered((MouseEvent e) -> {
             connectButton.setEffect(shadow);
          });
        connectButton.setOnMouseExited((MouseEvent e) -> {
         connectButton.setEffect(null);
        });
       
        gridPane.add(connectButton, 1,3);
        GridPane.setMargin(connectButton, new Insets(30, 10, 10, 20));
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setId("gridpane-view");
        return gridPane;
   }
   
   private BorderPane tasksPane() {
      Button loadButton = new Button("Load Data");
      loadButton.setOnAction(new ShowTableHandler());
      loadButton.setId("button-loadData");
      loadButton.setOnMouseEntered((MouseEvent e) -> {
             loadButton.setEffect(shadow);
          });
      loadButton.setOnMouseExited((MouseEvent e) -> {
             loadButton.setEffect(null);
          });
      
      HBox hbox = new HBox(10, loadButton);
      hbox.setPadding(new Insets(30));
      hbox.setAlignment(Pos.CENTER);
      borderPane.setBottom(hbox);
      borderPane.setId("borderpane-view");
      return borderPane;
   }
   
   private void showAlert(String title, String message, AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
   
   
   class ConnectButtonHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            try {
                tasksDAO.getConnection();
                showAlert("Database Connection Information", "Connection established!", AlertType.INFORMATION);
                Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
                Scene scene = new Scene(tasksPane(), 1000, 700);
                scene.getStylesheets().add(getClass().getResource("Style.css").toExternalForm());
                window.setScene(scene);
                window.setTitle("Tasks");
                window.show();
            }
            catch(SQLException | ClassNotFoundException se) {
                 logger.log(Level.SEVERE,  se.getMessage());
            }          
        }
    }
   
   class ShowTableHandler implements EventHandler<ActionEvent> {
       @Override
       public void handle(ActionEvent event) {
           try {
           observableTasks = FXCollections.observableArrayList(tasksDAO.loadTasks());
           tasksTableView = new TableView(observableTasks);
           taskIdCol.setCellValueFactory(cellData -> cellData.getValue().taskIdProperty().asObject());
           taskDescCol.setCellValueFactory(cellData -> cellData.getValue().taskDescProperty());
           taskDescCol.setPrefWidth(80);
           taskAmtCol.setCellValueFactory(cellData -> cellData.getValue().taskAmtProperty().asObject());
           taskAmtCol.setPrefWidth(80);
           taskDateCol.setCellValueFactory(cellData -> cellData.getValue().taskDateProperty());
           taskDateCol.setPrefWidth(80);
           tasksTableView.getColumns().addAll(taskIdCol, taskDescCol, taskAmtCol, taskDateCol);
           VBox vbox = new VBox(10, tasksTableView);
           vbox.setAlignment(Pos.CENTER);
           borderPane.setCenter(vbox);
       }
           catch(SQLException | ClassNotFoundException se) {
               logger.log(Level.SEVERE, se.getMessage());
           }
       }
   }

    public static void main(String[] args) {
     
        launch(args);
   }
    
    
}
