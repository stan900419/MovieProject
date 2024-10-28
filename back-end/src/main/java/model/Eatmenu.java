package model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the eatmenu database table.
 * 
 */
@Entity
@NamedQuery(name="Eatmenu.findAll", query="SELECT e FROM Eatmenu e")
@NamedQuery(name = "Eatmenu.findByName", query = "SELECT d FROM Eatmenu d WHERE d.itemName = :itemName")
public class Eatmenu implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String itemName;

	private String itemDescription;

	private String itemPrice;

	public Eatmenu() {
	}

	public Eatmenu(String itemName, String itemDescription, String itemPrice) {
		super();
		this.itemName = itemName;
		this.itemDescription = itemDescription;
		this.itemPrice = itemPrice;
	}

	public String getItemName() {
		return this.itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getItemDescription() {
		return this.itemDescription;
	}

	public void setItemDescription(String itemDescription) {
		this.itemDescription = itemDescription;
	}

	public String getItemPrice() {
		return this.itemPrice;
	}

	public void setItemPrice(String itemPrice) {
		this.itemPrice = itemPrice;
	}

}