package root.demo.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Membership implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	@Id
	@Column(nullable = false, unique = true)
	private Long id;
	
	@Column(nullable=false)
	private boolean payed;
	
	@DateTimeFormat(pattern="dd-MM-yyyy")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable=false, columnDefinition="DATETIME")
	private Date payDay;
	
	@OneToOne(cascade = CascadeType.ALL, mappedBy = "membership",fetch = FetchType.EAGER)
	@JsonIgnore
	private Magazine magazine;
	
	public Membership(){}
	

	public Membership(Long id, boolean payed, Date payDay, Magazine magazine) {
		super();
		this.id = id;
		this.payed = payed;
		this.payDay = payDay;
		this.magazine = magazine;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}




	public Magazine getMagazine() {
		return magazine;
	}


	public void setMagazine(Magazine magazine) {
		this.magazine = magazine;
	}


	public boolean isPayed() {
		return payed;
	}

	public void setPayed(boolean payed) {
		this.payed = payed;
	}



	public Date getPayDay() {
		return payDay;
	}

	public void setPayDay(Date payDay) {
		this.payDay = payDay;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	@Override
	public String toString() {
		return "Membership [id=" + id + ", payed=" + payed + ", payDay=" + payDay + ", magazine=" + magazine + "]";
	}

	
	
	
}
