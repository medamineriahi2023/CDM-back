package oga.microservice.athentification.service;

import oga.microservice.athentification.dtos.InfractionDTO;
import oga.microservice.athentification.entities.Infraction;
import oga.microservice.athentification.service.abstracts.ICrudService;

public interface IInfractionService extends ICrudService<Infraction, InfractionDTO> {
}
