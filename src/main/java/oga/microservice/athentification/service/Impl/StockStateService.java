package oga.microservice.athentification.service.Impl;

import oga.microservice.athentification.dtos.StockStateDTO;
import oga.microservice.athentification.entities.Equipment;
import oga.microservice.athentification.entities.StockState;
import oga.microservice.athentification.repository.StockStateRepository;
import oga.microservice.athentification.service.IStockStateService;
import oga.microservice.athentification.service.abstracts.AbstractCrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class StockStateService extends AbstractCrudService<StockState, StockStateDTO> implements IStockStateService {
    @Autowired
    StockStateRepository stockStateRepository;


    public Optional<StockState> stockStates(Equipment e) {
        return stockStateRepository.findByEquipmentContains(e);
    }
}
