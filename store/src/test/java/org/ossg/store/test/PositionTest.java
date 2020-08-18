package org.ossg.store.test;

import io.quarkus.test.junit.QuarkusTest;

import javax.inject.Inject;

import org.bson.Document;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.ossg.store.model.Position;
import org.ossg.store.model.serializer.PositionSerializer;

@QuarkusTest
public class PositionTest {

	@Inject
    PositionSerializer<Document> serializer;

    @Test
	void testEquals() {

		/** test empty objects */
		Position first = new Position();
		Position second = new Position();
		
		Assertions.assertTrue(first.equals(second));
		Assertions.assertTrue(second.equals(first));
		
        /** test different objects */
        first.setLatitude(339.43).setLongitude(987349.999);
		second.setLatitude(7865.333).setLongitude(6464.099);
		
		Assertions.assertFalse(first.equals(second));
		Assertions.assertFalse(second.equals(first));
		
		/** test equals objects */
        second.setLatitude(339.43).setLongitude(987349.999);
		
		Assertions.assertTrue(first.equals(second));
		Assertions.assertTrue(second.equals(first));
		
	}
	
	@Test
	void testHashCode() {
		
		/** test empty objects */
		Position first = new Position();
		Position second = new Position();
		
		Assertions.assertEquals(first.hashCode(), second.hashCode());
		
		/** test different objects */
        first.setLatitude(339.43).setLongitude(987349.999);
		second.setLatitude(7865.333).setLongitude(6464.099);
		
		Assertions.assertNotEquals(first.hashCode(), second.hashCode());
		
		/** test equals objects */
        second.setLatitude(339.43).setLongitude(987349.999);
		
		Assertions.assertEquals(first.hashCode(), second.hashCode());
		
	}
 
	@Test
	void testSerialization() {

		Position first = new Position().setLatitude(339.43).setLongitude(987349.999);
		Position second = new Position();
		
		
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