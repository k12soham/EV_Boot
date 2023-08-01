package evprj.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import evprj.entity.ChargingSession;
import evprj.entity.ChargingStation;
import evprj.repo.ChargingSessionRepository;
import evprj.repo.ChargingStationRepository;

@Service
public class ChargingService {
	@Autowired
	private ChargingStationRepository chargingStationRepository;

	@Autowired
	private ChargingSessionRepository chargingSessionRepository;

	public ChargingStation chargingStation(ChargingStation chargingStation) {
		
		chargingStationRepository.save(chargingStation);
		return null;
	}

	public ChargingSession chargingSession(ChargingSession chargingSession) {
		chargingSessionRepository.save(chargingSession);
		return null;
	}

}
