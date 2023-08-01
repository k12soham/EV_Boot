package evprj.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import evprj.entity.ChargingStation;

public interface ChargingStationRepository extends JpaRepository<ChargingStation, Long>  {

}
