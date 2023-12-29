package oga.microservice.athentification.rest;

import oga.microservice.athentification.dtos.EquipmentDTO;
import oga.microservice.athentification.entities.Equipment;
import oga.microservice.athentification.rest.AbstractRestController.AbstractCrudController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "equipment")
public class EquipmentController extends AbstractCrudController<Equipment, EquipmentDTO> {
    public EquipmentController() {
        super(Equipment.class);
    }
}
