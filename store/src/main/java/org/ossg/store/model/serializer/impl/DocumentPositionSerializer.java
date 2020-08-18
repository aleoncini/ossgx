package org.ossg.store.model.serializer.impl;

import javax.enterprise.context.ApplicationScoped;

import org.bson.Document;
import org.ossg.store.model.Position;
import org.ossg.store.model.serializer.PositionSerializer;

@ApplicationScoped
public class DocumentPositionSerializer implements PositionSerializer<Document>{

	@Override
	public Document serialize(Position position) {
		if (position == null
				|| (position.getLatitude() == 0) 
	            || (position.getLongitude() == 0) 
	        ) {
	            throw new IllegalArgumentException("Player object contains invalid data");
	        }

	        return new Document("latitude", position.getLatitude())
	                .append("longitude", position.getLongitude());
	}

	@Override
	public Position deserialize(Document document) {
        double latitude = document.getDouble("latitude");
        double longitude = document.getDouble("longitude");

        if (document == null
				|| (latitude == 0)
				|| (longitude == 0)
			) {
			throw new IllegalArgumentException("Unable to deserialize Position object. Document contains invalid data");
        }


        return new Position()
                    .setLatitude(latitude)
                    .setLongitude(longitude);
	}
    
}