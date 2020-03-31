
package library.assistant.settings;

import com.google.gson.Gson;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import javax.swing.JOptionPane;

public class Preferences {
    
        public static final String CONFIG_FILE = "config.json";
        
        private int noDaysFine;
        private float finePerDay;
        private String username;
        private String password;

    public Preferences() {
     
    }

    public int getNoDaysFine() {
        return noDaysFine;
    }

    public void setNoDaysFine(int noDaysFine) {
        this.noDaysFine = noDaysFine;
    }

    public float getFinePerDay() {
        return finePerDay;
    }

    public void setFinePerDay(float finePerDay) {
        this.finePerDay = finePerDay;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public boolean isValidUser(String username, String password){
         BCryptPass bCryptPass = new BCryptPass();
         Preferences preferences = getPreferences();
         boolean isValid = false;
         
        if(username.equals(preferences.getUsername()) && bCryptPass.verifyHash(password, preferences.getPassword()))
        {
            isValid = true;
        }
        return isValid;
    }
    
    public static void initConfig(){
        Writer writer = null;
        Preferences preferences = new Preferences();
        BCryptPass bCryptPass = new BCryptPass();
        
        preferences.setNoDaysFine(14);
        preferences.setFinePerDay(2);
        preferences.setUsername("admin");
        preferences.setPassword(bCryptPass.hash("admin"));
        
        Gson gson = new Gson();
        try{
            writer = new FileWriter(CONFIG_FILE);
            gson.toJson(preferences,writer);
            writer.close();
        }catch(IOException e){
            JOptionPane.showMessageDialog(null, "Error " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public static Preferences getPreferences(){
        Reader reader = null;
        Preferences preferences = new Preferences();
        Gson gson = new Gson();
        try{
            reader = new FileReader(CONFIG_FILE);
            preferences = gson.fromJson(reader,Preferences.class);
        }catch(IOException e){
            JOptionPane.showMessageDialog(null, "Error " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        return preferences;
    }

        public static void savePreferences(Preferences preferences){
        Writer writer = null;
        Gson gson = new Gson();
        try{
            writer = new FileWriter(CONFIG_FILE);
            gson.toJson(preferences,writer);
            writer.close();
            JOptionPane.showMessageDialog(null, "Config saved", "Saved", JOptionPane.INFORMATION_MESSAGE);
        }catch(IOException e){
            JOptionPane.showMessageDialog(null, "Error " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }finally{
        
        }
    }
}
