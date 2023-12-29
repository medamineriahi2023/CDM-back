package oga.microservice.athentification.factories.factory;

import oga.microservice.athentification.dtos.StockStateDTO;
import oga.microservice.athentification.entities.StockState;
import oga.microservice.athentification.exceptions.EntityNotFoundException;
import oga.microservice.athentification.factories.abstracts.AbstractEntityFactory;
import oga.microservice.athentification.service.Impl.EquipmentService;
import oga.microservice.athentification.service.Impl.ServiceService;
import oga.microservice.athentification.service.Impl.StockStateService;
import oga.microservice.athentification.service.Impl.UserService;
import oga.microservice.athentification.validator.enums.ErrorCodes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class StockStateFactory extends AbstractEntityFactory<StockState, StockStateDTO> {
    @Autowired
    private EquipmentFactory equipmentFactory;

    @Autowired
    private ServiceFactory serviceFactory;

    @Autowired
    private ServiceService serviceService;
    @Autowired
    private EquipmentService equipmentService;

    @Autowired
    private UserFactory userFactory;

    @Autowired
    private UserService userService;
    @Autowired
    private StockStateService stockStateService;

    @Override
    public StockState dtoToEntity(StockStateDTO dto, List<String> updateFields) throws EntityNotFoundException {
        StockState entity;

        if (dto.getId() != null) {
            entity = this.stockStateService.findById(dto.getId())
                    .orElseThrow(() -> new EntityNotFoundException("entity not found", ErrorCodes.ENTITY_NOT_FOUND, new ArrayList<>()));
        } else {
            entity = new StockState();
        }

        if (canUpdate("fournisseur", updateFields) && dto.getFournisseur() != null && dto.getFournisseur().getId() != null) {
            entity.setFournisseur(serviceService.findById(dto.getFournisseur().getId())
                    .orElseThrow(() -> new EntityNotFoundException("entity not found", ErrorCodes.ENTITY_NOT_FOUND, new ArrayList<>())));
        }


        if (canUpdate("num_bl", updateFields)) {
            entity.setNumBl(dto.getNumBl());
        }

        if (canUpdate("code", updateFields)) {
            entity.setCode(dto.getCode());
        }


        if (canUpdate("signataire", updateFields) && dto.getSignataire() != null && dto.getSignataire().getId() != null) {
            entity.setSignataire(userService.findById(dto.getSignataire().getId()).orElseThrow(()->new EntityNotFoundException("entity not found", ErrorCodes.ENTITY_NOT_FOUND, new ArrayList<>())));
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
    public StockStateDTO entityToDto(StockState entity, String fieldsParams) {
        return new StockStateDTO(entity, serviceFactory, equipmentFactory,userFactory, fieldsParams);
    }

    @Override
    public StockStateDTO entityToDto(StockState entity) {
        return new StockStateDTO(entity, serviceFactory, equipmentFactory, userFactory);
    }


}
