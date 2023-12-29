package oga.microservice.athentification.rest;

import oga.microservice.athentification.dtos.InfractionDTO;
import oga.microservice.athentification.entities.Infraction;
import oga.microservice.athentification.rest.AbstractRestController.AbstractCrudController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "infraction")
public class InfractionController extends AbstractCrudController<Infraction, InfractionDTO> {
    public InfractionController() {
        super(Infraction.class);
    }

}
