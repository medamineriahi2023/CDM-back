package oga.microservice.athentification.service;

import oga.microservice.athentification.dtos.PlainteDTO;
import oga.microservice.athentification.entities.Plainte;
import oga.microservice.athentification.service.abstracts.ICrudService;

public interface IPlainteService extends ICrudService<Plainte, PlainteDTO> {
}
