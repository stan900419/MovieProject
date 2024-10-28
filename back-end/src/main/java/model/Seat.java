package model;

import javax.persistence.*;

@Entity 
@Table(name = "seat") 
public class Seat {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private int id; 
    
    @Column(name = "moviesession") 
    private String moviesession;
    
    @Column(name = "seatid")
    private String seatid;
    
    @Column(name = "seatstate")
    private String seatstate;
    
    @Column(name = "memberno")
    private String memberno;
    
    @Column(name = "orderid")
    private String orderId;

    public Seat() {}

    public Seat(int id, String moviesession, String seatid, String seatstate, String memberno, String orderid) {
        this.id = id;
        this.moviesession = moviesession;
        this.seatid = seatid;
        this.seatstate = seatstate;
        this.memberno = memberno;
        this.orderId = orderid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMoviesession() {
        return moviesession;
    }

    public void setMoviesession(String moviesession) {
        this.moviesession = moviesession;
    }

    public String getSeatid() {
        return seatid;
    }

    public void setSeatid(String seatid) {
        this.seatid = seatid;
    }

    public String getSeatstate() {
        return seatstate;
    }

    public void setSeatstate(String seatstate) {
        this.seatstate = seatstate;
    }

    public String getMemberno() {
        return memberno;
    }

    public void setMemberno(String memberno) {
        this.memberno = memberno;
    }

    public String getOrderid() {
        return orderId;
    }

    public void setOrderid(String orderid) {
        this.orderId = orderid;
    }

    @Override
    public String toString() {
        return "Seat [id=" + id + ", moviesession=" + moviesession + ", seatid=" + seatid + ", seatstate=" + seatstate
                + ", memberno=" + memberno + ", orderid=" + orderId + "]";
    }
}
