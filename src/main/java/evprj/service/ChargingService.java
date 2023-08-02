package evprj.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import evprj.entity.ChargingSession;
import evprj.entity.ChargingStation;
import evprj.entity.EVChargingStation;
import evprj.repo.ChargingSessionRepository;
import evprj.repo.ChargingStationRepository;
import evprj.repo.EVChargingStationRepository;

@Service
public class ChargingService {
	@Autowired
	private ChargingStationRepository chargingStationRepository;

	@Autowired
	private ChargingSessionRepository chargingSessionRepository;
	
	@Autowired
	private EVChargingStationRepository evChargingStationRepository;

	public ChargingStation chargingStation(ChargingStation chargingStation) {
		
		chargingStationRepository.save(chargingStation);
		return null;
	}

	public ChargingSession chargingSession(ChargingSession chargingSession) {
		chargingSessionRepository.save(chargingSession);
		return null;
	}
	
    public ResponseEntity<?> saveEVchargingStation(EVChargingStation evChargingStation) {
    	EVChargingStation ChargingStation = new EVChargingStation();
		evChargingStation.setCharging_station_id(evChargingStation.getCharging_station_id());
		evChargingStation.setCharging_station_name(evChargingStation.getCharging_station_name());
		evChargingStation.setCharging_connector_type(evChargingStation.getCharging_connector_type());
		evChargingStation.setActive_ports(evChargingStation.getActive_ports());
		evChargingStation.setUser_id(evChargingStation.getUser_id());
		evChargingStation.setCharging_power(evChargingStation.getCharging_power());
		evChargingStation.setCharging_current(evChargingStation.getCharging_current());
		evChargingStation.setBattery_state_of_charge(evChargingStation.getBattery_state_of_charge());
		evChargingStation.setStart_charging_time(evChargingStation.getStart_charging_time());
		evChargingStation.setEstimated_end_time(evChargingStation.getEstimated_end_time());
		evChargingStation.setCharging_error_notification(evChargingStation.getCharging_error_notification());
    	evChargingStationRepository.save(evChargingStation);
		return null;
	}


}
