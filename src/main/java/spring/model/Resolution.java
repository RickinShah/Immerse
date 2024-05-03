package spring.model;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@SuppressWarnings("serial")
@Getter @Setter @AllArgsConstructor @NoArgsConstructor
@Entity
@Table(name="Resolution")
public class Resolution implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(length = 50)
	private long rid;
	@Column(length = 5)
	private String rname;
	
	@ManyToOne
	@JoinColumn(name = "vid")
	private NetflixModel netflix;
	
	public Resolution(String rname, NetflixModel netflix) {
		this.rname = rname;
		this.netflix = netflix;
	}
}
