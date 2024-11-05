package test2.asp;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Random;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import aspectj.j.example.HeavyComp;

class HeavyCompTest {

	private HeavyComp heavy;

	@BeforeEach
	void setUp() {
		heavy = new HeavyComp();
	}

	@Test
	void stressInsertManyItems() {
		for (int i = 0; i < 500; i++) {
			Random random = new Random(System.currentTimeMillis());
			int val = random.nextInt(10000) + 1;
			assertEquals(val, heavy.insertManyItems(val));
		}
	}

}
