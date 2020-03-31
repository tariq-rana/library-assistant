/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library.assistant.ui.addmember;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import library.assistant.database.model.Member;

import library.assistant.database.dao.MemberDAO;

public class AddMemberController implements Initializable {
    MemberDAO memberDAO = new MemberDAO();
    
    @FXML
    private AnchorPane rootPane;

    @FXML
    private JFXTextField id;
    @FXML
    private JFXTextField name;
    @FXML
    private JFXTextField mobile;
    @FXML
    private JFXTextField email;
    @FXML
    private JFXButton saveBtn;
    @FXML
    private JFXButton cancelBtn;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void saveMember(ActionEvent event) {

        if (id.getText().isEmpty()
                || name.getText().isEmpty()
                || mobile.getText().isEmpty()
                || email.getText().isEmpty()) {

            JOptionPane.showMessageDialog(null, "Enter all fields", "Error", JOptionPane.ERROR_MESSAGE);
        } else {

            Member member = new Member(id.getText(), name.getText(), mobile.getText(), email.getText());
            memberDAO.saveMember(member);
        }
    }

    @FXML
    private void cancel(ActionEvent event) {
        Stage stage = (Stage) rootPane.getScene().getWindow();
        stage.close();
    }

    public void setEditMember(Member member){
        id.setText(member.getId());
        name.setText(member.getName());
        mobile.setText(member.getMobile());
        email.setText(member.getEmail());
        id.setDisable(true);
    }
}
