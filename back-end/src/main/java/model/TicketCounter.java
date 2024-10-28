package model;

public class TicketCounter {
	String userName;
	int seatNum;
	public TicketCounter(String userName,int seatNum){
		this.userName = userName;
		this.seatNum = seatNum;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public int getSeatNum() {
		return seatNum;
	}
	public void setSeatNum(int seatNum) {
		this.seatNum = seatNum;
	}
	

}
