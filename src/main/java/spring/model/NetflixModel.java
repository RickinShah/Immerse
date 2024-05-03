package spring.model;

import java.io.Serializable;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter @Getter @NoArgsConstructor @AllArgsConstructor
@SuppressWarnings("serial")
@Entity
@Table(name = "Netflix")
public class NetflixModel implements Serializable {
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
	@OneToMany(mappedBy = "netflix", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<Resolution> resolution;
	@OneToMany(mappedBy = "netflix", fetch= FetchType.LAZY)
	private List<Wishlist> wishlists;


	public NetflixModel(String show_id, String type, String title, String director, String cast, String country,
			String date_added, int release_year, String rating, String duration, String listed_in, String description,
			List<Resolution> resolution) {
		super();
		this.show_id = show_id;
		this.type = type;
		this.title = title;
		this.director = director;
		this.cast = cast;
		this.country = country;
		this.date_added = date_added;
		this.release_year = release_year;
		this.rating = rating;
		this.duration = duration;
		this.listed_in = listed_in;
		this.description = description;
		this.resolution = resolution;
	}
	
}
