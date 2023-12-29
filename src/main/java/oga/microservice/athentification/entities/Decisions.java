package oga.microservice.athentification.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import oga.microservice.athentification.entities.abstracts.AbstractEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "decisions")
@Getter
@Setter
@NoArgsConstructor
public class Decisions extends AbstractEntity {
    @Column(name = "date")
    private Date date;
    @Column(name = "num", length = 255)
    private int num;
    @Column(name = "decision_object", length = 255)
    private String decisionObject;
    @Column(name = "offender", length = 255)
    private String offender;
    @Column(name = "final_decision", length = 255)
    private String finalDecision;
    @Column(name = "day_of_implementation", length = 255)
    private String dayOfImplementation;
    //id complain (user id ) TODO
}
