package oga.microservice.athentification.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import oga.microservice.athentification.entities.abstracts.AbstractEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "stock_state")
@Getter
@Setter
@NoArgsConstructor
public class StockState extends AbstractEntity {
    @Column(name = "num_bl")
    private int numBl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fournisseur_id")
    private Service fournisseur;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "signataire_id")
    private User signataire;

    @Column(name = "code")
    private String code;

    @OneToMany(fetch = FetchType.LAZY)
    private List<Equipment> equipment = new ArrayList<>();


}
