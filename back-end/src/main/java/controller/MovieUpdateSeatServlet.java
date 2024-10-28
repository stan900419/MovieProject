package controller;

import model.Order;
import model.Seat;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.OrderDAO;
import dao.SeatDAO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@WebServlet("/updateSeat")
public class MovieUpdateSeatServlet extends HttpServlet {

	private SeatDAO seatDAO;

	@Override
	public void init() throws ServletException {
		seatDAO = new SeatDAO();
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String moviesession = request.getParameter("moviesession");
		String customerName = request.getParameter("customerName");
		String seatQuantityStr = request.getParameter("seatQuantity");
		int seatQuantity;

		if (seatQuantityStr == null || seatQuantityStr.isEmpty()) {
			response.getWriter().write("Error: Seat quantity is not provided.");
			return;
		} else {
			try {
				seatQuantity = Integer.parseInt(seatQuantityStr);
			} catch (NumberFormatException e) {
				response.getWriter().write("Error: Invalid seat quantity format.");
				return;
			}
		}

		if (customerName == null) {
			response.getWriter().write("Error: Customer name is not set.");
			return;
		}

		List<Seat> seats = seatDAO.getSeatsBySession(moviesession);
		if (seats == null || seats.isEmpty()) {
			response.getWriter().write("No seats available for this session.");
			return;
		}

		// 顧客選擇座位的邏輯，這裡假設通過參數傳入選中的座位ID
		String[] selectedSeatIds = request.getParameter("selectedSeats").split(",");

		if (selectedSeatIds.length != seatQuantity) {
			response.getWriter().write("Error: You must select " + seatQuantity + " seats.");
			return;
		}
		//Wade_add
        OrderDAO orderDao = new OrderDAO();
        List<Order> order = new ArrayList<>();
        List<Integer> realOrderIds = new ArrayList<>();
        order = orderDao.getOrderListByName(customerName);
        for(int i=order.size()-1;i>order.size()-1-seatQuantity;i--)
        {
        		realOrderIds.add(order.get(i).getOrderId());
        }
        int count =0;
        //Wade_add
        
		// 更新座位狀態並設置顧客信息
		for (String seatId : selectedSeatIds) {
			Seat seat = seatDAO.getSeatBySessionAndSeatId(moviesession, seatId);
			if (seat != null && !"sell".equals(seat.getSeatstate())) {
				seat.setSeatstate("sell"); // 更新座位狀態為已售出
				seat.setMemberno(customerName); // 設置顧客名稱
				// Wade_add
				System.out.println(" realOrderIds :"+realOrderIds.get(count));
				orderDao.updateSeatById(realOrderIds.get(count), seat.getSeatid());

				seatDAO.updateSeat(seat);
				
			} else {
				response.getWriter().write("Error: Seat " + seatId + " is already sold or unavailable.");
				return;
			}
			count++;
		}

		response.getWriter().write("Seats updated successfully!");
	}
}
