package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import model.Order;
import model.OrderSummary;

public class OrderDAO {
	EntityManager create() {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("movieproject_back-end-new");
		return factory.createEntityManager();
	}

	public int insertTicket(Order order) {
		EntityManager mgr = create();
		mgr.getTransaction().begin();
		Order tempOrder = mgr.merge(order);
		mgr.getTransaction().commit();
		System.out.println("insertTicket orderId:" + tempOrder.getOrderId());
		return tempOrder.getOrderId();
	}

	public void updateOrder(Order order) {
		EntityManager mgr = create();
		Order tempOrder = mgr.find(Order.class, order.getOrderId());
		if (tempOrder != null) {
			mgr.getTransaction().begin();
			Order tempOrder_updated = mgr.merge(order);
			mgr.getTransaction().commit();
			System.out.println("updateByid tempOrder_updated:" + tempOrder_updated);
		}

	}

	public List<Order> getAll() {
		EntityManager mgr = create();
		TypedQuery<Order> query = mgr.createNamedQuery("Order.findAll", Order.class);
		return query.getResultList();

	}

//	public Order getOrderFromUserName(String userName) {
//		EntityManager mgr = create();
//		Order tempOrder = mgr.find(Order.class, userName);
//		return tempOrder;
//		
//	}
	public int getOrderIdsByName(String name) {

		String jpql = "SELECT orderId FROM Order o WHERE o.name = :name";
		TypedQuery<Order> query = create().createQuery(jpql, Order.class);
		query.setParameter("name", name);

		List<Order> orderIds = query.getResultList();
		return orderIds.get(orderIds.size() - 1).getOrderId();
	}

	public Order getOrderByName(String userName) {

		/*
		 * String jpql = "SELECT o FROM Order o WHERE o.userName = :userName";
		 * TypedQuery<Order> query = create().createQuery(jpql, Order.class);
		 * query.setParameter("userName", userName);
		 * 
		 * List<Order> orderIds = query.getResultList(); return
		 * orderIds.get(orderIds.size()-1);
		 */
		EntityManager mgr = create();
		TypedQuery<Order> query = mgr.createNamedQuery("Order.findByName", Order.class);
		query.setParameter("userName", userName);
		System.out.println("userName" + userName);
		List<Order> orders = query.getResultList();
		return orders.get(orders.size() - 1);

	}

	public void deleteById(int orderId) {
		EntityManager mgr = create();
		Order order = mgr.find(Order.class, orderId);
		if (order != null) {
			mgr.getTransaction().begin();
			mgr.remove(order);
			mgr.getTransaction().commit();
		} else {
			System.out.println("delete id not found:" + orderId);
		}
	}

	public void updateById(int orderId, Order order) {
		EntityManager mgr = create();
		Order tempOrder = mgr.find(Order.class, orderId);
		if (tempOrder != null) {
			mgr.getTransaction().begin();
			mgr.merge(order);
			mgr.getTransaction().commit();
		} else {
			System.out.println("delete id not found:" + orderId);
		}

	}

	public void updateSeatById(int orderId, String seatId) {
		EntityManager mgr = create();
		Order tempOrder = mgr.find(Order.class, orderId);
		tempOrder.setSeat(seatId);
		System.out.println("order set seatId:" + seatId);
		if (tempOrder != null) {
			mgr.getTransaction().begin();
			mgr.merge(tempOrder);
			mgr.getTransaction().commit();
		} else {
			System.out.println("delete id not found:" + orderId);
		}

	}

	public OrderSummary getLatestOrdersSummaryByUserNameAndEmptySeat(String userName, int limit) {
		EntityManager mgr = create();
		List<Order> orders = new ArrayList<>();

		try {
			// 使用 JPQL 查詢語句，按 orderId 逆序排列並設置限制筆數
			String jpql = "SELECT o FROM Order o WHERE o.userName = :userName AND o.seat IS NULL ORDER BY o.orderId DESC";
			TypedQuery<Order> query = mgr.createQuery(jpql, Order.class);
			query.setParameter("userName", userName);
			query.setMaxResults(limit); // 設置最大結果數

			orders = query.getResultList(); // 獲取結果列表
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 確保 EntityManager 被關閉
			if (mgr != null && mgr.isOpen()) {
				mgr.close();
			}
		}

		// 返回DTO，包含userName、session和訂單數量
		return new OrderSummary(userName, "your_session_value", orders.size());
	}

	public String getLatestUserName() {
		EntityManager mgr = create();
		String userName = null;

		try {
			// 查询最新的订单
			String jpql = "SELECT o.userName FROM Order o ORDER BY o.orderId DESC";
			TypedQuery<String> query = mgr.createQuery(jpql, String.class);
			query.setMaxResults(1); // 只获取最新一笔

			List<String> result = query.getResultList();
			if (!result.isEmpty()) {
				userName = result.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (mgr != null && mgr.isOpen()) {
				mgr.close();
			}
		}

		return userName;
	}

	public List<Order> getLatestOrdersByUserName(String userName, int limit) {
		EntityManager mgr = create();
		List<Order> orders = new ArrayList<>();

		try {
			String jpql = "SELECT o FROM Order o WHERE o.userName = :userName ORDER BY o.orderId DESC";
			TypedQuery<Order> query = mgr.createQuery(jpql, Order.class);
			query.setParameter("userName", userName);
			query.setMaxResults(limit); // 设置最大结果数

			orders = query.getResultList(); // 获取结果列表
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (mgr != null && mgr.isOpen()) {
				mgr.close();
			}
		}

		return orders;
	}

	public List<Order> getOrderListByName(String customerName) {
		EntityManager mgr = create();
		TypedQuery<Order> query = mgr.createNamedQuery("Order.findByName", Order.class);
		query.setParameter("userName", customerName);
		System.out.println("userName" + customerName);
		List<Order> orders = query.getResultList();
		return orders;
	}

	//確保是真正訂單by Stan
	public List<Order> getOrdersByUserName(String userName) {
		EntityManager mgr = create();
		String jpql = "SELECT o FROM Order o WHERE o.userName = :userName "
				+ "AND o.seat IS NOT NULL "
				+ "AND o.totalPrice IS NOT NULL "
				+ "ORDER BY o.orderId DESC";
		TypedQuery<Order> query = mgr.createQuery(jpql, Order.class);
		query.setParameter("userName", userName);
		// 抓末6筆data
		query.setMaxResults(6);
		List<Order> orders = query.getResultList();
		return orders;
	}

}