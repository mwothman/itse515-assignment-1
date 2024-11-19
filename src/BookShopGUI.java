import java.awt.*;
import java.awt.event.*;

public class BookShopGUI {
    private Frame frame;
    private TextField searchField, searcAutherField, titleField, authorField, priceField, stockField, puplicationDateField;
    private Button searchButton, searchAuthorButton, clearSearchButton, filterButton, buyButton, addBookButton, deleteBookButton;
    private Panel bookButtonsPanel, warehousePanel;
    private Label selectedBookLabel, totalBooksLabel, totalSoldLabel;
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
        searcAutherField = new TextField(20);

        searchButton = new Button("Search");
        searchButton.setBackground(new Color(100, 150, 200)); // Muted blue button
        searchButton.setForeground(Color.WHITE);

        searchAuthorButton = new Button("Search Author");
        searchAuthorButton.setBackground(new Color(100, 150, 200)); // Muted blue button
        searchAuthorButton.setForeground(Color.WHITE);

        clearSearchButton = new Button("Clear Search");
        clearSearchButton.setBackground(new Color(200, 100, 100)); // Muted red button
        clearSearchButton.setForeground(Color.WHITE);
        filterButton = new Button("Filter Available Books");
        filterButton.setBackground(new Color(100, 200, 100)); // Soft green button
        filterButton.setForeground(Color.WHITE);

        topPanel.add(new Label("Search Book:"));
        topPanel.add(searchField);
        topPanel.add(searchButton);
        topPanel.add(new Label("Search Auther:"));
        topPanel.add(searcAutherField);
        topPanel.add(searchAuthorButton);
        topPanel.add(clearSearchButton);
        topPanel.add(filterButton);

        // Warehouse panel for adding and deleting books
        warehousePanel = new Panel(new GridLayout(0, 2, 5, 5));
        titleField = new TextField();
        authorField = new TextField();
        priceField = new TextField();
        stockField = new TextField();
        puplicationDateField = new TextField();

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
        warehousePanel.add(new Label("Puplication Date:"));
        warehousePanel.add(puplicationDateField);
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
        totalBooksLabel = new Label("Total Books: " + bookTableData.getTotalBooks());
        totalBooksLabel.setForeground(new Color(60, 60, 60));
        totalSoldLabel = new Label("Total Books Sold: " + bookTableData.getTotalBooksSold());
        totalSoldLabel.setForeground(new Color(60, 60, 60));
        buyButton = new Button("Buy");
        buyButton.setEnabled(false);
        buyButton.setBackground(new Color(200, 180, 50)); // Muted yellow button
        buyButton.setForeground(Color.WHITE);

        bottomPanel.add(selectedBookLabel);
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
        puplicationDateField.addTextListener(e -> checkAddButtonState());

        searchButton.addActionListener(e -> {
            String searchQuery = searchField.getText();
            if (!searchQuery.isEmpty()) {
                Book[] filteredBooks = bookTableData.filterByTitle(searchQuery);
                populateBookButtons(filteredBooks);
            }
        });

        searchAuthorButton.addActionListener(e -> {
            String searchQuery = searcAutherField.getText();
            if (!searchQuery.isEmpty()) {
                Book[] filteredBooks = bookTableData.filterByAuther(searchQuery);
                populateBookButtons(filteredBooks);
            }
        });

        clearSearchButton.addActionListener(e -> {
            searchField.setText("");
            searcAutherField.setText("");
            populateBookButtons(bookTableData.getBooks());
        });

        filterButton.addActionListener(e -> {
            Book[] availableBooks = bookTableData.filterByAvailablity();
            populateBookButtons(availableBooks);
        });

        buyButton.addActionListener(e -> {
            String selectedBookTitle = selectedBookLabel.getText().replace("Selected Book: ", "");
            if (bookTableData.isAvailable(selectedBookTitle)) {
                int currentStock = bookTableData.getStock(selectedBookTitle);
                bookTableData.updateSoldCount(selectedBookTitle, 1);
                bookTableData.setStock(selectedBookTitle, currentStock - 1);

                // Update the UI to reflect the changes
                updateBookButton(selectedBookTitle); // Update the UI for the selected book
                updateStatistics(); // Update warehouse statistics
            } else {
                // Show message that the book is out of stock
                System.out.println("This book is out of stock!");
            }
        });

