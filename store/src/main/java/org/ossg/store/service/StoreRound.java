package org.ossg.store.service;

import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.result.DeleteResult;

import org.bson.Document;
import org.ossg.store.model.Round;
import org.ossg.store.model.serializer.RoundSerializer;

import io.quarkus.mongodb.runtime.MongoClientName;

@ApplicationScoped
public class StoreRound {
    private final static String COLLECTION = "rounds";

    @Inject
    @MongoClientName("ossg")
    MongoClient mongoClient;

    @Inject
    RoundSerializer<Document> serializer;

    public Round get(String id){
        Document query = new Document("id", id);
        Document round = mongoClient.getDatabase("ossg").getCollection(COLLECTION).find(query).first();

        return (round != null) ? serializer.deserialize(round) : null;
    }

    public void add(Round round){
        Document document = serializer.serialize(round);
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

    public List<Round> list(){
        List<Round> list = new ArrayList<>();
        MongoCursor<Document> cursor = mongoClient.getDatabase("ossg").getCollection(COLLECTION).find().iterator();

        try {
            while (cursor.hasNext()) {
                Document document = cursor.next();
                Round round = serializer.deserialize(document);
                list.add(round);
            }
        } finally {
            cursor.close();
        }
        return list;
    }

    public List<Round> search(String playerId, String courseId, String tournamentId, int day, int month, int year){
        List<Round> list = new ArrayList<>();
        Document filter = new Document();

        if(playerId != null){
            filter.append("playerId", playerId);
        }
        if(courseId != null){
            filter.append("courseId", courseId);
        }
        if(tournamentId != null){
            filter.append("tournamentId", tournamentId);
        }
        if(year > 0){
            filter.append("dayOfEvent.year", year);
        }
        if(month > 0){
            filter.append("dayOfEvent.month", month);
        }
        if(day > 0){
            filter.append("dayOfEvent.day", day);
        }

        MongoCursor<Document> cursor = mongoClient.getDatabase("ossg").getCollection(COLLECTION).find(filter).iterator();

        try {
            while (cursor.hasNext()) {
                list.add(serializer.deserialize(cursor.next()));
            }
        } finally {
            cursor.close();
        }
        return list;
    }
    
}