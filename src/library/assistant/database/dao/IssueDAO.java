package library.assistant.database.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.swing.JOptionPane;
import library.assistant.database.DbHandler;
import library.assistant.database.model.Issue;

public class IssueDAO {
        
    private final Connection conn;
    private PreparedStatement pstmt;
    private ResultSet rs;
    private String query;


    public IssueDAO(){
        this.conn  = DbHandler.getConnection();
    }
            
            
    public void insertIssue(Issue issue){
         query = "Insert into issue (book_id,member_id,renew_count) Values (?,?,?)";
        try {
                pstmt = conn.prepareStatement(query);
                pstmt.setString(1,issue.getBookId());
                pstmt.setString(2,issue.getMemberId());
                pstmt.setInt(3,issue.getRenewCount());
                pstmt.execute();
                JOptionPane.showMessageDialog(null, "Issued", "Saved", JOptionPane.INFORMATION_MESSAGE);
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    
    public void updateIssue(int id,  Issue issue){
        query = "Update issue Set book_id=? ,member_id=?, renew_count=?  Where id = ?";
        try{
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1,issue.getBookId());
            pstmt.setString(1,issue.getMemberId());
            pstmt.setInt(1,issue.getRenewCount());
            pstmt.setInt(4, id);
            pstmt.execute();
            JOptionPane.showMessageDialog(null, "Issue updated", "Updated", JOptionPane.INFORMATION_MESSAGE);
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    
    
    public void deleteIssue(int id){
         query = "Delete from issue Where id = ?";
        try {
                pstmt = conn.prepareStatement(query);
                pstmt.setInt(1, id);
                pstmt.execute();
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }
 
       public void deleteIssueByBookId(String book_id){
         query = "Delete from issue Where book_id = ?";
        try {
                pstmt = conn.prepareStatement(query);
                pstmt.setString(1, book_id);
                pstmt.execute();
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public ObservableList<String> findIssueByBookId(String book_id){
        ObservableList<String> issueList = FXCollections.observableArrayList();        

        query = "Select * from v_issue Where book_id = ?";
        try {
                pstmt = conn.prepareStatement(query);
                pstmt.setString(1, book_id);
                rs = pstmt.executeQuery();
                
                while(rs.next()){
                    issueList.add("Issue Date and Time: " + rs.getTimestamp("issue_time").toLocalDateTime());
                    issueList.add("Renew count: " + rs.getInt("renew_count"));
                    issueList.add("Book Info:");
                    issueList.add("Id: " + rs.getString("book_id"));
                    issueList.add("Title: " + rs.getString("title"));
                    issueList.add("Author: " + rs.getString("author"));
                    issueList.add("Publihser: " + rs.getString("publisher"));

                    issueList.add("Member Info:");
                    issueList.add("Name: " + rs.getString("name"));
                    issueList.add("Mobile: " + rs.getString("mobile"));
                    issueList.add("email: " + rs.getString("email"));
                }
                
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
       return issueList  ;
    }
    
    public void renewBook(String book_id){
         query = "Update issue Set  issue_time = Now(), renew_count = renew_count + 1  Where book_id = ?";
        try {
                pstmt = conn.prepareStatement(query);
                pstmt.setString(1, book_id);
                pstmt.execute();
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
