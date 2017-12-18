package fr.dta.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import fr.dta.constraintes.ISBN;

@Entity
@Table(name = "book")
public class Book {
	
	//@NotNull
	@ISBN
	private String isbn;
	@Column
	private String title;
	@Column
	private String author;
	@Id
	@NotNull
	@Column(name="id")
	private int id;
	@Column(name="nb_pages")
	private int nbPages;
	@Column(name="publication_date") 
	@Temporal(TemporalType.DATE)
	private Date publicationDate;
	
	
	
	
	public int getId() {
		return id;
	}
	public String getIsbn() {
		return isbn;
	}
	public String getTitle() {
		return title;
	}
	public String getAuthor() {
		return author;
	}
	public int getNbPages() {
		return nbPages;
	}
	public Date getPublicationDate() {
		return publicationDate;
	}
	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setNbPages(int nbPages) {
		this.nbPages = nbPages;
	}
	public void setPublicationDate(Date publicationDate) {
		this.publicationDate = publicationDate;
	}
	@Override
	public String toString() {
		return "Book [isbn=" + isbn + ", title=" + title + ", author=" + author + ", id=" + id + ", nbPages=" + nbPages
				+ ", publicationDate=" + publicationDate + "]";
	}
	

}
