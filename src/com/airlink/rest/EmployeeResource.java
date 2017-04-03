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

import com.airlink.model.business.EmployeeMgr;
import com.airlink.model.domain.Employee;

@Path("/employees")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class EmployeeResource {

	private EmployeeMgr employeeMgr = EmployeeMgr.getInstance();
	
	@GET
	public Response getEmployees() {
		List<Employee> employees = employeeMgr.getEmployees();
		
		GenericEntity<List<Employee>> entity = 
				new GenericEntity<List<Employee>>(employees) {};
				
		return Response.ok(entity).build();
	}
	
	@POST
	public Response createEmployee(Employee employee) {
		// Validate
		if (employee == null || !employee.validate()) {
			return Response.status(Status.BAD_REQUEST)
					.entity("Invalid employee provided").build();
		}
		
		Employee result = employeeMgr.registerEmployee(employee);
		
		return Response.ok(result).build();
	}
	
	@GET
	@Path("{id}")
	public Response getEmployee(
			@PathParam("id") int id) {
		Employee result = employeeMgr.retrieveEmployee(id);
		if (result == null) {
			return Response.noContent().build();
		}
		
		return Response.ok(result).build();
	}
	
	@POST
	@Path("{id}")
	public Response updateEmployee(
			@PathParam("id") int id,
			Employee employee) {
		// Validate
		if (employee == null || !employee.validate()) {
			return Response.status(Status.BAD_REQUEST)
					.entity("Invalid employee provided").build();
		}
		
		if (id != employee.getId()) {
			return Response.status(Status.BAD_REQUEST)
					.entity("Ids must match").build();
		}
	
		Employee e = employeeMgr.retrieveEmployee(id);
		if (e == null) {
			return Response.status(Status.NOT_FOUND)
					.entity("Could not update employee. Id doesn't exist").build();
		}
		
		Employee result = employeeMgr.updateEmployee(employee);
		
		return Response.ok(result).build();
	}
	
	@DELETE
	@Path("{id}")
	public Response deleteEmployee(
			@PathParam("id") int id) {
		Employee e = employeeMgr.retrieveEmployee(id);
		if (e == null) {
			return Response.status(Status.NOT_FOUND)
					.entity("Could not delete employee. Id doesn't exist").build();
		}
		
		Employee result = employeeMgr.deleteEmployee(e);	
		
		return Response.ok(result).build();
	}
	
}
