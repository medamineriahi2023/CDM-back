package oga.microservice.athentification.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import oga.microservice.athentification.entities.abstracts.AbstractEntity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "infraction")
@Getter
@Setter
@NoArgsConstructor
public class Infraction extends AbstractEntity {
    @Column(name = "num_infraction", unique = true)
    private int numInfraction;

    @Column(name = "date")
    private Date date;

    @Column(name = "cin", length = 8)
    private String cin;

    @Column(name = "aide_affectee", unique = true)
    private String aideAffectee;

    @Column(name = "objet")
    private String objet;

    @Column(name = "niveau_travaux")
    private String niveauTravaux;

    @Column(name = "dommages")
    private String dommages;

    @Column(name = "decision")
    private String decision;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private FileBlob fileBlob;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Plainte plainte;
}
