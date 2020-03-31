package library.assistant.database.dao;

import library.assistant.database.model.Book;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.Alert;
import javax.swing.JOptionPane;
import library.assistant.database.DbHandler;


public class BookDAO {
    private final Connection conn;
    private PreparedStatement pstmt;
    private ResultSet rs;
    private String query;
    
    
    public BookDAO(){
         this.conn = DbHandler.getConnection();
    }

      public void saveBook(Book book) throws Exception {
        String id = book.getId();
        if (isBookExists(id)) {
            try {
                updateBook(id, book);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            try {
                insertBook(book);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public void insertBook(Book book) {
        query = "insert into book (id,title,author,publisher,is_available) Values (?,?,?,?,?)";
        try {

            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, book.getId());
            pstmt.setString(2, book.getTitle());
            pstmt.setString(3, book.getAuthor());
            pstmt.setString(4, book.getPublisher());
            pstmt.setBoolean(5, book.isIsAvailable());

            pstmt.execute();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Book saved");
            alert.show();

           // JOptionPane.showMessageDialog(null, "Book saved", "Saved", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void updateBook(String id, Book book) {
        query = "Update  book set title = ?, author = ? , publisher = ?, is_available = ?  Where id = ?";
        try {
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, book.getTitle());
            pstmt.setString(2, book.getAuthor());
            pstmt.setString(3, book.getPublisher());
            pstmt.setBoolean(4, book.isIsAvailable());
            pstmt.setString(5, id);

            pstmt.execute();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Book updated");
            alert.show();

//            JOptionPane.showMessageDialog(null, "Book updated", "Updated", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void deleteBook(String id) {
        query = "Delete from book where id = ?";

        try {
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, id);
            pstmt.execute();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean isBookExists(String id) {
        boolean retVal = false;

        try {
            query = "Select id from book where id = ?";
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, id);
            rs = pstmt.executeQuery();
            if(rs.next()){
                retVal = true;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
        return retVal;
    }
    
    public List<Book> findAllBook(){
        List<Book> bookList = new ArrayList<>();
         try {
            query = "Select * from book Order By ID";
            pstmt = conn.prepareStatement(query);
            rs = pstmt.executeQuery();
            while(rs.next()){
                 bookList.add(new Book(rs.getString("id"),rs.getString("title"),rs.getString("author"),rs.getString("publisher"),rs.getBoolean("is_available")));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
        return bookList;
    }
    
    public Book findOneBook(String id){
        Book book = null;

        try {
            query = "Select * from book where id = ?";
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, id);
            rs = pstmt.executeQuery();
            if(rs.next()){
                book = new Book(rs.getString("id"),rs.getString("title"),rs.getString("author"),rs.getString("publisher"),rs.getBoolean("is_available"));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
        return book;
    }
    
    public void issueRcvBook(String bookId, boolean isAvailable){
        query = "Update  book set  is_available = ?  Where id = ?";
        try {
            pstmt = conn.prepareStatement(query);
            pstmt.setBoolean(1,isAvailable);
            pstmt.setString(2, bookId);
            pstmt.execute();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
        
    }
}
