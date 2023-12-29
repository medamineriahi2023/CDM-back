package oga.microservice.athentification.service.Impl;

import oga.microservice.athentification.dtos.SortieStockDTO;
import oga.microservice.athentification.entities.Equipment;
import oga.microservice.athentification.entities.SortieStock;
import oga.microservice.athentification.repository.SortieStockRepository;
import oga.microservice.athentification.service.ISortieStockService;
import oga.microservice.athentification.service.abstracts.AbstractCrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class SortieStockService extends AbstractCrudService<SortieStock, SortieStockDTO> implements ISortieStockService {

    @Autowired
    SortieStockRepository sortieStockRepository;


    public Optional<SortieStock> stockStates(Equipment e) {
        return sortieStockRepository.findByEquipmentContains(e);
    }
}
