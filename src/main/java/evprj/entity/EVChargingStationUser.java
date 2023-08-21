package evprj.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/*@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString*/


@Entity
@Data
public class EVChargingStationUser {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	@Column(name = "user_name", nullable = false)
	private String userName;
	@Column(name = "phone")
	private String phone;
	@Column(name = "vehicle_desc")
	private String vehicleDes;

}
