package oga.microservice.athentification.service;

import oga.microservice.athentification.dtos.HistoryDTO;
import oga.microservice.athentification.entities.History;
import oga.microservice.athentification.service.abstracts.ICrudService;

public interface IHistoryService extends ICrudService<History, HistoryDTO> {
}
