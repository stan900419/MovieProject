package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the movies database table.
 * 
 */
@Entity
@Table(name="movies")
@NamedQuery(name="Movie.findAll", query="SELECT m FROM Movie m")
public class Movie implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int movieid;

	private String cinemaRoom;

	private String moviecast;

	private String moviedirector;

	@Temporal(TemporalType.DATE)
	private Date movieenddate;

	private String moviegrading;

	private String movieintro;

	private String moviekind;

	private String movieName;

	@Temporal(TemporalType.DATE)
	private Date moviestartdate;

	private String movieType;

	private int runtime;

	public Movie() {
	}

	public int getMovieid() {
		return this.movieid;
	}

	public void setMovieid(int movieid) {
		this.movieid = movieid;
	}

	public String getCinemaRoom() {
		return this.cinemaRoom;
	}

	public void setCinemaRoom(String cinemaRoom) {
		this.cinemaRoom = cinemaRoom;
	}

	public String getMoviecast() {
		return this.moviecast;
	}

	public void setMoviecast(String moviecast) {
		this.moviecast = moviecast;
	}

	public String getMoviedirector() {
		return this.moviedirector;
	}

	public void setMoviedirector(String moviedirector) {
		this.moviedirector = moviedirector;
	}

	public Date getMovieenddate() {
		return this.movieenddate;
	}

	public void setMovieenddate(Date movieenddate) {
		this.movieenddate = movieenddate;
	}

	public String getMoviegrading() {
		return this.moviegrading;
	}

	public void setMoviegrading(String moviegrading) {
		this.moviegrading = moviegrading;
	}

	public String getMovieintro() {
		return this.movieintro;
	}

	public void setMovieintro(String movieintro) {
		this.movieintro = movieintro;
	}

	public String getMoviekind() {
		return this.moviekind;
	}

	public void setMoviekind(String moviekind) {
		this.moviekind = moviekind;
	}

	public String getMovieName() {
		return this.movieName;
	}

	public void setMovieName(String movieName) {
		this.movieName = movieName;
	}

	public Date getMoviestartdate() {
		return this.moviestartdate;
	}

	public void setMoviestartdate(Date moviestartdate) {
		this.moviestartdate = moviestartdate;
	}

	public String getMovieType() {
		return this.movieType;
	}

	public void setMovieType(String movieType) {
		this.movieType = movieType;
	}

	public int getRuntime() {
		return this.runtime;
	}

	public void setRuntime(int runtime) {
		this.runtime = runtime;
	}

	@Override
	public String toString() {
		return "Movie [movieid=" + movieid + ", cinemaRoom=" + cinemaRoom + ", moviecast=" + moviecast
				+ ", moviedirector=" + moviedirector + ", movieenddate=" + movieenddate + ", moviegrading="
				+ moviegrading + ", movieintro=" + movieintro + ", moviekind=" + moviekind + ", movieName=" + movieName
				+ ", moviestartdate=" + moviestartdate + ", movieType=" + movieType + ", runtime=" + runtime + "]";
	}

}