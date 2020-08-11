package org.ossg.store.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

import org.ossg.store.model.Player;

@QuarkusTest
public class PlayerTest {

    @Test
	void testEquals() {

		/** test empty objects */
		Player first = new Player();
		Player second = new Player();
		
		Assertions.assertTrue(first.equals(second));
		Assertions.assertTrue(second.equals(first));
		
		/** test different objects */
		first.setId("12345").setName("pippo");
		second.setId("34567").setName("pluto");
		
		Assertions.assertFalse(first.equals(second));
		Assertions.assertFalse(second.equals(first));
		
		/** test equals objects */
		second.setId("12345").setName("pippo");
		
		Assertions.assertTrue(first.equals(second));
		Assertions.assertTrue(second.equals(first));
		
	}
	
	@Test
	void testHashCode() {
		
		/** test empty objects */
		Player first = new Player();
		Player second = new Player();
		
		Assertions.assertEquals(first.hashCode(), second.hashCode());
		
		/** test different objects */
		first.setId("12345").setName("pippo");
		second.setId("34567").setName("pluto");
		
		Assertions.assertNotEquals(first.hashCode(), second.hashCode());
		
		/** test equals objects */
		second.setId("12345").setName("pippo");
		
		Assertions.assertEquals(first.hashCode(), second.hashCode());
		
	}

}