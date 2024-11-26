public class BookTable {
    private Book[] books;
    private int[] quantities;
    private int[] soldCopies;
    private int currentSize;

    public BookTable(int capacity) {
        books = new Book[capacity];
        quantities = new int[capacity];
        soldCopies = new int[capacity];
        currentSize = 0;
    }

    public boolean addBook(Book book, int quantity) {
        for (int i = 0; i < currentSize; i++) {
            if (books[i].equals(book)) {
                quantities[i] += quantity;
                return true;
            }
        }
        if (currentSize < books.length) {
            books[currentSize] = book;
            quantities[currentSize] = quantity;
            soldCopies[currentSize] = 0;
            currentSize++;
            return true;
        }
        return false; // No space left
    }

    public boolean deleteBook(String title) {
        Book foundBook = searchBook(title);
        if(foundBook == null){
            return false;
        }
        for (int i = 0; i < currentSize; i++) {
            if (books[i].getTitle().equalsIgnoreCase(foundBook.getTitle())) {
                books[i] = books[currentSize - 1];
                quantities[i] = quantities[currentSize - 1];
                soldCopies[i] = soldCopies[currentSize - 1];
                currentSize--;
                return true;
            }
        }
        return false;
    }

    public Book searchBook(String search) {
        for (int i = 0; i < currentSize; i++) {
            if (books[i].getTitle().equalsIgnoreCase(search) || books[i].getAuthor().equalsIgnoreCase(search)) {
                return books[i];
            }
        }
        return null;
    }

    public int getTotalBooks() {
        int total = 0;
        for (int i = 0; i < currentSize; i++) {
            total += quantities[i];
        }
        return total;
    }

    public int getTotalBooksSold() {
        int total = 0;
        for (int i = 0; i < currentSize; i++) {
            total += soldCopies[i];
        }
        return total;
    }

    public int getAvailableCopies(String title) {
        for (int i = 0; i < currentSize; i++) {
            if (books[i].getTitle().equalsIgnoreCase(title)) {
                return quantities[i];
            }
        }
        return 0;
    }

    public boolean sellBook(String title) {
        for (int i = 0; i < currentSize; i++) {
            if (books[i].getTitle().equalsIgnoreCase(title) && quantities[i] > 0) {
                quantities[i]--;
                soldCopies[i]++;
                return true;
            }
        }
        return false; // Out of stock
    }
}
