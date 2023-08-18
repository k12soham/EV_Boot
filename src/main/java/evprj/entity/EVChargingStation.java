package evprj.entity;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class EVChargingStation {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer charging_station_id;
	private String charging_station_name;
	private String charging_connector_type;
	private Integer active_ports;
	private Integer user_id;
	private double charging_power;
	private double charging_current;
	private String charging_status;
	private Integer battery_state_of_charge;
	private Integer battery_percentage;
	private Date start_charging_time;
	private Date estimated_end_time;
	private String charging_error_notification;
}