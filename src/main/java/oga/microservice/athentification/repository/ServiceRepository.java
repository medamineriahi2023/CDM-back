package oga.microservice.athentification.repository;

import oga.microservice.athentification.entities.Service;
import oga.microservice.athentification.repository.abstracts.ICrudRepository;

import java.util.Optional;

public interface ServiceRepository extends ICrudRepository<Service, Long> {
    Optional<Service> findServiceByServiceName(String s);
}
