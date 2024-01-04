package MainControllers;

import javax.swing.*;
import java.io.*;
import java.util.HashMap;
import java.util.Scanner;

// Database class follows a singleton pattern to ensure that
// only a single instance of this class exist throughout run time
public class Database {
    private static Database INSTANCE;
    private HashMap<String, String> DATA;
    private final File ACCOUNTS;
    private final File ADMINS;
    private Database() {
        ADMINS = new File("src/Files/ADMINS.txt");

        ACCOUNTS = new File("src/Files/Accounts.txt");
        DATA = new HashMap<>();
        // checks if accounts file exists, if not then creates one
        try {
            if (!ACCOUNTS.exists()) ACCOUNTS.createNewFile();
            readFile();
        }
        catch (IOException ignored) {showErrorMessage();}
    }

    public static synchronized Database getINSTANCE() {
        // singleton method
        if (INSTANCE == null) INSTANCE = new Database();
        return INSTANCE;
    }
    private void readFile(){
        // reads the accounts file then transfers its contents to a dict
        try {
            Scanner scanner = new Scanner(ACCOUNTS);
            while (scanner.hasNextLine()){
                // splits data accordingly
                String[] information = scanner.nextLine().split("\\|");
                // initializes a container for the data of this account number
                // iterates through the information then saves the data inside the user
                String username = information[0].split(":")[1];
                String password = information[1].split(":")[1];
                DATA.put(username, password);
            }
        }
        catch (FileNotFoundException ignored){
            showErrorMessage();
        }
    }
    public void addNewAccount(String username, String password){
        // adds the data of this account number to the accounts file
        username = username.toLowerCase();
        try{
            BufferedWriter writer = new BufferedWriter(new FileWriter(ACCOUNTS, true));
            writer.write("username:"+username+"|"+"password:"+password+"\n");
            writer.close();

            DATA.put(username, password);
        }
        catch (IOException ignored){showErrorMessage();}
    }
    public void addNewAdmin(String username, String password){
        // adds the data of this account number to the accounts file
        username = username.toLowerCase();
        try{
            BufferedWriter writer = new BufferedWriter(new FileWriter(ADMINS, true));
            writer.write("username:"+username+"|"+"password:"+password+"\n");
            writer.close();

            DATA.put(username, password);
        }
        catch (IOException ignored){showErrorMessage();}
    }
    public boolean checkIfExistent(String username){
        return DATA.containsKey(username);
    }

    public boolean checkIfInDatabase(String username, String password) {
        username = username.toLowerCase();
        return DATA.containsKey(username) && DATA.get(username).equals(password);
    }

    public void showErrorMessage(){
        JOptionPane.showMessageDialog(null, "An error has occured",
                                                "ERROR", JOptionPane.ERROR_MESSAGE);
    }
}
