
package library.assistant.database.model;

import java.time.LocalDateTime;
import java.util.Objects;


public class Issue {
    private int id;
    private String bookId;
    private String memberId;
    private LocalDateTime issueTime;
    private int renewCount;

    public Issue(){};
    
    public Issue(int id, String bookId, String memberId, LocalDateTime issueTime, int renewCount) {
        this.id = id;
        this.bookId = bookId;
        this.memberId = memberId;
        this.issueTime = issueTime;
        this.renewCount = renewCount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public LocalDateTime getIssueTime() {
        return issueTime;
    }

    public void setIssueTime(LocalDateTime issueTime) {
        this.issueTime = issueTime;
    }

    public int getRenewCount() {
        return renewCount;
    }

    public void setRenewCount(int renewCount) {
        this.renewCount = renewCount;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 67 * hash + this.id;
        hash = 67 * hash + Objects.hashCode(this.bookId);
        hash = 67 * hash + Objects.hashCode(this.memberId);
        hash = 67 * hash + Objects.hashCode(this.issueTime);
        hash = 67 * hash + this.renewCount;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Issue other = (Issue) obj;
        if (this.id != other.id) {
            return false;
        }
        if (this.renewCount != other.renewCount) {
            return false;
        }
        if (!Objects.equals(this.bookId, other.bookId)) {
            return false;
        }
        if (!Objects.equals(this.memberId, other.memberId)) {
            return false;
        }
        if (!Objects.equals(this.issueTime, other.issueTime)) {
            return false;
        }
        return true;
    }
    
    
}
