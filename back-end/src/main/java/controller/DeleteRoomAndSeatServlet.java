package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.RoomDAO;
import dao.SeatDAO;
import model.*;

/**
 * Servlet implementation class DeleteRoomAndSeatServlet
 */
@WebServlet("/deleteRoomAndSeat")
public class DeleteRoomAndSeatServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteRoomAndSeatServlet() {
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
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    String idParam = request.getParameter("id");
	    String session = request.getParameter("session");
	    int id = Integer.parseInt(idParam); // 確保從請求中獲取 id
	    
	    SeatDAO seatDAO = new SeatDAO();
	    seatDAO.deleteSeatsBySession(session);
	    RoomDAO roomDAO = new RoomDAO();
	    roomDAO.deleteRoomById(id); // 使用新方法刪除房間
	    response.getWriter().write("刪除成功");
	}


}
