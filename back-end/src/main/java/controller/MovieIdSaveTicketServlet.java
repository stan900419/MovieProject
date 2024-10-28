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

import dao.EatmenuDAO;
import dao.MovieDao;
import dao.OrderDAO;
import model.Eatmenu;
import model.Movie;
import model.Order;
import model.TicketCounter;

/**
 * Servlet implementation class MovieGetServlet___store user selected movie
 */
@WebServlet("/movieidsaveticket")
public class MovieIdSaveTicketServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public MovieIdSaveTicketServlet() {
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
		List<Integer> ticketTypeCounts;
		OrderDAO orderDAO;
		String userName = null;
		int ticketNumberTotal = 0;
		for (int i = 1; i < 3; i++)// temporarily set to two
		{
			int inTableOrderCount;
			orderDAO = new OrderDAO();
			if (i == 1)
				inTableOrderCount = 0;
			else
				inTableOrderCount = 1;
			userName = request.getParameter("userNameTicket");
			Order order = orderDAO.getOrderByName(userName);
			for (int j = 1; j <= 4; j++) {
				if (request.getParameter("ticketType_" + i + j) != null) {

					if (Integer.parseInt(request.getParameter("ticketNum_" + i + j)) != 0) {
						ticketTypeCounts = new ArrayList<>();
						ticketTypeCounts.add(Integer.parseInt(request.getParameter("ticketNum_" + i + j)));
						String ticketType = request.getParameter("ticketType_" + i + j);
						int ticketNum = Integer.parseInt(request.getParameter("ticketNum_" + i + j));

						/*****************************************
						 * duplicate
						 ******************************************/
						int id = order.getOrderId();

						for (int ticketTypeCount = 0; ticketTypeCount < ticketTypeCounts.size(); ticketTypeCount++) {
							for (int k = 0; k < ticketTypeCounts.get(ticketTypeCount); k++) {
								// if(ticketTypeCount>0&&k==0)continue;
								System.out.println("k:" + k);
								System.out.println("inTableOrderCount:" + inTableOrderCount);
								System.out.println("id+inTableOrderCount:" + (id + inTableOrderCount));
								order.setTicketType("" + i + j);
								if (i == 1) {
									EatmenuDAO eatMenuDao = new EatmenuDAO();
									if (j == 1) {
										Eatmenu eatItem;
										order.setMoviePrice(600);// 地瓜球,紅柚綠茶
										eatItem = eatMenuDao.getEatItemByName("地瓜球");
										order.setEatItem(eatItem);
										eatItem = eatMenuDao.getEatItemByName("紅柚綠茶");
										order.appendEatItem(eatItem);
									} else if (j == 2) {
										Eatmenu eatItem;
										order.setMoviePrice(600);// 墨魚甜不辣,梅香脆果綠
										eatItem = eatMenuDao.getEatItemByName("墨魚甜不辣");
										order.setEatItem(eatItem);
										eatItem = eatMenuDao.getEatItemByName("梅香脆果綠");
										order.appendEatItem(eatItem);
									} else if (j == 3) {
										Eatmenu eatItem;
										order.setMoviePrice(600);
										eatItem = eatMenuDao.getEatItemByName("田字薯餅");
										order.setEatItem(eatItem);
										eatItem = eatMenuDao.getEatItemByName("起司奶蓋紅茶");
										order.appendEatItem(eatItem);
									} else if (j == 4) {
										Eatmenu eatItem;
										order.setMoviePrice(600);
										eatItem = eatMenuDao.getEatItemByName("墨魚甜不辣");
										order.setEatItem(eatItem);
										eatItem = eatMenuDao.getEatItemByName("紅柚綠茶");
										order.appendEatItem(eatItem);
									}
								}
								if (i == 2) {
									if (j == 1) {
										order.setMoviePrice(300);
										order.setTotalPrice(300);
									} else if (j == 2) {
										order.setMoviePrice(280);
										order.setTotalPrice(280);
									} else if (j == 3) {
										order.setMoviePrice(244);
										order.setTotalPrice(280);
									} else if (j == 4) {
										order.setMoviePrice(150);
										order.setTotalPrice(280);
									}
									Eatmenu eatItem = new Eatmenu(null, null, null);
									order.eatSetToNone(eatItem);
								}
								order.setOrderId(id + inTableOrderCount);
								System.out.println("______" + order.getMoviePrice());
								System.out.println("______" + order.getEatPrice());
								order.setTotalPrice(order.calTotalPrice());
								;
								orderDAO.insertTicket(order);
								inTableOrderCount++;

							}
						}

						/*****************************************
						 * duplicate
						 ******************************************/

						// orderDAO.deleteById(order.getOrderId());

						ticketNumberTotal += ticketNum;
//						 response.getWriter().append(" Session saved " + " ticketType_" + i + j + " : " +
//						 ticketType + " ticketNum :" + ticketNum);

					}

				}
			}

		}
		List<TicketCounter> ticketCounters = new ArrayList<>();
		TicketCounter ticketCounter = new TicketCounter(userName, ticketNumberTotal);
		ticketCounters.add(ticketCounter);
		Gson gson = new Gson();
		String jsonString = gson.toJson(ticketCounter);

		response.getWriter().append(jsonString);

	}

}
