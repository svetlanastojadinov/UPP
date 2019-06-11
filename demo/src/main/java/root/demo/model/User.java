package root.demo.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import root.demo.model.role.RoleName;

@Entity
public class User implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public User() {
		this.role = RoleName.KORISNIK;

	}

	public User(String username, String password, String email, String name, String surname) {
		super();
		this.username = username;
		this.password = password;
		this.email = email;
		this.name = name;
		this.surname = surname;
		// this.articles = articles;
		// this.magazines = magazines;
	}

	@Column
	private String city;

	@Column
	private String country;

	@Column(unique = true)
	@Id
	private String username;

	@Column
	private String password;

	@Column(unique = true)
	private String email;

	@Column
	private String name;

	@Column
	private String surname;

	private RoleName role;

	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnore
	@JoinColumn(name = "scientific_area_id", nullable = true)
	private ScientificArea area;

	// created
	// @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
	// private Set<Article> articles = new HashSet<Article>(0);
	//
	// @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
	// private Set<Magazine> magazines = new HashSet<Magazine>(0);
	//
	// // in cart
	//
	// @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch =
	// FetchType.EAGER)
	// @JoinTable(name = "user_article_in_cart", joinColumns = @JoinColumn(name
	// = "user_id"), inverseJoinColumns = @JoinColumn(name = "articles_id"))
	// private Set<Article> articles_in_cart = new HashSet<Article>(0);
	//
	// @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch =
	// FetchType.EAGER)
	// @JoinTable(name = "user_magazine_in_cart", joinColumns = @JoinColumn(name
	// = "user_id"), inverseJoinColumns = @JoinColumn(name = "magazine_id"))
	// private Set<Magazine> magazines_in_cart = new HashSet<Magazine>(0);
	//
	// public Set<Article> getArticles_in_cart() {
	// return articles_in_cart;
	// }
	//
	// public void setArticles_in_cart(Set<Article> articles_in_cart) {
	// this.articles_in_cart = articles_in_cart;
	// }
	//
	// public Set<Magazine> getMagazines_in_cart() {
	// return magazines_in_cart;
	// }
	//
	// public void setMagazines_in_cart(Set<Magazine> magazines_in_cart) {
	// this.magazines_in_cart = magazines_in_cart;
	// }
	//
	// public Set<Article> getArticles() {
	// return articles;
	// }
	//
	// public void setArticles(Set<Article> articles) {
	// this.articles = articles;
	// }
	//
	// public Set<Magazine> getMagazines() {
	// return magazines;
	// }
	//
	// public void setMagazines(Set<Magazine> magazines) {
	// this.magazines = magazines;
	// }

	public ScientificArea getArea() {
		return area;
	}

	public void setArea(ScientificArea area) {
		this.area = area;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public RoleName getRole() {
		return role;
	}

	public void setRole(RoleName role) {
		this.role = role;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

}
