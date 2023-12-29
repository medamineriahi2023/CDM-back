package oga.microservice.athentification.factories.factory;

import oga.microservice.athentification.dtos.ReceiverDTO;
import oga.microservice.athentification.entities.Receiver;
import oga.microservice.athentification.exceptions.EntityNotFoundException;
import oga.microservice.athentification.factories.abstracts.AbstractEntityFactory;
import oga.microservice.athentification.service.Impl.CanalService;
import oga.microservice.athentification.service.Impl.FileBlobService;
import oga.microservice.athentification.service.Impl.ReceiverService;
import oga.microservice.athentification.validator.enums.ErrorCodes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ReceiverFactory extends AbstractEntityFactory<Receiver, ReceiverDTO> {
    @Autowired
    private ReceiverService receiverService;

    @Autowired
    FileBlobService fileBlobService;

    @Autowired
    private CanalService canalService;

    @Autowired
    private CanalFactory canalFactory;
    @Autowired
    private FileBlobFactory fileBlobFactory;

    @Override
    public Receiver dtoToEntity(ReceiverDTO dto, List<String> updateFields) throws EntityNotFoundException {
        Receiver entity;

        if (dto.getId() != null) {
            entity = this.receiverService.findById(dto.getId())
                    .orElseThrow(() -> new EntityNotFoundException("entity not found", ErrorCodes.ENTITY_NOT_FOUND, new ArrayList<>()));
        } else {
            entity = new Receiver();
        }

        if (canUpdate("canal", updateFields) && dto.getCanal() != null && dto.getCanal().getId() != null) {
            entity.setCanal(canalService.findById(dto.getCanal().getId())
                    .orElseThrow(() -> new EntityNotFoundException("entity not found", ErrorCodes.ENTITY_NOT_FOUND, new ArrayList<>())));
        }

        if (canUpdate("file", updateFields) && dto.getFileBlob() != null && dto.getFileBlob().getId() != null) {
            entity.setFileBlob(fileBlobService.findById(dto.getFileBlob().getId())
                    .orElseThrow(() -> new EntityNotFoundException("entity not found", ErrorCodes.ENTITY_NOT_FOUND, new ArrayList<>())));
        }

        if (canUpdate("numReceiver", updateFields)) {
            entity.setNumReceiver(dto.getNumReceiver());
        }
        if (canUpdate("date", updateFields)) {
            entity.setDate(dto.getDate());
        }

        if (canUpdate("object", updateFields)) {
            entity.setObjet(dto.getObjet());
        }

        if (canUpdate("emitteur", updateFields)) {
            entity.setEmitteur(dto.getEmitteur());
        }

        return entity;
    }

    @Override
    public ReceiverDTO entityToDto(Receiver entity, String fieldsParams) {
        return new ReceiverDTO(entity, canalFactory, fileBlobFactory,fieldsParams);
    }

    @Override
    public ReceiverDTO entityToDto(Receiver entity) {
        return new ReceiverDTO(entity, canalFactory, fileBlobFactory);
    }


}
