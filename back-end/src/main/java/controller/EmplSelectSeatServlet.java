package controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.*;

import com.google.gson.Gson;

import dao.SeatDAO;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/selectSeat")
public class EmplSelectSeatServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private SeatDAO seatDAO = new SeatDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String session = request.getParameter("session"); // 接收場次編號

        List<Seat> seats = seatDAO.getSeatsBySession(session); // 取得座位資料
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        PrintWriter out = response.getWriter();
        Gson gson = new Gson();
        out.print(gson.toJson(seats)); // 回傳 JSON
        out.flush();
    }
}

