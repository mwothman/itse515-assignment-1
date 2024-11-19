import java.awt.*;
import java.awt.event.*;

public class BookShopGUI {
    private Frame frame;
    private TextField searchField, titleField, authorField, priceField, stockField;
    private Button searchButton, clearSearchButton, filterButton, buyButton, addBookButton, deleteBookButton;
    private Panel bookButtonsPanel, warehousePanel;
    private Label selectedBookLabel, soldCountLabel, totalBooksLabel, totalSoldLabel;
    private BookTable bookTableData;
    private ScrollPane scrollPane;

    public BookShopGUI() {
        bookTableData = new BookTable();

        frame = new Frame("Book Shop");
        frame.setLayout(new BorderLayout());
        frame.setBackground(new Color(240, 240, 240)); // Light gray background for the frame

        // Top panel with search options
        Panel topPanel = new Panel();
        topPanel.setBackground(new Color(200, 200, 200)); // Subtle light gray for top panel
        searchField = new TextField(20);
        searchButton = new Button("Search");
        searchButton.setBackground(new Color(100, 150, 200)); // Muted blue button
        searchButton.setForeground(Color.WHITE);
        clearSearchButton = new Button("Clear Search");
        clearSearchButton.setBackground(new Color(200, 100, 100)); // Muted red button
        clearSearchButton.setForeground(Color.WHITE);
        filterButton = new Button("Filter Available Books");
        filterButton.setBackground(new Color(100, 200, 100)); // Soft green button
        filterButton.setForeground(Color.WHITE);

        topPanel.add(searchField);
        topPanel.add(searchButton);
        topPanel.add(clearSearchButton);
        topPanel.add(filterButton);

        // Warehouse panel for adding and deleting books
        warehousePanel = new Panel(new GridLayout(0, 2, 5, 5));
        titleField = new TextField();
        authorField = new TextField();
        priceField = new TextField();
        stockField = new TextField();

        addBookButton = new Button("Add Book");
        addBookButton.setBackground(new Color(100, 200, 100));
        addBookButton.setForeground(Color.WHITE);
        addBookButton.setEnabled(false);

        deleteBookButton = new Button("Delete Book");
        deleteBookButton.setBackground(new Color(200, 100, 100));
        deleteBookButton.setForeground(Color.WHITE);
        deleteBookButton.setEnabled(false);

        warehousePanel.add(new Label("Title:"));
        warehousePanel.add(titleField);
        warehousePanel.add(new Label("Author:"));
        warehousePanel.add(authorField);
        warehousePanel.add(new Label("Price:"));
        warehousePanel.add(priceField);
        warehousePanel.add(new Label("Stock:"));
        warehousePanel.add(stockField);
        warehousePanel.add(addBookButton);
        warehousePanel.add(deleteBookButton);

        // Book buttons panel with scroll
        bookButtonsPanel = new Panel();
        bookButtonsPanel.setLayout(new GridLayout(0, 2, 5, 5)); // Adjusted layout for book buttons
        scrollPane = new ScrollPane();
        scrollPane.add(bookButtonsPanel); // Add the book buttons panel to the scroll pane
        populateBookButtons(bookTableData.getBooks()); // Populate initial book buttons

        // Bottom panel with selected book details
        Panel bottomPanel = new Panel(new GridLayout(5, 1, 5, 5));
        bottomPanel.setBackground(new Color(230, 230, 230)); // Light neutral background for bottom panel
        selectedBookLabel = new Label("Selected Book: None");
        selectedBookLabel.setForeground(new Color(60, 60, 60)); // Dark gray text for better readability
        soldCountLabel = new Label("Sold Count: 0");
        soldCountLabel.setForeground(new Color(60, 60, 60));
        totalBooksLabel = new Label("Total Books: " + bookTableData.getTotalBooks());
        totalBooksLabel.setForeground(new Color(60, 60, 60));
        totalSoldLabel = new Label("Total Books Sold: " + calculateTotalSoldBooks());
        totalSoldLabel.setForeground(new Color(60, 60, 60));
        buyButton = new Button("Buy");
        buyButton.setEnabled(false);
        buyButton.setBackground(new Color(200, 180, 50)); // Muted yellow button
        buyButton.setForeground(Color.WHITE);

        bottomPanel.add(selectedBookLabel);
        bottomPanel.add(soldCountLabel);
        bottomPanel.add(totalBooksLabel);
        bottomPanel.add(totalSoldLabel);
        bottomPanel.add(buyButton);

        // Center panel for warehouse and book display
        Panel centerPanel = new Panel(new BorderLayout());
        centerPanel.setBackground(new Color(230, 230, 230));
        centerPanel.add(warehousePanel, BorderLayout.NORTH);
        centerPanel.add(scrollPane, BorderLayout.CENTER); // Add the scroll pane to the center panel

        // Adding panels to the frame
        frame.add(topPanel, BorderLayout.NORTH);
        frame.add(centerPanel, BorderLayout.CENTER); // Now centerPanel contains the scrollPane
        frame.add(bottomPanel, BorderLayout.SOUTH);

        // Adding event listeners
        addEventListeners();

        // Frame settings
        frame.setSize(800, 600);
        frame.setVisible(true);

        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                System.exit(0);
            }
        });
    }

    private void addEventListeners() {

        // Listens for changes in the fields to enable/disable Add button
        titleField.addTextListener(e -> checkAddButtonState());
        authorField.addTextListener(e -> checkAddButtonState());
        priceField.addTextListener(e -> checkAddButtonState());
        stockField.addTextListener(e -> checkAddButtonState());

        searchButton.addActionListener(e -> {
            String searchQuery = searchField.getText();
            if (!searchQuery.isEmpty()) {
                Book[] filteredBooks = bookTableData.filterByTitle(searchQuery);
                populateBookButtons(filteredBooks);
            }
        });

        clearSearchButton.addActionListener(e -> {
            searchField.setText("");
            populateBookButtons(bookTableData.getBooks());
        });

        filterButton.addActionListener(e -> {
            Book[] availableBooks = bookTableData.filterByAvailablity();
            populateBookButtons(availableBooks);
        });

        buyButton.addActionListener(e -> {
            for (Book book : bookTableData.getBooks()) {
                if (book.getTitle().equals(selectedBookLabel.getText().replace("Selected Book: ", ""))) {
                    book.updateSoldAmount(1);
                    book.updateTotalStock(book.getStock() - 1);
                    updateBookButton(book); // Update only the selected book's button
                    selectedBookLabel.setText("Selected Book: " + book.getTitle());
                    soldCountLabel.setText("Sold Count: " + book.getSoldCount());
                    updateStatistics();
                    return;
                }
            }
        });

        addBookButton.addActionListener(e -> {
            String title = titleField.getText();
            String author = authorField.getText();
            String priceText = priceField.getText();
            String stockText = stockField.getText();

            // Validate input fields
            if (title.isEmpty() || author.isEmpty() || priceText.isEmpty() || stockText.isEmpty()) {
                System.out.println("All fields must be filled out!");
                return; // You can show a dialog instead for user-friendly feedback
            }

            try {
                float price = Float.parseFloat(priceText);
                int stock = Integer.parseInt(stockText);
                Book newBook = new Book(title, author, "N/A", price, stock);
                bookTableData.addBook(newBook, stock); // Add the new book to the BookTable
                populateBookButtons(bookTableData.getBooks()); // Update the book buttons in the UI
                clearFields(); // Optionally clear the input fields
            } catch (NumberFormatException ex) {
                System.out.println("Invalid price or stock value!");
            }
        });

        // Delete Book button
        deleteBookButton.addActionListener(e -> {
            String title = selectedBookLabel.getText().replace("Selected Book: ", "");

            // Check if a book is selected
            if (title.isEmpty() || title.equals("None")) {
                System.out.println("No book selected to delete!");
                return;
            }

            // Find and remove the selected book from the BookTable
            for (Book book : bookTableData.getBooks()) {
                if (book.getTitle().equals(title)) {
                    bookTableData.deleteBook(book); // Remove the book from the BookTable
                    populateBookButtons(bookTableData.getBooks()); // Update the book buttons in the UI
                    clearFields(); // Optionally clear the input fields
                    break;
                }
            }
            deleteBookButton.setEnabled(false);
        });
    }

    private void clearFields() {
        titleField.setText("");
        authorField.setText("");
        priceField.setText("");
        stockField.setText("");
    }

    // Method to check if the add button should be enabled or disabled
    private void checkAddButtonState() {
        // Disable the Add Book button if any field is empty
        if (titleField.getText().isEmpty() || authorField.getText().isEmpty() ||
                priceField.getText().isEmpty() || stockField.getText().isEmpty()) {
            addBookButton.setEnabled(false); // Disable Add Book button
        } else {
            addBookButton.setEnabled(true); // Enable Add Book button
        }
    }

    private void populateBookButtons(Book[] books) {
        bookButtonsPanel.removeAll();
        for (Book book : books) {
            Panel bookPanel = new Panel(new GridLayout(6, 1));
            bookPanel.setBackground(new Color(255, 255, 255)); // White background for book panel

            Label titleLabel = new Label("Title: " + book.getTitle());
            titleLabel.setForeground(new Color(50, 50, 50)); // Dark gray text
            Label authorLabel = new Label("Author: " + book.getAuthor());
            authorLabel.setForeground(new Color(50, 50, 50));
            Label priceLabel = new Label("Price: $" + book.getPrice());
            priceLabel.setForeground(new Color(80, 100, 80)); // Muted green text
            Label stockLabel = new Label("Stock: " + book.getStock());
            stockLabel.setForeground(new Color(100, 50, 50)); // Soft red text
            Label availableLabel = new Label("Available: " + (book.isBookAvailable() ? "Yes" : "No"));
            availableLabel.setForeground(new Color(50, 50, 50));

            Button selectButton = new Button("Select");
            selectButton.setBackground(new Color(100, 150, 200)); // Soft blue button
            selectButton.setForeground(Color.WHITE);
            selectButton.addActionListener(e -> {
                selectedBookLabel.setText("Selected Book: " + book.getTitle());
                soldCountLabel.setText("Sold Count: " + book.getSoldCount());
                buyButton.setEnabled(true);
                deleteBookButton.setEnabled(true);
            });

            bookPanel.add(titleLabel);
            bookPanel.add(authorLabel);
            bookPanel.add(priceLabel);
            bookPanel.add(stockLabel);
            bookPanel.add(availableLabel);
            bookPanel.add(selectButton);

            bookButtonsPanel.add(bookPanel);
        }

        bookButtonsPanel.revalidate();
        bookButtonsPanel.repaint();
    }

    private void updateBookButton(Book book) {
        for (Component comp : bookButtonsPanel.getComponents()) {
            Panel bookPanel = (Panel) comp;
            Label titleLabel = (Label) bookPanel.getComponent(0);
            if (titleLabel.getText().contains(book.getTitle())) {
                Label stockLabel = (Label) bookPanel.getComponent(3);
                stockLabel.setText("Stock: " + book.getStock());
                bookPanel.repaint();
            }
        }
    }

    private int calculateTotalSoldBooks() {
        int totalSold = 0;
        for (Book book : bookTableData.getBooks()) {
            totalSold += book.getSoldCount();
        }
        return totalSold;
    }

    private void updateStatistics() {
        totalBooksLabel.setText("Total Books: " + bookTableData.getTotalBooks());
        totalSoldLabel.setText("Total Books Sold: " + calculateTotalSoldBooks());
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(BookShopGUI::new);
    }
}
