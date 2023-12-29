package oga.microservice.athentification.dtos;

import com.fasterxml.jackson.annotation.JsonFilter;
import lombok.*;
import oga.microservice.athentification.dtos.abstracts.AbstractDTO;
import oga.microservice.athentification.entities.Sender;
import oga.microservice.athentification.factories.factory.CanalFactory;
import oga.microservice.athentification.factories.factory.FileBlobFactory;
import oga.microservice.athentification.factories.factory.ServiceFactory;

import java.util.Date;
import java.util.Map;
import java.util.Set;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@JsonFilter("SenderDTO")
public class SenderDTO extends AbstractDTO {
    private int numSender;
    private Date date;
    private String distinataire;
    private ServiceDTO emitteur;
    private String objet;
    private CanalDTO canal;
    private FileBlobDTO fileBlob;


    public SenderDTO(Sender entity, CanalFactory canalFactory, ServiceFactory serviceFactory, FileBlobFactory fileBlobFactory) {
        this(entity,canalFactory,serviceFactory,fileBlobFactory,null);
    }

    public SenderDTO(Sender entity,CanalFactory canalFactory, ServiceFactory serviceFactory,FileBlobFactory fileBlobFactory, String fields) {
        super(entity, fields);
        Map<String, Set<String>> filters = _filterFromField(fields);

        if (_isFetched("numSender", filters)) {
            this.setNumSender(entity.getNumSender());
        }
        if (_isFetched("date", filters)) {
            this.setDate(entity.getDate());
        }
        if (_isFetched("objet", filters)) {
            this.setObjet(entity.getObjet());
        }

        if (_isFetched("destinataire", filters)) {
            this.setDistinataire(entity.getDistinataire());
        }

        if (_isFetched("emitteur", filters)) {
            this.setEmitteur(serviceFactory.entityToDto(entity.getEmitteur(), _fieldsFromChildFields("emitteur", fields)));
        }

        if (_isFetched("file", filters)) {
            this.setFileBlob(fileBlobFactory.entityToDto(entity.getFileBlob(), _fieldsFromChildFields("file", fields)));
        }

        if (_isFetched("canal", filters)) {
            this.setCanal(canalFactory.entityToDto(entity.getCanal(), _fieldsFromChildFields("canal", fields)));
        }
    }



}
