package oga.microservice.athentification.rest;

import oga.microservice.athentification.dtos.ReceiverDTO;
import oga.microservice.athentification.entities.Receiver;
import oga.microservice.athentification.rest.AbstractRestController.AbstractCrudController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "receiver")
public class ReceiverController extends AbstractCrudController<Receiver, ReceiverDTO> {
    public ReceiverController() {
        super(Receiver.class);
    }
}
