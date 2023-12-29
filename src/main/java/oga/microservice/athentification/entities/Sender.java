package oga.microservice.athentification.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import oga.microservice.athentification.entities.abstracts.AbstractEntity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "sender")
@Getter
@Setter
@NoArgsConstructor
public class Sender extends AbstractEntity {
    @Column(name = "num_sender", unique = true)
    private int numSender;
    @Column(name = "date")
    private Date date;
    @Column(name = "distinataire")
    private String distinataire;
    @ManyToOne(fetch = FetchType.LAZY)
    private Service emitteur;
    @Column(name = "objet")
    private String objet;
    @ManyToOne(fetch = FetchType.LAZY)
    private Canal canal;

    @ManyToOne(fetch = FetchType.LAZY)
    private FileBlob fileBlob;
}
