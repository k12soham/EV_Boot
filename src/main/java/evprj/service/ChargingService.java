package evprj.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    	evChargingStationRepository.save(evChargingStation);
		return null;
	}
    
    public ResponseEntity<?> getByStationId(Integer charging_station_id){
    	EVChargingStation evChargingStation = new EVChargingStation(); 
//    	List<EVChargingStation> l = new ArrayList<>();
    	evChargingStation=	evChargingStationRepository.findById(charging_station_id).get();
//    	System.out.println(l);
  System.out.println(evChargingStation.getCharging_connector_type());
    	return new ResponseEntity<>(evChargingStation, HttpStatus.OK);
    }


}
