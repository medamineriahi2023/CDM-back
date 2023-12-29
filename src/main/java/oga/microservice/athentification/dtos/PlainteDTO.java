package oga.microservice.athentification.dtos;

import com.fasterxml.jackson.annotation.JsonFilter;
import lombok.*;
import oga.microservice.athentification.dtos.abstracts.AbstractDTO;
import oga.microservice.athentification.entities.Plainte;
import oga.microservice.athentification.factories.factory.FileBlobFactory;

import java.util.Date;
import java.util.Map;
import java.util.Set;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@JsonFilter("PlainteDTO")
public class PlainteDTO extends AbstractDTO {
    private int numPlainte;
    private Date date;
    private String plaignant;

    private String accusateur;

    private String adressePlaignant;

    private String adresseAccusateur;

    private String adrBien;

    private String objetPlainte;

    private String remarques;

    private FileBlobDTO fileBlob;

    public PlainteDTO(Plainte entity, FileBlobFactory fileBlobFactory) {
        this(entity,fileBlobFactory,null);
    }
    public PlainteDTO(Plainte entity, FileBlobFactory fileBlobFactory ,String fields) {
        super(entity, fields);
        Map<String, Set<String>> filters = _filterFromField(fields);

        if (_isFetched("numPlainte", filters)) {
            this.setNumPlainte(entity.getNumPlainte());
        }
        if (_isFetched("date", filters)) {
            this.setDate(entity.getDate());
        }
        if (_isFetched("plaignant", filters)) {
            this.setPlaignant(entity.getPlaignant());
        }
        if (_isFetched("accusateur", filters)) {
            this.setAccusateur(entity.getAccusateur());
        }
        if (_isFetched("adresseAccusateur", filters)) {
            this.setAdresseAccusateur(entity.getAdresseAccusateur());
        }

        if (_isFetched("adressePlaignant", filters)) {
            this.setAdressePlaignant(entity.getAdressePlaignant());
        }

        if (_isFetched("adrBien", filters)) {
            this.setAdrBien(entity.getAdrBien());
        }

        if (_isFetched("objetPlainte", filters)) {
            this.setObjetPlainte(entity.getObjetPlainte());
        }

        if (_isFetched("remarques", filters)) {
            this.setRemarques(entity.getRemarques());
        }

        if (_isFetched("fileBlob", filters)) {
            this.setFileBlob(fileBlobFactory.entityToDto(entity.getFileBlob(), _fieldsFromChildFields("fileBlob", fields)));
        }
    }
}
