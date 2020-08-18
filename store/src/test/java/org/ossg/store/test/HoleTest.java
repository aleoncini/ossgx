package org.ossg.store.test;

import javax.inject.Inject;

import org.bson.Document;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

import org.ossg.store.model.Hole;
import org.ossg.store.model.serializer.HoleSerializer;

@QuarkusTest
public class HoleTest {
	@Inject
    HoleSerializer<Document> serializer;

    @Test
	void testEquals() {

		/** test empty objects */
		Hole first = new Hole();
        Hole second = new Hole();
		
		Assertions.assertTrue(first.equals(second));
		Assertions.assertTrue(second.equals(first));
		
		/** test different objects */
		first.setHcp(11).setPar(4);
		second.setHcp(18).setPar(3);
		
		Assertions.assertFalse(first.equals(second));
		Assertions.assertFalse(second.equals(first));
		
		/** test equals objects */
		second.setHcp(11).setPar(4);
		
		Assertions.assertTrue(first.equals(second));
		Assertions.assertTrue(second.equals(first));
		
	}
	
	@Test
	void testHashCode() {
		
		/** test empty objects */
		Hole first = new Hole();
		Hole second = new Hole();
		
		Assertions.assertEquals(first.hashCode(), second.hashCode());
		
		/** test different objects */
		first.setHcp(11).setPar(4);
		second.setHcp(18).setPar(3);
		
		Assertions.assertNotEquals(first.hashCode(), second.hashCode());
		
		/** test equals objects */
		second.setHcp(11).setPar(4);
		
		Assertions.assertEquals(first.hashCode(), second.hashCode());
		
	}

	@Test
	void testSerialization() {

		Hole first = new Hole().setHcp(11).setPar(4);
		Hole second = new Hole();
		
		
        /** test different objects */
		Assertions.assertFalse(first.equals(second));
		Assertions.assertFalse(second.equals(first));

		/** test equals objects */
		Document document = serializer.serialize(first);
        second = serializer.deserialize(document);
		
		Assertions.assertTrue(first.equals(second));
		Assertions.assertTrue(second.equals(first));
		
	}

}