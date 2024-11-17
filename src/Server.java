// import java.awt.*;
// import java.awt.event.*;
// import java.io.*;
// import java.net.*;

// public class Server extends Frame implements ActionListener {
//     private Label statusLabel;
//     private Button startButton;
//     private Button stopButton;
//     private ServerSocket serverSocket;
//     private boolean serverRunning = false;
//     private Thread serverThread;

//     public Server() {
//         // Set up the UI
//         setLayout(new FlowLayout());
//         setTitle("Server Application");
//         setSize(300, 200);

//         // Status label
//         statusLabel = new Label("Status: Server is stopped");
//         add(statusLabel);

//         // Start button
//         startButton = new Button("Start Server");
//         startButton.addActionListener(this);
//         add(startButton);

//         // Stop button
//         stopButton = new Button("Stop Server");
//         stopButton.addActionListener(this);
//         stopButton.setEnabled(false); // Initially disabled
//         add(stopButton);

//         // Window close event
//         addWindowListener(new WindowAdapter() {
//             public void windowClosing(WindowEvent e) {
//                 stopServer();
//                 dispose();
//             }
//         });

//         setVisible(true);
//     }

//     @Override
//     public void actionPerformed(ActionEvent e) {
//         if (e.getSource() == startButton) {
//             startServer();
//         } else if (e.getSource() == stopButton) {
//             stopServer();
//         }
//     }

//     private void startServer() {
//         try {
//             serverSocket = new ServerSocket(2000);
//             serverRunning = true;

//             // Update UI
//             statusLabel.setText("Status: Server is running");
//             startButton.setEnabled(false);
//             stopButton.setEnabled(true);

//             // Start a new thread for accepting client connections
//             serverThread = new Thread(() -> {
//                 try {
//                     while (serverRunning) {
//                         Socket clientSocket = serverSocket.accept();
//                         handleClientConnection(clientSocket);
//                     }
//                 } catch (IOException e) {
//                     System.out.println("Server error: " + e.getMessage());
//                 }
//             });
//             serverThread.start();

//         } catch (IOException e) {
//             System.out.println("Error starting server: " + e.getMessage());
//         }
//     }

//     private void stopServer() {
//         serverRunning = false;

//         // Update UI
//         statusLabel.setText("Status: Server is stopped");
//         startButton.setEnabled(true);
//         stopButton.setEnabled(false);

//         // Close server socket
//         try {
//             if (serverSocket != null && !serverSocket.isClosed()) {
//                 serverSocket.close();
//             }
//             if (serverThread != null && serverThread.isAlive()) {
//                 serverThread.join();
//             }
//         } catch (IOException | InterruptedException e) {
//             System.out.println("Error stopping server: " + e.getMessage());
//         }
//     }

//     private void handleClientConnection(Socket clientSocket) {
//         try (
//             PrintWriter printWriter = new PrintWriter(clientSocket.getOutputStream(), true);
//             BufferedReader bufferReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))
//         ) {
//             System.out.println("Client connected: " + clientSocket.getInetAddress());

//             // Example book array to send
//             Book[] books = {
//                 new Book("Java Programming", "Author A", "2020", 59.99f),
//                 new Book("Effective Java", "Author B", "2018", 49.99f),
//                 new Book("Clean Code", "Author C", "2008", 39.99f),
//                 new Book("Head First Design Patterns", "Author D", "2004", 45.99f),
//                 new Book("The Pragmatic Programmer", "Author E", "1999", 42.99f)
//             };

//             // Send the number of books
//             printWriter.println(books.length);

//             // Send each book's details
//             for (Book book : books) {
//                 printWriter.println(book.getTitle());
//                 printWriter.println(book.getAuthor());
//                 printWriter.println(book.getPublicationDate());
//                 printWriter.println(book.getPrice());
//             }
            

//             // Wait for client response (e.g., book selection)
//             String selectedBook = bufferReader.readLine();
//             System.out.println("Client selected book: " + selectedBook);

//         } catch (IOException e) {
//             System.out.println("Error handling client connection: " + e.getMessage());
//         } finally {
//             try {
//                 clientSocket.close();
//             } catch (IOException e) {
//                 System.out.println("Error closing client socket: " + e.getMessage());
//             }
//         }
//     }

//     public static void main(String[] args) {
//         new Server();
//     }
// }
