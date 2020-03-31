package library.assistant.settings;

import org.mindrot.jbcrypt.BCrypt;

public class BCryptPass {
    
    private final int logRounds = 9;

    public BCryptPass() {}

    public String hash(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt(logRounds));
    }

    public boolean verifyHash(String password, String hash) {
        return BCrypt.checkpw(password, hash);
    }
}



