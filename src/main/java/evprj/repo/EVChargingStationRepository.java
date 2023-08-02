package evprj.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import evprj.entity.EVChargingStation;

public interface EVChargingStationRepository extends JpaRepository<EVChargingStation, Integer> {

}
