package com.airlink.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.airlink.model.business.EmployeeMgr;
import com.airlink.model.domain.Address;
import com.airlink.model.domain.Employee;

@Path("/employees/{id}/address")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AddressResource {

	private EmployeeMgr employeeMgr = EmployeeMgr.getInstance();
	
	@GET
	public Response getAddress(
			@PathParam("id") int id) {
		// Return the address if exists. 204 Empty otherwise
		Employee employee = employeeMgr.retrieveEmployee(id);
		if (employee == null) {
			return Response.noContent().build();
		}
		Address result = employee.getAddress();
		if (result == null) {
			return Response.noContent().build();
		}
		
		return Response.ok(result).build();
	}
	
	@POST
	public Response createAddress(
			@PathParam("id") int id, 
			Address address) {
		// Validate
		if (address == null || !address.validate()) {
			return Response.status(Status.BAD_REQUEST)
					.entity("Invalid address provided").build();
		}
		
		Employee employee = employeeMgr.retrieveEmployee(id);
		if (employee == null) {
			return Response.status(Status.NOT_FOUND)
					.entity("Could not assign address. Employee id not found").build();
		}
		
		// Complain if employee already has an address
		if (employee.getAddress() != null) {
			return Response.status(Status.BAD_REQUEST)
					.entity("Could not assign new address. Employee has an existing address").build();
		}
		
		employee.setAddress(address);
		Employee result = employeeMgr.updateEmployee(employee);
		Address add = new Address(result.getAddress());
		
		return Response.ok(add).build();
	}
	
	@POST
	@Path("{addressId}")
	public Response updateAddress(
			@PathParam("id") int id,
			@PathParam("addressId") int addressId,
			Address address) {
		// Validate
		if (address == null || !address.validate()) {
			return Response.status(Status.BAD_REQUEST)
					.entity("Invalid address provided").build();
		}
		
		Employee employee = employeeMgr.retrieveEmployee(id);
		if (employee == null) {
			return Response.status(Status.NOT_FOUND)
					.entity("Could not assign address. Employee id not found").build();
		}
		
		// Move this logic somewhere else
		Address addrs = employee.getAddress();		
		if (addrs == null || addrs.getId() != addressId) {
			return Response.status(Status.BAD_REQUEST)
					.entity("Could not update address. Address with id doesn't exist").build();
		}
		
		address.setId(addressId);
		employee.setAddress(address);
		Employee e = employeeMgr.updateEmployee(employee);
		Address add = new Address(e.getAddress());
		
		return Response.ok(add).build();
	}
	
	@DELETE
	public Response deleteAddress(
			@PathParam("id") int id) {
		Employee employee = employeeMgr.retrieveEmployee(id);
		if (employee == null || employee.getAddress() == null) {
			return Response.status(Status.NOT_FOUND)
					.entity("Could not delete address. Employee id or address not found").build();
		}
		
		Address add = new Address(employee.getAddress());
		employee.setAddress(null);
		employeeMgr.updateEmployee(employee);
		
		return Response.ok(add).build();
	}
	
}
