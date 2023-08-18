package evprj.entity;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;

@Entity
@Data
public class ChargingSession {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "station_id")
	private ChargingStation chargingStation;

	@Temporal(TemporalType.TIMESTAMP)
	private Date startTime;

	@Temporal(TemporalType.TIMESTAMP)
	private Date endTime;

	private int duration;
	private double energyDelivered;
	private double totalCost;
	private Long userId;
	private String status;

	
}
