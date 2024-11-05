package aspectj.j.example;

import java.util.Random;

public class HeavyComp {

	public int insertManyItems(int n) {

		Inventory inventory = new Inventory();
		ShoppingCart sc = new ShoppingCart();
		int k = 100000;

		for (int i = 0; i < n; i++) {

			Random randomPrice = new Random(System.currentTimeMillis());
			Random randomName = new Random(System.currentTimeMillis());

			for (int j = 0; j < 2*i; j++) {
				Math.sin(i);
				Math.sqrt(i);
			}

			Item item = new Item(String.valueOf(randomName.nextInt(k) + i), randomPrice.nextInt(k) + 1);
			inventory.addItem(item);

			// just simulate some heavy computation
			inventory.sortItems();

			ShoppingCartOperator.addShoppingCartItem(sc, inventory, item);
		}
		return sc.getNrItems();
	}
}
