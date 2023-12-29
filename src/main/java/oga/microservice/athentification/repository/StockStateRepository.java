package oga.microservice.athentification.repository;

import oga.microservice.athentification.entities.Equipment;
import oga.microservice.athentification.entities.StockState;
import oga.microservice.athentification.repository.abstracts.ICrudRepository;

import java.util.Optional;

public interface StockStateRepository extends ICrudRepository<StockState, Long> {

    Optional<StockState> findByEquipmentContains(Equipment e);
}
