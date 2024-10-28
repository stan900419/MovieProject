package model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the movierank database table.
 * 
 */
@Entity
@NamedQuery(name="Movierank.findAll", query="SELECT m FROM Movierank m")
public class Movierank implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String moviename;

	private int count;

	public Movierank() {
	}

	public Movierank(String moviename, int count) {
		super();
		this.moviename = moviename;
		this.count = count;
	}

	public String getMoviename() {
		return this.moviename;
	}

	public void setMoviename(String moviename) {
		this.moviename = moviename;
	}

	public int getCount() {
		return this.count;
	}

	public void setCount(int count) {
		this.count = count;
	}

}