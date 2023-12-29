package oga.microservice.athentification.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import oga.microservice.athentification.entities.abstracts.AbstractEntity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "TrackingMotion")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class TrackingMotion extends AbstractEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "service_id")
    private Service distinataire;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_id")
    private Receiver receiver;
    @Column(name = "date_affectation")
    private Date date;
}
