package oga.microservice.athentification.repository;

import oga.microservice.athentification.entities.User;
import oga.microservice.athentification.repository.abstracts.ICrudRepository;

import java.util.List;

public interface UserRepository extends ICrudRepository<User, Long> {

    List<User> findAllByService_Id(Long service);
}
