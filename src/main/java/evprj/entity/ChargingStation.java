package evprj.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class ChargingStation {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String location;
	private String address;
	private int numChargingPoints;
	private int maxPower;
	private String protocolsSupported;
	private String status;

	public ChargingStation() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ChargingStation(Long id, String location, String address, int numChargingPoints, int maxPower,
			String protocolsSupported, String status) {
		super();
		this.id = id;
		this.location = location;
		this.address = address;
		this.numChargingPoints = numChargingPoints;
		this.maxPower = maxPower;
		this.protocolsSupported = protocolsSupported;
		this.status = status;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getNumChargingPoints() {
		return numChargingPoints;
	}

	public void setNumChargingPoints(int numChargingPoints) {
		this.numChargingPoints = numChargingPoints;
	}

	public int getMaxPower() {
		return maxPower;
	}

	public void setMaxPower(int maxPower) {
		this.maxPower = maxPower;
	}

	public String getProtocolsSupported() {
		return protocolsSupported;
	}

	public void setProtocolsSupported(String protocolsSupported) {
		this.protocolsSupported = protocolsSupported;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
