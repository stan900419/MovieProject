package controller;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import dao.MovierankDAO;
import model.Movierank;

@Path(value = "movierank")
public class MovieRankController {
	
@GET	
@Produces(MediaType.APPLICATION_JSON)
public List<Movierank> getList(){
	MovierankDAO movierankDAO = new MovierankDAO();
	List<Movierank> movierank = null;
	if(movierankDAO.getAll().size()==0)
		movierank=movierankDAO.addMovieMovierankList();
	else {
		System.out.println("ffff");
		movierank=movierankDAO.updateMovieMovierankList();
	}
	for(int i=0;i<movierank.size();i++)
	{
		System.out.println("__"+i+"__"+movierank.get(i).getMoviename());
	}
	return movierank;
}
}
