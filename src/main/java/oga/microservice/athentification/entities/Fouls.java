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
@Table(name = "fouls")
@Getter
@Setter
@NoArgsConstructor
public class Fouls extends AbstractEntity {
    @Column(name = "date_hour", length = 15)
    private Date date_hour;
    //complaintnt id
    @Column(name = "cin", length = 8)
    private String cin;
    @Column(name = "foul_object", length = 15)
    private String foulObject;

    @Column(name = "address_accuser", length = 15)
    private String address_accuser;

    @Column(name = "work_level", length = 15)
    private String workLevel;

    @Column(name = "resulting_damages", length = 15)
    private String resultingDamages;

    @Column(name = "assigned_assistant", length = 15)
    private String assignedAssistant;

    @Column(name = "final_decision", length = 15)
    private String finalDecision;
}
