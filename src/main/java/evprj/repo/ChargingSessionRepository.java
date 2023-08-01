package evprj.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import evprj.entity.ChargingSession;

public interface ChargingSessionRepository extends JpaRepository<ChargingSession, Long> {

}
