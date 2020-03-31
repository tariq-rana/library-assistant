
package library.assistant.ui.login;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.swing.JOptionPane;
import library.assistant.settings.Preferences;
import library.assistant.ui.main.MainController;


public class LoginController implements Initializable {
    boolean isMainShowing = false;
        
    @FXML
    private JFXTextField txtUsername;
    @FXML
    private JFXTextField txtPassword;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void loginProg(ActionEvent event) {
            String username = txtUsername.getText();
            String password = txtPassword.getText();
            Preferences preferences = new Preferences();
            
            if(preferences.isValidUser(username, password)){
                    try {  
                    Parent parent = FXMLLoader.load(getClass().getResource("/library/assistant/ui/main/Main.fxml"));
                    Stage stage = new Stage(StageStyle.DECORATED);
                    stage.setTitle("Library Assistant");
                    stage.setScene(new Scene(parent));
                    
                    if(!isMainShowing){
                        isMainShowing = true;
                        stage.show();
                    }
                } catch (IOException e) {
                    JOptionPane.showMessageDialog(null, "Error " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }else{
                 JOptionPane.showMessageDialog(null, "Error " +"Invalide user/pass", "Error", JOptionPane.ERROR_MESSAGE);
            }
    }

    @FXML
    private void cancelProg(ActionEvent event) {
        System.exit(0);
        
    }
    
}
