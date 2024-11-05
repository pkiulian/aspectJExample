package aspectj.j.example;

public class Item {
	
	private String id;
	private float price;

	public Item(String id, float price) {
		this.id = id;
		this.price = price;
	}

	public String getID() {
		return this.id;
	}

	public float getPrice() {
		return this.price;
	}

	public String toString() {
		return "Item: " + this.id;
	}
}