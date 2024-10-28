package controller;

import model.Room;
import dao.RoomDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/updateRoom")
public class UpdateRoomAndSeatsServlet extends HttpServlet {

    private RoomDAO roomDAO;

    @Override
    public void init() throws ServletException {
        roomDAO = new RoomDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 从请求中获取表单数据
        int ID = Integer.parseInt(request.getParameter("id")); // 房间 ID
        String roomName = request.getParameter("roomname"); // 房间名称
        String roomUsing = request.getParameter("roomusing"); // 房间状态
        String movieID = request.getParameter("movieid"); // 电影 ID
        String movieName = request.getParameter("moviename"); // 电影名称

        System.out.println("ID: " + request.getParameter("id"));
        System.out.println("房间名称: " + request.getParameter("roomname"));
        System.out.println("房间状态: " + request.getParameter("roomusing"));
        System.out.println("电影ID: " + request.getParameter("movieid"));
        System.out.println("电影名称: " + request.getParameter("moviename"));

        // 调用 RoomDAO 的 updateRoomFields 方法更新房间信息
        roomDAO.updateRoomFields(ID,roomName,roomUsing,movieID,movieName);

        // 返回成功响应
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().write("更新成功！");
    }
}
