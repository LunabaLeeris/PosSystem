package MainControllers;

import ClientSideSystem.*;
import MainControllers.LoginPage;
import MainControllers.RegisterPage;
import javax.swing.*;

public class ShoppingSystem extends JFrame{
    private int width, height;
    private ClientPage clientPage;
    private LoginPage loginPage;
    private RegisterPage registerPage;

    public ShoppingSystem(int width, int height){
        this.width = width; this.height = height;
        // sets the frame
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(width, height);
        this.setLayout(null);
        this.setResizable(true);
        this.setVisible(true);
        // adds the components to the frame
        initializeComponents();
        addComponents();
    }

    public void initializeComponents(){
        loginPage = new LoginPage(width, height, this);
        loginPage.setBounds(0, 0, width, height);
        registerPage = new RegisterPage(width, height, this);
        loginPage.setBounds(0, 0, width, height);
    }

    public void addComponents(){
        addLoginPage();
    }

    // CONTROL FUNCTIONS
    public void addLoginPage(){
        this.getContentPane().removeAll();
        this.add(loginPage);

        refresh();
    }
    public void addRegisterPage(){
        this.getContentPane().removeAll();
        this.add(registerPage);

        refresh();
    }
    public void addClientPage(String user){
        this.getContentPane().removeAll();
        clientPage = new ClientPage(width, height, user);
        clientPage.registerObserver(this);
        this.add(clientPage);

        refresh();
    }
    public void addAdminPage(String user){

    }
    // refreshes screen
    public void refresh(){
        this.revalidate();
        this.repaint();
    }
}
