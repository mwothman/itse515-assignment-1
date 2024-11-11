public class Book extends BaseBook {
	private int copies;
	private int soldCopies;
	private boolean availablity;

	public Book(String title, String auther, String publicationData, float price) {
		super(title, auther, publicationData, price);
		}
	
	public int getTotalCopies() {
		return copies;
	}
	public int getSoldCopies() {
		return soldCopies;
	}
	
	public void updateTotalCopies(int number) {
		this.copies = number;
	}

	public void updateSoldCopies(int number) {
		this.soldCopies = number;
	}

	public boolean isBookAvailable() {
		return availablity;
	}
}
