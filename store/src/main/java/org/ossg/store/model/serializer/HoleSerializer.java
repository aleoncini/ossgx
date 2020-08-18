package org.ossg.store.model.serializer;

import org.ossg.store.model.Hole;

public interface HoleSerializer<V> {
    
    /**
	 * Serialize the Course object
	 * 
	 * @param hole
	 * @throws IllegalArgumentException in case position object contains invalid data
	 * @return
	 */
	public V serialize(Hole hole);
	
	/**
	 * Deserialize the specified object into a Course
	 * 
	 * @param serialized
	 * @throws IllegalArgumentException in case serialized object contains invalid data
	 * @return
	 */
    public Hole deserialize(V serialized);

}