package oga.microservice.athentification.rest;

import oga.microservice.athentification.dtos.SortieStockDTO;
import oga.microservice.athentification.entities.Equipment;
import oga.microservice.athentification.entities.SortieStock;
import oga.microservice.athentification.rest.AbstractRestController.AbstractCrudController;
import oga.microservice.athentification.service.Impl.SortieStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(path = "sortie-stock")
public class SortieStockController extends AbstractCrudController<SortieStock, SortieStockDTO> {
    public SortieStockController() {
        super(SortieStock.class);
    }


    @Autowired
    SortieStockService sortieStockService;

    @CrossOrigin(origins = "*")
    @PostMapping(path = "getByEquipment")
    public ResponseEntity<MappingJacksonValue> stockStates(@RequestBody Equipment e) throws Exception {
        Optional<SortieStock> entity =  sortieStockService.stockStates(e);
        if (entity.isPresent()) {
            MappingJacksonValue mapping = _entityToMappingJacksonValue(entity.get(), null);

            return new ResponseEntity<>(mapping, HttpStatus.OK);
        }
        return new ResponseEntity<>(new MappingJacksonValue(""), HttpStatus.OK);
    }
}
