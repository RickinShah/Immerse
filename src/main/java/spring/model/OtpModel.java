package spring.model;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@SuppressWarnings("serial")
@Setter @Getter @NoArgsConstructor @AllArgsConstructor
@Entity
@Table(name = "Otp")
public class OtpModel implements Serializable {
	@Id
	private int user_id;
	@Column(length = 6)
	private int otp;
	private long created_at;
	@OneToOne
	@MapsId
	@JoinColumn(name = "user_id")
	private User user;

	public OtpModel(int user_id, int otp, long created_at) {
		super();
		this.user_id = user_id;
		this.otp = otp;
		this.created_at = created_at;
	}
	
	public OtpModel(int otp, long created_at, User user) {
		super();
		this.otp = otp;
		this.created_at = created_at;
		this.user = user;
	}

	public OtpModel(int user_id) {
		super();
		this.user_id = user_id;
	}

}
