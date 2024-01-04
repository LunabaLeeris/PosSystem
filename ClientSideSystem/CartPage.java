package ClientSideSystem;

import MainControllers.ShoppingSystem;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CartPage extends JPanel {
    private String currentUser;
    private final OrderTable ordersTable;
    private final JLabel flag = new JLabel("You haven't ordered yet...");
    private final String currency = " ₱";
    private JLabel subtotalValue, vatValue, shippingFeeValue, totalValue;
    private JButton checkOutButton;
    private JScrollPane ordersSection;
    private final int width, height;

    public CartPage(int width, int height, OrderTable table, String user){
        this.currentUser = user;
        this.width = width; this.height = height;
        this.setBackground(new Color(255, 255, 255));
        this.setLayout(null);
        this.setBackground(new Color(215, 215, 215));
        this.ordersTable = table;
        table.setObserver(this);

        addCart();
        addTransactionDetails();
        addCheckoutButton();

        this.setVisible(true);
    }
    public void addCart(){
        // LABEL
        JLabel ordersTag = new JLabel("YOUR CART");
        ordersTag.setForeground(new Color(26, 26, 42));
        ordersTag.setFont(new Font("Poppins", Font.BOLD, 20));
        ordersTag.setBounds(10, 5, width, 40);
        // ADDS FLAG THAT CART IS EMPTY
        flag.setFont(new Font("Poppins", Font.ITALIC, 20));
        flag.setForeground(new Color(26, 26, 42, 102));
        flag.setHorizontalAlignment(SwingConstants.CENTER);
        // INITIALIZATION OF THE TABLE PROPERTIES
        ordersSection = new JScrollPane(flag,
                JScrollPane.VERTICAL_SCROLLBAR_NEVER,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        ordersSection.setBounds(5, 50, (int) (width * .94), 255);
        ordersSection.revalidate();
        // ADDS THE COMPONENTS
        this.add(ordersTag);
        this.add(ordersSection);
    }
    public void addTransactionDetails(){
        // LABEL HEADLINE
        JLabel detailsTag = new JLabel("TRANSACTION DETAILS");
        detailsTag.setForeground(new Color(26, 26, 42));
        detailsTag.setFont(new Font("Poppins", Font.BOLD, 20));
        detailsTag.setBounds(10, 320, width, 40);
        // ADD transaction section
        JPanel transactionPanel = new JPanel();
        transactionPanel.setLayout(null);
        transactionPanel.setBackground(new Color(245, 245, 245));
        transactionPanel.setBounds((int)(width*.07), 380, (int)(width*.8), 140);
        transactionPanel.setVisible(true);
        // ADDS THE LABELS FOR SUBTOTAL, VAT AND SHIPPING FEE
        JLabel subtotal = new JLabel("Subtotal: ");
        subtotal.setForeground(new Color(50, 50, 83));
        subtotal.setFont(new Font("Poppins", Font.BOLD, 20));
        subtotal.setBounds((int)(width*.1), 390, width, 30);
        JLabel vat = new JLabel("VAT: ");
        vat.setForeground(new Color(50, 50, 83));
        vat.setFont(new Font("Poppins", Font.BOLD, 20));
        vat.setBounds((int)(width*.1), 430, width, 30);
        JLabel shippingFee = new JLabel("Shipping fee: ");
        shippingFee.setForeground(new Color(50, 50, 83));
        shippingFee.setFont(new Font("Poppins", Font.BOLD, 20));
        shippingFee.setBounds((int)(width*.1), 470, width, 30);
        // SUBTOTAL
        subtotalValue = new JLabel("0"+currency);
        subtotalValue.setForeground(new Color(50, 50, 83));
        subtotalValue.setHorizontalAlignment(SwingConstants.RIGHT);
        subtotalValue.setFont(new Font("Poppins", Font.PLAIN, 20));
        subtotalValue.setBounds(0, 390, (int)(width*.82), 30);
        // VAT VALUE
        vatValue = new JLabel("-0"+currency);
        vatValue.setForeground(new Color(206, 46, 46));
        vatValue.setHorizontalAlignment(SwingConstants.RIGHT);
        vatValue.setFont(new Font("Poppins", Font.PLAIN, 20));
        vatValue.setBounds(0, 430, (int)(width*.82), 30);
        // SHIPPING FEE
        shippingFeeValue = new JLabel("-0"+currency);
        shippingFeeValue.setForeground(new Color(206, 46, 46));
        shippingFeeValue.setHorizontalAlignment(SwingConstants.RIGHT);
        shippingFeeValue.setFont(new Font("Poppins", Font.PLAIN, 20));
        shippingFeeValue.setBounds(0, 470, (int)(width*.82), 30);
        // TOTAL VALUE
        totalValue = new JLabel("0"+currency);
        totalValue.setHorizontalAlignment(SwingConstants.CENTER);
        totalValue.setForeground(new Color(50, 50, 83));
        totalValue.setFont(new Font("Poppins", Font.BOLD, 30));
        totalValue.setBounds(0, (int)(height*.65), width, 40);
        // ADDS THE COMPONENTS TO SCREEN
        this.add(detailsTag);
        this.add(subtotal); this.add(subtotalValue);
        this.add(shippingFee); this.add(shippingFeeValue);
        this.add(vat); this.add(vatValue);
        this.add(totalValue);
        this.add(transactionPanel);
    }

     //CHECK OUT BUTTON
    public void addCheckoutButton(){
        checkOutButton = new JButton("Checkout");
        checkOutButton.setBackground(new Color(26, 26, 42));
        checkOutButton.setForeground(new Color(255, 255, 255));
        checkOutButton.setFont(new Font("Poppins", Font.BOLD, 20));
        checkOutButton.setBounds((int)(width*.25), (int)(height*.71), (int)(width*.5), (int)(width*.15));
        checkOutButton.setFocusable(false);

        checkOutButton.addActionListener(e ->{
            int choice = JOptionPane.showConfirmDialog(null, "Proceed checkout?",
                                                                "Confirm", JOptionPane.YES_NO_OPTION);
            if (choice != 0) return;
            checkOut();
        });

        this.add(checkOutButton);
    }
    public void checkOut(){
        // Writes to the transaction file about this transaction LOG
        String filePath = "src\\Files\\TransactionHistory.txt";

        StringBuilder fileContent = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                fileContent.append(line).append("\n");
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "An error occured",
                    "error", JOptionPane.ERROR_MESSAGE);
        }

        StringBuilder content = new StringBuilder();

        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("MMMM, dd|hh:mm a");
        content.append("=== ").append(LocalDateTime.now().format(dateFormat)).append(" === \n");
        content.append("Buyer name: ").append(currentUser);
        content.append("\n\nORDERS: \n");
        content.append(ordersTable.getContentsInStringFormat()).append("\n");

        double subtotal = ordersTable.computeSubTotal();
        double vat = ordersTable.computeVat(subtotal);
        double shippingFee = ordersTable.computeShippingFee(subtotal);

        content.append("subtotal: ").append(subtotal).append(currency).append("\n");
        content.append("vat: ").append(vat).append(currency).append("\n");
        content.append("shipping fee: ").append(shippingFee).append(currency).append("\n");
        content.append("total: ").append(subtotal + vat + shippingFee).append(currency).append("\n\n");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.append(content);
            writer.append(fileContent);
            JOptionPane.showMessageDialog(this, "Transaction Successful",
                    "error", JOptionPane.INFORMATION_MESSAGE);
        }
        // Something went wrong while writing
        catch (IOException e) {
            JOptionPane.showMessageDialog(this, "An error occured",
                                                      "error", JOptionPane.ERROR_MESSAGE);
        }

        ordersTable.removeAll();
        ordersTable.clearTable();
        updateValues();
    }
    public void updateValues(){
        checkIfEmpty();
        // UPDATES THE VALUES FOR THE RECEIPT
        double subtotal = ordersTable.computeSubTotal();
        double vat = ordersTable.computeVat(subtotal);
        double shippingFee = ordersTable.computeShippingFee(subtotal);
        double total = subtotal + vat + shippingFee;
        // SETS THE VALUES
        this.subtotalValue.setText(String.format(currency+" %.2f", subtotal));
        this.vatValue.setText(String.format("+"+currency+" %.2f", vat));
        this.shippingFeeValue.setText(String.format("+"+currency+" %.2f", shippingFee));
        this.totalValue.setText(String.format(currency+" %.2f", total));
        this.repaint();
    }
    // HELPER METHODS
    public void checkIfEmpty(){
        // checks if the cart is empty.
        if (ordersTable.getTableSize() > 0){
            ordersSection.setViewportView(ordersTable);
            checkOutButton.setEnabled(true);
        }
        else{
            ordersSection.setViewportView(flag);
            checkOutButton.setEnabled(false);
        }

        ordersSection.revalidate();
    }

        //LOG OUT BUTTON
    public void addLogoutButton(ShoppingSystem observer){
        JButton logoutButton = new JButton("Logout");
        logoutButton.setFont(new Font("Poppins", Font.BOLD, 12));
        logoutButton.setBounds(370, 5, 100,30);
        logoutButton.setBackground(new Color(206, 46, 46));
        logoutButton.setForeground(new Color(255, 255, 255));
        logoutButton.setFocusable(false);
        logoutButton.addActionListener(e -> {
            int choice = JOptionPane.showConfirmDialog(this, "Proceed logout?",
                                                    "Logout", JOptionPane.YES_NO_OPTION);

            if (choice != 0) return;
            observer.addLoginPage();
        });
        this.add(logoutButton);
    }
}
