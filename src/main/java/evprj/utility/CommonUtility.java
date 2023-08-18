package evprj.utility;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import evprj.entity.EVChargingStation;
import evprj.entity.EVChargingStationUser;

@Component
public class CommonUtility {

	public String convertToJSONForUser(EVChargingStationUser evChargingStationUser) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		ObjectNode jsonNode = objectMapper.createObjectNode();
		jsonNode.put("userId", evChargingStationUser.getId());
		jsonNode.put("name", evChargingStationUser.getUserName());
		jsonNode.put("phone", evChargingStationUser.getPhone());
		jsonNode.put("vehicleDesc", evChargingStationUser.getVehicleDes());
		
		
		return objectMapper.writeValueAsString(jsonNode);
	}
	
	public String convertToJSON(EVChargingStation evChargingStation, int batteryPercentage)
			throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		ObjectNode jsonNode = objectMapper.createObjectNode();
		jsonNode.put("stationId", evChargingStation.getCharging_station_id());
		jsonNode.put("name", evChargingStation.getCharging_station_name());
		jsonNode.put("power", evChargingStation.getCharging_power());
		jsonNode.put("batteryPercentage", batteryPercentage);
		return objectMapper.writeValueAsString(jsonNode);
	}
}