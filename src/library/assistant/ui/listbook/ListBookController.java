package library.assistant.ui.listbook;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.swing.JOptionPane;
import library.assistant.database.DbFillData;
import library.assistant.database.model.Book;
import library.assistant.database.dao.BookDAO;
import library.assistant.ui.addbook.AddBookController;

public class ListBookController implements Initializable {
    ObservableList<BookFXML> listBook = FXCollections.observableArrayList();

    @FXML
    private AnchorPane rootPane;
    @FXML
    private TableView<BookFXML> tableView;
    @FXML
    private TableColumn<BookFXML, String> idCol;
    @FXML
    private TableColumn<BookFXML, String> titleCol;
    @FXML
    private TableColumn<BookFXML, String> authorCol;
    @FXML
    private TableColumn<BookFXML, String> publisherCol;
    @FXML
    private TableColumn<BookFXML, Boolean> isAvailableCol;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
       initCol();
       loadBookAll();
    }    
    
    public void loadBookAll(){
        BookDAO bookDAO = new BookDAO();
        listBook.clear();
        
        bookDAO.findAllBook().forEach(b ->{
            listBook.add(new BookFXML(b.getId(),b.getTitle(),b.getAuthor(),b.getPublisher(),b.isIsAvailable()));
        });
        tableView.getItems().setAll(listBook);
    }
    
    
    private void initCol(){
           idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
           titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
           authorCol.setCellValueFactory(new PropertyValueFactory<>("author"));
           publisherCol.setCellValueFactory(new PropertyValueFactory<>("publisher"));
           isAvailableCol.setCellValueFactory(new PropertyValueFactory<>("isAvailable"));
    }

    @FXML
    private void deleteBook(ActionEvent event) {
        BookDAO bookDAO = new BookDAO();
        BookFXML selectedBook = tableView.getSelectionModel().getSelectedItem();

        int isOk = JOptionPane.showConfirmDialog(
                null,
                "Issue Book ?",
                "Issue",
                JOptionPane.YES_NO_OPTION);

                
        if(isOk==0 && selectedBook != null &&  selectedBook.getIsAvailable()){
            bookDAO.deleteBook(selectedBook.getId());
            loadBookAll();
        }else{
            
            //JOptionPane.showMessageDialog(null, "Book Issued", "Error", JOptionPane.ERROR_MESSAGE);
            ((Stage)  rootPane.getScene().getWindow()).setTitle("Error cannot delete book");            
        }
        
        
        
        //bookDAO.deleteBook(id);
    }

    @FXML
    private void editBook(ActionEvent event) {
        BookFXML selectedBook = tableView.getSelectionModel().getSelectedItem();
        try{
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/library/assistant/ui/addbook/AddBook.fxml"));
            Parent parent = loader.load();
            
            AddBookController addBookController = (AddBookController) loader.getController();
            Book editBook = new Book(selectedBook.getId(),selectedBook.getTitle(),selectedBook.getAuthor(),selectedBook.getPublisher(),selectedBook.getIsAvailable());    
            addBookController.setEditBook(editBook);
           
            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setScene(new Scene(parent));
            stage.setTitle("Edit Book");
            stage.show();
            stage.setOnCloseRequest((e)->{
                loadBookAll();
            });
        }catch(IOException e){
            System.out.println("Error: " + e.getLocalizedMessage());
        }
        
        
        
    }
    
    
    public static class BookFXML {
        private final SimpleStringProperty id;
        private final SimpleStringProperty title;
        private final SimpleStringProperty author;
        private final SimpleStringProperty publisher;
        private final SimpleBooleanProperty isAvailable;
        
        public BookFXML(String id, String title, String author, String publisher, Boolean isAvailable) {
            this.id = new SimpleStringProperty(id);
            this.title = new SimpleStringProperty(title);
            this.author = new SimpleStringProperty(author);
            this.publisher = new SimpleStringProperty(publisher);
            this.isAvailable = new SimpleBooleanProperty(isAvailable);
//            if(isAvailable){
//                this.isAvailable = new SimpleStringProperty("Available");
//            }else{
//                this.isAvailable = new SimpleStringProperty("Issued");
//            }
            
        }

        public String getId() {
            return id.get();
        }

        public String getTitle() {
            return title.get();
        }

        public String getAuthor() {
            return author.get();
        }

        public String getPublisher() {
            return publisher.get();
        }

        public Boolean getIsAvailable() {
            return isAvailable.get();
        }
        
        
    }
    
}
