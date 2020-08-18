package org.ossg.store.test;

import javax.inject.Inject;

import org.bson.Document;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.ossg.store.model.DayOfEvent;
import org.ossg.store.model.serializer.DayOfEventSerializer;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class DayOfEventTest {
    @Inject
    DayOfEventSerializer<Document> serializer;

    @Test
	void testEquals() {

		/** test empty objects */
		DayOfEvent first = new DayOfEvent();
		DayOfEvent second = new DayOfEvent();
		
		Assertions.assertTrue(first.equals(second));
		Assertions.assertTrue(second.equals(first));
		
		/** test different objects */
		first.setDay(5).setMonth(3).setYear(2019);
		second.setDay(7).setMonth(1).setYear(2020);
		
		Assertions.assertFalse(first.equals(second));
		Assertions.assertFalse(second.equals(first));
		
		/** test equals objects */
		second.setDay(5).setMonth(3).setYear(2019);
		
		Assertions.assertTrue(first.equals(second));
		Assertions.assertTrue(second.equals(first));
		
	}
	
	@Test
	void testHashCode() {
		
		/** test empty objects */
		DayOfEvent first = new DayOfEvent();
		DayOfEvent second = new DayOfEvent();
		
		Assertions.assertEquals(first.hashCode(), second.hashCode());
		
		/** test different objects */
		first.setDay(5).setMonth(3).setYear(2019);
		second.setDay(7).setMonth(1).setYear(2020);
		
		Assertions.assertNotEquals(first.hashCode(), second.hashCode());
		
		/** test equals objects */
		second.setDay(5).setMonth(3).setYear(2019);
		
		Assertions.assertEquals(first.hashCode(), second.hashCode());
		
	}
    
    @Test
	void testSerialization() {

		DayOfEvent first = new DayOfEvent().setDay(5).setMonth(3).setYear(2019);
		DayOfEvent second = new DayOfEvent();		
		
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