package oga.microservice.athentification.service;

import oga.microservice.athentification.dtos.ServiceDTO;
import oga.microservice.athentification.entities.Service;
import oga.microservice.athentification.service.abstracts.ICrudService;

import java.util.Optional;

public interface IServiceService extends ICrudService<Service, ServiceDTO> {
    Optional<Service> findByServiceName(String name);
}
