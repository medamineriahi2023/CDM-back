package oga.microservice.athentification.rest;

import oga.microservice.athentification.dtos.CanalDTO;
import oga.microservice.athentification.entities.Canal;
import oga.microservice.athentification.rest.AbstractRestController.AbstractCrudController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "canal")
public class CanalController extends AbstractCrudController<Canal, CanalDTO> {
    public CanalController() {
        super(Canal.class);
    }
}
