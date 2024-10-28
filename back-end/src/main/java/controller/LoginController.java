package controller;

import java.sql.Timestamp;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import dao.MemberDAO;
import model.Member;

@Path(value = "/login")
public class LoginController {
	@Context
	private HttpServletRequest request;

	@GET  //Wade寫的登入方式
	@Path("/{username}/{password}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response loginForm(@PathParam("username") String phone, @PathParam("password") String psw) {
		MemberDAO memberDAO = new MemberDAO();
		System.out.println("canlogin:" + memberDAO.canlogin(phone, psw));
		if (memberDAO.canlogin(phone, psw) == null) {
			HttpSession httpSession = request.getSession();
			httpSession.setAttribute("username", memberDAO.canlogin(phone, psw));
			return Response.ok().entity(memberDAO.canlogin(phone, psw)).build();
		}

		else {
			HttpSession httpSession = request.getSession();
			httpSession.setAttribute("username", memberDAO.canlogin(phone, psw));
			return Response.ok().entity(memberDAO.canlogin(phone, psw)).build();
		}

	}

	@POST
	@Path("/memberLogin")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response loginForm(@Context HttpServletRequest request, Member loginRequest) {
		MemberDAO memberDAO = new MemberDAO();
		String phone = loginRequest.getPhone(); // 從請求對象中獲取phone
		String psw = loginRequest.getPassword(); // 從請求對象中獲取password

		// 驗證是否能夠登入
		Member member = new MemberDAO().canlogin(phone, psw);

		if (member != null) {
			// 登入成功，設置 session
			HttpSession httpSession = request.getSession();
			httpSession.setAttribute("loggedmember", member);

			System.out.println("login success!");
			// 返回成功訊息
			return Response.ok().entity(member).build();
		} else {
			// 登入失敗
			return Response.status(Response.Status.UNAUTHORIZED).entity("登入失敗，帳號或密碼錯誤").build();
		}
	}
	
	@GET
	@Path("/bymemberLevel/{membershipLevel}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Member> getByMemberLevel(@QueryParam("membershipLevel") List<String> membershipLevel) {
		MemberDAO memberDAO = new MemberDAO();
		if(membershipLevel != null && !membershipLevel.isEmpty()) {
			return memberDAO.findByMemberLevel(membershipLevel);
		}else {
			return memberDAO.getAll();
		}
	}

	@POST
	@Path("register")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addMember(Member member) {
		member.setBalance(0);
		member.setMembershipLevel("bronze");
		member.setCreateDate(new Timestamp(System.currentTimeMillis()));
		MemberDAO dao = new MemberDAO();
		dao.Register(member);
		return Response.status(Response.Status.CREATED).entity(member).build(); // 只返回新增的會員
	}
	
	@PUT
	@Path("/{memberno}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateMembers(@PathParam("memberno") Integer memberno, Member member) {
		try {
			MemberDAO dao = new MemberDAO();
			Member existingMember = dao.getMembersByNo(memberno);
			
			if(existingMember != null) {
				dao.updateMembers(memberno, member);
				Member updatedMember = dao.getMembersByNo(memberno);
				return Response.ok()	// 建立一個 200 OK 的回應
						.entity(updatedMember)	// 設置回應的內容，將 updatedMember 作為回應的 body
						.build();	// 構建並返回 Response
			}else {
				return Response.ok()
						.entity("member not found")
						.build();
			}
		}catch(Exception e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity("a member error occurerd:" + e.getMessage())
					.build();
		}
	}
	
	@PUT
	@Path("/add/{memberno}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addBalance(@PathParam("memberno") Integer memberno, @QueryParam("amount") Integer amount) {
		MemberDAO memberDAO = new MemberDAO();
		try {
            // 验证输入参数
            if (amount == null || amount <= 0) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("Invalid amount. Amount should be greater than 0.")
                        .build();
            }

            Member member = memberDAO.getMembersByNo(memberno);
            member.addBalance(amount);
            memberDAO.updateBalance(member); // 更新会员余额到数据库

            String message = "加值已成功 by controller";

            // 返回包含訊息的 Response
            return Response.ok(message, MediaType.TEXT_PLAIN).build();

        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error occurred: " + e.getMessage())
                    .build();
        }
	}
	
	@PUT
	@Path("/minus/{memberno}")
	public Response minusBalance(@PathParam("memberno") Integer memberno, @QueryParam("consume") Integer consume) {
		MemberDAO dao = new MemberDAO();
		try {
            // 验证输入参数
            if (consume == null || consume <= 0) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("Invalid amount. Amount should be greater than 0.")
                        .build();
            }

            Member member = dao.getMembersByNo(memberno);
            member.minusBalance(consume);
            dao.updateBalance(member); // 更新会员余额到数据库

            String message = "點數扣款成功 by controller";

            // 返回包含訊息的 Response
            return Response.ok(message, MediaType.TEXT_PLAIN).build();

        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error occurred: " + e.getMessage())
                    .build();
        }
	}
	@GET
	@Path("/getBalance/{memberno}")
	public Response getBalance(@PathParam("memberno") Integer memberno) {
		MemberDAO dao = new MemberDAO();
		Member m = dao.getMembersByNo(memberno);
				
//		String message = "點數餘額:"+m.getBalance();
		return Response.ok(m.getBalance(), MediaType.TEXT_PLAIN).build();
		
	}
	
}
