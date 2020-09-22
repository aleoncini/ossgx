package org.ossg.store.rest;

import org.ossg.store.importing.H19Parser;
import org.ossg.store.model.Round;
import org.ossg.store.service.StoreRound;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/h19")
public class H19Resource {
    @Inject
    StoreRound service;
    @Inject
    H19Parser parser;

    @POST
    @Path("{id}")
    public Round add(@PathParam("id") String id, @QueryParam("overwrite") String overwrite) {
        if(service.get(id) != null){
            // round with the same ID already saved in the store
            if(overwrite == null){
                // overwrite not specified so nothing to do here
                return null;
            }
            if (! overwrite.equalsIgnoreCase("yes")){
                // overwrite specified but value is not yes, nothing to do here
                return null;
            }
            service.remove(id);
        }
        Round round = parser.parse(id);
        service.add(round);
        return round;
    }

    @GET
    @Path("{id}")
    public Round get(@PathParam("id") String id) {
        return service.get(id);
    }
    
}
