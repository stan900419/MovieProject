package dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import model.Employees;

public class EmployeeDAO {
	public EntityManager create() {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("movieproject_back-end-new");
		return factory.createEntityManager();
	}

	public List<Employees> getAll() {
		EntityManager mgr = create();
		TypedQuery<Employees> query = mgr.createNamedQuery("Employees.findAll", Employees.class);
		return query.getResultList();
	}

	public Employees canlogin(String username, String password) {
		Employees passedEmployee = null;
		List<Employees> employees = getAll();
		for (int i = 0; i < employees.size(); i++) {
			if (username.equals(employees.get(i).getEmployeeUsername()) == true) {
				if (password.equals(employees.get(i).getEmployeePassword()) == true) {
					passedEmployee = employees.get(i);
				}
			}
		}
		return passedEmployee;
	}

	public void Register(Employees employee) {
		EntityManager mgr = create();
		EntityTransaction transaction = mgr.getTransaction();
		try {
			transaction.begin();
			mgr.persist(employee);
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

	public Employees getEmployeesById(Integer employeeId) {
		EntityManager em = create();
		try {
			return em.find(Employees.class, employeeId);
		} catch (Exception e) {
			System.out.println("getEmployeesById error:" + e.getMessage());
		} finally {
			em.close();
		}
		return null;
	}

	public Employees getEmployeesByUsername(String username) {
		EntityManager em = create();
		Employees employee = null; // 儲存查詢結果
		try {
			employee = em.createQuery("SELECT e FROM Employees e WHERE e.employeeUsername = :username", Employees.class)
					.setParameter("username", username).getSingleResult();
		} catch (NoResultException e) {
			System.out.println("No employee found with username: " + username);
		} catch (Exception e) {
			System.out.println("error:" + e.getMessage());
		} finally {
			em.close();
		}
		return employee;
	}

	// 更新會員資料:帳號、性別、辦帳號日期不給更新
	public void updateEmployees(Integer employeeId, Employees newEmployee) {
		EntityManager em = create();
		EntityTransaction transaction = em.getTransaction();
		try {
			transaction.begin();
			Employees existingEmployee = em.find(Employees.class, employeeId);
			if (existingEmployee != null) {
				// 更新會員資料
				if (newEmployee.getEmployeeName() != null && !newEmployee.getEmployeeName().isEmpty()) {
					existingEmployee.setEmployeeName(newEmployee.getEmployeeName());
				}
				if (newEmployee.getEmployeePassword() != null && !newEmployee.getEmployeePassword().isEmpty()) {
					existingEmployee.setEmployeePassword(newEmployee.getEmployeePassword());
				}
				if (newEmployee.getEmployeeLevel() != null && !newEmployee.getEmployeeLevel().isEmpty()) {
					existingEmployee.setEmployeeLevel(newEmployee.getEmployeeLevel());
				}
				if (newEmployee.getEmployeePhone() != null && !newEmployee.getEmployeePhone().isEmpty()) {
					existingEmployee.setEmployeePhone(newEmployee.getEmployeePhone());
				}
				if (newEmployee.getEmployeeEmail() != null && !newEmployee.getEmployeeEmail().isEmpty()) {
					existingEmployee.setEmployeeEmail(newEmployee.getEmployeeEmail());
				}
				if (newEmployee.getEmployeeStatus() != null && !newEmployee.getEmployeeStatus().isEmpty()) {
					existingEmployee.setEmployeeStatus(newEmployee.getEmployeeStatus());
				}

				transaction.commit();
				System.out.println("Employee with Id: " + employeeId + " updated successfully.");
			} else {
				System.out.println("Employee with Id: " + employeeId + " not found");

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
}
