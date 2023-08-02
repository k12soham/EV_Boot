package evprj.entity;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
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
	private Date start_charging_time;
	private Date estimated_end_time;
	private String charging_error_notification;
	public EVChargingStation() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Integer getCharging_station_id() {
		return charging_station_id;
	}
	public void setCharging_station_id(Integer charging_station_id) {
		this.charging_station_id = charging_station_id;
	}
	public String getCharging_station_name() {
		return charging_station_name;
	}
	public void setCharging_station_name(String charging_station_name) {
		this.charging_station_name = charging_station_name;
	}
	public String getCharging_connector_type() {
		return charging_connector_type;
	}
	public void setCharging_connector_type(String charging_connector_type) {
		this.charging_connector_type = charging_connector_type;
	}
	public Integer getActive_ports() {
		return active_ports;
	}
	public void setActive_ports(Integer active_ports) {
		this.active_ports = active_ports;
	}
	public Integer getUser_id() {
		return user_id;
	}
	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}
	public double getCharging_power() {
		return charging_power;
	}
	public void setCharging_power(double charging_power) {
		this.charging_power = charging_power;
	}
	public double getCharging_current() {
		return charging_current;
	}
	public void setCharging_current(double charging_current) {
		this.charging_current = charging_current;
	}
	public String getCharging_status() {
		return charging_status;
	}
	public void setCharging_status(String charging_status) {
		this.charging_status = charging_status;
	}
	public Integer getBattery_state_of_charge() {
		return battery_state_of_charge;
	}
	public void setBattery_state_of_charge(Integer battery_state_of_charge) {
		this.battery_state_of_charge = battery_state_of_charge;
	}
	public Date getStart_charging_time() {
		return start_charging_time;
	}
	public void setStart_charging_time(Date start_charging_time) {
		this.start_charging_time = start_charging_time;
	}
	public Date getEstimated_end_time() {
		return estimated_end_time;
	}
	public void setEstimated_end_time(Date estimated_end_time) {
		this.estimated_end_time = estimated_end_time;
	}
	public String getCharging_error_notification() {
		return charging_error_notification;
	}
	public void setCharging_error_notification(String charging_error_notification) {
		this.charging_error_notification = charging_error_notification;
	}
	
	
	
	

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
 