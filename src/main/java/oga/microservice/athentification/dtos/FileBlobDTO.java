package oga.microservice.athentification.dtos;

import com.fasterxml.jackson.annotation.JsonFilter;
import lombok.*;
import oga.microservice.athentification.dtos.abstracts.AbstractDTO;
import oga.microservice.athentification.entities.FileBlob;

import java.util.Map;
import java.util.Set;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@JsonFilter("fileBlobDTO")
public class FileBlobDTO extends AbstractDTO {
    private byte[] file;
    private String extention;

    public FileBlobDTO(FileBlob entity) {
        this(entity,null);
    }
    public FileBlobDTO(FileBlob entity, String fields) {
        super(entity, fields);
        Map<String, Set<String>> filters = _filterFromField(fields);

        if (_isFetched("file", filters)) {
            this.setFile(entity.getFile());
        }

        if (_isFetched("ext", filters)) {
            this.setExtention(entity.getExtention());
        }
    }
}
