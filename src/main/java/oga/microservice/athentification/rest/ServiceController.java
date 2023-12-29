package oga.microservice.athentification.rest;

import oga.microservice.athentification.dtos.ServiceDTO;
import oga.microservice.athentification.entities.Service;
import oga.microservice.athentification.exceptions.EntityNotFoundException;
import oga.microservice.athentification.exceptions.InvalidEntityException;
import oga.microservice.athentification.rest.AbstractRestController.AbstractCrudController;
import oga.microservice.athentification.service.Impl.ServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(path = "service")
public class ServiceController extends AbstractCrudController<Service, ServiceDTO> {
    public ServiceController() {
        super(Service.class);
    }

    @Autowired
    ServiceService serviceService;

    @CrossOrigin(origins = "*")
    @PostMapping(path = "import")
    public ResponseEntity<List<Service>> importUsers(@RequestParam(name = "file") MultipartFile file) throws IOException, EntityNotFoundException, InvalidEntityException {
        return serviceService.importService(file);
    }
}