        addBookButton.addActionListener(e -> {
            String title = titleField.getText();
            String author = authorField.getText();
            String priceText = priceField.getText();
            String stockText = stockField.getText();
            String puplicationDateText = puplicationDateField.getText();

            // Validate input fields
            if (title.isEmpty() || author.isEmpty() || priceText.isEmpty() || stockText.isEmpty()
                    || puplicationDateText.isEmpty()) {
                System.out.println("All fields must be filled out!");
                return; // You can show a dialog instead for user-friendly feedback
            }

            try {
                float price = Float.parseFloat(priceText);
                int stock = Integer.parseInt(stockText);
                Book newBook = new Book(title, author, puplicationDateText, price);
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
        puplicationDateField.setText("");
    }

    // Method to check if the add button should be enabled or disabled
    private void checkAddButtonState() {
        // Disable the Add Book button if any field is empty
        if (titleField.getText().isEmpty() || authorField.getText().isEmpty() ||
                priceField.getText().isEmpty() || stockField.getText().isEmpty()
                || puplicationDateField.getText().isEmpty()) {
            addBookButton.setEnabled(false); // Disable Add Book button
        } else {
            addBookButton.setEnabled(true); // Enable Add Book button
        }
    }

    private void populateBookButtons(Book[] books) {
        bookButtonsPanel.removeAll();
        for (int i = 0; i < books.length; i++) {
            Book book = books[i];
            Panel bookPanel = new Panel(new GridLayout(6, 1));
            bookPanel.setBackground(new Color(255, 255, 255)); // White background for book panel

            Label titleLabel = new Label("Title: " + book.getTitle());
            titleLabel.setForeground(new Color(50, 50, 50)); // Dark gray text
            Label authorLabel = new Label("Author: " + book.getAuthor());
            authorLabel.setForeground(new Color(50, 50, 50));
            Label puplicationDateLabel = new Label("Date: " + book.getPublicationDate());
            authorLabel.setForeground(new Color(50, 50, 50));
            Label priceLabel = new Label("Price: $" + book.getPrice());
            priceLabel.setForeground(new Color(80, 100, 80)); // Muted green text
            Label stockLabel = new Label("Stock: " + bookTableData.getStock(book.getTitle()));
            stockLabel.setForeground(new Color(100, 50, 50)); // Soft red text
            Label availableLabel = new Label(
                    "Available: " + (bookTableData.isAvailable(book.getTitle()) ? "Yes" : "No"));
            availableLabel.setForeground(new Color(50, 50, 50));

            Button selectButton = new Button("Select");
            selectButton.setBackground(new Color(100, 150, 200)); // Soft blue button
            selectButton.setForeground(Color.WHITE);
            selectButton.addActionListener(e -> {
                selectedBookLabel.setText("Selected Book: " + book.getTitle());
                buyButton.setEnabled(true);
                deleteBookButton.setEnabled(true);
            });

            bookPanel.add(titleLabel);
            bookPanel.add(authorLabel);
            bookPanel.add(puplicationDateLabel);
            bookPanel.add(priceLabel);
            bookPanel.add(stockLabel);
            bookPanel.add(availableLabel);
            bookPanel.add(selectButton);

            bookButtonsPanel.add(bookPanel);
        }

        bookButtonsPanel.revalidate();
        bookButtonsPanel.repaint();
    }

    private void updateBookButton(String selectedBookTitle) {
        for (Component comp : bookButtonsPanel.getComponents()) {
            if (comp instanceof Panel) {
                Panel bookPanel = (Panel) comp;
                Label titleLabel = (Label) bookPanel.getComponent(0);

                // Check if the title matches the selected book's title
                if (titleLabel.getText().contains(selectedBookTitle)) {
                    // Find the stock label
                    Label stockLabel = (Label) bookPanel.getComponent(3);

                    // Get the book object from BookTable and update the stock label
                    Book selectedBook = bookTableData.getBookByTitle(selectedBookTitle);
                    if (selectedBook != null) {
                        stockLabel.setText("Stock: " + bookTableData.getStock(selectedBookTitle));
                    }

                    // Repaint the panel to reflect changes
                    bookPanel.repaint();
                }
            }
        }
    }

    private void updateStatistics() {
        totalBooksLabel.setText("Total Books: " + bookTableData.getTotalBooks());
        totalSoldLabel.setText("Total Books Sold: " + bookTableData.getTotalBooksSold());
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(BookShopGUI::new);
    }
}
