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
@WebServlet("/movieidsavemovie")
public class MovieIdSaveMovieServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public MovieIdSaveMovieServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		//
		// TODO Auto-generated method stub
		/*
		 * request.getSession().getAttribute("map"); HashMap<String, Object> readMap =
		 * (HashMap<String, Object>) request.getSession().getAttribute("map"); int
		 * count=request.getSession().getAttribute("map")!=null?(int)
		 * readMap.get("count"):0; int
		 * cc=request.getSession().getAttribute("map")!=null?(int) readMap.get("cc"):0;
		 * String string_test=request.getSession().getAttribute("map")!=null?(String)
		 * readMap.get("string_test"):"init";
		 * 
		 * try { Thread.sleep(10); System.out.println("FAServlet End...");//約執行0.068ms
		 * }catch(Exception ex) {} PrintWriter pp=response.getWriter();
		 * pp.print(count+" "+cc); count++; cc+=2; HashMap<String,Object> map = new
		 * HashMap<>(); map.put("count", count); map.put("cc", cc);
		 * map.put("string_test", "ttttt"); request.getSession().setAttribute("map",
		 * map);
		 */
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		// doGet(request, response);
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		HashMap<String, Object> readMap = (HashMap<String, Object>) request.getSession().getAttribute("map") != null
				? (HashMap<String, Object>) request.getSession().getAttribute("map")
				: new HashMap<>();

		if (request.getParameter("movieId") != null) {
			String customername = request.getParameter("customername");
			String movieId = request.getParameter("movieId");
			String movieName = request.getParameter("movieName");
			String movieType = request.getParameter("movieType");
			String movieStartDate = request.getParameter("movieStartDate");
			String runtime = request.getParameter("runtime");
			String moviePrice = request.getParameter("moviePrice");

			List<Movie> llList = new ArrayList<>();
			//if (readMap.get("movieId") == null) {
				System.out.println("-------------------------//////");
				OrderDAO orderDAO = new OrderDAO();
				Order order = new Order();
				order.setMovieName(movieName);
				order.setMoviePrice(Integer.parseInt(moviePrice));
				order.setMovieType(movieType);
				System.out.println("_____"+customername);
				if(customername!=null)
				{
					String userName = customername.equals("Guest")?"Guest"+(orderDAO.getAll().size()):customername;
                    order.setUserName(userName);
				}
				else if(customername.equals("old")){
					String userName = "Guest"+(orderDAO.getAll().size());
                    order.setUserName(userName);
				}
				
				
				String timeInterval=movieStartDate+runtime;
				order.setMovieTime(timeInterval);
				System.out.println("MovieTime:"+order.getMovieTime());
				
				orderDAO.insertTicket(order);
				response.getWriter().append(order.getUserName());
			//}

		}
		

	}

}
