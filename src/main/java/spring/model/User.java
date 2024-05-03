package spring.model;

import java.io.Serializable;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@SuppressWarnings("serial")
@Getter @Setter @AllArgsConstructor @NoArgsConstructor
@Entity
@Table(name = "User")
public class User implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(length = 60)
	private String password;
	@Column(length = 50, unique = true)
	private String email;
	@Column(length = 10, unique = true)
	private String mobile_no;
	@Enumerated(EnumType.STRING)
	private Role role;
	@Column(length = 1)
	private int status;
	@Column(length = 10)
	@Enumerated(EnumType.STRING)
	private Plan plan;
	@OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@PrimaryKeyJoinColumn
	private OtpModel otp;
	
	@OneToMany(mappedBy = "user", fetch=FetchType.LAZY)
	private List<Wishlist> wishlists;
	
	public User(String email) {
		this.email = email;
	}
	
}
