package evprj.service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

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

	public ResponseEntity<?> getByStationId(Integer charging_station_id) {
		EVChargingStation evChargingStation = new EVChargingStation();
		evChargingStation = evChargingStationRepository.findById(charging_station_id).get();

		return new ResponseEntity<>(evChargingStation, HttpStatus.OK);
	}
// websocket/////////////////

	private final Set<WebSocketSession> sessions = new HashSet<>();
	private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

	public EVChargingStation batterypercent(EVChargingStation evChargingStation) throws Exception {
		ObjectMapper mapper = new ObjectMapper();

		// Session session1 = entityManager.unwrap(Session.class);

		// EVChargingStation evChargingStation = new EVChargingStation();

		evChargingStation = evChargingStationRepository.findById(evChargingStation.getCharging_station_id()).get();

		if (evChargingStation != null) {

			updateBatteryPercentage(evChargingStation, evChargingStation.getBattery_percentage(),
					evChargingStation.getCharging_power());

		} else {
			System.out.println("else");
		}
		return null;

	}

	private String updateBatteryPercentage(EVChargingStation evChargingStation, int batteryPercentage, double d)
			throws Exception {
		
		double chargingPower = d / 10; // Normalize charging capacity to charging power

		batteryPercentage += chargingPower;
		final int newBatteryPercentage = batteryPercentage;


		String json = convertToJSON(evChargingStation, newBatteryPercentage);
		System.out.println(json);

		if (newBatteryPercentage >= 100) {

			scheduler.wait();

		}

		scheduler.schedule(() -> updateBatteryPercentage(evChargingStation, newBatteryPercentage, d), 2,
				TimeUnit.SECONDS);

		return json;
	}

	private String convertToJSON(EVChargingStation evChargingStation, int batteryPercentage)
			throws JsonProcessingException {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();
		String start = dtf.format(now);
		ObjectMapper objectMapper = new ObjectMapper();
		ObjectNode jsonNode = objectMapper.createObjectNode();
		jsonNode.put("stationId", evChargingStation.getCharging_station_id());
		jsonNode.put("name", evChargingStation.getCharging_station_name());
		jsonNode.put("power", evChargingStation.getCharging_power());
		jsonNode.put("batteryPercentage", batteryPercentage);
		// jsonNode.put("start-time",start );
		// Add other properties as needed

		return objectMapper.writeValueAsString(jsonNode);
	}
}
