public class BookTable {
    private Book[] books;

    public BookTable() {
        // Initialize books with default data
        this.books = new Book[] {
			new Book("The Great Gatsby", "F. Scott Fitzgerald", "1925-04-10", 10.99f, 10),
			new Book("Moby-Dick", "Herman Melville", "1851-11-14", 18.50f, 2),
			new Book("Pride and Prejudice", "Jane Austen", "1813-01-28", 8.99f, 6),
			new Book("The Catcher in the Rye", "J.D. Salinger", "1951-07-16", 10.49f, 8),
			new Book("The Hobbit", "J.R.R. Tolkien", "1937-09-21", 14.99f, 2),
			new Book("The Lord of the Rings", "J.R.R. Tolkien", "1954-07-29", 22.99f, 1),
			new Book("Harry Potter and the Sorcerer's Stone", "J.K. Rowling", "1997-06-26", 19.99f, 9),
			new Book("The Alchemist", "Paulo Coelho", "1988-11-11", 13.99f, 4)
		};
    }

    public Book[] getBooks() {
        return books;
    }

    public int getTotalBooks() {
		int total = 0;
		for (Book book : books){
			total += book.getStock();
		}
        return total;
    }

	public Book[] filterByAvailablity() {
	
		Book[] filteredBooks = new Book[0];
		for (Book book : books) {
	        if (book.isBookAvailable()) {
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

	public Book[] filterByAuther(String auther) {
	
		Book[] filteredBooks = new Book[0];
		for (Book book : books) {
	        if (book.getAuthor().equals(auther)) {
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

	public void addBook(Book book, int stock) {
		// Add the new book to the array (you can increase the array size if needed)
		Book[] temp = new Book[books.length + 1];
		System.arraycopy(books, 0, temp, 0, books.length);
		temp[books.length] = book;
		books = temp;
	}
	
	public void deleteBook(Book book) {
		// Remove the book from the array
		Book[] temp = new Book[books.length - 1];
		int index = 0;
		for (Book b : books) {
			if (!b.getTitle().equals(book.getTitle())) {
				temp[index++] = b;
			}
		}
		books = temp;
	}
	
}
