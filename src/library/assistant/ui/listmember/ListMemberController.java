package library.assistant.ui.listmember;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import library.assistant.database.dao.MemberDAO;
import library.assistant.database.model.Member;
import library.assistant.ui.addmember.AddMemberController;

public class ListMemberController implements Initializable {

    ObservableList<ListMemberController.MemberFXML> listMember = FXCollections.observableArrayList();

    @FXML
    private AnchorPane rootPane;
    @FXML
    private TableView<MemberFXML> tableView;
    @FXML
    private TableColumn<MemberFXML, String> idCol;
    @FXML
    private TableColumn<MemberFXML, String> nameCol;
    @FXML
    private TableColumn<MemberFXML, String> mobileCol;
    @FXML
    private TableColumn<MemberFXML, String> emailCol;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initCol();
        listMemberAll(null);
    }

    private void initCol() {
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        mobileCol.setCellValueFactory(new PropertyValueFactory<>("mobile"));
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
    }

    @FXML
    private void listMemberAll(ActionEvent event) {
        listMember.clear();
        MemberDAO memberDAO = new MemberDAO();
        memberDAO.findAllMember().forEach(m -> {
            listMember.add(new MemberFXML(m.getId(), m.getName(), m.getMobile(), m.getEmail()));
        });
        tableView.getItems().setAll(listMember);
    }

    @FXML
    private void editMember(ActionEvent event) {

        try {
            MemberFXML selectedMember = tableView.getSelectionModel().getSelectedItem();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/library/assistant/ui/addmember/AddMember.fxml"));

            Parent parent = loader.load();

            AddMemberController addMemberController = (AddMemberController) loader.getController();

            if (selectedMember != null) {
                Member editMember = new Member(selectedMember.getId(), selectedMember.getName(), selectedMember.getMobile(), selectedMember.getEmail());
                addMemberController.setEditMember(editMember);

                Stage stage = new Stage(StageStyle.DECORATED);
                stage.setScene(new Scene(parent));
                stage.setTitle("Edit Book");
                stage.show();
                stage.setOnCloseRequest((e) -> {
                    listMemberAll(null);
                });
            }
        } catch (IOException e) {
            System.out.println("Error: " + e.getLocalizedMessage());
        }

    }

    @FXML
    private void deleteMember(ActionEvent event) {
        MemberDAO memberDAO = new MemberDAO();
        MemberFXML selectedMember = tableView.getSelectionModel().getSelectedItem();

        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Delete");
        alert.setHeaderText("Delete Member");
        alert.setContentText("Delete Member ?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            memberDAO.deleteMember(selectedMember.getId());
            listMemberAll(null);
        } else {
            // ... user chose CANCEL or closed the dialog
        }

    }

    public static class MemberFXML {

        private final SimpleStringProperty id;
        private final SimpleStringProperty name;
        private final SimpleStringProperty mobile;
        private final SimpleStringProperty email;

        public MemberFXML(String id, String name, String mobile, String email) {
            this.id = new SimpleStringProperty(id);
            this.name = new SimpleStringProperty(name);
            this.mobile = new SimpleStringProperty(mobile);
            this.email = new SimpleStringProperty(email);
        }

        public String getId() {
            return id.get();
        }

        public String getName() {
            return name.get();
        }

        public String getMobile() {
            return mobile.get();
        }

        public String getEmail() {
            return email.get();
        }

    }
}
