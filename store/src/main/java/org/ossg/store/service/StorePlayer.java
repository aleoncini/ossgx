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
import org.ossg.store.model.Player;
import org.ossg.store.model.serializer.PlayerSerializer;

import io.quarkus.mongodb.runtime.MongoClientName;

@ApplicationScoped
public class StorePlayer {
    private final static String COLLECTION = "players";

    @MongoClientName("ossg")
    @Inject MongoClient mongoClient;

    @Inject
    PlayerSerializer<Document> serializer;

    public List<Player> list(){
        List<Player> list = new ArrayList<>();
        MongoCursor<Document> cursor = mongoClient.getDatabase("ossg").getCollection(COLLECTION).find().iterator();

        try {
            while (cursor.hasNext()) {
                Document document = cursor.next();
                Player player = serializer.deserialize(document);
                list.add(player);
            }
        } finally {
            cursor.close();
        }
        return list;
    }

    public List<Player> searchByName(String text){
        List<Player> list = new ArrayList<>();

        Document filter = new Document("name", Pattern.compile(text, Pattern.CASE_INSENSITIVE));
        MongoCursor<Document> cursor = mongoClient.getDatabase("ossg").getCollection(COLLECTION).find(filter).iterator();

        try {
            while (cursor.hasNext()) {
                Document document = cursor.next();
                Player player = serializer.deserialize(document);
                list.add(player);
            }
        } finally {
            cursor.close();
        }
        return list;
    }

    public Player get(String id){
        Document query = new Document("id", id);
        Document player = mongoClient.getDatabase("ossg").getCollection(COLLECTION).find(query).first();

        return (player != null) ? serializer.deserialize(player) : null;
    }

    public void add(Player player){
        Document document = serializer.serialize(player);
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

}