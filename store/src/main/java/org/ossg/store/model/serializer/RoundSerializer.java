package org.ossg.store.model.serializer;

import org.ossg.store.model.Round;

public interface RoundSerializer<V> {
    public V serialize(Round round);	
    public Round deserialize(V serialized);
}