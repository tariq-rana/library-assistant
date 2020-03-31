package library.assistant.ui.listbook;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;


public class ListBookLoader extends Application{
    
    @Override
    public void start(Stage stage) throws Exception{
         Parent root = FXMLLoader.load(getClass().getResource("ListBook.fxml"));
       
        Scene scene = new Scene(root);
        
        stage.setTitle("List Book");
        stage.setScene(scene);
        stage.show();
        
        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX((primScreenBounds.getWidth() - stage.getWidth()) / 2);
        stage.setY((primScreenBounds.getHeight() - stage.getHeight()) / 2);
    }

    public static void main(String[] args) {
        launch(args);
    }
    
}
