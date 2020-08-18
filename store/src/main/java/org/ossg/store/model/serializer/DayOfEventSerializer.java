package org.ossg.store.model.serializer;

import org.ossg.store.model.DayOfEvent;

public interface DayOfEventSerializer<V> {
    
	public V serialize(DayOfEvent dayOfEvent);	
    public DayOfEvent deserialize(V serialized);

}