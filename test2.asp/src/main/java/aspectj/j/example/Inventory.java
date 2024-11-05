package aspectj.j.example;

import java.util.Collections;

import java.util.List;
import java.util.Vector;

public class Inventory {
	private List<Item> items = new Vector<>();

	public void addItem(Item item) {
		this.items.add(item);
	}

	public void removeItem(Item item) {
		this.items.remove(item);
	}

	public void sortItems() {
		  Collections.sort(this.items, (item1, item2) -> {
		        
		        int priceComparison = Float.compare(item1.getPrice(), item2.getPrice());
		        
		        
		        if (priceComparison == 0) {
		            return item1.getID().compareTo(item2.getID());
		        }
		        return priceComparison;
		    });
	}
	
	
}