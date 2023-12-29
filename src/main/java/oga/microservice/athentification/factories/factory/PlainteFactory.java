package oga.microservice.athentification.factories.factory;

import oga.microservice.athentification.dtos.PlainteDTO;
import oga.microservice.athentification.entities.Plainte;
import oga.microservice.athentification.exceptions.EntityNotFoundException;
import oga.microservice.athentification.factories.abstracts.AbstractEntityFactory;
import oga.microservice.athentification.service.Impl.FileBlobService;
import oga.microservice.athentification.service.Impl.PlainteService;
import oga.microservice.athentification.validator.enums.ErrorCodes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PlainteFactory extends AbstractEntityFactory<Plainte, PlainteDTO> {
    @Autowired
    private PlainteService plainteService;

    @Autowired
    private FileBlobService fileBlobService;

    @Autowired
    private FileBlobFactory fileBlobFactory;


    @Override
    public Plainte dtoToEntity(PlainteDTO dto, List<String> updateFields) throws EntityNotFoundException {
        Plainte entity;

        if (dto.getId() != null) {
            entity = this.plainteService.findById(dto.getId())
                    .orElseThrow(() -> new EntityNotFoundException("entity not found", ErrorCodes.ENTITY_NOT_FOUND, new ArrayList<>()));
        } else {
            entity = new Plainte();
        }

        if (canUpdate("fileBlob", updateFields) && dto.getFileBlob() != null && dto.getFileBlob().getId() != null) {
            entity.setFileBlob(fileBlobService.findById(dto.getFileBlob().getId())
                    .orElseThrow(() -> new EntityNotFoundException("entity not found", ErrorCodes.ENTITY_NOT_FOUND, new ArrayList<>())));
        }

        if (canUpdate("date", updateFields)) {
            entity.setDate(dto.getDate());
        }
        if (canUpdate("numPlainte", updateFields)) {
            entity.setNumPlainte(dto.getNumPlainte());
        }

        if (canUpdate("accusateur", updateFields)) {
            entity.setAccusateur(dto.getAccusateur());
        }

        if (canUpdate("plaignant", updateFields)) {
            entity.setPlaignant(dto.getPlaignant());
        }
        if (canUpdate("adresseAccusateur", updateFields)) {
            entity.setAdresseAccusateur(dto.getAdresseAccusateur());
        }
        if (canUpdate("adressePlaignant", updateFields)) {
            entity.setAdressePlaignant(dto.getAdressePlaignant());
        }
        if (canUpdate("remarques", updateFields)) {
            entity.setRemarques(dto.getRemarques());
        }
        if (canUpdate("adrBien", updateFields)) {
            entity.setAdrBien(dto.getAdrBien());
        }

        if (canUpdate("objetPlainte", updateFields)) {
            entity.setObjetPlainte(dto.getObjetPlainte());
        }



        return entity;
    }

    @Override
    public PlainteDTO entityToDto(Plainte entity, String fieldsParams) {
        return new PlainteDTO(entity, fileBlobFactory, fieldsParams);
    }

    @Override
    public PlainteDTO entityToDto(Plainte entity) {
        return new PlainteDTO(entity, fileBlobFactory);
    }


}
