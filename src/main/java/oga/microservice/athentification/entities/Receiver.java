package oga.microservice.athentification.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import oga.microservice.athentification.entities.abstracts.AbstractEntity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "receiver")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Receiver extends AbstractEntity {
    @Column(name = "num_receiver")
    private int numReceiver;
    @Column(name = "date")
    private Date date;
    @Column(name = "emitteur")
    private String emitteur;
    @Column(name = "objet")
    private String objet;

    @ManyToOne(fetch = FetchType.LAZY)
    private Canal canal;

    @ManyToOne(fetch = FetchType.LAZY)
    private FileBlob fileBlob;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "tracking_motion_id")
    private TrackingMotion trackingMotion;
}
