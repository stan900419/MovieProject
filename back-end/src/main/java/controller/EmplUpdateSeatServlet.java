package controller;

import dao.SeatDAO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;

@WebServlet("/emplupdateSeat")
public class EmplUpdateSeatServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // 取得前端傳來的參數
            String idStr = request.getParameter("id");
            String seatid = request.getParameter("seatid");
            String seatstate = request.getParameter("seatstate");
            String memberno = request.getParameter("memberno");

            // 確認是否收到參數
            System.out.println("Received Parameters - id: " + idStr + ", seatid: " + seatid + ", seatstate: " + seatstate + ", memberno: " + memberno);

            // 解析 id 字串為整數
            int id = Integer.parseInt(idStr);

            // 創建 SeatDAO 實例並更新座位資訊
            SeatDAO seatDAO = new SeatDAO();
            boolean isUpdated = seatDAO.emplUpdateSeat(id, seatid, seatstate, memberno);

            // 設定回應類型
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            // 根據更新結果回傳 JSON 反應
            String jsonResponse = isUpdated
                ? "{\"status\": \"success\", \"message\": \"Seat updated successfully.\"}"
                : "{\"status\": \"error\", \"message\": \"Failed to update seat.\"}";

            response.getWriter().write(jsonResponse);
        } catch (NumberFormatException e) {
            // 捕獲數字解析錯誤
            System.out.println("Error parsing 'id' parameter: " + e.getMessage());
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid ID format.");
        } catch (Exception e) {
            // 捕獲其他例外並記錄錯誤訊息
            System.out.println("Error in EmplUpdateSeatServlet: " + e.getMessage());
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Internal Server Error.");
        }
    }


}
