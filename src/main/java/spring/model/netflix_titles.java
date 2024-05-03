package spring.model;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter @Getter @NoArgsConstructor @AllArgsConstructor
@SuppressWarnings("serial")
@Entity
public class netflix_titles implements Serializable {
	@Id
	@Column(length = 50)
	private String show_id;
	@Column(length = 50)
	private String type;
	@Column(length = 64)
	private String title;
	@Column(length = 64)
	private String director;
	@Column(length = 1024)
	private String cast;
	@Column(length = 128)
	private String country;
	@Column(length = 50)
	private String date_added;
	@Column(length = 11)
	private int release_year;
	@Column(length = 50)
	private String rating;
	@Column(length = 50)
	private String duration;
	@Column(length = 128)
	private String listed_in;
	@Column(length = 256)
	private String description;
	
}
