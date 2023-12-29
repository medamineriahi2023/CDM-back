package oga.microservice.athentification.dtos;

import com.fasterxml.jackson.annotation.JsonFilter;
import lombok.*;
import oga.microservice.athentification.dtos.abstracts.AbstractDTO;
import oga.microservice.athentification.entities.Infraction;
import oga.microservice.athentification.factories.factory.FileBlobFactory;
import oga.microservice.athentification.factories.factory.PlainteFactory;

import java.util.Date;
import java.util.Map;
import java.util.Set;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@JsonFilter("InfractionDTO")
public class InfractionDTO extends AbstractDTO {
    private int numInfraction;
    private Date date;
    private String cin;

    private String aideAffectee;

    private String objet;

    private String niveauTravaux;

    private String dommages;

    private String decision;

    private PlainteDTO plainte;

    private FileBlobDTO fileBlob;

    public InfractionDTO(Infraction entity, FileBlobFactory fileBlobFactory, PlainteFactory plainteFactory) {
        this(entity,fileBlobFactory,plainteFactory,null);
    }
    public InfractionDTO(Infraction entity, FileBlobFactory fileBlobFactory ,PlainteFactory plainteFactory, String fields) {
        super(entity, fields);
        Map<String, Set<String>> filters = _filterFromField(fields);

        if (_isFetched("numInfraction", filters)) {
            this.setNumInfraction(entity.getNumInfraction());
        }
        if (_isFetched("date", filters)) {
            this.setDate(entity.getDate());
        }
        if (_isFetched("cin", filters)) {
            this.setCin(entity.getCin());
        }
        if (_isFetched("aide_affectee", filters)) {
            this.setAideAffectee(entity.getAideAffectee());
        }
        if (_isFetched("objet", filters)) {
            this.setObjet(entity.getObjet());
        }

        if (_isFetched("niveau_travaux", filters)) {
            this.setNiveauTravaux(entity.getNiveauTravaux());
        }

        if (_isFetched("dommages", filters)) {
            this.setDommages(entity.getDommages());
        }

        if (_isFetched("decision", filters)) {
            this.setDecision(entity.getDecision());
        }

        if (_isFetched("fileBlob", filters) && entity.getFileBlob() != null ) {
            this.setFileBlob(fileBlobFactory.entityToDto(entity.getFileBlob(), _fieldsFromChildFields("fileBlob", fields)));
        }
        if (_isFetched("plainte", filters)) {
            this.setPlainte(plainteFactory.entityToDto(entity.getPlainte(), _fieldsFromChildFields("plainte", fields)));
        }
    }
}
