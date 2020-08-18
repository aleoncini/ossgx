package org.ossg.store.model.serializer.impl;

import javax.enterprise.context.ApplicationScoped;

import org.bson.Document;
import org.ossg.store.model.DayOfEvent;
import org.ossg.store.model.serializer.DayOfEventSerializer;

@ApplicationScoped
public class DocumentDayOfEventSerializer implements DayOfEventSerializer<Document> {

	@Override
	public Document serialize(DayOfEvent dayOfEvent) {
		if (dayOfEvent == null
				|| (dayOfEvent.getDay() == 0) 
                || (dayOfEvent.getMonth() == 0)
                || (dayOfEvent.getYear() == 0) 
	        ) {
	            throw new IllegalArgumentException("DayOfEvent object contains invalid data");
	        }

	        return new Document("day", dayOfEvent.getDay())
                    .append("month", dayOfEvent.getMonth())
                    .append("year", dayOfEvent.getYear());
	}

	@Override
	public DayOfEvent deserialize(Document document) {
        int day = document.getInteger("day");
        int month = document.getInteger("month");
        int year = document.getInteger("year");

        if (document == null
				|| (day == 0)
                || (month == 0)
                || (year == 0)
			) {
			throw new IllegalArgumentException("Unable to deserialize the object. Document contains invalid data");
        }

        return new DayOfEvent().setDay(day).setMonth(month).setYear(year);
	}
    
}