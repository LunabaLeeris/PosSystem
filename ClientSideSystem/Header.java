package ClientSideSystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class Header extends JPanel {
    private ActionListener parent;
    public JButton gotoCart;
    public JButton gotoProducts;
    public Header(ActionListener parent){
        this.setLayout(new GridLayout(0, 3, 10, 10));
        this.setBackground(new Color(0, 0, 0));
        this.setLayout(new BorderLayout());
        this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        this.parent = parent;

        initializeComponents();
        addCartButton();
        addLogo();

        this.setVisible(true);
    }

    public void initializeComponents(){
        gotoCart = new JButton("Cart");
        gotoCart.setPreferredSize(new Dimension(150,100));
        gotoCart.setFont(new Font("Poppins", Font.BOLD, 13));
        gotoCart.setBackground(new Color(128, 128, 255));
        gotoCart.setForeground(new Color(255, 255, 255));
        gotoCart.setFocusable(false);

        gotoProducts = new JButton("Products");
        gotoProducts.setFont(new Font("Poppins", Font.BOLD, 13));
        gotoProducts.setPreferredSize(new Dimension(150,100));
        gotoProducts.setBackground(new Color(152, 152, 248));
        gotoProducts.setForeground(new Color(255, 255, 255));
        gotoProducts.setFocusable(false);
        // adding action listener
        gotoCart.addActionListener(parent);
        gotoProducts.addActionListener(parent);

        // add buttons
        this.add(gotoProducts, BorderLayout.EAST);
    }

    public void addCartButton(){
        this.add(gotoCart, BorderLayout.EAST);
        this.remove(gotoProducts);
    }

    public void addProductsButton(){
        this.add(gotoProducts, BorderLayout.EAST);
        this.remove(gotoCart);
    }

    public void addLogo(){
        ImageIcon imageIcon = new ImageIcon("src\\logo-new.jpg");
        JLabel label = new JLabel(imageIcon);
        label.setPreferredSize(new Dimension(150, 50));
        this.add(label, BorderLayout.WEST);
    }
}
