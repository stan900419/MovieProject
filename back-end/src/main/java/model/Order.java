package model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the orders database table.
 * 
 */
@Entity
@Table(name="orders")
@NamedQuery(name="Order.findAll", query="SELECT o FROM Order o")
@NamedQuery(name = "Order.findByName", query = "SELECT d FROM Order d WHERE d.userName = :userName")
public class Order implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int orderId;

	private String cinema;

	private String cinemaRoom;

	private String eatName;

	private int eatPrice;

	private String movieName;

	private int moviePrice;

	private String movieTime;

	private String movieType;

	private String seat;

	private String session;

	private String ticketType;

	private int totalPrice;

	private String userName;

	

	public Order() {
	}

	public int getOrderId() {
		return this.orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public String getCinema() {
		return this.cinema;
	}

	public void setCinema(String cinema) {
		this.cinema = cinema;
	}

	public String getCinemaRoom() {
		return this.cinemaRoom;
	}

	public void setCinemaRoom(String cinemaRoom) {
		this.cinemaRoom = cinemaRoom;
	}

	public String getEatName() {
		return this.eatName;
	}

	public void setEatName(String eatName) {
		this.eatName = eatName;
	}

	public int getEatPrice() {
		return this.eatPrice;
	}

	public void setEatPrice(int eatPrice) {
		this.eatPrice = eatPrice;
	}

	public String getMovieName() {
		return this.movieName;
	}

	public void setMovieName(String movieName) {
		this.movieName = movieName;
	}

	public int getMoviePrice() {
		return this.moviePrice;
	}

	public void setMoviePrice(int moviePrice) {
		this.moviePrice = moviePrice;
	}

	public String getMovieTime() {
		return this.movieTime;
	}

	public void setMovieTime(String movieTime) {
		this.movieTime = movieTime;
	}

	public String getMovieType() {
		return this.movieType;
	}

	public void setMovieType(String movieType) {
		this.movieType = movieType;
	}

	public String getSeat() {
		return this.seat;
	}

	public void setSeat(String seat) {
		this.seat = seat;
	}

	public String getSession() {
		return this.session;
	}

	public void setSession(String session) {
		this.session = session;
	}

	public String getTicketType() {
		return this.ticketType;
	}

	public void setTicketType(String ticketType) {
		this.ticketType = ticketType;
	}

	public int getTotalPrice() {
		return this.totalPrice;
	}

	public void setTotalPrice(int totalPrice) {
		this.totalPrice = totalPrice;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setEatItem(Eatmenu eatItem) {
		// TODO Auto-generated method stub
		this.eatName = eatItem.getItemName();
		this.eatPrice = Integer.parseInt(eatItem.getItemPrice());
		
	}

	public void appendEatItem(Eatmenu eatItem) {
		// TODO Auto-generated method stub
		this.eatName += (","+eatItem.getItemName());
		this.eatPrice += Integer.parseInt(eatItem.getItemPrice());
	}

	public void eatSetToNone(Eatmenu eatItem) {
		// TODO Auto-generated method stub
		this.eatName = null;
		this.eatPrice = 0;
		
	}

	public int calTotalPrice() {
		this.totalPrice = this.getMoviePrice()+this.getEatPrice();
		return this.totalPrice;
	}
	@Override
	public String toString() {
	    return "Order [OrderId=" + getOrderId() + 
	           ", UserName=" + getUserName() + 
	           ", TicketType=" + getTicketType() + 
	           ", MoviePrice=" + getMoviePrice() + 
	           ", EatItem=" + getEatName() + 
	           ", TotalPrice=" + getTotalPrice() + "]";
	}

}