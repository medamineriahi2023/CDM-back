package oga.microservice.athentification.factories.factory;

import oga.microservice.athentification.dtos.FileBlobDTO;
import oga.microservice.athentification.entities.FileBlob;
import oga.microservice.athentification.exceptions.EntityNotFoundException;
import oga.microservice.athentification.factories.abstracts.AbstractEntityFactory;
import oga.microservice.athentification.service.Impl.FileBlobService;
import oga.microservice.athentification.validator.enums.ErrorCodes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class FileBlobFactory extends AbstractEntityFactory<FileBlob, FileBlobDTO> {
    @Autowired
    private FileBlobService fileBlobService;

    @Override
    public FileBlob dtoToEntity(FileBlobDTO dto, List<String> updateFields) throws EntityNotFoundException {
        FileBlob entity;

        if (dto.getId() != null) {
            entity = this.fileBlobService.findById(dto.getId())
                    .orElseThrow(() -> new EntityNotFoundException("entity not found", ErrorCodes.ENTITY_NOT_FOUND, new ArrayList<>()));
        } else {
            entity = new FileBlob();
        }

        if (canUpdate("file", updateFields)) {
            entity.setFile(dto.getFile());
        }
        if (canUpdate("ext", updateFields)) {
            entity.setExtention(dto.getExtention());
        }

        return entity;
    }

    @Override
    public FileBlobDTO entityToDto(FileBlob entity, String fieldsParams) {
        return new FileBlobDTO(entity, fieldsParams);
    }

    @Override
    public FileBlobDTO entityToDto(FileBlob entity) {
        return new FileBlobDTO(entity);
    }


}
