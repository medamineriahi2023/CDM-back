package oga.microservice.athentification.service;

import oga.microservice.athentification.dtos.ReceiverDTO;
import oga.microservice.athentification.entities.Receiver;
import oga.microservice.athentification.service.abstracts.ICrudService;

public interface IReceiverService extends ICrudService<Receiver, ReceiverDTO> {
}
