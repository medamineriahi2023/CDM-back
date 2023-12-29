package oga.microservice.athentification.dtos;

import com.fasterxml.jackson.annotation.JsonFilter;
import lombok.*;
import oga.microservice.athentification.dtos.abstracts.AbstractDTO;
import oga.microservice.athentification.entities.Complaints;

import java.util.Date;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@JsonFilter("ComplaintsDTO")
public class ComplaintsDTO extends AbstractDTO {
    private Date date;
    private String complainant;
    private String accuser;
    private String addressComplainant;
    private String addressAccuser;
    private String realEstateAddress;
    private String objectComplaint;
    private String remarque;

    public ComplaintsDTO(Complaints entity) {
        super(entity);
        this.setDate(entity.getDate());
        this.setComplainant(entity.getComplainant());
        this.setAccuser(entity.getAccuser());
        this.setAddressComplainant(entity.getAddressComplainant());
        this.setAddressAccuser(entity.getAddressAccuser());
        this.setRealEstateAddress(entity.getRealEstateAddress());
        this.setObjectComplaint(entity.getObjectComplaint());
        this.setRemarque(entity.getRemarque());


    }

    public ComplaintsDTO(Complaints entity, String fields) {
        super(entity, fields);
//        Map<String, Set<String>> filters = _filterFromField(fields);
//
//        if (_isFetched("name", filters)) {
//            this.setName(entity.getName());
//        }

        //TODO
    }

}
