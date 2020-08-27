package org.ossg.store.model.serializer.impl;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.bson.Document;
import org.ossg.store.model.Course;
import org.ossg.store.model.Hole;
import org.ossg.store.model.Position;
import org.ossg.store.model.serializer.CourseSerializer;
import org.ossg.store.model.serializer.HoleSerializer;
import org.ossg.store.model.serializer.PositionSerializer;

@ApplicationScoped
public class DocumentCourseSerializer implements CourseSerializer<Document>{

    @Inject
    PositionSerializer<Document> positionSerializer;
    @Inject
    HoleSerializer<Document> holeSerializer;

	@Override
	public Document serialize(Course course) {
		if (course == null
				|| (course.getId() == null) 
	            || (course.getId().length() == 0) 
	            || (course.getName() == null) 
	            || (course.getName().length() == 0) 
	        ) {
	            throw new IllegalArgumentException("Course object contains invalid data");
	        }

            Document document = new Document("id", course.getId()).append("name", course.getName());

            if (course.getAddress() != null){
                document.append("address", course.getAddress());
            }
    
            if (course.getCity() != null){
                document.append("city", course.getCity());
            }
    
            if (course.getCountry() != null){
                document.append("country", course.getCountry());
            }
    
            if (course.getCap() != null){
                document.append("cap", course.getCap());
            }
    
            if (course.getWebsite() != null){
                document.append("website", course.getWebsite());
            }
    
            if (course.getPosition() != null){
                document.append("position", positionSerializer.serialize(course.getPosition()));
            }

            List<Document> holeDocuments = new ArrayList<Document>();
            for (Hole hole : course.getHoles()) {
                holeDocuments.add( holeSerializer.serialize(hole) );                
            }
    
            document.append("holes",holeDocuments);
            return document;    
	}

	@Override
	public Course deserialize(Document serialized) {
        String id = serialized.getString("id");
        String name = serialized.getString("name");

		if (serialized == null
				|| (id == null)
				|| (id.length() == 0)
				|| (name == null)
				|| (name.length() == 0)
			) {
			throw new IllegalArgumentException("Unable to deserialize Course object. Document contains invalid data");
        }

        Course course = new Course().setId(id).setName(name);

        if (serialized.containsKey("address")){
            course.setAddress(serialized.getString("address"));
        }
        if (serialized.containsKey("city")){
            course.setCity(serialized.getString("city"));
        }
        if (serialized.containsKey("country")){
            course.setCountry(serialized.getString("country"));
        }
        if (serialized.containsKey("cap")){
            course.setCap(serialized.getString("cap"));
        }
        if (serialized.containsKey("website")){
            course.setWebsite(serialized.getString("website"));
        }
        if (serialized.containsKey("position")){
            Document positionDocument = (Document) serialized.get("position");
            Position position = positionSerializer.deserialize(positionDocument);
            course.setPosition(position);
        }

        List<Document> holeDocuments = (List<Document>) serialized.get("holes");

        Hole[] holes = new Hole[18];
        int index = 0;
        for (Document holeDocument : holeDocuments){
            Hole hole = holeSerializer.deserialize(holeDocument);
            holes[index++] = hole;
        }
        course.setHoles(holes);

        return course;
	}
    
}