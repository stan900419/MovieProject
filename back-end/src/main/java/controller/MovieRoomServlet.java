package controller;

import java.io.IOException;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import dao.RoomDAO;

import java.util.Date;
import model.Movie;
import model.Room;

/**
 * Servlet implementation class MovieOrderStep2Servlet__askForRoom
 */
@WebServlet("/movieroom")
public class MovieRoomServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MovieRoomServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		//response.setContentType("text/html;charset=utf-8");
		response.setContentType("application/json;charset=utf-8");
		System.out.println("uuusername:"+request.getSession().getAttribute("username"));
		RoomDAO roomDAO=new RoomDAO();
		String movieId = (String) request.getAttribute("movieId");
		
		List<Room>rooms = roomDAO.getAll();
		
		Gson gson=new Gson();
		String jsonString = gson.toJson(rooms);
		response.getWriter().append(jsonString);
		
	}
//	String formatDate(String date) {
//		DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
//		 try {
//			Date dateString = format.parse(date);
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return dateString;
//	}

}
