package ClientSideSystem;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class OrderTable extends JPanel {
    private CartPage observer = null;
    private final String currency = " ₱";
    private HashMap<String, Details> orders = new HashMap<>();
    public OrderTable(){
        this.setBackground(new Color(215, 215, 215));
        this.setLayout(new FlowLayout(FlowLayout.LEFT));
        this.setVisible(true);
    }

    public void addOrder(Product product, int additionalQuantity){
        // If already ordered this kind of order so we increment the previous instance
        String orderName = product.getName();

        if (orders.containsKey(orderName)){
            int quantity = orders.get(orderName).quantity + additionalQuantity;
            orders.get(orderName).order.changeDescription("Quantity: "
                    +quantity + " | Price: "+(quantity * product.getPrice())+ currency);

            orders.get(orderName).quantity = quantity;
        }
        // If not yet ordered, adds this oder to the screen
        else{
            orders.put(orderName, new Details(product, additionalQuantity));
            product.changeDescription("Quantity: " +additionalQuantity
                    + " | Price: "+(additionalQuantity * product.getPrice())+ currency);
            product.setPreferredSize(new Dimension(150, 240));
            // adds the components to the order table
            product.add(Box.createRigidArea(new Dimension(0, 5)));
            product.add(createRemoveButton(this, orderName));
            this.add(product);
        }
    }
    // HELPER METHODS
    public JButton createRemoveButton(JPanel parent, String orderName){
        JButton button = new JButton("remove");
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setFocusable(false);
        button.setBackground(new Color(245, 80, 80));
        button.setForeground(new Color(255, 255, 255));

        button.addActionListener(e -> {
            int choice = JOptionPane.showConfirmDialog(this,
                    "Remove this order?",
                    "Confirm", JOptionPane.YES_NO_OPTION);

            if (choice != 0) return;

            Details orderToRemove = orders.remove(orderName);
            parent.remove(orderToRemove.order);
            observer.updateValues();
            parent.repaint(); parent.revalidate();
        });

        return button;
    }
    // HELPER METHODS
    public int getTableSize(){
        return orders.size();
    }
    public void clearTable(){
        orders.clear();
    }
    public void setObserver(CartPage observer){
        this.observer = observer;
    }
    public StringBuilder getContentsInStringFormat(){
        StringBuilder contents = new StringBuilder();

        for (String order: orders.keySet()){
            int quantity = orders.get(order).quantity;
            int price = orders.get(order).order.getPrice();

            contents.append("- ").append(quantity).append(" ")
                    .append(order).append(" | ")
                    .append(price).append(currency).append("\n");
        }

        return contents;
    }
    // CALCULATIONS
    public double computeSubTotal(){
        int sum = 0;

        for (Details p: orders.values())
            sum += p.order.getPrice()*p.quantity;

        return sum;
    }
    public double computeVat(double subTotal){
        double vatVar = .12;
        return subTotal* vatVar;
    }
    public double computeShippingFee(double subtotal){
        double shippingFeeVar = .05;
        return subtotal* shippingFeeVar;
    }
    // HELPER CLASS
   private static class Details{
        Product order;
        int quantity;
        Details(Product order, int quantity){
            this.order = order;
            this.quantity = quantity;
        }
    }
}
