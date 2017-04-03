package com.airlink.rest;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.airlink.model.business.JobManager;
import com.airlink.model.domain.Job;

@Path("/jobs")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class JobResource {
	
	private JobManager jobMgr = JobManager.getInstance();
	
	@GET
	public Response getJobs() {
		List<Job> jobs = jobMgr.getJobs();
		
		GenericEntity<List<Job>> entity = 
				new GenericEntity<List<Job>>(jobs) {};
		
		return Response.ok(entity).build();
	}

	@POST
	public Response createJob(Job job) {
		// Validate
		if (job == null || !job.validate()) {
			return Response.status(Status.BAD_REQUEST)
					.entity("Invalid job provided").build();
		}
		
		Job result = jobMgr.registerJob(job);
		
		return Response.ok(result).build();
	}
	
	@GET
	@Path("{id}")
	public Response getJob(
			@PathParam("id") int id) {
		Job result = jobMgr.retrieveJob(id);
		if (result == null) {
			return Response.noContent().build();
		}
		
		return Response.ok(result).build();
	}
	
	@POST
	@Path("{id}")
	public Response updateJob(
			@PathParam("id") int id,
			Job job) {
		// Validate
		if (job == null || !job.validate()) {
			return Response.status(Status.BAD_REQUEST)
					.entity("Invalid job provided").build();
		}
		
		if (id != job.getId()) {
			return Response.status(Status.BAD_REQUEST)
					.entity("Ids must match").build();
		}
		
		Job j = jobMgr.retrieveJob(id);
		if (j == null) {
			return Response.status(Status.NOT_FOUND)
					.entity("Could not update Job. Id doesn't exist").build();
		}
		
		Job result = jobMgr.updateJob(job);
		
		return Response.ok(result).build();
	}
	
	@DELETE
	@Path("{id}")
	public Response deleteJob(
			@PathParam("id") int id) {
		Job j = jobMgr.retrieveJob(id);
		if (j == null) {
			return Response.status(Status.NOT_FOUND)
					.entity("Could not delete job. Id doesn't exist").build();
		}
		
		Job result = jobMgr.deleteJob(j);
		
		return Response.ok(result).build();
	}
}
