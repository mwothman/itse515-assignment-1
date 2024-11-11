public class BaseBook {
	private final String title;
	private final String author;
	private final String publicationDate;
	private final float price;
	
	public BaseBook(String title, String author, String publicationDate, float price){
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
}
