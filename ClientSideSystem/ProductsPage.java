package ClientSideSystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.HashMap;
import java.util.List;

// HOLDS THE PRODUCTS
public class ProductsPage extends JPanel implements KeyListener{
    private final String productImagesPath = "src\\ProductImages";
    private final String currency = " ₱";
    private OrderTable table;
    private HashMap<String, JPanel> products;
    private JPanel productsSection;
    private JTextField searchField;
    private SearchAPI search;
    private JLabel flag;
    private JScrollPane scrollSection;
    private int width, height;

    public ProductsPage(OrderTable table, int width, int height){
        this.width = width; this.height = height;
        this.setBackground(new Color(215, 215, 215));
        this.setLayout(null);
        this.table = table;
        // adds the components
        this.add(Box.createRigidArea(new Dimension(5, 5)));
        addSearchField();
        this.add(Box.createRigidArea(new Dimension(5, 5)));
        addProductsSection();
        this.setVisible(true);
    }

    //Search Items
    public void addSearchField(){
        searchField = new JTextField("Search here...");
        searchField.setBounds((int)(width*.02), 5, (int)(width*.93), (int) (height*.05));
        searchField.setFont(new Font("Poppins", Font.BOLD, 13));
        searchField.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 5));
        searchField.setBackground(new Color(255, 255, 255));
        searchField.setCaretPosition(10);
        searchField.setVisible(true);
        searchField.addKeyListener(this);
        searchField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (searchField.getText().equals("Search here...")) {
                    searchField.setText("");
                    searchField.setBackground(new Color(190, 190, 255));
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                if (searchField.getText().isEmpty()) {
                    searchField.setText("Search here...");
                    searchField.setBackground(new Color(255, 255, 255));
                }
            }
        });

        this.add(searchField);
    }

    public void addProductsSection(){
        products = new HashMap<>();
        search = new SearchAPI();
        // Initialize the JPanel
        productsSection = new JPanel();
        productsSection.setBackground(new Color(215, 215, 215));
        productsSection.setLayout(new FlowLayout(FlowLayout.LEFT));
        productsSection.setVisible(true);
        // FLAG
        flag = new JLabel("No results found...");
        flag.setFont(new Font("Poppins", Font.ITALIC, 20));
        flag.setForeground(new Color(26, 26, 42, 102));
        flag.setHorizontalAlignment(SwingConstants.CENTER);
        // adds the products on productImages to the JPanel
        File[] productImages = new File(productImagesPath).listFiles();

        if (productImages == null) return;
        for (File product : productImages) {
            String[] productDetails = product.getName().split("\\.");
            // Initialize the button
            JButton productPlaceHolder = new JButton();
            productPlaceHolder.setBackground(new Color(34, 34, 57));
            // Scale the image to the appropriate size
            Image original = new ImageIcon(product.toString()).getImage();
            productPlaceHolder.setIcon(new ImageIcon(original.getScaledInstance(225, 225, Image.SCALE_SMOOTH)));
            // ADDs the action listener to the button
            productPlaceHolder.addActionListener(e -> {
                // show confirmation
                String quantity = JOptionPane.showInputDialog(this,
                        "Name: " + productDetails[0] + "\nPrice: " + productDetails[1] + currency,
                        "Input quantity", JOptionPane.QUESTION_MESSAGE);
                // exited or did not input any quantity
                if (quantity == null || quantity.isEmpty()) return;
                // checks if the number inputted is a valid integer
                try {
                    int q = Integer.parseInt(quantity);
                    JOptionPane.showMessageDialog(this,
                            "Order added to cart",
                            "Success", JOptionPane.INFORMATION_MESSAGE);
                    // adds value to the table
                    JLabel imageHolder = new JLabel();
                    // resizes the image
                    Image originalImage = new ImageIcon(product.toString()).getImage();
                    Image resizedImage = originalImage.getScaledInstance(
                            (int) (originalImage.getWidth(null)*.7),
                            (int) (originalImage.getHeight(null)*.7),
                            Image.SCALE_SMOOTH);
                    // adds the image to the label
                    imageHolder.setIcon(new ImageIcon(resizedImage));
                    Product newOrder = new Product(imageHolder, productDetails[0],
                            Integer.parseInt(productDetails[1]));
                    table.addOrder(newOrder, q);
                }
                // inputted a non int value
                catch (NumberFormatException exception) {
                    JOptionPane.showMessageDialog(this,
                            "Invalid amount!", "Error", JOptionPane.WARNING_MESSAGE);
                }
                //itemCost();
            });
            JPanel newProduct = new Product(productPlaceHolder, productDetails[0], Integer.parseInt(productDetails[1]));
            newProduct.setPreferredSize(new Dimension((int) (width/3.35), (int) (width/1.8)));
            products.put(productDetails[0], newProduct);
            productsSection.add(newProduct);
        }
        productsSection.setPreferredSize(new Dimension(width, computeApproximateHeight(products.size())));
        scrollSection = new JScrollPane(productsSection);
        scrollSection.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollSection.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollSection.setBounds(0,  (int)(height*.07), (int)(width*.97), (int)(height*.8));

        this.add(scrollSection);
    }
    // HELPER METHODS
    public int computeApproximateHeight(int size){
        return (int) ((size/3 + 1)*(width/1.5));
    }
    public void addToScreen(List<String> toAdd){
        productsSection.removeAll();

        if (toAdd.size() == 0) scrollSection.setViewportView(flag);
        else {
            for (String product: toAdd)
                productsSection.add(products.get(product));

            scrollSection.setViewportView(productsSection);
            productsSection.setPreferredSize(new Dimension(width,
                    computeApproximateHeight(toAdd.size())));
            productsSection.revalidate();
            productsSection.repaint();
        }
    }
    // KEYBOARD LISTENERS
    @Override
    public void keyTyped(KeyEvent e) {
        // nothing
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // refreshes the focus on the search bar
        if (e.getKeyCode() == KeyEvent.VK_ENTER){
            searchField.setFocusable(false);
            searchField.setFocusable(true);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (searchField.getText() == null || searchField.getText().isEmpty())
            addToScreen(products.keySet().stream().toList());
        else{
            addToScreen(search.find(searchField.getText(), products.keySet()));
        }
    }
    // KEYBOARD LISTER

}
