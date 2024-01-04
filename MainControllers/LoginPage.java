package MainControllers;

import javax.swing.*;
import javax.xml.crypto.Data;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class LoginPage extends JPanel {
    private ShoppingSystem observer;
    private JTextField username;
    private JPasswordField password;
    private JCheckBox AdminCheckBox;
    private Database accounts;
    public LoginPage(int width, int height, ShoppingSystem observer){
        this.observer = observer;
        // sets the frame
        this.setLayout(null);
        this.setBackground(new Color(0, 0, 0));
        accounts = Database.getINSTANCE();
        // adds the components to the frame
        initializeComponents(width, height);
        this.setVisible(true);
    }

    public void initializeComponents(int width, int height){
        // LABELS
        JLabel greeting = new JLabel("Login Account");
        greeting.setFont(new Font("Poppins", Font.BOLD, 25));
        greeting.setBounds(0, (int)(height*.31), width, 100);
        greeting.setForeground(new Color(128, 128, 255));
        greeting.setHorizontalAlignment(SwingConstants.CENTER);
        this.add(greeting);
        JLabel tagline = new JLabel("\"Style Meets Comfort: Your Perfect Shirt Shop\"");
        tagline.setFont(new Font("Poppins", Font.ITALIC, 12));
        tagline.setBounds(0, (int)(height*.26), width, 50);
        tagline.setForeground(new Color(255, 255, 255));
        tagline.setHorizontalAlignment(SwingConstants.CENTER);
        this.add(tagline);
        // INPUT FIELDS
        addUserField(width, height);
        addPasswordField(width, height);
        // ADDS ICON
        ImageIcon imageIcon = new ImageIcon("src\\logo-new.jpg");
        Image resizedImage = imageIcon.getImage().
                getScaledInstance(250, 250, Image.SCALE_SMOOTH);
        JLabel imageHolder = new JLabel(new ImageIcon(resizedImage));
        imageHolder.setBounds(125, (int)(height*.1), 250, 250);
        imageHolder.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.add(imageHolder);
        // REGISTER AND LOGIN BUTTON
        JButton registerButton = new JButton("register");
        registerButton.addActionListener(e -> observer.addRegisterPage());
        registerButton.setFont(new Font("Poppins", Font.BOLD, 10));
        registerButton.setBounds((int)(width*.25), (int)(height*.56), 75, 25);
        registerButton.setBackground(new Color(152, 152, 248));
        registerButton.setForeground(new Color(255, 255, 255));
        this.add(registerButton);

        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(e -> login());
        loginButton.setFont(new Font("Poppins", Font.BOLD, 15));
        loginButton.setBounds((int)(width*.25), (int)(height*.62), (int)(width*.5), (int)(height*.05));
        loginButton.setBackground(new Color(152, 152, 248));
        loginButton.setForeground(new Color(255, 255, 255));
        this.add(loginButton);
    }
    public void addUserField(int width, int height){
        username = new JTextField("Enter username...");
        username.setBounds((int)(width*.25), (int)(height*.4), (int)(width*.5), (int)(height*.05));
        username.setToolTipText("\"Enter username");
        username.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (username.getText().equals("Enter username...")) {
                    username.setText("");
                }
                username.setBackground(new Color(190, 190, 255));
            }
            @Override
            public void focusLost(FocusEvent e) {
                if (username.getText().isEmpty()) {
                    username.setText("Enter username...");
                }
                username.setBackground(new Color(255, 255, 255));
            }
        });
        this.add(username);
    }
    public void addPasswordField(int width, int height){
        //sets up password visibility function using JButton
        JToggleButton showPasswordButton = new JToggleButton("Show");
        showPasswordButton.addMouseListener(new MouseAdapter() { //uses mouse event (pressed)
            @Override
            public void mousePressed(MouseEvent e) {
                if(showPasswordButton.isSelected()) {
                    password.setEchoChar((char) 0); //shows string value of passwordfield using char
                    showPasswordButton.setText("Hide");// set JButton text to hide when it is pressed
                }
                else {
                    password.setEchoChar('●'); //returns passwordfield/ hides the password again
                    showPasswordButton.setText("Show"); //set JButton text to show if unpressed or pressed for a second time
                }
            }
        });
        showPasswordButton.setFont(new Font("Poppins", Font.PLAIN, 10));
        showPasswordButton.setFocusable(false);
        showPasswordButton.setForeground(new Color(50, 50, 83));
        showPasswordButton.setBackground(new Color(255, 255, 255));
        showPasswordButton.setBounds(300, (int)(height*.56), 75, 25);
        // PASSWORD INPUT TEXT
        password = new JPasswordField("Enter password...");
        password.setBounds((int)(width*.25), (int)(height*.48), (int)(width*.5), (int)(height*.05));
        password.setEchoChar((char) 0);
        showPasswordButton.setVisible(false);
        password.setToolTipText("\"Enter password");

        password.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (new String(password.getPassword()).equals("Enter password...")) {
                    password.setText("");
                    password.setEchoChar('●');
                    showPasswordButton.setVisible(true);
                    showPasswordButton.setText("Show");
                }
                password.setBackground(new Color(190, 190, 255));
            }
            @Override
            public void focusLost(FocusEvent e) {
                if (new String(password.getPassword()).isEmpty()) {
                    password.setText("Enter password...");
                    password.setEchoChar((char) 0);
                    showPasswordButton.setVisible(false);
                    showPasswordButton.setText("Show");
                }
                password.setBackground(new Color(255, 255, 255));
            }
        });
        this.add(password);
        this.add(showPasswordButton);
    }
    // LOGIN LOGIC
    public void login(){
        String usernameInput = username.getText();
        String passwordInput = new String(password.getPassword());
        if (usernameInput.isEmpty() || usernameInput.equals("Enter username...") ||
                passwordInput.isEmpty() || passwordInput.equals("Enter password...")){
            JOptionPane.showMessageDialog(this, "Fill up everything",
                                                    "Error", JOptionPane.ERROR_MESSAGE);
        }
        else if (!accounts.checkIfInDatabase(usernameInput, passwordInput)){
            JOptionPane.showMessageDialog(this, "Incorrect username or password",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
        else{
            JOptionPane.showMessageDialog(this, "Login successful",
                    "Welcome", JOptionPane.INFORMATION_MESSAGE);
            observer.addClientPage(usernameInput);
        }
    }
}
