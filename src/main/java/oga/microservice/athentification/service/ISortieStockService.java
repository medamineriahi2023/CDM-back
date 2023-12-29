package oga.microservice.athentification.service;

import oga.microservice.athentification.dtos.SortieStockDTO;
import oga.microservice.athentification.entities.SortieStock;
import oga.microservice.athentification.service.abstracts.ICrudService;

public interface ISortieStockService extends ICrudService<SortieStock, SortieStockDTO> {
}
