package oga.microservice.athentification.repository;

import oga.microservice.athentification.entities.Equipment;
import oga.microservice.athentification.entities.SortieStock;
import oga.microservice.athentification.repository.abstracts.ICrudRepository;

import java.util.Optional;

public interface SortieStockRepository extends ICrudRepository<SortieStock, Long> {
    Optional<SortieStock> findByEquipmentContains(Equipment e);
}
