package org.ossg.store.service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.result.DeleteResult;

import org.bson.Document;
import org.ossg.store.model.Course;
import org.ossg.store.model.serializer.CourseSerializer;

import io.quarkus.mongodb.runtime.MongoClientName;

@ApplicationScoped
public class StoreCourse {
    private final static String COLLECTION = "courses";

    @Inject
    @MongoClientName("ossg")
    MongoClient mongoClient;

    @Inject
    CourseSerializer<Document> serializer;

    public Course get(String id){
        Document query = new Document("id", id);
        Document course = mongoClient.getDatabase("ossg").getCollection(COLLECTION).find(query).first();

        return (course != null) ? serializer.deserialize(course) : null;
    }

    public void add(Course course){
        Document document = serializer.serialize(course);
        if(document != null){
            mongoClient.getDatabase("ossg").getCollection(COLLECTION).insertOne(document);
        }
    }

    public boolean remove(String id){
        boolean isRemoved = false;
        Document query = new Document("id", id);
        DeleteResult result = mongoClient.getDatabase("ossg").getCollection(COLLECTION).deleteOne(query);
        if(result.getDeletedCount() == 1){
            isRemoved = true;
        }
        return isRemoved;
    }

    public List<Course> list(){
        List<Course> list = new ArrayList<>();
        MongoCursor<Document> cursor = mongoClient.getDatabase("ossg").getCollection(COLLECTION).find().iterator();

        try {
            while (cursor.hasNext()) {
                Document document = cursor.next();
                Course course = serializer.deserialize(document);
                list.add(course);
            }
        } finally {
            cursor.close();
        }
        return list;
    }

    public List<Course> searchByName(String text){
        List<Course> list = new ArrayList<>();

        Document filter = new Document("name", Pattern.compile(text, Pattern.CASE_INSENSITIVE));
        MongoCursor<Document> cursor = mongoClient.getDatabase("ossg").getCollection(COLLECTION).find(filter).iterator();

        try {
            while (cursor.hasNext()) {
                Document document = cursor.next();
                Course course = serializer.deserialize(document);
                list.add(course);
            }
        } finally {
            cursor.close();
        }
        return list;
    }

}