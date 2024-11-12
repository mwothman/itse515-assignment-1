import java.awt.*;
import java.awt.event.*;
import java.net.Socket;
import java.io.*;

public class Client extends Frame implements ActionListener {

    // Book array
    private Book[] books;
    private Button[] bookSelectionButtons;
    private Label selectedBookLabel;
    private TextField searchField;
    private Button searchButton, clearSearchButton, filterAvailabilityButton, clearSelectionButton, buyButton;
    private Panel bookPanel;
    private ScrollPane scrollPane;

    // Socket and stream declarations
    private Socket socket;
    private InputStream inputStream = null;
    private OutputStream outputStream = null;
    private PrintWriter printWriter = null;
    private BufferedReader bufferReader = null;

    // Constructor
    public Client() {
        setLayout(new BorderLayout());

        // bookPanel to show all books for sale
        bookPanel = new Panel();
        bookPanel.setLayout(new GridLayout(0, 2));

        // GUI elements (search, filter, etc.)
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

        // Panel to hold the selected book label and action buttons
        Panel bottomPanel = new Panel();
        bottomPanel.setLayout(new FlowLayout());
        bottomPanel.add(selectedBookLabel); // Display the selected book here
        bottomPanel.add(buyButton); // Add the Buy button
        bottomPanel.add(clearSelectionButton); // Add the Clear Selection button
        add(bottomPanel, BorderLayout.SOUTH); // Add the bottom panel to the frame

        setTitle("Client");
        setSize(750, 750);
        setVisible(true);

        // Add window listener to close the frame on exit
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                try {
                    closeConnection();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                dispose();
            }
        });

        // Set up server connection
        try {
            socket = new Socket("127.0.0.1", 2000);
            inputStream = socket.getInputStream();
            outputStream = socket.getOutputStream();
            printWriter = new PrintWriter(outputStream, true);
            bufferReader = new BufferedReader(new InputStreamReader(inputStream));

            // Receive and display books
            receiveBooksFromServer();
        } catch (IOException e) {
            System.out.println("Error connecting with the server: " + e);
        }

    }

    private void receiveBooksFromServer() {
        try {
            // Read the number of books from the server
            int numberOfBooks = Integer.parseInt(bufferReader.readLine());

            // Allocate the array with the size of the number of books
            books = new Book[numberOfBooks];

            // Read the book details from the server and store them in the books array
            for (int i = 0; i < numberOfBooks; i++) {
                String title = bufferReader.readLine();
                String author = bufferReader.readLine();
                String publicationDate = bufferReader.readLine();
                float price = Float.parseFloat(bufferReader.readLine());

                // Create a new Book object with the received data
                books[i] = new Book(title, author, publicationDate, price);
                System.out
                        .println("Received book: " + title + " | " + author + " | " + publicationDate + " | " + price);
            }

            // Now update the UI with received books
            updateBookSelectionUI();
        } catch (IOException e) {
            System.out.println("Error receiving books: " + e);
        } catch (NumberFormatException e) {
            System.out.println("Error parsing number: " + e);
        }
    }

    // Method to update the book selection UI with the received books
    private void updateBookSelectionUI() {
        bookSelectionButtons = new Button[books.length];
        for (int i = 0; i < books.length; i++) {
            bookSelectionButtons[i] = new Button(books[i].getTitle());
            bookSelectionButtons[i].setBackground(Color.lightGray);
            bookSelectionButtons[i].setPreferredSize(new Dimension(150, 30)); // Set size for uniform appearance
            bookSelectionButtons[i].addActionListener(this); // Add action listener to each button
            bookPanel.add(bookSelectionButtons[i]);
        }
        scrollPane = new ScrollPane(ScrollPane.SCROLLBARS_ALWAYS);
        scrollPane.add(bookPanel);
        add(scrollPane, BorderLayout.CENTER);
        validate(); // Refresh the frame layout
    }

    // Method to close the connection to the server
    private void closeConnection() throws IOException {
        if (printWriter != null)
            printWriter.println("Exit");
        if (inputStream != null)
            inputStream.close();
        if (outputStream != null)
            outputStream.close();
        if (printWriter != null)
            printWriter.close();
        if (bufferReader != null)
            bufferReader.close();
        if (socket != null)
            socket.close();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == searchButton) {
            // Filter bookSelectionButtons based on search
            String query = searchField.getText().toLowerCase();
            for (int i = 0; i < books.length; i++) {
                if (books[i].getTitle().toLowerCase().contains(query)) {
                    bookSelectionButtons[i].setVisible(true);
                } else {
                    bookSelectionButtons[i].setVisible(false);
                }
            }
        } else if (e.getSource() == clearSearchButton) {
            // Clear search and reset all bookSelectionButtons
            searchField.setText("");
            for (Button bookButton : bookSelectionButtons) {
                bookButton.setVisible(true);
            }
        } else if (e.getSource() == filterAvailabilityButton) {
            // Filter books by availability
            filterBooksByAvailability();
        } else if (e.getSource() == buyButton) {
            String selectedBook = selectedBookLabel.getText().replace("Selected Book: ", "");
            if (!selectedBook.equals("None")) {
                // Code to handle buying the selected book (e.g., show confirmation)
                printWriter.println(selectedBook); // Send the selected book to the server
            } else {
                selectedBookLabel.setText("No book selected!");
            }
        } else if (e.getSource() == clearSelectionButton) {
            // Clear the selected book
            selectedBookLabel.setText("Selected Book: None");
        } else {
            // Handle book selection
            for (int i = 0; i < books.length; i++) {
                if (e.getSource() == bookSelectionButtons[i]) {
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
            bookSelectionButtons[i].setVisible(books[i].isBookAvailable());
        }
    }

    // Main method to launch the application
    public static void main(String[] args) {
        new Client();
    }
}
