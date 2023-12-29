package oga.microservice.athentification.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import oga.microservice.athentification.entities.abstracts.AbstractEntity;

import javax.persistence.*;

@Entity
@Table(name = "equipment")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Equipment extends AbstractEntity {
    @JoinColumn(name = "category_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Category category;

    @JoinColumn(name = "sous_category_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private SousCategory sousCategory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id")
    private Article article;

    @Column(name = "qte")
    private int qte;

    @ManyToOne(fetch = FetchType.LAZY)
    private SortieStock sortieStock;

    @ManyToOne(fetch = FetchType.LAZY)
    private StockState stockState;

    private Etat etat;

}
