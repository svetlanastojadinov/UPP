package root.demo.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

@Entity
public class Article implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(nullable = false, unique = true)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column
	private String title;

	@Column
	private String author;

	@Column
	private String keyWords;

	@Column
	private String abst;

	@Column
	private String filePath;

	@Column
	private ScientificArea scArea;

	@Column
	private ArticleStatus status; 

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "article_id")
	private Set<User> recezenti;
	
	@Column
	private String chiefEditor;
	
	public Article() {
		super();
		this.status = ArticleStatus.PREGLEDA_SE;
		// TODO Auto-generated constructor stub
	}

	public Article(long id, String title, String author) {
		super();
		this.id = id;
		this.title = title;
		this.author = author;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getKeyWords() {
		return keyWords;
	}

	public void setKeyWords(String keyWords) {
		this.keyWords = keyWords;
	}

	public String getAbst() {
		return abst;
	}

	public void setAbst(String abst) {
		this.abst = abst;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public ScientificArea getScArea() {
		return scArea;
	}

	public void setScArea(ScientificArea scArea) {
		this.scArea = scArea;
	}

	public ArticleStatus getStatus() {
		return status;
	}

	public void setStatus(ArticleStatus status) {
		this.status = status;
	}
	
	public Set<User> getRecezenti() {
		return recezenti;
	}
	

	public String getChiefEditor() {
		return chiefEditor;
	}

	public void setChiefEditor(String chiefEditor) {
		this.chiefEditor = chiefEditor;
	}

	public void setRecezenti(Set<User> recezenti) {
		this.recezenti = recezenti;
	}

}