package model;

import java.io.Serializable;
import javax.persistence.*;


@Entity
@Table(name="employees")
@NamedQuery(name="Employees.findAll", query="SELECT e FROM Employees e")
public class Employees implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer employeeId;
	private String employeeName;
	private String employeeUsername;
	private String employeePassword;
	private String employeeLevel;	// 主管職/一般員工
	private String employeePhone;
	private String employeeEmail;
	private String employeeStatus;	// 在職中/已離職

	public Employees() {	}

	public Employees(String employeeName, String employeeUsername, String employeePassword, String employeeLevel,
			String employeePhone, String employeeEmail, String employeeStatus) {
		super();
		this.employeeName = employeeName;
		this.employeeUsername = employeeUsername;
		this.employeePassword = employeePassword;
		this.employeeLevel = employeeLevel;
		this.employeePhone = employeePhone;
		this.employeeEmail = employeeEmail;
		this.employeeStatus = employeeStatus;
	}

	public String getEmployeeStatus() {
		return employeeStatus;
	}

	public void setEmployeeStatus(String employeeStatus) {
		this.employeeStatus = employeeStatus;
	}

	public Integer getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Integer employeeId) {
		this.employeeId = employeeId;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public String getEmployeeUsername() {
		return employeeUsername;
	}

	public void setEmployeeUsername(String employeeUsername) {
		this.employeeUsername = employeeUsername;
	}

	public String getEmployeePassword() {
		return employeePassword;
	}

	public void setEmployeePassword(String employeePassword) {
		this.employeePassword = employeePassword;
	}

	public String getEmployeeLevel() {
		return employeeLevel;
	}

	public void setEmployeeLevel(String employeeLevel) {
		this.employeeLevel = employeeLevel;
	}

	public String getEmployeePhone() {
		return employeePhone;
	}

	public void setEmployeePhone(String employeePhone) {
		this.employeePhone = employeePhone;
	}

	public String getEmployeeEmail() {
		return employeeEmail;
	}

	public void setEmployeeEmail(String employeeEmail) {
		this.employeeEmail = employeeEmail;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
}