package com.airlink.rest;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import com.airlink.notes.Note;
import com.airlink.notes.NoteMgr;

@Path("/note")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class NoteResource {

	private static NoteMgr noteMgr = NoteMgr.getInstance();
	
	@POST
    public Response create(Note note) {
        Note result = noteMgr.create(note);
        ResponseBuilder builder = Response.ok(result);
        
        return builder.build();
    }
	
	@GET
    @Path("{id}")
    public Response find (
            @PathParam("id") int id ) {
        ResponseBuilder builder = Response.ok(noteMgr.get(id));
        return builder.build();
    }
	
    @POST
    @Path("{id}")
    public Response update(Note note) {
        Note result = noteMgr.update(note);
        ResponseBuilder builder = Response.ok(result);
        
        return builder.build();
    }
	
    @GET
	public Response findAll() {
		List<Note> notes = noteMgr.getNotes();
		GenericEntity<List<Note>> entity = new GenericEntity<List<Note>>(notes) {};
        ResponseBuilder builder = Response.ok(entity);
		
		return builder.build();
	}
    
    @GET
    @Path("/test")
    public Response test() {
    	return Response.ok("Testing...").build();
    }
}
