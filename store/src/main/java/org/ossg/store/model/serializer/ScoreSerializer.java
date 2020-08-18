package org.ossg.store.model.serializer;

import org.ossg.store.model.Score;

public interface ScoreSerializer<V> {
	public V serialize(Score score);	
    public Score deserialize(V serialized);
}