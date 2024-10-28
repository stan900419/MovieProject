package controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import dao.EmployeeDAO;
import model.Employees;

@Path(value = "/loginEmployee")
public class EmployeeController {
	@Context
	private HttpServletRequest request;
	
	@POST
	@Path("/login")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response loginForm(@Context HttpServletRequest request, Employees employee) {
		String employeeUsername = employee.getEmployeeUsername();
		String psw = employee.getEmployeePassword();
		Employees emp = new EmployeeDAO().canlogin(employeeUsername, psw);
		
		if(emp != null) {
			HttpSession httpSession = request.getSession();
			httpSession.setAttribute("loggedemployee", emp);
			
			System.out.println("login success");
			System.out.println(Response.ok().entity(emp).build());
			return Response.ok().entity(emp).build();
		} else {
			return Response.status(Response.Status.UNAUTHORIZED).entity("登入失敗，帳號或密碼錯誤").build();
		}
	}
	
	@GET
	@Path("/checkEmployeeUsername")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getEmployeeByUsername(@QueryParam("employeeUsername") String employeeUsername) {
		EmployeeDAO dao = new EmployeeDAO();
		Employees employee = dao.getEmployeesByUsername(employeeUsername);
		Map<String, Object> response = new HashMap<>();

	    if (employee != null) {
	        response.put("exists", true);
	        response.put("message", "此帳號已被使用");
	        return Response.ok(response).build();  // 200 OK 回應
	    } else {
	        response.put("exists", false);
	        response.put("message", "此帳號可使用");
	        return Response.ok(response).build();  // 200 OK 回應
	    }
	}
	
	
	@POST
	@Path("/employeeRegister")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addEmployee(Employees employee) {
		employee.setEmployeeStatus("在職中");
		EmployeeDAO dao = new EmployeeDAO();
		dao.Register(employee);
		return Response.status(Response.Status.CREATED).entity(employee).build();
	}
	
	// 這個方法有問題!
	@PUT
	@Path("/id/{employeeId}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateEmployees(@PathParam("employeeId") Integer employeeId,Employees employee) {
		try {
			EmployeeDAO dao = new EmployeeDAO();
			Employees existingEmployee = dao.getEmployeesById(employeeId);
			
			if(existingEmployee != null) {
				dao.updateEmployees(employeeId, employee);
				Employees updatedEmployee = dao.getEmployeesById(employeeId);
				return Response.ok()
						.entity(updatedEmployee)
						.build();
			} else{
				return Response.ok()
						.entity("emeployee not found")
						.build();
			}
		} catch(Exception e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity("a employee error occurred:" + e.getMessage())
					.build();
		}
	}
}
