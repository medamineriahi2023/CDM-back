package oga.microservice.athentification.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import oga.microservice.athentification.entities.abstracts.AbstractEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "sortie_stock")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class SortieStock extends AbstractEntity {
    @Column(name = "num_commande", length = 15)
    private int numCommande;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "service_id")
    private Service service;
    @Column(name = "nom_signataire")
    private String signataire;

    @OneToMany(fetch = FetchType.LAZY)
    private List<Equipment> equipment = new ArrayList<>();
}
