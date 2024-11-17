public class Book {
    private final String title;
    private final String author;
    private final String publicationDate;
    private final float price;
    private int stock;
    private int soldCount;

    public Book(String title, String author, String publicationDate, float price, int stock) {
        this.title = title;
        this.author = author;
        this.publicationDate = publicationDate;
        this.price = price;
        this.stock = stock;
        this.soldCount = 0;
    }

    public int getStock() {
        return stock;
    }

    public int getSoldCount() {
        return soldCount;
    }

    public void updateTotalStock(int number) {
		if(number < 0){
			this.stock = 0;
		}else{
			this.stock = number;
		}
    }

    public void updateSoldAmount(int number) {
		if(stock > 0){
			if(number < 0){
				this.soldCount = 0;
			}else{
				this.soldCount += number;
			}
		}
    }

    public boolean isBookAvailable() {
        return stock > soldCount;
    }

    public String getTitle() {
        return this.title;
    }

    public String getAuthor() {
        return this.author;
    }

    public String getPublicationDate() {
        return this.publicationDate;
    }

    public float getPrice() {
        return this.price;
    }
}
