import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;

class Button extends JButton {
    Button() {
        this.setPreferredSize(new Dimension(40, 60));
        this.setFocusable(false);
        this.setBackground(Color.LIGHT_GRAY);
        this.setForeground(Color.white);
        this.setText("+");
    }
}

class CartButton extends JButton {
    CartButton(String text) {
        this.setFocusable(false);
        this.setText(text);
    }
}

class NextButton extends JButton {
    NextButton(String text) {
        this.setPreferredSize(new Dimension(90, 20));
        this.setBackground(Color.LIGHT_GRAY);
        this.setFocusable(false);
        this.setText(text);
    }
}

class SearchField extends JTextField {
    SearchField() {
        this.setColumns(7);
        this.setText("Enter here to search");
    }
}

class BottomPanel extends JPanel {
    private NextButton next;
    private NextButton back;

    BottomPanel() {
        this.setPreferredSize(new Dimension(900, 50));
        this.setLayout(null);
        this.setBackground(Color.BLUE);

        next = new NextButton("Next");
        back = new NextButton("Back");

        back.setBounds(10, 15, 70, 20);
        next.setBounds(820, 15, 70, 20);

        this.add(next);
        this.add(back);
    }

    public NextButton getNextButton() {
        return next;
    }

    public NextButton getBackButton() {
        return back;
    }
}

class Tile extends JPanel {
    private JLabel priceLabel;
    private JLabel qtyLabel;
    private JLabel nameLabel;
    private int quantity;
    private double price;
    private String productName;

    Tile(String productName, double price) {
        this.productName = productName;
        this.price = price;
        this.quantity = 1; // Default quantity to 1

        this.setPreferredSize(new Dimension(870, 80));
        this.setBackground(Color.LIGHT_GRAY);
        this.setLayout(null);

        nameLabel = new JLabel(productName);
        nameLabel.setBounds(10, 10, 180, 20);
        nameLabel.setFont(new Font("Aharoni", Font.BOLD, 16));

        priceLabel = new JLabel("Price: $" + price);
        priceLabel.setBounds(750, 5, 120, 20);
        priceLabel.setFont(new Font("Aharoni", Font.BOLD, 12));

        qtyLabel = new JLabel("Qty: " + quantity);
        qtyLabel.setBounds(760, 55, 80, 20);
        qtyLabel.setFont(new Font("Aharoni", Font.BOLD, 12));

        JButton plusButton = new JButton("+");
        plusButton.setBounds(750, 32, 45, 20);

        JButton minusButton = new JButton("-");
        minusButton.setBounds(800, 32, 45, 20);

        this.add(nameLabel);
        this.add(priceLabel);
        this.add(qtyLabel);
        this.add(plusButton);
        this.add(minusButton);

        plusButton.addActionListener(e -> {
            quantity++;
            qtyLabel.setText("Qty: " + quantity);
        });

        minusButton.addActionListener(e -> {
            if (quantity > 0) {
                quantity--;
                qtyLabel.setText("Qty: " + quantity);
            }
        });
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }

    public String getProductName() {
        return productName;
    }
}

class LeftPanel extends JPanel {
    private CartButton addProductButton;
    private CartButton deleteButton;

    LeftPanel() {
        this.setBackground(Color.RED);
        this.setPreferredSize(new Dimension(80, 500));
        this.setLayout(null);

        addProductButton = new CartButton("Add product");
        addProductButton.setBackground(Color.GREEN);
        addProductButton.setBounds(0, 2, 80, 30);

        deleteButton = new CartButton("Delete");
        deleteButton.setBackground(Color.GREEN);
        deleteButton.setBounds(0, 34, 80, 30);

        this.add(addProductButton);
        this.add(deleteButton);
    }

    public CartButton getAddProductButton() {
        return addProductButton;
    }

    public CartButton getDeleteButton() {
        return deleteButton;
    }
}

class TopPanel extends JPanel {
    private SearchField searchField;
    private NextButton searchButton;

    TopPanel() {
        this.setPreferredSize(new Dimension(900, 80));
        this.setBackground(Color.PINK);
        this.setLayout(null);

        JLabel title = new JLabel("My Store");
        title.setBounds(15, 10, 300, 50);
        title.setFont(new Font("Times New Roman", Font.BOLD, 42));
        this.add(title);

        JLabel logo = new JLabel(new ImageIcon("bmw.png"));
        logo.setBounds(350, 25, 100, 70);
        this.add(logo);

        searchField = new SearchField();
        searchField.setBounds(615, 20, 180, 30);
        this.add(searchField);

        searchButton = new NextButton("Search");
        searchButton.setBounds(805, 20, 90, 30);
        this.add(searchButton);
    }

