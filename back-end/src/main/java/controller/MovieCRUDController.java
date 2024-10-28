package controller;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import dao.MovieDao;
import model.Movie;

@Path(value = "/crud/")
public class MovieCRUDController {
	@GET
	@Path("getAll")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllMovie() {
		MovieDao dao = new MovieDao();
		List<Movie> listOfMovie = dao.getAll();

		if (listOfMovie != null) {
			return Response.ok(listOfMovie).build();
		} else {
			return Response.status(Response.Status.NOT_FOUND).entity("all movie not found by controller!").build();
		}
	}

	@GET
	@Path("bymovieid/{movieid}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getByMovieId(@PathParam("movieid") Integer movieId) {
		MovieDao dao = new MovieDao();
		Movie movie = dao.getMovieById(movieId);
		if (movie != null) {
			return Response.ok().entity(movie).build();
		} else {
			String errorMessage = String.format("無法找到編號為 %d 的電影，請確認 ID 是否正確。", movieId);
			return Response.status(Response.Status.NOT_FOUND).entity(errorMessage).build();

		}
	}

	@GET
	@Path("kind")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getMovieByKind(@QueryParam("kind") List<String> movieKinds) {
		MovieDao dao = new MovieDao();
		List<Movie> listOfMovieByKind = dao.findByMovieKinds(movieKinds);
		List<Movie> listOfAllMovie = dao.getAll();

		if (movieKinds != null && !movieKinds.isEmpty()) {
			return Response.ok().entity(listOfMovieByKind).build();
		} else {
			System.out.println("找不到該種類的電影");
			return Response.ok().entity(listOfAllMovie).build();
		}
	}

	@POST
	@Path("addMovie")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addMovie(Movie movie) {
		MovieDao dao = new MovieDao();
		dao.addMovie(movie);
		List<Movie> listOfAllMovie = dao.getAll();
		return Response.ok().entity(listOfAllMovie).build();
	}

	@PUT
	@Path("update/{movieid}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateMovie(@PathParam("movieid") Integer movieId, Movie movie) {
		try {
			MovieDao dao = new MovieDao();
			Movie existingMovie = dao.getMovieById(movieId);

			if (existingMovie != null) {
				dao.updateMovies(movieId, movie);
				Movie updatedMovie = dao.getMovieById(movieId);
				return Response.ok().entity(updatedMovie).build();
			} else {
				return Response.status(Response.Status.NOT_FOUND).entity("movie not found by the id").build();
			}
		} catch (Exception e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity("a movie error occurred:" + e.getMessage()).build();
		}
	}

}
