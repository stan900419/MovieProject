package controller;

import model.Order;
import model.OrderSummary;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.OrderDAO;

import java.io.IOException;
import java.util.List;

@WebServlet("/SelectOrderForSeat")
public class SelectOrderForSeatServlet extends HttpServlet {

    private OrderDAO orderDAO;

    @Override
    public void init() throws ServletException {
        orderDAO = new OrderDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        String latestUserName = orderDAO.getLatestUserName();

        if (latestUserName == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.getWriter().write("{\"error\": \"No user found.\"}");
            return;
        }

        // 使用 userName 查询最新的几笔订单
        List<Order> latestOrders = orderDAO.getLatestOrdersByUserName(latestUserName, 30); // 获取最新的 5 笔订单

        // 计算最新的 session 和订单数量
        String latestSession = "";
        int orderCount = latestOrders.size();

        if (orderCount > 0) {
            latestSession = latestOrders.get(0).getSession(); // 假设 session 字段在 Order 类中
        }

        String jsonResponse = String.format(
            "{\"userName\":\"%s\", \"session\":\"%s\", \"count\":%d}",
            latestUserName,
            latestSession,
            orderCount
        );

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(jsonResponse);
    }

}
