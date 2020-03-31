package library.assistant.database;

import library.assistant.database.model.Book;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class DbFillData {

    private final Connection conn;
    private PreparedStatement pstmt;
    private Statement stmt;

    private ResultSet rs;
    private String query;

    private List<Book> books = new ArrayList<>();

    public DbFillData() {
        this.conn = DbHandler.getConnection();
        fillBookList();

    }

    private void fillBookList() {
        books.add(new Book("01", "Learn Java", "Tariq", "Phakt",true));
        books.add(new Book("02", "Learn Cooking", "Nazish", "Phakt",true));
        books.add(new Book("03", "Learn Premiere Pro", "Taybah", "Phakt",true));
        books.add(new Book("04", "Learn Boxing", "Ahmed", "Phakt",true));
        books.add(new Book("05", "Learn Minecraft", "Alaa", "Phakt",true));
        books.add(new Book("06", "Learn Aapp", "Anaya", "Phakt",true));
    }

    public void fillBookData() {
        query = "insert into book (id,title,author,publisher,is_available) Values (?,?,?,?,?)";
        books.forEach(book -> {
            try {
                pstmt = conn.prepareStatement(query);
                pstmt.setString(1, book.getId());
                pstmt.setString(2, book.getTitle());
                pstmt.setString(3, book.getAuthor());
                pstmt.setString(4, book.getPublisher());
                pstmt.setBoolean(5, book.isIsAvailable());
                pstmt.execute();

            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}
