package oga.microservice.athentification.service;

import oga.microservice.athentification.dtos.StockStateDTO;
import oga.microservice.athentification.entities.StockState;
import oga.microservice.athentification.service.abstracts.ICrudService;

public interface IStockStateService extends ICrudService<StockState, StockStateDTO> {
}
