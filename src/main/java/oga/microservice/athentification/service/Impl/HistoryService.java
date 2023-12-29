package oga.microservice.athentification.service.Impl;

import oga.microservice.athentification.dtos.HistoryDTO;
import oga.microservice.athentification.entities.History;
import oga.microservice.athentification.service.IHistoryService;
import oga.microservice.athentification.service.abstracts.AbstractCrudService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class HistoryService extends AbstractCrudService<History, HistoryDTO> implements IHistoryService {
}