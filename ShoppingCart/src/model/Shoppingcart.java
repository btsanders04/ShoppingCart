package model;

import java.io.Serializable;

import javax.persistence.*;

import java.math.BigDecimal;


/**
 * The persistent class for the SHOPPINGCART database table.
 * 
 */
@Entity
@Table(name="SHOPPINGCART" , schema="TESTDB")
@NamedQuery(name="Shoppingcart.findAll", query="SELECT s FROM Shoppingcart s")
public class Shoppingcart implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="SHOPPINGCART_ID_GENERATOR", sequenceName="SEQ_SHOP", allocationSize = 1, initialValue = 20, schema="TESTDB")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SHOPPINGCART_ID_GENERATOR")
	@Column(name="ID")
	private long id;

	@Column(name="AMOUNT")
	private int amount;

	@Column(name="PRODUCT_ID")
	private long productId;

	@Column(name="USER_ID")
	private long userId;

	public Shoppingcart() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getAmount() {
		return this.amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public long getProductId() {
		return this.productId;
	}

	public void setProductId(long productId) {
		this.productId = productId;
	}

	public long getUserId() {
		return this.userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

}