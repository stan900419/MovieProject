package controller;

import model.Seat;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.SeatDAO;

import java.io.IOException;
import java.util.List;

@WebServlet("/getAllSeats")
public class MovieSelectSeatServlet extends HttpServlet {

    private SeatDAO seatDAO;

    @Override
    public void init() throws ServletException {
        seatDAO = new SeatDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String moviesession = request.getParameter("moviesession");
        System.out.println("movieSessionTest"+moviesession);

        
        List<Seat> allSeats = seatDAO.getSeatsBySession(moviesession);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

       
        StringBuilder json = new StringBuilder("[");
        for (int i = 0; i < allSeats.size(); i++) {
            Seat seat = allSeats.get(i);
            json.append("{\"seatid\":\"").append(seat.getSeatid())
                .append("\", \"seatstate\":").append(seat.getSeatstate() != null ? "\"" + seat.getSeatstate() + "\"" : "null") // 返回 null 而不是字符串
                .append("}");
            if (i < allSeats.size() - 1) {
                json.append(",");
            }
        }
        json.append("]");
        
        response.getWriter().write(json.toString());
    }
}