    public SearchField getSearchField() {
        return searchField;
    }

    public NextButton getSearchButton() {
        return searchButton;
    }
}

class CenterPanel extends JPanel {
    private JPanel productPanel;
    private JScrollPane scrollPane;
    private ArrayList<Tile> tiles;

    CenterPanel() {
        this.setPreferredSize(new Dimension(900, 500));
        this.setLayout(new BorderLayout(3,3));

        productPanel = new JPanel();
        // productPanel.setMaximumSize(new Dimension(870,80));
        productPanel.setLayout(new BoxLayout(productPanel, BoxLayout.Y_AXIS)); // Vertical layout

        scrollPane = new JScrollPane(productPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        this.add(scrollPane, BorderLayout.CENTER);

        tiles = new ArrayList<>();
    }

    public void addTile(Tile tile) {
        tiles.add(tile);
        tile.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80)); 
        productPanel.add(Box.createVerticalStrut(10)); // Adding vertical gap between tiles
        productPanel.add(tile);
        productPanel.revalidate();
        productPanel.repaint();
    }

    public ArrayList<Tile> getTiles() {
        return tiles;
    }

    public void searchProduct(String query) {
        productPanel.removeAll();
        for (Tile tile : tiles) {
            if (tile.getProductName().toLowerCase().contains(query.toLowerCase())) {
                productPanel.add(Box.createVerticalStrut(10)); // Adding vertical gap between tiles
                productPanel.add(tile);
            }
        }
        productPanel.revalidate();
        productPanel.repaint();
    }
}

class Frame extends JFrame {
    private CenterPanel centerPanel;
    private TopPanel topPanel;
    private BottomPanel bottomPanel;
    private LeftPanel leftPanel;

    Frame() {
        this.setLayout(new BorderLayout(5, 5));

        centerPanel = new CenterPanel();
        topPanel = new TopPanel();
        bottomPanel = new BottomPanel();
        leftPanel = new LeftPanel();

        this.add(centerPanel, BorderLayout.CENTER);
        this.add(topPanel, BorderLayout.NORTH);
        this.add(bottomPanel, BorderLayout.SOUTH);
        this.add(leftPanel, BorderLayout.WEST);

        this.pack();
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);

        initializeActions();
    }

    private void initializeActions() {
        leftPanel.getAddProductButton().addActionListener(e -> {
            JTextField productNameField = new JTextField(10);
            JTextField priceField = new JTextField(10);

            JPanel myPanel = new JPanel();
            myPanel.add(new JLabel("Product Name:"));
            myPanel.add(productNameField);
            myPanel.add(Box.createHorizontalStrut(15));
            myPanel.add(new JLabel("Price:"));
            myPanel.add(priceField);

            int result = JOptionPane.showConfirmDialog(null, myPanel, 
                     "Please Enter Product Name and Price", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                String productName = productNameField.getText();
                double price = Double.parseDouble(priceField.getText());
                centerPanel.addTile(new Tile(productName, price));
            }
        });

        topPanel.getSearchButton().addActionListener(e -> {
            String query = topPanel.getSearchField().getText();
            centerPanel.searchProduct(query);
        });

        bottomPanel.getNextButton().addActionListener(e -> {
            double totalCost = centerPanel.getTiles().stream()
                .mapToDouble(tile -> tile.getPrice() * tile.getQuantity())
                .sum();
            
            JFrame totalCostFrame = new JFrame("Total Cost");
            totalCostFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            totalCostFrame.setSize(300, 200);
            totalCostFrame.setLayout(new BorderLayout());
            
            JLabel totalCostLabel = new JLabel("Total cost of shopping: $" + totalCost, JLabel.CENTER);
            totalCostLabel.setFont(new Font("Arial", Font.BOLD, 16));
            totalCostFrame.add(totalCostLabel, BorderLayout.CENTER);
            
            totalCostFrame.setVisible(true);
        });

        bottomPanel.getBackButton().addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Returning to previous page...");
        });
    }
}

public class App{
    public static void main(String[] args) {
        new Frame();
    }
}
