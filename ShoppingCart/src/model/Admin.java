package model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the "ADMIN" database table.
 * 
 */
@Entity
@Table(name="ADMIN", schema="TESTDB")
@NamedQuery(name="Admin.findAll", query="SELECT a FROM Admin a")
public class Admin implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="USER_ID")
	private long userId;

	public Admin() {
	}

	public long getUserId() {
		return this.userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

}