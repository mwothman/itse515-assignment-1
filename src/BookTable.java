public class BookTable {
	
    private Book[] books = new Book[] {
		new Book("The Great Gatsby", "F. Scott Fitzgerald", "1925-04-10", 10.99f),
		new Book("Moby-Dick", "Herman Melville", "1851-11-14", 18.50f),
		new Book("Pride and Prejudice", "Jane Austen", "1813-01-28", 8.99f),
		new Book("The Catcher in the Rye", "J.D. Salinger", "1951-07-16", 10.49f),
		new Book("The Hobbit", "J.R.R. Tolkien", "1937-09-21", 14.99f),
		new Book("The Lord of the Rings", "J.R.R. Tolkien", "1954-07-29", 22.99f),
		new Book("Harry Potter and the Sorcerer's Stone", "J.K. Rowling", "1997-06-26", 19.99f),
		new Book("The Alchemist", "Paulo Coelho", "1988-11-11", 13.99f)
	};

    private int[] stock = {3,2,7,1,5,9,1,2}; // Keep track of stock for each book
    private int[] soldCount = {0,0,0,0,0,0,0,0}; // Keep track of sold count for each book

public Book[] getBooks(){
	return books;
}

    // Add a book with stock to the warehouse
    public void addBook(Book newBook, int stockAmount) {
        // Step 1: Create new arrays with increased size
        Book[] newBooks = new Book[books.length + 1];
        int[] newStock = new int[stock.length + 1];
        int[] newSoldCount = new int[soldCount.length + 1];

        // Step 2: Copy old data into the new arrays
        for (int i = 0; i < books.length; i++) {
            newBooks[i] = books[i];
            newStock[i] = stock[i];
            newSoldCount[i] = soldCount[i];
        }

        // Step 3: Add the new book and stock information to the end of the arrays
        newBooks[books.length] = newBook;
        newStock[stock.length] = stockAmount;
        newSoldCount[soldCount.length] = 0; // New book starts with 0 sold

        // Step 4: Point the old arrays to the new ones
        books = newBooks;
        stock = newStock;
        soldCount = newSoldCount;
    }
	

    // Delete a book from the warehouse
    public void deleteBook(Book bookToDelete) {
        // Find the index of the book to delete
        int indexToDelete = -1;
        for (int i = 0; i < books.length; i++) {
            if (books[i].equals(bookToDelete)) {
                indexToDelete = i;
                break;
            }
        }

        // If the book was not found, exit the method
        if (indexToDelete == -1) {
            System.out.println("Book not found.");
            return;
        }

        // Create new arrays with decreased size
        Book[] newBooks = new Book[books.length - 1];
        int[] newStock = new int[stock.length - 1];
        int[] newSoldCount = new int[soldCount.length - 1];

        // Copy all elements except the one to delete
        for (int i = 0, j = 0; i < books.length; i++) {
            if (i != indexToDelete) {
                newBooks[j] = books[i];
                newStock[j] = stock[i];
                newSoldCount[j] = soldCount[i];
                j++;
            }
        }

        // Reassign the arrays
        books = newBooks;
        stock = newStock;
        soldCount = newSoldCount;

        System.out.println("Book deleted successfully.");
    }

    // Get the stock of a book by title
    public int getStock(String title) {
        for (int i = 0; i < books.length; i++) {
            if (books[i] != null && books[i].getTitle().equals(title)) {
                return stock[i];
            }
        }
        return 0; // Book not found
    }

    // Set the stock of a book
    public void setStock(String title, int stockAmount) {
        for (int i = 0; i < books.length; i++) {
            if (books[i] != null && books[i].getTitle().equals(title)) {
                stock[i] = stockAmount;
                break;
            }
        }
    }

    // Get the sold count of a book by title
    public int getSoldCount(String title) {
        for (int i = 0; i < books.length; i++) {
            if (books[i] != null && books[i].getTitle().equals(title)) {
                return soldCount[i];
            }
        }
        return 0; // Book not found
    }

    // Update the sold count of a book by title
    public void updateSoldCount(String title, int soldAmount) {
        for (int i = 0; i < books.length; i++) {
            if (books[i] != null && books[i].getTitle().equals(title)) {
                soldCount[i] += soldAmount;
                break;
            }
        }
    }

    // Check if a book is available
    public boolean isAvailable(String title) {
        for (int i = 0; i < books.length; i++) {
            if (books[i] != null && books[i].getTitle().equals(title)) {
                return stock[i] > 0;
            }
        }
        return false;
    }

    // Get total books in the warehouse
    public int getTotalBooks() {
        int total = 0;
        for (int i = 0; i < books.length; i++) {
            if (books[i] != null) {
                total += stock[i];
            }
        }
        return total;
    }

    // Get total books sold
    public int getTotalBooksSold() {
        int total = 0;
        for (int i = 0; i < books.length; i++) {
            if (books[i] != null) {
                total += soldCount[i];
            }
        }
        return total;
    }

    // Filter books by availability (only those with stock > 0)
    public Book[] getAvailableBooks() {
        Book[] availableBooks = new Book[books.length];
        int count = 0;
        for (int i = 0; i < books.length; i++) {
            if (books[i] != null && stock[i] > 0) {
                availableBooks[count++] = books[i];
            }
        }
        return availableBooks;
    }

	public boolean containsBook(Book book) {
		for (Book storedBook : books) {
			if (storedBook.getTitle().equals(book.getTitle())) {
				return true;
			}
		}
		return false;
	}

	public Book[] filterByAvailablity() {
	
		Book[] filteredBooks = new Book[0];
		for (Book book : books) {
	        if (isAvailable(book.getTitle())) {
	            Book[] temp = new Book[filteredBooks.length + 1];
	            for (int i = 0; i < filteredBooks.length; i++) {
	                temp[i] = filteredBooks[i];
	            }
	            temp[filteredBooks.length] = book;
	            filteredBooks = temp;
	        }
	    }
		
		return filteredBooks;
	}

	public Book[] filterByTitle(String title) {
		Book[] filteredBooks = new Book[0];
		for (Book book : books) {
			// Check if the title contains the search query, ignoring case
			if (book.getTitle().toLowerCase().contains(title.toLowerCase())) {
				Book[] temp = new Book[filteredBooks.length + 1];
				for (int i = 0; i < filteredBooks.length; i++) {
					temp[i] = filteredBooks[i];
				}
				temp[filteredBooks.length] = book;
				filteredBooks = temp;
			}
		}
		return filteredBooks;
	}

	public Book getBookByTitle(String title) {
        for (int i = 0; i < getTotalBooks(); i++) {
            if (books[i].getTitle().equals(title)) {
                return books[i]; // Return the book if the title matches
            }
        }
        return null; // Return null if no book is found
    }

	public Book[] filterByAuther(String author) {
	
		Book[] filteredBooks = new Book[0];
		for (Book book : books) {
			// Check if the title contains the search query, ignoring case
			if (book.getAuthor().toLowerCase().contains(author.toLowerCase())) {
				Book[] temp = new Book[filteredBooks.length + 1];
				for (int i = 0; i < filteredBooks.length; i++) {
					temp[i] = filteredBooks[i];
				}
				temp[filteredBooks.length] = book;
				filteredBooks = temp;
			}
		}
		return filteredBooks;
	}
	
}
