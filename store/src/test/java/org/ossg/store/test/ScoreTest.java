package org.ossg.store.test;

import javax.inject.Inject;

import org.bson.Document;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.ossg.store.model.Score;
import org.ossg.store.model.serializer.ScoreSerializer;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class ScoreTest {
	@Inject
    ScoreSerializer<Document> serializer;
	
	@Test
	void testEquals() {

		/** test empty objects */
		Score first = new Score();
		Score second = new Score();
		
		Assertions.assertTrue(first.equals(second));
		Assertions.assertTrue(second.equals(first));
		
		/** test different objects */
		first.setHcp(3).setPar(4).setStrokes(6).setAdditionalStrokes(2).setPoints(2);
		second.setHcp(3).setPar(4).setStrokes(5).setAdditionalStrokes(1).setPoints(2);

		Assertions.assertFalse(first.equals(second));
		Assertions.assertFalse(second.equals(first));
		
		/** test equals objects */
		second.setStrokes(6).setAdditionalStrokes(2);
		
		Assertions.assertTrue(first.equals(second));
		Assertions.assertTrue(second.equals(first));
		
	}
	
	@Test
	void testHashCode() {
		
		/** test empty objects */
		Score first = new Score();
		Score second = new Score();
		
		Assertions.assertEquals(first.hashCode(), second.hashCode());
		
		/** test different objects */
		first.setHcp(3).setPar(4).setStrokes(6).setAdditionalStrokes(2).setPoints(2);
		second.setHcp(3).setPar(4).setStrokes(5).setAdditionalStrokes(1).setPoints(2);
		
		Assertions.assertNotEquals(first.hashCode(), second.hashCode());
		
		/** test equals objects */
		second.setStrokes(6).setAdditionalStrokes(1);
		
		Assertions.assertEquals(first.hashCode(), second.hashCode());
		
	}

    @Test
	void testSerialization() {

		Score first = new Score().setHcp(3).setPar(4).setStrokes(6).setAdditionalStrokes(2).setPoints(2);
		Score second = new Score();
				
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