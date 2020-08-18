package org.ossg.store.model.serializer;

import org.ossg.store.model.Position;

public interface PositionSerializer<V> {
    
    /**
	 * Serialize the Course object
	 * 
	 * @param position
	 * @throws IllegalArgumentException in case position object contains invalid data
	 * @return
	 */
	public V serialize(Position position);
	
	/**
	 * Deserialize the specified object into a Course
	 * 
	 * @param serialized
	 * @throws IllegalArgumentException in case serialized object contains invalid data
	 * @return
	 */
    public Position deserialize(V serialized);

}