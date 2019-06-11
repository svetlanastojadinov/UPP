package root.demo.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class ScientificArea implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(nullable = false, unique = true)
	private String id;

	@Column
	private String name;

	@Column
	private String year;

	@Column
	private String urednik;

	public ScientificArea() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getUrednik() {
		return urednik;
	}

	public void setUrednik(String urednik) {
		this.urednik = urednik;
	}

}
