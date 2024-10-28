package model;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Time;
import java.util.Date;


/**
 * The persistent class for the room database table.
 * 
 */
@Entity
@NamedQuery(name="Room.findAll", query="SELECT r FROM Room r")
public class Room implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	private String movieid;

	@Temporal(TemporalType.DATE)
	private Date playdate;

	private Time playtime;

	private String roommovie;

	private String roomname;

	private String roomusing;

	private String session;
	
	private int seats;
	
	public Room() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMovieid() {
		return this.movieid;
	}

	public void setMovieid(String movieid) {
		this.movieid = movieid;
	}

	public Date getPlaydate() {
		return this.playdate;
	}

	public void setPlaydate(Date playdate) {
		this.playdate = playdate;
	}

	public Time getPlaytime() {
		return this.playtime;
	}

	public void setPlaytime(Time playtime) {
		this.playtime = playtime;
	}

	public String getRoommovie() {
		return this.roommovie;
	}

	public void setRoommovie(String roommovie) {
		this.roommovie = roommovie;
	}

	public String getRoomname() {
		return this.roomname;
	}

	public void setRoomname(String roomname) {
		this.roomname = roomname;
	}

	public String getRoomusing() {
		return this.roomusing;
	}

	public void setRoomusing(String roomusing) {
		this.roomusing = roomusing;
	}

	public String getSession() {
		return this.session;
	}

	public void setSession(String session) {
		this.session = session;
	}
	
	

	public int getSeats() {
		return seats;
	}

	public void setSeats(int seats) {
		this.seats = seats;
	}

	@Override
	public String toString() {
		return "Room [id=" + id + ", movieid=" + movieid + ", playdate=" + playdate + ", playtime=" + playtime
				+ ", roommovie=" + roommovie + ", roomname=" + roomname + ", roomusing=" + roomusing + ", session="
				+ session +", seats=" + seats + "]";
	}

}