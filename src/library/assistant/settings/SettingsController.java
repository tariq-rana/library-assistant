/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library.assistant.settings;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Administration
 */
public class SettingsController implements Initializable {

    @FXML
    private JFXTextField noDaysFine;
    @FXML
    private JFXTextField finePerDay;
    @FXML
    private JFXTextField username;
    @FXML
    private JFXPasswordField password;
    @FXML
    private JFXButton saveBtn;
    @FXML
    private JFXButton cancelBtn;
    @FXML
    private AnchorPane rootPane;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadInitValues();
    }    

    @FXML
    private void saveSettings(ActionEvent event) {
        BCryptPass bCryptPass = new BCryptPass();
        Preferences preferences = new Preferences();
        preferences.setNoDaysFine(Integer.parseInt(noDaysFine.getText()));
        preferences.setFinePerDay(Float.parseFloat(finePerDay.getText()));
        preferences.setUsername(username.getText());
        if(password.getText().length() > 20){
            preferences.setPassword(password.getText());
        }else{
            preferences.setPassword(bCryptPass.hash(password.getText()));
        }
        Preferences.savePreferences(preferences);
        cancelSettings(null);
    }

    @FXML
    private void cancelSettings(ActionEvent event) {
        ((Stage)  rootPane.getScene().getWindow()).close();
//        
//         Stage stage =  (Stage) rootPane.getScene().getWindow();
//         stage.close();
    }
    
    private void loadInitValues(){
        Preferences preferences = Preferences.getPreferences();
        noDaysFine.setText(String.valueOf(preferences.getNoDaysFine()));
        finePerDay.setText(String.valueOf(preferences.getFinePerDay()));
        username.setText(preferences.getUsername());
        password.setText(preferences.getPassword());
    }
    
}
