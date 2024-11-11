public class BookTable {
	private Book[] books;
	
	public Book[] geBooks() {
		return books;
	}

	public void updateBooks(Book[] books) {
		this.books = books;
	}
	
	public int totalBooks() {
		return books.length;
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
	        if (book.getTitle().equals(title)) {
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
	

