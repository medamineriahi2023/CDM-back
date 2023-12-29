package oga.microservice.athentification.dtos;

import com.fasterxml.jackson.annotation.JsonFilter;
import lombok.*;
import oga.microservice.athentification.dtos.abstracts.AbstractDTO;
import oga.microservice.athentification.entities.Decisions;

import java.util.Date;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@JsonFilter("DecisionDTO")
public class DecisionsDTO extends AbstractDTO {
    private Date date;
    private int num;
    private String decisionObject;
    private String offender;
    private String finalDecision;
    private String dayOfImplementation;
    //id complain (user id ) TODO

    public DecisionsDTO(Decisions entity) {
        super(entity);
        this.setDate(entity.getDate());
        this.setNum(entity.getNum());
        this.setDecisionObject(entity.getDecisionObject());
        this.setOffender(entity.getOffender());
        this.setFinalDecision(entity.getFinalDecision());
        this.setDayOfImplementation(entity.getDayOfImplementation());
    }

    public DecisionsDTO(Decisions entity, String fields) {
        super(entity, fields);
//        Map<String, Set<String>> filters = _filterFromField(fields);
//
//        if (_isFetched("name", filters)) {
//            this.setName(entity.getName());
//        }

        //TODO
    }
}
