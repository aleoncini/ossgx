package org.ossg.store.service;

import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.result.DeleteResult;

import org.bson.Document;
import org.ossg.store.model.DayOfEvent;
import org.ossg.store.model.Tournament;
import org.ossg.store.model.serializer.DayOfEventSerializer;
import org.ossg.store.model.serializer.TournamentSerializer;

import io.quarkus.mongodb.runtime.MongoClientName;

@ApplicationScoped
public class StoreTournament {
    private final static String COLLECTION = "tournaments";

    @Inject
    @MongoClientName("ossg")
    MongoClient mongoClient;

    @Inject
    TournamentSerializer<Document> tournamentSerializer;

    @Inject
    DayOfEventSerializer<Document> dayOfEventSerializer;

    public Tournament get(String id){
        Document query = new Document("id", id);
        Document tournament = mongoClient.getDatabase("ossg").getCollection(COLLECTION).find(query).first();

        return (tournament != null) ? tournamentSerializer.deserialize(tournament) : null;
    }

    public void add(Tournament tournament){
        Document document = tournamentSerializer.serialize(tournament);
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

    public List<Tournament> list(){
        List<Tournament> list = new ArrayList<>();
        MongoCursor<Document> cursor = mongoClient.getDatabase("ossg").getCollection(COLLECTION).find().iterator();

        try {
            while (cursor.hasNext()) {
                Document document = cursor.next();
                Tournament tournament = tournamentSerializer.deserialize(document);
                list.add(tournament);
            }
        } finally {
            cursor.close();
        }
        return list;
    }

    public List<Tournament> searchByDate(int day, int month, int year){
        List<Tournament> list = new ArrayList<>();

        Document filter = new Document("dayOfEvent.year", year);
        if(month > 0){
            filter.append("dayOfEvent.month", month);
        }
        if(day > 0){
            filter.append("dayOfEvent.day", day);
        }

        MongoCursor<Document> cursor = mongoClient.getDatabase("ossg").getCollection(COLLECTION).find(filter).iterator();

        try {
            while (cursor.hasNext()) {
                Document document = cursor.next();
                Tournament tournament = tournamentSerializer.deserialize(document);
                list.add(tournament);
            }
        } finally {
            cursor.close();
        }
        return list;
    }
    
}