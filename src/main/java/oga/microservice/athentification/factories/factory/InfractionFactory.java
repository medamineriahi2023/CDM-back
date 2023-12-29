package oga.microservice.athentification.factories.factory;

import oga.microservice.athentification.dtos.InfractionDTO;
import oga.microservice.athentification.entities.Infraction;
import oga.microservice.athentification.exceptions.EntityNotFoundException;
import oga.microservice.athentification.factories.abstracts.AbstractEntityFactory;
import oga.microservice.athentification.service.Impl.FileBlobService;
import oga.microservice.athentification.service.Impl.InfractionService;
import oga.microservice.athentification.service.Impl.PlainteService;
import oga.microservice.athentification.validator.enums.ErrorCodes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class InfractionFactory extends AbstractEntityFactory<Infraction, InfractionDTO> {
    @Autowired
    private InfractionService infractionService;

    @Autowired
    private FileBlobService fileBlobService;

    @Autowired
    private PlainteService plainteService;

    @Autowired
    private FileBlobFactory fileBlobFactory;

    @Autowired
    private PlainteFactory plainteFactory;


    @Override
    public Infraction dtoToEntity(InfractionDTO dto, List<String> updateFields) throws EntityNotFoundException {
        Infraction entity;

        if (dto.getId() != null) {
            entity = this.infractionService.findById(dto.getId())
                    .orElseThrow(() -> new EntityNotFoundException("entity not found", ErrorCodes.ENTITY_NOT_FOUND, new ArrayList<>()));
        } else {
            entity = new Infraction();
        }

        if (canUpdate("fileBlob", updateFields) && dto.getFileBlob() != null && dto.getFileBlob().getId() != null) {
            entity.setFileBlob(fileBlobService.findById(dto.getFileBlob().getId())
                    .orElseThrow(() -> new EntityNotFoundException("entity not found", ErrorCodes.ENTITY_NOT_FOUND, new ArrayList<>())));
        }

        if (canUpdate("plainte", updateFields) && dto.getPlainte() != null && dto.getPlainte().getId() != null) {
            entity.setPlainte(plainteService.findById(dto.getPlainte().getId())
                    .orElseThrow(() -> new EntityNotFoundException("entity not found", ErrorCodes.ENTITY_NOT_FOUND, new ArrayList<>())));
        }


        if (canUpdate("date", updateFields)) {
            entity.setDate(dto.getDate());
        }
        if (canUpdate("numInfraction", updateFields)) {
            entity.setNumInfraction(dto.getNumInfraction());
        }

        if (canUpdate("accusateur", updateFields)) {
            entity.setCin(dto.getCin());
        }

        if (canUpdate("plaignant", updateFields)) {
            entity.setAideAffectee(dto.getAideAffectee());
        }
        if (canUpdate("adresseAccusateur", updateFields)) {
            entity.setObjet(dto.getObjet());
        }
        if (canUpdate("adressePlaignant", updateFields)) {
            entity.setNiveauTravaux(dto.getNiveauTravaux());
        }
        if (canUpdate("remarques", updateFields)) {
            entity.setDommages(dto.getDommages());
        }
        if (canUpdate("adrBien", updateFields)) {
            entity.setDecision(dto.getDecision());
        }

        return entity;
    }

    @Override
    public InfractionDTO entityToDto(Infraction entity, String fieldsParams) {
        return new InfractionDTO(entity, fileBlobFactory,plainteFactory, fieldsParams);
    }

    @Override
    public InfractionDTO entityToDto(Infraction entity) {
        return new InfractionDTO(entity, fileBlobFactory, plainteFactory);
    }


}
