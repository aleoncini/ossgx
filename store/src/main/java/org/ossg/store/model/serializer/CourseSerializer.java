package org.ossg.store.model.serializer;

import org.ossg.store.model.Course;

public interface CourseSerializer<V> {
    
    /**
	 * Serialize the Course object
	 * 
	 * @param course
	 * @throws IllegalArgumentException in case player object contains invalid data
	 * @return
	 */
	public V serialize(Course course);
	
	/**
	 * Deserialize the specified object into a Course
	 * 
	 * @param serialized
	 * @throws IllegalArgumentException in case serialized object contains invalid data
	 * @return
	 */
    public Course deserialize(V serialized);

}