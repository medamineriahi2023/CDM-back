package oga.microservice.athentification.dtos;

import com.fasterxml.jackson.annotation.JsonFilter;
import lombok.*;
import oga.microservice.athentification.dtos.abstracts.AbstractDTO;
import oga.microservice.athentification.entities.Receiver;
import oga.microservice.athentification.factories.factory.CanalFactory;
import oga.microservice.athentification.factories.factory.FileBlobFactory;

import java.util.Date;
import java.util.Map;
import java.util.Set;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@JsonFilter("ReceiverDTO")
public class ReceiverDTO extends AbstractDTO {
    private int numReceiver;
    private Date date;
    private String emitteur;
    private String objet;
    private CanalDTO canal;
    private FileBlobDTO fileBlob;


    public ReceiverDTO(Receiver entity, CanalFactory categoryFactory,FileBlobFactory fileBlobFactory) {
        this(entity,categoryFactory, fileBlobFactory , null);
    }
    public ReceiverDTO(Receiver entity, CanalFactory canalFactory, FileBlobFactory fileBlobFactory , String fields) {
        super(entity, fields);
        Map<String, Set<String>> filters = _filterFromField(fields);

        if (_isFetched("numReceiver", filters)) {
            this.setNumReceiver(entity.getNumReceiver());
        }
        if (_isFetched("date", filters)) {
            this.setDate(entity.getDate());
        }
        if (_isFetched("emiteur", filters)) {
            this.setEmitteur(entity.getEmitteur());
        }
        if (_isFetched("object", filters)) {
            this.setObjet(entity.getObjet());
        }
        if (_isFetched("file", filters)) {
            this.setFileBlob(fileBlobFactory.entityToDto(entity.getFileBlob(), _fieldsFromChildFields("file", fields)));
        }
        if (_isFetched("category", filters)) {
            this.setCanal(canalFactory.entityToDto(entity.getCanal(), _fieldsFromChildFields("canal", fields)));
        }
    }
}
