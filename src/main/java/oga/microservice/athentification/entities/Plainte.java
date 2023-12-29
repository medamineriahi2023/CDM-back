package oga.microservice.athentification.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import oga.microservice.athentification.entities.abstracts.AbstractEntity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "plaintes")
@Getter
@Setter
@NoArgsConstructor
public class Plainte extends AbstractEntity {
    @Column(name = "num_plainte", unique = true)
    private int numPlainte;

    @Column(name = "date")
    private Date date;

    @Column(name = "plaignant")
    private String plaignant;

    @Column(name = "accusateur", unique = true)
    private String accusateur;

    @Column(name = "adresse_plaignant")
    private String adressePlaignant;

    @Column(name = "adresse_accusateur")
    private String adresseAccusateur;

    @Column(name = "adr_bien")
    private String adrBien;

    @Column(name = "objet_plainte")
    private String objetPlainte;

    @Column(name = "remarques")
    private String remarques;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private FileBlob fileBlob;
}
