public class Book {
    private String title;
    private String author;
    private String publicationDate;
    private double price;

    public Book(String title, String author, String publicationDate, double price) {
        this.title = title;
        this.author = author;
        this.publicationDate = publicationDate;
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getPublicationDate() {
        return publicationDate;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Book book = (Book) obj;
        return title.equalsIgnoreCase(book.title) && author.equalsIgnoreCase(book.author);
    }
}
