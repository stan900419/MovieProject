package controller;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import dao.MovieDao;
import dao.RoomDAO;
import model.Movie;
import model.Room;


@Path("/movie")
public class MovieService {
	
	@GET
	@Path("getAllRooms")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Room> getAll(){
		RoomDAO roomDAO = new RoomDAO();
    	return roomDAO.getAll();
	}

	@GET
	@Path("getAllMovies")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Movie> movieget(){
		MovieDao dao=new MovieDao();		
		return dao.getAll();
	}
}
