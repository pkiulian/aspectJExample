package aspectj.j.example;

import java.util.List;
import java.util.Vector;

public class ShoppingCart {
	private List<Item> items = new Vector<>();
	private float totalValue = 0;

	public void addItem(Item item) {
		this.totalValue+=item.getPrice();
		this.items.add(item);
	}

	public void removeItem(Item item) {
		this.totalValue-=item.getPrice();
		this.items.remove(item);
	}

	public void empty() {
		this.totalValue =0;
		this.items.clear();
	}

	public float totalValue() {
		return this.totalValue;
	}
	
	public int getNrItems() {
		return this.items.size();
	}
}