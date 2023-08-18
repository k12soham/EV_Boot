package evprj.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import evprj.entity.EVChargingStationUser;
import evprj.repo.EVChargingStationUserRepository;
import evprj.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	EVChargingStationUserRepository evChargingStationUserRepository;

	@Override
	public EVChargingStationUser getUserById(int id) {
		EVChargingStationUser evChargingStationUser = evChargingStationUserRepository.findById(id).get();
		return evChargingStationUser;
	}

	@Override
	public EVChargingStationUser createNewUser(EVChargingStationUser user) {
		EVChargingStationUser evChargingStationUser=evChargingStationUserRepository.save(user);
		return evChargingStationUser;
	}

}