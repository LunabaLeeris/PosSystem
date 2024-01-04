package ClientSideSystem;

import MainControllers.ShoppingSystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClientPage extends JPanel implements ActionListener {
    private final String currentUser;
    // PAGES
    private ProductsPage PRODUCTS;
    private CartPage CART;
    private Header HEADER;
    private OrderTable TABLE;


    public ClientPage(int width, int height, String user){
        // sets the frame
        this.setSize(width, height);
        this.setLayout(null);
        this.setVisible(true);
        this.setBackground(new Color(215, 215, 215));
        this.currentUser = user;
        // adds the components to the frame
        initializeComponents(width, height);
        addComponents();
    }

    public void initializeComponents(int width, int height){
        // table used throughout the system
        TABLE = new OrderTable();
        // COMPONENTS
        PRODUCTS = new ProductsPage(TABLE, width, height);
        PRODUCTS.setBounds(0, (int) (height * .09), (int) (width*.97), (int) (height * .88));
        CART = new CartPage(width, height, TABLE, currentUser);
        CART.setBounds(0, (int) (height * .09), (int) (width*.97), (int) (height * .88));
        HEADER = new Header(this);
        HEADER.setBounds(0, 0, (int) (width*.97), (int) (height * .08));
    }
    public void addComponents(){
        // adds components to body
        CART.revalidate();
        // adds BODY and HEADER to screen
        this.add(HEADER);
        this.add(PRODUCTS);
        // refreshes the components
        HEADER.repaint();
        PRODUCTS.revalidate();
        this.revalidate();
    }
    // ACTION LISTENER
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == HEADER.gotoCart){
            HEADER.addProductsButton();

            this.remove(PRODUCTS);
            this.add(CART);
            CART.checkIfEmpty();
            CART.updateValues();

            HEADER.repaint();
            CART.revalidate();
            this.repaint();
        }
        else if (e.getSource() == HEADER.gotoProducts){
            HEADER.addCartButton();

            this.remove(CART);
            this.add(PRODUCTS);

            HEADER.repaint();
            PRODUCTS.revalidate();
            this.repaint();
        }
    }

    public void registerObserver(ShoppingSystem observer){
        CART.addLogoutButton(observer);
    }
}
