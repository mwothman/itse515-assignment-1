public class Book {
    private String title;
    private String author;
    private String publicationDate;
    private float price;

    public Book(String title, String author, String publicationDate, float price) {
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

    public float getPrice() {
        return price;
    }

    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Book book = (Book) obj;
        return title.equals(book.title) && author.equals(book.author);
    }
}
