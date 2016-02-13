package io.egen.rentalflix;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Entity representing a movie.
 * Fields: id, title, year, language
 */
@Entity
public class Movie {
	
	@Id
	private Integer id;
	private String title;
	private String year;
	private String language;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	
	public boolean equals(Object o){
		if(o instanceof Movie){
			return ((Movie)o).getId() == id;
		}
		return false;
	}
	
	public int hashCode(){
		return title.hashCode() + id.hashCode();
	}
	
}
