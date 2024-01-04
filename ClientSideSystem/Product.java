package ClientSideSystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Product extends JPanel {
    private JLabel productName = new JLabel();
    private JLabel productDescription = new JLabel();
    private final int price;
    private final String currency = " ₱";
    private JComponent Image;

    public Product(JComponent img, String name, int price){
        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        this.price = price;


        Image = img;
        changeName(name);
        changeDescription(price+currency);
        initializeComponents();

        this.add(Image);
        Image.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.add(Box.createRigidArea(new Dimension(0, 5)));
        this.add(productName);
        productName.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.add(productDescription);
        productDescription.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.revalidate();
    }
    public void initializeComponents(){
        productName.setBackground(new Color(26, 26, 42));
        productName.setFont(new Font("Poppings", Font.BOLD, 13));
        productDescription.setBackground(new Color(26, 26, 42));
        productDescription.setFont(new Font("Poppings", Font.PLAIN, 12));
        addHoverEffect(this);
    }
    public void changeName(String text){
        productName.setText(text);
    }
    public void changeDescription(String text){
        productDescription.setText(text);
    }
    public String getName(){
        return this.productName.getText();
    }
    public int getPrice(){
        return this.price;
    }
    public void addHoverEffect(JPanel parent){
        // adds a changing color on hover effect
        Image.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                parent.setBackground(new Color(152, 152, 248));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                parent.setBackground(new Color(255, 255, 255));
            }
        });
    }
}
