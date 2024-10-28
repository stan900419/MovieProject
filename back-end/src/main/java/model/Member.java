package model;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the members database table.
 * 
 */
@Entity
@Table(name="members")
@NamedQuery(name="Member.findAll", query="SELECT m FROM Member m")
public class Member implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int memberno;

	private Integer balance;

	@Column(name="create_date")
	private Timestamp createDate;

	private String gender;

	@Column(name="membership_level")
	private String membershipLevel;

	private String name;

	private String password;

	private String phone;

	private String role;

	private String username;
	
	private String email;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Member() {
	}

	public int getMemberno() {
		return this.memberno;
	}

	public void setMemberno(int memberno) {
		this.memberno = memberno;
	}

	public Integer getBalance() {
		return this.balance;
	}

	public void setBalance(Integer balance) {
		this.balance = balance;
	}

	public Timestamp getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}

	public String getGender() {
		return this.gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getMembershipLevel() {
		return this.membershipLevel;
	}

	public void setMembershipLevel(String membershipLevel) {
		this.membershipLevel = membershipLevel;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getRole() {
		return this.role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	public void addBalance(Integer amount) {
		if (amount != null && amount > 0) {
			this.balance += amount;
        } else {
            throw new IllegalArgumentException("Invalid amount to add");
        }
    }
	
	public void minusBalance(Integer consume) {
		if(consume!=null && consume>0) {
			this.balance -= consume;
		}else {
			throw new IllegalArgumentException("Invalid amount to add");
		}
	}

    public void subtractBalance(Integer amount) {
        if(amount != null && amount > 0) {
        	if (this.balance >= amount) {
	            this.balance -= amount;
	        } else {
	            throw new IllegalArgumentException("餘額不足");
	        }
        } else {
        	throw new IllegalArgumentException("扣款金額錯誤，應>0");
        }
	    	
    }

}