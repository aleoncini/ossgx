package org.ossg.store.model.serializer.impl;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.bson.Document;
import org.ossg.store.model.DayOfEvent;
import org.ossg.store.model.Tournament;
import org.ossg.store.model.serializer.DayOfEventSerializer;
import org.ossg.store.model.serializer.TournamentSerializer;

@ApplicationScoped
public class DocumentTournamentSerializer implements TournamentSerializer<Document>{

	@Inject
    DayOfEventSerializer<Document> dayOfEventSerializer;

	@Override
	public Document serialize(Tournament tournament) {
		if (tournament == null
            || (tournament.getId() == null) 
            || (tournament.getTitle() == null) 
            || (tournament.getDayOfEvent() == null) 
        ) {
	            throw new IllegalArgumentException("Object contains invalid data");
	        }

	        return new Document("id", tournament.getId())
                    .append("title", tournament.getTitle())
                    .append("dayOfEvent", dayOfEventSerializer.serialize(tournament.getDayOfEvent()));
	}

	@Override
	public Tournament deserialize(Document document) {
        String id = document.getString("id");
        String title = document.getString("title");
		Document dayOfEventDocument = (Document) document.get("dayOfEvent");
		DayOfEvent dayOfEvent = dayOfEventSerializer.deserialize(dayOfEventDocument);

        if (document == null
				|| (id == null)
				|| (title == null)
				|| (dayOfEvent == null)
			) {
			throw new IllegalArgumentException("Unable to deserialize the Tournament object. Document contains invalid data");
        }

        return new Tournament().setId(id).setTitle(title).setDayOfEvent(dayOfEvent);

	}
    
}