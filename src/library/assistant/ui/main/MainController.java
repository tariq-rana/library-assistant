
package library.assistant.ui.main;


import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;

import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;

import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.swing.JOptionPane;

import library.assistant.database.dao.BookDAO;
import library.assistant.database.dao.IssueDAO;
import library.assistant.database.dao.MemberDAO;
import library.assistant.database.model.Book;
import library.assistant.database.model.Issue;
import library.assistant.database.model.Member;


public class MainController implements Initializable {

    ObservableList<String> issueList = FXCollections.observableArrayList();   
    
    @FXML
    private TextField searchByBookId;
    @FXML
    private Label bookTitle;
    @FXML
    private Label bookAuthor;
    @FXML
    private Label bookIsAvailable;
    @FXML
    private TextField searchByMemberId;
    @FXML
    private Label memberName;
    @FXML
    private Label memberMobile;
    @FXML
    private Button issueBtn;
    @FXML
    private TextField bookIdRenewSub;
    @FXML
    private ListView<String> issueListView;
    @FXML
    private Button renewBtn;
    @FXML
    private Button subBtn;
    @FXML
    private StackPane rootPane;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void loadAddMember(ActionEvent event) {
        loadWindow("/library/assistant/ui/addmember/AddMember.fxml","Add new member");
    }

    @FXML
    private void loadAddBook(ActionEvent event) {
        loadWindow("/library/assistant/ui/addbook/AddBook.fxml","Add new book");
    }

    @FXML
    private void loadMemberList(ActionEvent event) {
        loadWindow("/library/assistant/ui/listmember/ListMember.fxml","Member list");
    }

    @FXML
    private void loadBookList(ActionEvent event) {
        loadWindow("/library/assistant/ui/listbook/ListBook.fxml","Book list");
    }


    @FXML
    private void loadSettings(ActionEvent event) {
                loadWindow("/library/assistant/settings/Settings.fxml","Settings");
    }
    
    void loadWindow(String loc, String title){
        try {  
            Parent parent = FXMLLoader.load(getClass().getResource(loc));
            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setTitle(title);
            stage.setScene(new Scene(parent));
        
            stage.show();
            
        } catch (IOException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }

    @FXML
    private void loadBookInfo(ActionEvent event) {
        String id = searchByBookId.getText();
        
        BookDAO bookDAO = new BookDAO();
        Book book =  bookDAO.findOneBook(id);
        if(book == null){
            bookTitle.setText("Book Name");
            bookAuthor.setText("Author");
            bookIsAvailable.setText("isAvailable");
            JOptionPane.showMessageDialog(null, "Book not found", "Error", JOptionPane.ERROR_MESSAGE);
        }else{
            bookTitle.setText(book.getTitle());
            bookAuthor.setText(book.getAuthor());
            bookIsAvailable.setText(book.isIsAvailable() ? "Available" : "Issued");
        }
       
    }

    @FXML
    private void loadMemberInfo(ActionEvent event) {
        String id = searchByMemberId.getText();
        
        MemberDAO memberDAO = new MemberDAO();
        Member member = memberDAO.findOneMember(id);
        
        if(member == null){
            memberName.setText("Member Name");
            memberMobile.setText("Contact");
            JOptionPane.showMessageDialog(null, "Member not found", "Error", JOptionPane.ERROR_MESSAGE);
        }else{
            memberName.setText(member.getName());
            memberMobile.setText(member.getMobile());
        }
    }

    @FXML
    private void issueBook(ActionEvent event) {
        loadBookInfo(null);
        loadMemberInfo(null);
        
        
        int isOk = JOptionPane.showConfirmDialog(
                null,
                "Issue Book ?",
                "Issue",
                JOptionPane.YES_NO_OPTION);
        
        if( isOk == 0 &&  "Available".equals(bookIsAvailable.getText()) && !"Book Name".equals(bookTitle.getText())  && !"Member Name".equals(memberName.getText())){
            IssueDAO issueDAO = new IssueDAO();
            BookDAO bookDAO = new BookDAO();
            
            String bookId = searchByBookId.getText();
            String memberId = searchByMemberId.getText();
            
            Issue issue = new Issue(0,bookId,memberId,LocalDateTime.now(),0);
            issueDAO.insertIssue(issue);
            bookDAO.issueRcvBook(bookId, false);
            bookIsAvailable.setText("Issued");
        }else{
            JOptionPane.showMessageDialog(null, "Check Data", "Error", JOptionPane.ERROR_MESSAGE);
        }
        
    }

    @FXML
    private void loadBookListRenewSub(ActionEvent event) {
        String book_id = bookIdRenewSub.getText();
        IssueDAO issueDAO = new IssueDAO();
        issueList = issueDAO.findIssueByBookId(book_id);
        issueListView.getItems().setAll(issueList);
    }

    @FXML
    private void renewBook(ActionEvent event) {
       if(!issueListView.getItems().isEmpty()){
            int isOk = JOptionPane.showConfirmDialog(
                    null,
                    "Renew Book ?",
                    "Renew",
                    JOptionPane.YES_NO_OPTION);

            if(isOk == 0){
                IssueDAO issueDAO = new IssueDAO();
                String book_id = bookIdRenewSub.getText();
                issueDAO.renewBook(book_id);
                loadBookListRenewSub(null); 
            }
           
       }
    }

    @FXML
    private void subBook(ActionEvent event) {
        if(!issueListView.getItems().isEmpty()){
            int isOk = JOptionPane.showConfirmDialog(
                    null,
                    "Submit Book ?",
                    "Submission",
                    JOptionPane.YES_NO_OPTION);

            if (isOk == 0) {
                IssueDAO issueDAO = new IssueDAO();
                BookDAO bookDAO = new BookDAO();

                String book_id = bookIdRenewSub.getText();

                issueDAO.deleteIssueByBookId(book_id);
                bookDAO.issueRcvBook(book_id, true);

                loadBookListRenewSub(null);
            }
        }
        
    }

    @FXML
    private void closeMain(ActionEvent event) {
            ((Stage) rootPane.getScene().getWindow()).close();
    }

    @FXML
    private void fullScreenView(ActionEvent event) {
        Stage stage = ((Stage) rootPane.getScene().getWindow());
        stage.setFullScreen(!stage.isFullScreen());
    }

}
