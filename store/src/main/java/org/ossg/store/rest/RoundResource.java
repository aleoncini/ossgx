package org.ossg.store.rest;

import java.util.List;

import org.bson.Document;
import org.ossg.store.model.Round;
import org.ossg.store.model.serializer.RoundSerializer;
import org.ossg.store.service.StoreRound;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/rounds")
public class RoundResource {
    @Inject
    StoreRound service;
    @Inject
    RoundSerializer<Document> serializer;

    @GET
    public List<Round> list() {
        return service.list();
    }

    @POST
    public Round add(String json) {
        Round round = serializer.deserialize(Document.parse(json));
        service.add(round);
        return round;
    }

    @GET
    @Path("{id}")
    public Round get(@PathParam("id") String id) {
        return service.get(id);
    }

    @GET
    @Path("find")
    public List<Round> findByDate(
        @QueryParam("playerId") String playerId, 
        @QueryParam("courseId") String courseId, 
        @QueryParam("tournamentId") String tournamentId, 
        @QueryParam("year") int year, 
        @QueryParam("month") int month, 
        @QueryParam("day") int day) {
        return service.search(playerId, courseId, tournamentId, day, month, year);
    }

    @DELETE
    @Path("{id}")
    public String remove(@PathParam("id") String id) {
        Document result = new Document("id",id);
        if(service.remove(id)){
            result.append("removed", "yes");
        } else {
            result.append("removed", "no");
        }
        return result.toJson();
    }    
}