package controller;

import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Room;
import dao.RoomDAO;
import model.Seat;
import dao.SeatDAO; // 确保导入 SeatDAO
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Servlet implementation class MovieInsertRoomServlet
 */
@WebServlet("/insertroom")
public class MovieInsertRoomServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public MovieInsertRoomServlet() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        String roomname = request.getParameter("roomname");
        String roomusing = request.getParameter("roomusing");
        String movieid = request.getParameter("movieid");
        String moviename = request.getParameter("moviename");
        String playdateStr = request.getParameter("playdate");
        String playtimeStr = request.getParameter("playtime");
        String session = request.getParameter("sessionID");
        int seats = Integer.parseInt(request.getParameter("seatnumber"));

        Date playdate = Date.valueOf(playdateStr);
        Time playtime = Time.valueOf(playtimeStr + ":00");

        Room room = new Room();
        room.setRoomname(roomname);
        room.setRoomusing(roomusing);
        room.setMovieid(movieid);
        room.setRoommovie(moviename);
        room.setPlaydate(playdate);
        room.setPlaytime(playtime);
        room.setSession(session);
        room.setSeats(seats);

        RoomDAO roomDAO = new RoomDAO();
        roomDAO.addRoom(room);

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("movieproject_back-end-new");
        EntityManager em = emf.createEntityManager();
        SeatDAO seatDAO = new SeatDAO(); 
        boolean allSeatsInserted = true; 

        try {
            em.getTransaction().begin();

            for (int i = 1; i <= seats; i++) {
                String seatid = "A" + i;

                // 检查座位是否已存在且场次编号也匹配
                if (seatDAO.seatExists(session, seatid)) {
                    System.out.println("座位 " + seatid + " 在场次 " + session + " 中已存在，跳过插入。");
                    continue; // 跳过已经存在的座位
                }

                Seat seat = new Seat();
                seat.setMoviesession(session);
                seat.setSeatid(seatid);
                seat.setSeatstate(null);
                seat.setMemberno(null);
                seat.setOrderid(null);

                em.persist(seat);
                System.out.println("座位 " + seatid + " 成功新增。");
            }

            em.getTransaction().commit();
        } catch (Exception e) {
            allSeatsInserted = false;
            em.getTransaction().rollback();
            System.out.println("新增座位出错: " + e.getMessage());
        } finally {
            em.close();
            emf.close();
        }
        
        /*

        if (allSeatsInserted) {
            response.sendRedirect("success.html");
        } else {
            response.getWriter().write("新增座位失敗");
        }
        */
    }
}
