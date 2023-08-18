package evprj.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import evprj.entity.EVChargingStationUser;

public interface EVChargingStationUserRepository extends JpaRepository<EVChargingStationUser, Integer> {

}