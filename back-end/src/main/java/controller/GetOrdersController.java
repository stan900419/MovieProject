package controller;

import java.util.*;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;

import dao.OrderDAO;
import model.Order;
import model.TicketBooking;

@Path(value = "/orders")
public class GetOrdersController {

	@GET // 抓當次的購票紀錄(僅限當次，因為roomsession從購票時的流程來的)
	@Path("/{username}/{roomSession}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response loginForm(@PathParam("username") String username, @PathParam("roomSession") String RoomSession) {
		OrderDAO dao = new OrderDAO();
		List<Order> orders = dao.getOrdersByUserName(username);
		System.out.println(RoomSession);

		if (orders != null) {
			// 用session篩選，抓同場次的資料，用lambda語法
			orders.removeIf(order -> !order.getSession().equals(RoomSession));
			// 返回找到的訂單及 HTTP 200 狀態碼
			return Response.ok(orders).build();
		} else {
			// 如果找不到訂單，返回 HTTP 404 狀態碼
			return Response.status(Response.Status.NOT_FOUND).entity("Order not found for username: " + username)
					.build();
		}
	}

	@POST
	@Path("/savetickets")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response saveTicket(TicketBooking request) {
		// 建立一個所有票數的 List
		List<String> ticketNums = Arrays.asList(request.getTicketNum_11(), request.getTicketNum_12(),
				request.getTicketNum_13(), request.getTicketNum_14(), request.getTicketNum_21(),
				request.getTicketNum_22(), request.getTicketNum_23(), request.getTicketNum_24());

		// 對應的票種
		List<String> ticketTypes = Arrays.asList(request.getTicketType_11(), request.getTicketType_12(),
				request.getTicketType_13(), request.getTicketType_14(), request.getTicketType_21(),
				request.getTicketType_22(), request.getTicketType_23(), request.getTicketType_24());

		// 處理票務邏輯
		try {
			// 座位和票應該一一對應，每個座位應該對應特定的票種
			String[] seatArray = request.getSeat(); // 取得 String[] 類型的座位
			List<String> seats = Arrays.asList(seatArray);
			int seatIndex = 0;

			// 遍歷所有票數和票種
			for (int i = 0; i < ticketNums.size(); i++) {
				String ticketNumStr = ticketNums.get(i);

				// 如果該票種的票數不為 0，才進行插入邏輯
				if (ticketNumStr != null && !ticketNumStr.isEmpty()) {
					int ticketNum = Integer.parseInt(ticketNumStr);

					// 取得對應的票種
					String ticketType = ticketTypes.get(i);

					// 如果有票，為每個票插入一個座位
					for (int k = 0; k < ticketNum; k++) {
						// 確保有足夠的座位數
						if (seatIndex < seats.size()) {
							String seat = seats.get(seatIndex);

							// 插入每張票
							Order order = new Order();
							order.setUserName(request.getUsername());
							order.setCinemaRoom(request.getCinemaroom());
							order.setMovieName(request.getRoommovie());
							order.setMovieTime(request.getMovietime());
							order.setSession(request.getSession());
							order.setSeat(seat); // 將座位分配給票
							order.setTicketType(ticketType); // 設置票種

							// 根據票種來設置票價
							int price = calculateTicketPrice(ticketType);
							order.setTotalPrice(price);

							// 將訂單插入資料庫
							OrderDAO dao = new OrderDAO();
							dao.insertTicket(order);

							// 座位索引增加，指向下一個座位
							seatIndex++;
						} else {
							// 如果座位不足，拋出錯誤或跳出迴圈
							System.out.println("座位數不足，無法分配所有票。");
							break;
						}
					}
				}
			}

			return Response.status(Response.Status.OK).entity("後端Tickets saved successfully!").build();

		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error saving tickets.").build();
		}

	}

	// 假設一個根據票種來計算票價的邏輯
	private int calculateTicketPrice(String ticketType) {
		switch (ticketType) {
		case "11":
			return 380; // 套票一
		case "12":
			return 360; // 套票二
		case "13":
			return 350; // 套票三
		case "14":
			return 300; // 套票四
		case "21":
			return 250; // 全票
		case "22":
			return 230; // 學生票
		case "23":
			return 220; // 聯名票
		case "24":
			return 180; // 愛心票
		default:
			return 0;
		}
	}
}
