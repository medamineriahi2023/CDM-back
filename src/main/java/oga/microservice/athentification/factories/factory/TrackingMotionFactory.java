package oga.microservice.athentification.factories.factory;

import oga.microservice.athentification.dtos.TrackingMotionDTO;
import oga.microservice.athentification.entities.TrackingMotion;
import oga.microservice.athentification.exceptions.EntityNotFoundException;
import oga.microservice.athentification.factories.abstracts.AbstractEntityFactory;
import oga.microservice.athentification.service.Impl.ReceiverService;
import oga.microservice.athentification.service.Impl.ServiceService;
import oga.microservice.athentification.service.Impl.TrackingMotionService;
import oga.microservice.athentification.validator.enums.ErrorCodes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TrackingMotionFactory extends AbstractEntityFactory<TrackingMotion, TrackingMotionDTO> {
    @Autowired
    private TrackingMotionService trackingMotionService;

    @Autowired
    private ReceiverService receiverService;

    @Autowired
    private ServiceService serviceService;

    @Autowired
    private ReceiverFactory receiverFactory;

    @Autowired
    private ServiceFactory serviceFactory;

    @Override
    public TrackingMotion dtoToEntity(TrackingMotionDTO dto, List<String> updateFields) throws EntityNotFoundException {
        TrackingMotion entity;

        if (dto.getId() != null) {
            entity = this.trackingMotionService.findById(dto.getId())
                    .orElseThrow(() -> new EntityNotFoundException("entity not found", ErrorCodes.ENTITY_NOT_FOUND, new ArrayList<>()));
        } else {
            entity = new TrackingMotion();
        }

        if (canUpdate("receiver", updateFields) && dto.getReceiver() != null && dto.getReceiver().getId() != null) {
            entity.setReceiver(receiverService.findById(dto.getReceiver().getId())
                    .orElseThrow(() -> new EntityNotFoundException("entity not found", ErrorCodes.ENTITY_NOT_FOUND, new ArrayList<>())));
        }

        if (canUpdate("category", updateFields) && dto.getDistinataire() != null && dto.getDistinataire().getId() != null) {
            entity.setDistinataire(serviceService.findById(dto.getDistinataire().getId())
                    .orElseThrow(() -> new EntityNotFoundException("entity not found", ErrorCodes.ENTITY_NOT_FOUND, new ArrayList<>())));
        }

        if (canUpdate("date", updateFields)) {
            entity.setDate(dto.getDate());
        }
        return entity;
    }

    @Override
    public TrackingMotionDTO entityToDto(TrackingMotion entity, String fieldsParams) {
        return new TrackingMotionDTO(entity, serviceFactory, receiverFactory, fieldsParams);
    }

    @Override
    public TrackingMotionDTO entityToDto(TrackingMotion entity) {
        return new TrackingMotionDTO(entity, serviceFactory, receiverFactory);
    }


}
