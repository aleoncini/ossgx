package org.ossg.store.model.serializer.impl;

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
	            || (course.getHoles() == null) 
	            || (course.getHoles().size() < 18) 
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
    
            Document holes = new Document();
            for (int i=1; i<=18; i++){
                String holeName = String.valueOf(i);
                holes.append(holeName, holeSerializer.serialize(course.getHole(i)));
            }
            document.append("holes",holes);
            return document;    
	}

	@Override
	public Course deserialize(Document serialized) {
        String id = serialized.getString("id");
        String name = serialized.getString("name");
        Document holesDocument = (Document) serialized.get("holes");

		if (serialized == null
				|| (id == null)
				|| (id.length() == 0)
				|| (name == null)
				|| (name.length() == 0)
				|| (holesDocument == null)
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

        for (int i=1; i<=18; i++){
            String holeName = String.valueOf(i);
            Document holeDocument = (Document) holesDocument.get(holeName);
            Hole hole = holeSerializer.deserialize(holeDocument);
            course.setHole(i, hole);
        }

        return course;
	}
    
}