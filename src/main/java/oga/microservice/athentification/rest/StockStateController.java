package oga.microservice.athentification.rest;

import oga.microservice.athentification.dtos.StockStateDTO;
import oga.microservice.athentification.entities.Equipment;
import oga.microservice.athentification.entities.StockState;
import oga.microservice.athentification.rest.AbstractRestController.AbstractCrudController;
import oga.microservice.athentification.service.Impl.StockStateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(path = "stock-state")
public class StockStateController extends AbstractCrudController<StockState, StockStateDTO> {

    @Autowired
    StockStateService stockStateService;


    public StockStateController() {
        super(StockState.class);
    }
    @CrossOrigin(origins = "*")
    @PostMapping(path = "getByEquipment")
    public ResponseEntity<MappingJacksonValue> stockStates(@RequestBody Equipment e) throws Exception {
        Optional<StockState> entity =  stockStateService.stockStates(e);
        if (entity.isPresent()) {
            MappingJacksonValue mapping = _entityToMappingJacksonValue(entity.get(), null);

            return new ResponseEntity<>(mapping, HttpStatus.OK);
        }
        return new ResponseEntity<>(new MappingJacksonValue(""), HttpStatus.OK);
    }
}
