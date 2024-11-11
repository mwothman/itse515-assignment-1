import java.io.*;
import java.net.*;

public class Server {

    public static void main(String[] args) {
        // Stream definitions for connection  
        InputStream inputStream = null;
        OutputStream outputStream = null;

        // Writers and readers for communication 
        PrintWriter printWriter = null;
        BufferedReader bufferedReader = null;

        // Example book array
        Book[] books = {
            new Book("Java Programming", "Author A", "2020", 59.99f),
            new Book("Effective Java", "Author B", "2018", 49.99f),
            new Book("Clean Code", "Author C", "2008", 39.99f),
            new Book("Head First Design Patterns", "Author D", "2004", 45.99f),
            new Book("The Pragmatic Programmer", "Author E", "1999", 42.99f)
        };

        System.out.println("Server starting");

        // Establish server socket
        try {
            ServerSocket serverSocket = new ServerSocket(2000);
            while (true) {
                Socket socket = serverSocket.accept();
                inputStream = socket.getInputStream();
                outputStream = socket.getOutputStream();
                printWriter = new PrintWriter(outputStream, true);
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                System.out.println("System set up");

                // Send the number of books to the client
                printWriter.println(books.length);

                // Send each book's details to the client
                for (Book book : books) {
                    printWriter.println(book.getTitle());
                    printWriter.println(book.getAuthor());
                    printWriter.println(book.getPublicationDate());
                    printWriter.println(book.getPrice());
                    printWriter.println(book.isBookAvailable());
                }

                printWriter.close();
                bufferedReader.close();
                inputStream.close();
                outputStream.close();
                System.out.println("Closed down");
            }
        } catch (IOException e) {
            System.out.println("Trouble with connection: " + e);
        }
    }
}
