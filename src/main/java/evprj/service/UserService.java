package evprj.service;

import org.springframework.stereotype.Service;

import evprj.entity.EVChargingStationUser;

@Service
public interface UserService {
	
	public EVChargingStationUser getUserById(int id);
	public EVChargingStationUser createNewUser(EVChargingStationUser user);

}