package oga.microservice.athentification.rest;

import oga.microservice.athentification.dtos.HistoryDTO;
import oga.microservice.athentification.entities.History;
import oga.microservice.athentification.rest.AbstractRestController.AbstractCrudController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "history")
public class HistoryController extends AbstractCrudController<History, HistoryDTO> {
    public HistoryController() {
        super(History.class);
    }
}
