package oga.microservice.athentification.factories.factory;

import oga.microservice.athentification.dtos.SenderDTO;
import oga.microservice.athentification.entities.Sender;
import oga.microservice.athentification.exceptions.EntityNotFoundException;
import oga.microservice.athentification.factories.abstracts.AbstractEntityFactory;
import oga.microservice.athentification.service.Impl.CanalService;
import oga.microservice.athentification.service.Impl.FileBlobService;
import oga.microservice.athentification.service.Impl.SenderService;
import oga.microservice.athentification.service.Impl.ServiceService;
import oga.microservice.athentification.validator.enums.ErrorCodes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class SenderFactory extends AbstractEntityFactory<Sender, SenderDTO> {
    @Autowired
    private SenderService senderService;

    @Autowired
    private CanalService canalService;

    @Autowired
    private ServiceService serviceService;

    @Autowired
    private CanalFactory canalFactory;

    @Autowired
    private ServiceFactory serviceFactory;

    @Autowired
    private FileBlobFactory fileBlobFactory;

    @Autowired
    private FileBlobService fileBlobService;

    @Override
    public Sender dtoToEntity(SenderDTO dto, List<String> updateFields) throws EntityNotFoundException {
        Sender entity;

        if (dto.getId() != null) {
            entity = this.senderService.findById(dto.getId())
                    .orElseThrow(() -> new EntityNotFoundException("entity not found", ErrorCodes.ENTITY_NOT_FOUND, new ArrayList<>()));
        } else {
            entity = new Sender();
        }

        if (canUpdate("canal", updateFields) && dto.getObjet() != null && dto.getCanal().getId() != null) {
            entity.setCanal(canalService.findById(dto.getCanal().getId())
                    .orElseThrow(() -> new EntityNotFoundException("entity not found", ErrorCodes.ENTITY_NOT_FOUND, new ArrayList<>())));
        }

        if (canUpdate("emitteur", updateFields) && dto.getEmitteur() != null && dto.getEmitteur().getId() != null) {
            entity.setEmitteur(serviceService.findById(dto.getEmitteur().getId())
                    .orElseThrow(() -> new EntityNotFoundException("entity not found", ErrorCodes.ENTITY_NOT_FOUND, new ArrayList<>())));
        }

        if (canUpdate("file", updateFields) && dto.getFileBlob() != null && dto.getFileBlob().getId() != null) {
            entity.setFileBlob(fileBlobService.findById(dto.getFileBlob().getId())
                    .orElseThrow(() -> new EntityNotFoundException("entity not found", ErrorCodes.ENTITY_NOT_FOUND, new ArrayList<>())));
        }

        if (canUpdate("objet", updateFields)) {
            entity.setObjet(dto.getObjet());
        }
        if (canUpdate("date", updateFields)) {
            entity.setDate(dto.getDate());
        }

        if (canUpdate("numSender", updateFields)) {
            entity.setNumSender(dto.getNumSender());
        }

        if (canUpdate("distinataire", updateFields)) {
            entity.setDistinataire(dto.getDistinataire());
        }
        return entity;
    }

    @Override
    public SenderDTO entityToDto(Sender entity, String fieldsParams) {
        return new SenderDTO(entity, canalFactory, serviceFactory,fileBlobFactory, fieldsParams);
    }

    @Override
    public SenderDTO entityToDto(Sender entity) {
        return new SenderDTO(entity, canalFactory, serviceFactory, fileBlobFactory);
    }


}
