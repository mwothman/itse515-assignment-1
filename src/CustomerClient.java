import java.awt.*;
import java.awt.event.*;

public class CustomerClient extends Frame implements ActionListener {
    // Book array instead of String array
    private Book[] books = {
        new Book("Java Programming", "Author A", "2020", 59.99f, 10, 2, true),
        new Book("Effective Java", "Author B", "2018", 49.99f, 5, 5, true),
        new Book("Clean Code", "Author C", "2008", 39.99f, 8, 3, true),
        new Book("Head First Design Patterns", "Author D", "2004", 45.99f, 2, 1, true),
        new Book("The Pragmatic Programmer", "Author E", "1999", 42.99f, 0, 7, false)
    };
    private Button[] bookButtons;
    private Label selectedBookLabel;  // Label to show the selected book
    private TextField searchField;
    private Button searchButton, clearSearchButton, filterAvailabilityButton, clearSelectionButton, buyButton;
    private Panel bookPanel;
    private ScrollPane scrollPane;

    // Constructor to set up GUI components
    public CustomerClient() {
        setLayout(new BorderLayout());

        // Initialize components
        bookPanel = new Panel();
        bookPanel.setLayout(new FlowLayout(FlowLayout.LEFT)); // Flow layout for horizontal arrangement

        // Create and add Buttons for each book, using the title of each Book object
        bookButtons = new Button[books.length];
        for (int i = 0; i < books.length; i++) {
            bookButtons[i] = new Button(books[i].getTitle());
            bookButtons[i].setBackground(Color.LIGHT_GRAY); // Optional: set background color
            bookButtons[i].setPreferredSize(new Dimension(150, 30)); // Set size for uniform appearance
            bookButtons[i].addActionListener(this); // Add action listener to each button
            bookPanel.add(bookButtons[i]);
        }

        // Add the book panel to a scrollable container
        scrollPane = new ScrollPane(ScrollPane.SCROLLBARS_AS_NEEDED);
        scrollPane.add(bookPanel);

        // Label to display the selected book
        selectedBookLabel = new Label("Selected Book: None");

        searchField = new TextField(20);
        searchButton = new Button("Search");
        clearSearchButton = new Button("Clear Search");
        filterAvailabilityButton = new Button("Filter by Availability"); // Button to filter by availability
        buyButton = new Button("Buy");
        clearSelectionButton = new Button("Clear Selection");

        // Set up event listeners
        searchButton.addActionListener(this);
        clearSearchButton.addActionListener(this);
        filterAvailabilityButton.addActionListener(this);
        buyButton.addActionListener(this);
        clearSelectionButton.addActionListener(this);

        // Layout the components
        Panel topPanel = new Panel();
        topPanel.add(new Label("Search:"));
        topPanel.add(searchField);
        topPanel.add(searchButton);
        topPanel.add(clearSearchButton);
        topPanel.add(filterAvailabilityButton); // Add Filter by Availability button

        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        // Panel to hold the selected book label and action buttons
        Panel bottomPanel = new Panel();
        bottomPanel.setLayout(new FlowLayout());

        bottomPanel.add(selectedBookLabel); // Display the selected book here
        bottomPanel.add(buyButton); // Add the Buy button
        bottomPanel.add(clearSelectionButton); // Add the Clear Selection button

        add(bottomPanel, BorderLayout.SOUTH); // Add the bottom panel to the frame

        // Frame settings
        setTitle("Customer Client");
        setSize(500, 400);
        setVisible(true);

        // Add window listener to close the frame on exit
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == searchButton) {
            // Filter book buttons based on search query
            String query = searchField.getText().toLowerCase();
            for (int i = 0; i < books.length; i++) {
                if (books[i].getTitle().toLowerCase().contains(query)) {
                    bookButtons[i].setVisible(true);
                } else {
                    bookButtons[i].setVisible(false);
                }
            }
        } else if (e.getSource() == clearSearchButton) {
            // Clear search and reset visibility of all book buttons
            searchField.setText("");
            for (Button bookButton : bookButtons) {
                bookButton.setVisible(true);
            }
        } else if (e.getSource() == filterAvailabilityButton) {
            // Filter books by availability
            filterBooksByAvailability();
        } else if (e.getSource() == buyButton) {
            String selectedBook = selectedBookLabel.getText().replace("Selected Book: ", "");
            if (!selectedBook.equals("None")) {
                // Code to handle buying the selected book (e.g., show confirmation)
                System.out.println("Purchasing book: " + selectedBook);
            } else {
                selectedBookLabel.setText("No book selected!");
            }
        } else if (e.getSource() == clearSelectionButton) {
            // Clear the selected book
            selectedBookLabel.setText("Selected Book: None");
        } else {
            // Handle book selection
            for (int i = 0; i < books.length; i++) {
                if (e.getSource() == bookButtons[i]) {
                    selectedBookLabel.setText("Selected Book: " + books[i].getTitle());
                    break;
                }
            }
        }
    }

    // Placeholder method for filtering books by availability
    private void filterBooksByAvailability() {
        for (int i = 0; i < books.length; i++) {
            // Display the button only if the book is available
            bookButtons[i].setVisible(books[i].isBookAvailable());
        }
    }

    // Main method to launch the application
    public static void main(String[] args) {
        new CustomerClient();
    }
}
