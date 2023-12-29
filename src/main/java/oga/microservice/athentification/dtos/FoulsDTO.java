package oga.microservice.athentification.dtos;

import com.fasterxml.jackson.annotation.JsonFilter;
import lombok.*;
import oga.microservice.athentification.dtos.abstracts.AbstractDTO;

import java.util.Date;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@JsonFilter("foulsDTO")
public class FoulsDTO extends AbstractDTO {
    private Date date_hour;
    //complaintnt id
    private String cin;
    private String foulObject;

    private String address_accuser;

    private String workLevel;

    private String resultingDamages;

    private String assignedAssistant;

    private String finalDecision;
}
