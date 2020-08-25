package org.ossg.store.test;

import javax.inject.Inject;

import org.bson.Document;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.ossg.store.model.DayOfEvent;
import org.ossg.store.model.Round;
import org.ossg.store.model.serializer.RoundSerializer;
import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class RoundTest {
	@Inject
    RoundSerializer<Document> serializer;

    @Test
	void testEquals() {

		/** test empty objects */
		Round first = new Round();
		Round second = new Round();
		
		Assertions.assertTrue(first.equals(second));
		Assertions.assertTrue(second.equals(first));
		
		/** test different objects */
        first.setId("aaa")
            .setPlayerId("bbb")
            .setCourseId("ccc")
            .setPlayerName("pippo")
            .setCourseName("campo da golf")
            .setPhcp(10)
            .setDayOfEvent(new DayOfEvent());
        second.setId("sss")
            .setPlayerId("fff")
            .setCourseId("ccc")
            .setPlayerName("pluto")
            .setCourseName("campo da golf")
            .setPhcp(11)
            .setDayOfEvent(new DayOfEvent());
		
		Assertions.assertFalse(first.equals(second));
		Assertions.assertFalse(second.equals(first));
		
		/** test equals objects */
        second.setId("aaa")
            .setPlayerId("bbb")
            .setCourseId("ccc")
            .setPlayerName("pippo")
            .setCourseName("campo da golf")
            .setPhcp(10)
            .setDayOfEvent(new DayOfEvent());
		
		Assertions.assertTrue(first.equals(second));
		Assertions.assertTrue(second.equals(first));
		
	}
	
	@Test
	void testHashCode() {
		
		/** test empty objects */
		Round first = new Round();
		Round second = new Round();
		
		Assertions.assertEquals(first.hashCode(), second.hashCode());
		
		/** test different objects */
        first.setId("aaa")
            .setPlayerId("bbb")
            .setCourseId("ccc")
            .setPlayerName("pippo")
            .setCourseName("campo da golf")
            .setPhcp(10)
            .setDayOfEvent(new DayOfEvent());
        second.setId("sss")
            .setPlayerId("fff")
            .setCourseId("ccc")
            .setPlayerName("pluto")
            .setCourseName("campo da golf")
            .setPhcp(11)
            .setDayOfEvent(new DayOfEvent());
		
		Assertions.assertNotEquals(first.hashCode(), second.hashCode());
		
		/** test equals objects */
        second.setId("aaa")
            .setPlayerId("bbb")
            .setCourseId("ccc")
            .setPlayerName("pippo")
            .setCourseName("campo da golf")
            .setPhcp(10)
            .setDayOfEvent(new DayOfEvent());
		
		Assertions.assertEquals(first.hashCode(), second.hashCode());
		
	}

	@Test
	void testSerialization() {

		Round first = new Round();
		Round second = new Round();
        
        DayOfEvent dayOfEvent = new DayOfEvent().setDay(14).setMonth(1).setYear(2020);
        first.setId("aaa")
            .setPlayerId("bbb")
            .setCourseId("ccc")
            .setPlayerName("pippo")
            .setCourseName("campo da golf")
            .setPhcp(10)
            .setDayOfEvent(dayOfEvent);

        for(int i = 1; i<= 18; i++){
            first.setScore(i, i, 4, 5, 2, 3);
        }    
		
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