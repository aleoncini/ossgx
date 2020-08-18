package org.ossg.store.rest;

import java.util.List;

import org.bson.Document;
import org.ossg.store.model.Course;
import org.ossg.store.model.serializer.CourseSerializer;
import org.ossg.store.service.StoreCourse;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/courses")
public class CourseResource {

    @Inject
    StoreCourse service;
    @Inject
    CourseSerializer<Document> serializer;

    @GET
    public List<Course> list() {
        return service.list();
    }

    @POST
    public Course add(String courseJson) {
        Course course = serializer.deserialize(Document.parse(courseJson));
        service.add(course);
        return course;
    }

    @GET
    @Path("{id}")
    public Course get(@PathParam("id") String id) {
        return service.get(id);
    }

    @GET
    @Path("find/{name}")
    public List<Course> findByName(@PathParam("name") String name) {
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