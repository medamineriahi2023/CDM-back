package oga.microservice.athentification.rest;

import oga.microservice.athentification.dtos.PlainteDTO;
import oga.microservice.athentification.entities.Plainte;
import oga.microservice.athentification.rest.AbstractRestController.AbstractCrudController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "plainte")
public class PlainteController extends AbstractCrudController<Plainte, PlainteDTO> {
    public PlainteController() {
        super(Plainte.class);
    }

}
