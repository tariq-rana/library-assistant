package library.assistant.ui.addbook;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import library.assistant.database.model.Book;
import library.assistant.database.dao.BookDAO;


public class AddBookController implements Initializable {

    BookDAO bookDAO = new BookDAO();

    @FXML
    private AnchorPane rootPane;
    
    @FXML
    private JFXTextField title;
    @FXML
    private JFXTextField id;
    @FXML
    private JFXTextField author;
    @FXML
    private JFXTextField publisher;
    
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
    private void saveBook(ActionEvent event) {
        String retMsg;

        if (id.getText().isEmpty()
                || title.getText().isEmpty()
                || author.getText().isEmpty()
                || publisher.getText().isEmpty()) {

            
            JOptionPane.showMessageDialog(null, "Enter all fields", "Error", JOptionPane.ERROR_MESSAGE);
        } else {

            Book book = new Book(id.getText(), title.getText(), author.getText(), publisher.getText(), true);
            try {
                bookDAO.saveBook(book);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    @FXML
    private void cancel(ActionEvent event) {
        Stage stage =  (Stage) rootPane.getScene().getWindow();
        stage.close();
    }
    
    public void setEditBook(Book book){
        id.setText(book.getId());
        title.setText(book.getTitle());
        author.setText(book.getAuthor());
        publisher.setText(book.getPublisher());
        id.setEditable(false);
    }

}
