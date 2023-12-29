package oga.microservice.athentification.rest;

import oga.microservice.athentification.dtos.UserDto;
import oga.microservice.athentification.entities.User;
import oga.microservice.athentification.exceptions.EntityNotFoundException;
import oga.microservice.athentification.exceptions.InvalidEntityException;
import oga.microservice.athentification.rest.AbstractRestController.AbstractCrudController;
import oga.microservice.athentification.service.Impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(path ="user")
public class UserController extends AbstractCrudController<User, UserDto> {
    public UserController() {
        super(User.class);
    }

    @Autowired
    UserService userService;

    @CrossOrigin(origins = "*")
    @PostMapping(path = "import")
    public ResponseEntity<List<User>> importUsers(@RequestParam(name = "file") MultipartFile file) throws IOException, EntityNotFoundException, InvalidEntityException {
        return userService.importUsers(file);
    }

    @CrossOrigin(origins = "*")
    @GetMapping(path = "user-service/{id}")
    public ResponseEntity<MappingJacksonValue> getUsersByService(@PathVariable("id") Long serviceId) throws Exception {
        List<User> entities = userService.getUsersByService(serviceId);
        MappingJacksonValue mapping = _entitiesToMappingJacksonValues(entities, null);
        return new ResponseEntity<>(mapping, HttpStatus.OK);
    }
}
