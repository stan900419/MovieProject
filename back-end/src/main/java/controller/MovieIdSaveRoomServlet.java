package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.mysql.cj.Session;

import dao.MovieDao;
import dao.OrderDAO;
import model.Movie;
import model.Order;

/**
 * Servlet implementation class MovieGetServlet___store user selected movie
 */
@WebServlet("/movieidsaveroom")
public class MovieIdSaveRoomServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public MovieIdSaveRoomServlet() {super();}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");		

		if (request.getParameter("cinemaRoom") != null) {
			String cinema = request.getParameter("cinema");
			String cinemaRoom = request.getParameter("cinemaRoom");
			String playdate = request.getParameter("playdate");
			String playtime = request.getParameter("playtime");
			String session = request.getParameter("session");
			String runtime = request.getParameter("runtime");
			OrderDAO orderDAO = new OrderDAO();
			String userName = request.getParameter("userNameRoom");
			System.out.println("cinemaRoom userName:"+userName);
			Order order = orderDAO.getOrderByName(userName);
			order.setCinema(cinema);
			order.setCinemaRoom(cinemaRoom);
			order.setMovieTime(playdate + playtime+"_+"+runtime);
			System.out.println(order.getMovieTime());
			order.setSession(session);
			orderDAO.updateOrder(order);
			response.getWriter().append(
					" Session saved " + "cinemaRoom:" + order.getCinemaRoom() + " playtime" + order.getMovieTime());
		}
	}

}
