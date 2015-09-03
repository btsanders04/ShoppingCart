package model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the CREDIT_INFO database table.
 * 
 */
@Entity
@Table(name="CREDIT_INFO", schema = "TestDb")
@NamedQuery(name="CreditInfo.findAll", query="SELECT c FROM CreditInfo c")
public class CreditInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="USER_ID")
	private long userId;

	@Column(name="BILL_ADD")
	private String billAdd;

	private int card;

	private int secure;

	@Column(name="SHIP_ADD")
	private String shipAdd;

	public CreditInfo() {
	}

	public long getUserId() {
		return this.userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getBillAdd() {
		return this.billAdd;
	}

	public void setBillAdd(String billAdd) {
		this.billAdd = billAdd;
	}

	public int getCard() {
		return this.card;
	}

	public void setCard(int card) {
		this.card = card;
	}

	public int getSecure() {
		return this.secure;
	}

	public void setSecure(int secure) {
		this.secure = secure;
	}

	public String getShipAdd() {
		return this.shipAdd;
	}

	public void setShipAdd(String shipAdd) {
		this.shipAdd = shipAdd;
	}

}