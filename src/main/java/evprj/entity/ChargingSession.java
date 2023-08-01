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

@Entity
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

	public ChargingSession() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ChargingSession(Long id, ChargingStation chargingStation, Date startTime, Date endTime, int duration,
			double energyDelivered, double totalCost, Long userId, String status) {
		super();
		this.id = id;
		this.chargingStation = chargingStation;
		this.startTime = startTime;
		this.endTime = endTime;
		this.duration = duration;
		this.energyDelivered = energyDelivered;
		this.totalCost = totalCost;
		this.userId = userId;
		this.status = status;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ChargingStation getChargingStation() {
		return chargingStation;
	}

	public void setChargingStation(ChargingStation chargingStation) {
		this.chargingStation = chargingStation;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public double getEnergyDelivered() {
		return energyDelivered;
	}

	public void setEnergyDelivered(double energyDelivered) {
		this.energyDelivered = energyDelivered;
	}

	public double getTotalCost() {
		return totalCost;
	}

	public void setTotalCost(double totalCost) {
		this.totalCost = totalCost;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
