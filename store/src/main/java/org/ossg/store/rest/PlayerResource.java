package org.ossg.store.rest;

import java.util.List;

import org.bson.Document;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.ossg.store.model.Player;
import org.ossg.store.service.StorePlayer;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/players")
public class PlayerResource {
    @Inject StorePlayer service;

    @GET
    public List<Player> list() {
        return service.list();
    }

    @POST
    public Player add(Player player) {
        service.add(player);
        return player;
    }

    @GET
    @Path("{id}")
    public Player get(@PathParam("id") String id) {
        return service.get(id);
    }

    @GET
    @Path("find/{name}")
    public List<Player> findByName(@PathParam("name") String name) {
        return service.searchByName(name);
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