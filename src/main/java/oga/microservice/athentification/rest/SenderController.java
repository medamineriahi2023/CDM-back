package oga.microservice.athentification.rest;

import oga.microservice.athentification.dtos.SenderDTO;
import oga.microservice.athentification.entities.Sender;
import oga.microservice.athentification.rest.AbstractRestController.AbstractCrudController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "sender")
public class SenderController extends AbstractCrudController<Sender, SenderDTO> {
    public SenderController() {
        super(Sender.class);
    }
}
