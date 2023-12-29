package oga.microservice.athentification.factories.factory;

import oga.microservice.athentification.dtos.SortieStockDTO;
import oga.microservice.athentification.entities.SortieStock;
import oga.microservice.athentification.exceptions.EntityNotFoundException;
import oga.microservice.athentification.factories.abstracts.AbstractEntityFactory;
import oga.microservice.athentification.service.Impl.EquipmentService;
import oga.microservice.athentification.service.Impl.ServiceService;
import oga.microservice.athentification.service.Impl.SortieStockService;
import oga.microservice.athentification.validator.enums.ErrorCodes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class SortieStockFactory extends AbstractEntityFactory<SortieStock, SortieStockDTO> {
    @Autowired
    private EquipmentFactory equipmentFactory;

    @Autowired
    private ServiceFactory serviceFactory;

    @Autowired
    private ServiceService serviceService;
    @Autowired
    private EquipmentService equipmentService;
    @Autowired
    private SortieStockService sortieStockService;

    @Override
    public SortieStock dtoToEntity(SortieStockDTO dto, List<String> updateFields) throws EntityNotFoundException {
        SortieStock entity;

        if (dto.getId() != null) {
            entity = this.sortieStockService.findById(dto.getId())
                    .orElseThrow(() -> new EntityNotFoundException("entity not found", ErrorCodes.ENTITY_NOT_FOUND, new ArrayList<>()));
        } else {
            entity = new SortieStock();
        }

        if (canUpdate("service", updateFields) && dto.getService() != null && dto.getService().getId() != null) {
            entity.setService(serviceService.findById(dto.getService().getId())
                    .orElseThrow(() -> new EntityNotFoundException("entity not found", ErrorCodes.ENTITY_NOT_FOUND, new ArrayList<>())));
        }


        if (canUpdate("num_cde", updateFields)) {
            entity.setNumCommande(dto.getNumCommande());
        }
        if (canUpdate("signataire", updateFields)) {
            entity.setSignataire(dto.getSignataire());
        }

        if (canUpdate("equipments", updateFields)) {
            entity.getEquipment().addAll(dto.getEquipments().stream().map(groupDTO -> {
                try {
                    return equipmentService.findById(groupDTO.getId())
                            .orElseThrow(() -> new EntityNotFoundException("entity not found", ErrorCodes.ENTITY_NOT_FOUND, new ArrayList<>()));
                } catch (EntityNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }).toList());
            entity.setEquipment(entity.getEquipment());
        }

        return entity;
    }

    @Override
    public SortieStockDTO entityToDto(SortieStock entity, String fieldsParams) {
        return new SortieStockDTO(entity, serviceFactory, equipmentFactory, fieldsParams);
    }

    @Override
    public SortieStockDTO entityToDto(SortieStock entity) {
        return new SortieStockDTO(entity, serviceFactory, equipmentFactory);
    }


}
