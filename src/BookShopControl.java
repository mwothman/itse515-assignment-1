import java.awt.*;
import java.awt.event.*;

public class BookShopControl extends Frame {
    private BookTable bookTable;

    private TextField titleField, authorField, dateField, priceField, quantityField, searchField;
    private TextArea displayArea;
    private Label totalBooksLabel, totalSoldBooksLabel;

    public BookShopControl(int warehouseCapacity) {
        bookTable = new BookTable(warehouseCapacity);

        setTitle("Computer Book Shop");
        setSize(700, 600);
        setLayout(new BorderLayout());
        setBackground(new Color(240, 248, 255)); // Light blue background

        // Warehouse Panel
        Panel warehousePanel = new Panel(new GridLayout(6, 2, 10, 10));
        warehousePanel.setBackground(new Color(230, 245, 241)); // Light green panel
        warehousePanel.setFont(new Font("Arial", Font.PLAIN, 14));
        warehousePanel.setPreferredSize(new Dimension(300, 250));
        warehousePanel.add(new Label("Title:"));
        titleField = new TextField();
        warehousePanel.add(titleField);

        warehousePanel.add(new Label("Author:"));
        authorField = new TextField();
        warehousePanel.add(authorField);

        warehousePanel.add(new Label("Publication Date:"));
        dateField = new TextField();
        warehousePanel.add(dateField);

        warehousePanel.add(new Label("Price:"));
        priceField = new TextField();
        warehousePanel.add(priceField);

        warehousePanel.add(new Label("Quantity:"));
        quantityField = new TextField();
        warehousePanel.add(quantityField);

        Button addBookButton = new Button("Add Book");
        addBookButton.setBackground(new Color(173, 216, 230)); // Soft blue
        addBookButton.addActionListener(e -> addBook());
        addBookButton.setFont(new Font("Arial", Font.BOLD, 12));
        warehousePanel.add(addBookButton);

        Button deleteBookButton = new Button("Delete Book");
        deleteBookButton.setBackground(new Color(255, 182, 193)); // Soft pink
        deleteBookButton.addActionListener(e -> deleteBook());
        deleteBookButton.setFont(new Font("Arial", Font.BOLD, 12));
        warehousePanel.add(deleteBookButton);

        // Statistics Panel
        Panel statsPanel = new Panel(new GridLayout(2, 2, 10, 10));
        statsPanel.setBackground(new Color(240, 255, 240)); // Light green background
        statsPanel.add(new Label("Total Books:"));
        totalBooksLabel = new Label("0");
        totalBooksLabel.setFont(new Font("Arial", Font.BOLD, 14));
        statsPanel.add(totalBooksLabel);

        statsPanel.add(new Label("Total Sold Books:"));
        totalSoldBooksLabel = new Label("0");
        totalSoldBooksLabel.setFont(new Font("Arial", Font.BOLD, 14));
        statsPanel.add(totalSoldBooksLabel);

        // Combine Warehouse and Statistics
        Panel warehouseStatsPanel = new Panel(new BorderLayout());
        warehouseStatsPanel.add(warehousePanel, BorderLayout.NORTH);
        warehouseStatsPanel.add(statsPanel, BorderLayout.SOUTH);

        // Display Panel
        displayArea = new TextArea();
        displayArea.setFont(new Font("Arial", Font.PLAIN, 14));
        displayArea.setBackground(Color.WHITE);
        displayArea.setEditable(false);
        displayArea.setPreferredSize(new Dimension(300, 200));

        // Search and Purchase Panel
        Panel customerPanel = new Panel(new FlowLayout());
        customerPanel.setBackground(new Color(245, 245, 255)); // Very light blue
        customerPanel.setFont(new Font("Arial", Font.PLAIN, 14));
        searchField = new TextField(20);
        customerPanel.add(new Label("Search:"));
        customerPanel.add(searchField);

        Button searchButton = new Button("Search");
        searchButton.setBackground(new Color(173, 216, 230)); // Soft blue
        searchButton.addActionListener(e -> searchBook());
        searchButton.setFont(new Font("Arial", Font.BOLD, 12));
        customerPanel.add(searchButton);

        Button purchaseButton = new Button("Purchase");
        purchaseButton.setBackground(new Color(255, 239, 213)); // Light cream
        purchaseButton.addActionListener(e -> purchaseBook());
        purchaseButton.setFont(new Font("Arial", Font.BOLD, 12));
        customerPanel.add(purchaseButton);

        // Add Components to Frame
        add(warehouseStatsPanel, BorderLayout.WEST);
        add(displayArea, BorderLayout.CENTER);
        add(customerPanel, BorderLayout.SOUTH);

        // Frame Closing Event
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }

    private void addBook() {
        String title = titleField.getText();
        String author = authorField.getText();
        String date = dateField.getText();
        double price = Double.parseDouble(priceField.getText());
        int quantity = Integer.parseInt(quantityField.getText());

        Book book = new Book(title, author, date, price);
        if (bookTable.addBook(book, quantity)) {
            displayArea.setText("Book added successfully!\n");
            updateStatistics();
        } else {
            displayArea.setText("Error: Warehouse full or book already exists.\n");
        }
    }

    private void deleteBook() {
        String title = searchField.getText();
        if (bookTable.deleteBook(title)) {
            displayArea.setText("Book deleted successfully!\n");
            updateStatistics();
        } else {
            displayArea.setText("Error: Book not found or warehouse empty.\n");
        }
    }

    private void searchBook() {
        String search = searchField.getText();
        Book book = bookTable.searchBook(search);
        if (book != null) {
            displayArea.setText("Book Found:\n" +
                    "Title: " + book.getTitle() + "\n" +
                    "Author: " + book.getAuthor() + "\n" +
                    "Publication Date: " + book.getPublicationDate() + "\n" +
                    "Price: $" + book.getPrice() + "\n");
        } else {
            displayArea.setText("Book not found.\n");
        }
    }

    private void purchaseBook() {
        String title = searchField.getText();
        if (bookTable.sellBook(title)) {
            displayArea.setText("Book purchased successfully!\n");
            updateStatistics();
        } else {
            displayArea.setText("Book out of stock or not found.\n");
        }
    }

    private void updateStatistics() {
        totalBooksLabel.setText(String.valueOf(bookTable.getTotalBooks()));
        totalSoldBooksLabel.setText(String.valueOf(bookTable.getTotalBooksSold()));
    }

    public static void main(String[] args) {
        BookShopControl app = new BookShopControl(10);
        app.setVisible(true);
    }
}
