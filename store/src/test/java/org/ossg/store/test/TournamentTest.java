package org.ossg.store.test;

import javax.inject.Inject;

import org.bson.Document;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.ossg.store.model.DayOfEvent;
import org.ossg.store.model.Tournament;
import org.ossg.store.model.serializer.TournamentSerializer;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class TournamentTest {
	@Inject
    TournamentSerializer<Document> serializer;

    @Test
	void testEquals() {

		/** test empty objects */
		Tournament first = new Tournament();
		Tournament second = new Tournament();
		
		Assertions.assertTrue(first.equals(second));
		Assertions.assertTrue(second.equals(first));
		
		/** test different objects */
		first.setId("aaa").setTitle("First tournament").setDayOfEvent(new DayOfEvent());
		second.setId("bbb").setTitle("Second tournament").setDayOfEvent(new DayOfEvent());
		
		Assertions.assertFalse(first.equals(second));
		Assertions.assertFalse(second.equals(first));
		
		/** test equals objects */
		second.setId("aaa").setTitle("First tournament").setDayOfEvent(new DayOfEvent());
		
		Assertions.assertTrue(first.equals(second));
		Assertions.assertTrue(second.equals(first));
		
	}
	
	@Test
	void testHashCode() {
		
		/** test empty objects */
		Tournament first = new Tournament();
		Tournament second = new Tournament();
		
		Assertions.assertEquals(first.hashCode(), second.hashCode());
		
		/** test different objects */
		first.setId("aaa").setTitle("First tournament").setDayOfEvent(new DayOfEvent());
		second.setId("bbb").setTitle("Second tournament").setDayOfEvent(new DayOfEvent());
		
		Assertions.assertNotEquals(first.hashCode(), second.hashCode());
		
		/** test equals objects */
		second.setId("aaa").setTitle("First tournament").setDayOfEvent(new DayOfEvent());
		
		Assertions.assertEquals(first.hashCode(), second.hashCode());
		
	}

	@Test
	void testSerialization() {

		DayOfEvent dayOfEvent = new DayOfEvent().setDay(4).setMonth(7).setYear(2020);
		Tournament first = new Tournament().setId("aaa").setTitle("First tournament").setDayOfEvent(dayOfEvent);
		Tournament second = new Tournament();
		
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