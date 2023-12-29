package oga.microservice.athentification.service;

import oga.microservice.athentification.dtos.SenderDTO;
import oga.microservice.athentification.entities.Sender;
import oga.microservice.athentification.service.abstracts.ICrudService;

public interface ISenderService extends ICrudService<Sender, SenderDTO> {
}
