package dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import model.Member;

public class MemberDAO {
	public EntityManager create() {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("movieproject_back-end-new");
		return factory.createEntityManager();
	}

	public List<Member> getAll() {
		EntityManager mgr = create();
		TypedQuery<Member> query = mgr.createNamedQuery("Member.findAll", Member.class);
		return query.getResultList();
	}

	public List<Member> findByMemberLevel(List<String> membershipLevel) {
		EntityManager em = create();
		try {
			// 使用 JPQL 動態構建查詢
			String queryString = "SELECT m FROM Members m WHERE ";

			// 構建每個條件
			for (int i = 0; i < membershipLevel.size(); i++) {
				queryString += "m.membershipLevel LIKE :Level" + i; // 使用 LIKE 進行部分匹配
				if (i < membershipLevel.size() - 1) {
					queryString += " OR "; // 使用 OR 來連接多個條件
				}
			}

			// 創建查詢
			TypedQuery<Member> query = em.createQuery(queryString, Member.class);

			// 設置每個參數
			for (int i = 0; i < membershipLevel.size(); i++) {
				query.setParameter("Level" + i, "%" + membershipLevel.get(i) + "%"); // 加上 % 進行部分匹配
			}

			return query.getResultList(); // 返回結果
		} catch (Exception e) {
			System.out.println("membershipLevel query error:" + e.getMessage());
		} finally {
			em.close();
		}
		return null;
	}

	public Member canlogin(String username, String password) {
		Member passedUser = null;
		List<Member> members = getAll();
		for (int i = 0; i < members.size(); i++) {
			if (username.equals(members.get(i).getPhone()) == true) {
				if (password.equals(members.get(i).getPassword()) == true) {
					passedUser = members.get(i);
				}
			}
		}
		return passedUser;
	}

	// 添加新會員到数据库
	public void Register(Member member) {
		EntityManager mgr = create();
		EntityTransaction transaction = mgr.getTransaction();
		try {
			transaction.begin();
			mgr.persist(member);
			transaction.commit();
		} catch (Exception ex) {
			if (transaction.isActive()) {
				transaction.rollback(); // 出现错误时回滚事务
			}
			System.out.println("Add Member error: " + ex.getMessage());
		} finally {
			mgr.close();
		}
	}

	// 根据ID获取电影
	public Member getMembersByNo(Integer memberno) {
		EntityManager em = create();
		try {
			return em.find(Member.class, memberno);
		} catch (Exception e) {
			System.out.println("getMembersByNo error:" + e.getMessage());
		} finally {
			em.close();
		}
		return null;
	}

	// 更新會員資料:帳號、性別、辦帳號日期不給更新
	public void updateMembers(Integer memberno, Member newMember) {
		EntityManager em = create();
		EntityTransaction transaction = em.getTransaction();
		try {
			transaction.begin();
			Member existingMember = em.find(Member.class, memberno);
			if (existingMember != null) {
				// 更新會員資料
				if (newMember.getName() != null && !newMember.getName().isEmpty()) {
					existingMember.setName(newMember.getName());
				}
				if (newMember.getPassword() != null && !newMember.getPassword().isEmpty()) {
					existingMember.setPassword(newMember.getPassword());
				}
				if (newMember.getPhone() != null && !newMember.getPhone().isEmpty()) {
					existingMember.setPhone(newMember.getPhone());
				}
				if (newMember.getMembershipLevel() != null && !newMember.getMembershipLevel().isEmpty()) {
					existingMember.setMembershipLevel(newMember.getMembershipLevel());
				}
				if (newMember.getBalance() != null) {
					existingMember.setBalance(newMember.getBalance());
				}

				transaction.commit();
				System.out.println("Member with No: " + memberno + " updated successfully.");
			} else {
				System.out.println("Member with No: " + memberno + " not found");

			}
		} catch (Exception e) {
			if (transaction.isActive()) {
				transaction.rollback();
			}
			e.printStackTrace();
		} finally {
			em.close();
		}
	}

	public void deleteMembers(Integer memberno) {
		EntityManager em = create();
		EntityTransaction transaction = em.getTransaction();
		try {
			transaction.begin();
			Member member = em.find(Member.class, memberno);
			if (member != null) {
				em.remove(member);
				transaction.commit();
				System.out.println("Member with ID: " + memberno + " deleted successfully.");
			} else {
				System.out.println("Member with ID: " + memberno + " not found");
				transaction.rollback();
			}
		} catch (Exception e) {
			if (transaction.isActive()) {
				transaction.rollback();
			}
			e.printStackTrace();
		} finally {
			em.close();
		}
	}

	public void updateBalance(Member member) {
		EntityManager em = create();
		try {
			em.getTransaction().begin();
			em.merge(member); // 更新會員的balance
			em.getTransaction().commit();
		} catch (Exception e) {
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback(); // 回滾
			}
			System.out.println("update Balance error:" + e.getMessage());
		} finally {
			em.close();
		}
	}

}
