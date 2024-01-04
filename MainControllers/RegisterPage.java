package MainControllers;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class RegisterPage extends JPanel{
    private ShoppingSystem observer;
    private JTextField username;
    private JPasswordField password;
    private JPasswordField confirmPassword;
    private JCheckBox AdminCheckBox;
    private Database accounts;
    public RegisterPage(int width, int height, ShoppingSystem observer){
        this.accounts = Database.getINSTANCE();
        this.observer = observer;
        // sets the frame
        this.setSize(width, height);
        this.setLayout(null);
        this.setBackground(new Color(0, 0, 0));
        // adds the components to the frame
        initializeComponents(width, height);
        this.setVisible(true);
    }

    public void initializeComponents(int width, int height){
        // LABELS
        JLabel greeting = new JLabel("Register account");
        greeting.setFont(new Font("Poppins", Font.BOLD, 25));
        greeting.setBounds(0, (int)(height*.31), width, 100);
        greeting.setForeground(new Color(128, 128, 255));
        greeting.setHorizontalAlignment(SwingConstants.CENTER);
        this.add(greeting);
        JLabel tagline = new JLabel("\"Elevate Your Style: Unleash Your Fashion Statement\"");
        tagline.setFont(new Font("Poppins", Font.ITALIC, 12));
        tagline.setBounds(0, (int)(height*.26), width, 50);
        tagline.setForeground(new Color(255, 255, 255));
        tagline.setHorizontalAlignment(SwingConstants.CENTER);
        this.add(tagline);
        // INPUT FIELDS
        addUserField(width, height);
        password = new JPasswordField("Enter password...");
        confirmPassword = new JPasswordField("Confirm password...");
        addPasswordField(width, height, password, (int)(width*.25), (int)(height*.49), "Enter password...");
        addPasswordField(width, height, confirmPassword, (int)(width*.25), (int)(height*.58), "Confirm password...");
        // ADDS ICON
        ImageIcon imageIcon = new ImageIcon("src\\logo-new.jpg");
        Image resizedImage = imageIcon.getImage().
                getScaledInstance(250, 250, Image.SCALE_SMOOTH);
        JLabel imageHolder = new JLabel(new ImageIcon(resizedImage));
        imageHolder.setBounds(125, (int)(height*.1), 250, 250);
        imageHolder.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.add(imageHolder);
        // REGISTER AND LOGIN BUTTON
        JButton loginButton = new JButton("login");
        loginButton.addActionListener(e -> observer.addLoginPage());
        loginButton.setFont(new Font("Poppins", Font.BOLD, 10));
        loginButton.setBounds((int)(width*.25), (int)(height*.636), 75, 25);
        loginButton.setBackground(new Color(152, 152, 248));
        loginButton.setForeground(new Color(255, 255, 255));
        this.add(loginButton);

        JButton registerButton = new JButton("Register");
        registerButton.setFont(new Font("Poppins", Font.BOLD, 15));
        registerButton.setBounds((int)(width*.25), (int)(height*.7), (int)(width*.5), (int)(height*.05));
        registerButton.setBackground(new Color(152, 152, 248));
        registerButton.setForeground(new Color(255, 255, 255));
        registerButton.addActionListener(e -> register());
        this.add(registerButton);
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
    public void addPasswordField(int width, int height, JPasswordField field, int x, int y, String value){
        //sets up password visibility function using JButton
        JToggleButton showPasswordButton = new JToggleButton("Show");
        showPasswordButton.addMouseListener(new MouseAdapter() { //uses mouse event (pressed)
            @Override
            public void mousePressed(MouseEvent e) {
                if(showPasswordButton.isSelected()) {
                    field.setEchoChar((char) 0); //shows string value of passwordfield using char
                    showPasswordButton.setText("Hide");// set JButton text to hide when it is pressed
                }
                else {
                    field.setEchoChar('●'); //returns passwordfield/ hides the password again
                    showPasswordButton.setText("Show"); //set JButton text to show if unpressed or pressed for a second time
                }
            }
        });
        showPasswordButton.setFont(new Font("Poppins", Font.PLAIN, 10));
        showPasswordButton.setFocusable(false);
        showPasswordButton.setForeground(new Color(50, 50, 83));
        showPasswordButton.setBackground(new Color(255, 255, 255));
        showPasswordButton.setBounds(300, (int)(y + height*.056), 75, 25);
        // PASSWORD INPUT TEXT
        field.setBounds(x, y, (int)(width*.5), (int)(height*.05));
        field.setEchoChar((char) 0);
        showPasswordButton.setVisible(false);
        field.setToolTipText(value);
        field.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (new String(field.getPassword()).equals(value)) {
                    field.setText("");
                    field.setEchoChar('●');
                    showPasswordButton.setVisible(true);
                    showPasswordButton.setText("Show");
                }
                field.setBackground(new Color(190, 190, 255));
            }
            @Override
            public void focusLost(FocusEvent e) {
                if (new String(field.getPassword()).isEmpty()) {
                    field.setText(value);
                    field.setEchoChar((char) 0);
                    showPasswordButton.setVisible(false);
                    showPasswordButton.setText("Show");
                }
                field.setBackground(new Color(255, 255, 255));
            }
        });
        this.add(field);
        this.add(showPasswordButton);
    }
    // REGISTER LOGIC
    public void register(){
        String usernameInput = username.getText();
        String passwordInput = new String(password.getPassword());
        String confirmPasswordInput = new String(confirmPassword.getPassword());

        if (usernameInput.isEmpty() || usernameInput.equals("Enter username...") ||
                passwordInput.isEmpty() || passwordInput.equals("Enter password...") ||
                confirmPasswordInput.isEmpty() || confirmPasswordInput.equals("Confirm password...")){
            JOptionPane.showMessageDialog(this, "Fill up everything",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
        else if (accounts.checkIfExistent(usernameInput)){
            JOptionPane.showMessageDialog(this, "User already exist",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
        else if (!passwordInput.equals(confirmPasswordInput)){
            JOptionPane.showMessageDialog(this, "Password don't match",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
        else{
            JOptionPane.showMessageDialog(this, "Registration successful",
                    "Success", JOptionPane.INFORMATION_MESSAGE);
            accounts.addNewAccount(usernameInput, passwordInput);
            observer.addLoginPage();
        }
    }
}
