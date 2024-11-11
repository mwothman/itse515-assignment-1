public class BaseBook {
	private final String title;
	private final String auther;
	private final String publicationData;
	private final float price;
	
	public BaseBook(String title, String auther, String publicationData, float price){
		this.title = title;
		this.auther = auther;
		this.publicationData = publicationData;
		this.price = price;
	}
	public String getTitle() {
		return title;
	}
	
	public String getAuther() {
		return auther;
	}
	
	public String getPublicationData() {
		return publicationData;
	}
	
	public float getPrice() {
		return price;
	}
}
