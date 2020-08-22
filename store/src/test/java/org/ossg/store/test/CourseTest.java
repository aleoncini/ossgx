package org.ossg.store.test;

import javax.inject.Inject;

import org.bson.Document;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.ossg.store.model.Course;
import org.ossg.store.model.Hole;
import org.ossg.store.model.Position;
import org.ossg.store.model.serializer.CourseSerializer;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class CourseTest {
	@Inject
    CourseSerializer<Document> serializer;

    @Test
	void testEquals() {

		/** test empty objects */
		Course first = new Course();
        Course second = new Course();
		
		Assertions.assertTrue(first.equals(second));
		Assertions.assertTrue(second.equals(first));
		
		/** test different objects */
		first.setName("National Golf Club").setCap("00147");
		second.setName("Circolo di Golf").setAddress("Via della bandierina, 18");
		
		Assertions.assertFalse(first.equals(second));
		Assertions.assertFalse(second.equals(first));
		
        /** test equals objects */
        second = new Course().setName("National Golf Club").setCap("00147");
		
		Assertions.assertTrue(first.equals(second));
		Assertions.assertTrue(second.equals(first));
		
	}
	
	@Test
	void testHashCode() {
		
		/** test empty objects */
		Course first = new Course();
        Course second = new Course();
		
		Assertions.assertEquals(first.hashCode(), second.hashCode());
		
		/** test different objects */
		first.setName("National Golf Club").setCap("00147");
		second.setName("Circolo di Golf").setAddress("Via della bandierina, 18");
		
		Assertions.assertNotEquals(first.hashCode(), second.hashCode());
		
		/** test equals objects */
        second = new Course().setName("National Golf Club").setCap("00147");
		
		Assertions.assertEquals(first.hashCode(), second.hashCode());
		
	}

	@Test
	void testSerialization() {

		Position position = new Position().setLatitude(234.26876).setLongitude(45.254);
		Course first = new Course()
						.setId("aaa")
						.setName("Circolo di Golf")
						.setAddress("Via della bandierina, 18")
						.setPosition(position);
		for (int i=1; i<=18; i++){
			first.setHole(i, new Hole().setHcp(i).setPar(4));
		}				
        Course second = new Course();
				
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