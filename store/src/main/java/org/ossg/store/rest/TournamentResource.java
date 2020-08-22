package org.ossg.store.rest;

import java.util.List;

import org.bson.Document;
import org.ossg.store.model.Tournament;
import org.ossg.store.model.serializer.TournamentSerializer;
import org.ossg.store.service.StoreTournament;

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
@Path("/tournaments")
public class TournamentResource {
    @Inject
    StoreTournament service;
    @Inject
    TournamentSerializer<Document> serializer;

    @GET
    public List<Tournament> list() {
        return service.list();
    }

    @POST
    public Tournament add(String tournamentJson) {
        Tournament tournament = serializer.deserialize(Document.parse(tournamentJson));
        service.add(tournament);
        return tournament;
    }

    @GET
    @Path("{id}")
    public Tournament get(@PathParam("id") String id) {
        return service.get(id);
    }

    @GET
    @Path("find")
    public List<Tournament> findByDate(@QueryParam("year") int year, @QueryParam("month") int month, @QueryParam("day") int day) {
        return service.searchByDate(day, month, year);
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