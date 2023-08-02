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
	private Integer battery_state_of_charge;
	private Date start_charging_time;
	private Date estimated_end_time;
	private String charging_error_notification;

}


/*
Charging_Station_Name
Charging_Connector_Type
Active_Ports
UserId
  Charging_Status:Active,Pause,Completed
Charging_Power
Charging_Current
Battery_State_Of_Charge
Start_Charging_Time
Estimated_End_Time
Charging_Error_Notification: Send Codes*/
 