package oga.microservice.athentification.service;

import oga.microservice.athentification.entities.User;
import oga.microservice.athentification.dtos.UserDto;
import oga.microservice.athentification.service.abstracts.ICrudService;

public interface IUserService extends ICrudService<User , UserDto> {
}
