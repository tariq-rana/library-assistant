
package library.assistant.database.dao;

import library.assistant.database.model.Member;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.Alert;
import javax.swing.JOptionPane;
import library.assistant.database.DbHandler;


public class MemberDAO {
    private final Connection conn;
    private PreparedStatement pstmt;
    private ResultSet rs;
    private String query;

    
    public MemberDAO(){
        this.conn = DbHandler.getConnection();
    }
    
    public void saveMember(Member member){
        String id = member.getId();
        if (isMemberExists(id)) {
            try {
                updateMember(id, member);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            try {
                insertMember(member);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        
    }
    
    public void insertMember(Member member){
        query = "Insert into member (id,name,mobile,email) Values (?,?,?,?)";
        try{
                pstmt = conn.prepareStatement(query);
                pstmt.setString(1, member.getId());    
                pstmt.setString(2, member.getName());    
                pstmt.setString(3, member.getMobile());    
                pstmt.setString(4, member.getEmail());    
                pstmt.execute();
               //  JOptionPane.showMessageDialog(null, "Member saved", "Saved", JOptionPane.INFORMATION_MESSAGE);
        }catch (SQLException e){
             JOptionPane.showMessageDialog(null, e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
     public void updateMember(String id, Member member) {
        query = "Update  member set name = ?, mobile = ? , email = ?  Where id = ?";
        try {
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, member.getName());
            pstmt.setString(2, member.getMobile());
            pstmt.setString(3, member.getEmail());
            pstmt.setString(4, id);

            pstmt.execute();
            //JOptionPane.showMessageDialog(null, "Member updated", "Updated", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
      public void deleteMember(String id) {
        query = "Delete from member where id = ?";

        try {
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, id);
            pstmt.execute();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private boolean isMemberExists(String id){
        boolean retVal = false;
        try {
            query = "Select id from member where id = ?";
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
    
    public List<Member> findAllMember(){
        List<Member> memberList = new ArrayList<>();
        try{
            query = "Select * from member Order by ID";
            pstmt = conn.prepareStatement(query);
            rs = pstmt.executeQuery();
            while(rs.next()){
                memberList.add(new Member(rs.getString("id"),rs.getString("name"),rs.getString("mobile"),rs.getString("email")));
            }
            
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }    
        return memberList;
    }

    public Member findOneMember(String id){
        Member member = null;
        try{
            query = "Select * from member where id = ?";
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, id);
            rs = pstmt.executeQuery();
            if(rs.next()){
                member = new Member(rs.getString("id"),rs.getString("name"),rs.getString("mobile"),rs.getString("email"));
            }
            
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }    
        return member;
    }
    
}
