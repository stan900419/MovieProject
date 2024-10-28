package controller;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Room;

import com.google.gson.Gson;

import dao.RoomDAO;

@WebServlet("/selectroom")
public class MovieSelectRoomServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public MovieSelectRoomServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // 建立 RoomDAO 物件來獲取房間資料
        RoomDAO roomDAO = new RoomDAO();
        List<Room> rooms = roomDAO.getAll();

        // 使用 Gson 將房間資料轉換為 JSON 字串
        Gson gson = new Gson();
        String json = gson.toJson(rooms);

        // 將 JSON 字串傳送給前端
        response.getWriter().write(json);
    }
}
