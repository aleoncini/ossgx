package org.ossg.store.model.serializer;

import org.ossg.store.model.Tournament;

public interface TournamentSerializer<V> {
	public V serialize(Tournament tournament);
    public Tournament deserialize(V serialized);
}