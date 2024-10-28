package dao;

import javax.persistence.*;

import model.Seat;

import java.util.ArrayList;
import java.util.List;

public class SeatDAO {
	private EntityManagerFactory emf;

	public SeatDAO() {
		emf = Persistence.createEntityManagerFactory("movieproject_back-end-new");
	}

	public boolean addSeat(Seat seat) {
		EntityManager em = emf.createEntityManager();
		try {
			em.getTransaction().begin();
			em.persist(seat);
			em.getTransaction().commit();
			return true;
		} catch (Exception e) {
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
			System.out.println("Error inserting seat: " + e.getMessage());
			return false;
		} finally {
			em.close();
		}
	}

	// 检查座位是否已存在的同时，检查场次编号
	public boolean seatExists(String moviesession, String seatid) {
		EntityManager em = emf.createEntityManager();
		try {
			String jpql = "SELECT COUNT(s) FROM Seat s WHERE s.moviesession = :moviesession AND s.seatid = :seatid";
			Long count = em.createQuery(jpql, Long.class).setParameter("moviesession", moviesession)
					.setParameter("seatid", seatid).getSingleResult();
			return count > 0; // 只要存在同一场次的座位，返回 true
		} catch (NoResultException e) {
			return false; // 没有结果则返回 false
		} catch (Exception e) {
			System.out.println("Error checking seat existence: " + e.getMessage());
			return false;
		} finally {
			em.close();
		}
	}

	// 根据场次与座位编号取得座位信息
	public Seat getSeatBySessionAndSeatId(String moviesession, String seatid) {
		EntityManager em = emf.createEntityManager();
		Seat seat = null;
		try {
			String jpql = "SELECT s FROM Seat s WHERE s.moviesession = :moviesession AND s.seatid = :seatid";
			seat = em.createQuery(jpql, Seat.class).setParameter("moviesession", moviesession)
					.setParameter("seatid", seatid).getSingleResult();
		} catch (NoResultException e) {
			System.out.println("No seat found for session: " + moviesession + " and seat ID: " + seatid);
		} catch (Exception e) {
			System.out.println("Error retrieving seat: " + e.getMessage());
		} finally {
			em.close();
		}
		return seat;
	}

	// 更新座位
	public boolean updateSeat(Seat seat) {
		EntityManager em = emf.createEntityManager();
		try {
			em.getTransaction().begin();
			em.merge(seat); // 使用merge来更新
			em.getTransaction().commit();
			return true;
		} catch (Exception e) {
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
			System.out.println("Error updating seat: " + e.getMessage());
			return false;
		} finally {
			em.close();
		}
	}

	// 根据场次获取所有座位
	public List<Seat> getSeatsBySession(String moviesession) {
		EntityManager em = emf.createEntityManager();
		List<Seat> seats = new ArrayList<>();
		try {
			String jpql = "SELECT s FROM Seat s WHERE s.moviesession = :moviesession";
			seats = em.createQuery(jpql, Seat.class).setParameter("moviesession", moviesession).getResultList();
		} catch (Exception e) {
			System.out.println("Error retrieving seats by session: " + e.getMessage());
		} finally {
			em.close();
		}
		return seats;
	}

	// 删除座位
	public boolean deleteSeat(int id) {
		EntityManager em = emf.createEntityManager();
		try {
			em.getTransaction().begin();
			Seat seat = em.find(Seat.class, id);
			if (seat != null) {
				em.remove(seat);
				em.getTransaction().commit();
				return true;
			}
		} catch (Exception e) {
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
			System.out.println("Error deleting seat: " + e.getMessage());
			return false;
		} finally {
			em.close();
		}
		return false;
	}

	// 新增：根據場次刪除所有座位
	public boolean deleteSeatsBySession(String moviesession) {
		EntityManager em = emf.createEntityManager();
		try {
			em.getTransaction().begin();
			Query query = em.createQuery("DELETE FROM Seat s WHERE s.moviesession = :moviesession");
			query.setParameter("moviesession", moviesession);
			int deletedCount = query.executeUpdate(); // 執行刪除操作
			em.getTransaction().commit();
			return deletedCount > 0; // 如果有刪除的記錄，返回 true
		} catch (Exception e) {
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
			System.out.println("Error deleting seats by session: " + e.getMessage());
			return false;
		} finally {
			em.close();
		}
	}

	public boolean emplUpdateSeat(int id, String seatid, String seatstate, String memberno) {
		EntityManager em = emf.createEntityManager();
		try {
			em.getTransaction().begin();
			String sql = "UPDATE movieproject.seat SET seatid = ?, seatstate = ?, memberno = ? WHERE id = ?";
			Query query = em.createNativeQuery(sql);
			query.setParameter(1, seatid);
			query.setParameter(2, seatstate);
			query.setParameter(3, memberno);
			query.setParameter(4, id);

			int updatedCount = query.executeUpdate();
			em.getTransaction().commit();
			return updatedCount > 0;
		} catch (Exception e) {
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
			System.out.println("Error updating seat: " + e.getMessage());
			e.printStackTrace();
			return false;
		} finally {
			em.close();
		}
	}

}
