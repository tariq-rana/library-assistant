package library.assistant.ui.main;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import library.assistant.database.DbHandler;

public class MainLoader extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        
        //Set Database Connection
        new Thread(DbHandler::getConnection).start();
                
        Parent root = FXMLLoader.load(getClass().getResource("Main.fxml"));
        

        Scene scene = new Scene(root);

        stage.setTitle("Library Assistant");
        stage.setScene(scene);
        stage.show();

        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX((primScreenBounds.getWidth() - stage.getWidth()) / 2);
        stage.setY((primScreenBounds.getHeight() - stage.getHeight()) / 2);
        
    }

    @Override
    public void stop() throws Exception {
        //Close Database Connection
        DbHandler.closeConnection();
        super.stop(); 
    }

    
    
    public static void main(String[] args) {
        launch(args);
    }
    
    
    
    
}
