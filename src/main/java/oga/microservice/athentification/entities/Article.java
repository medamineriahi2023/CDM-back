package oga.microservice.athentification.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import oga.microservice.athentification.entities.abstracts.AbstractEntity;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "article")
@Getter
@Setter
@NoArgsConstructor
public class Article extends AbstractEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sous_category_id")
    private SousCategory sousCategory;
    @Column(name = "code", unique = true)
    private int code;
    @Column(name = "stock_min")
    private int stockMin;
    @Column(name = "stock_cri")
    private int stockCri;
    @Column(name = "articleName", unique = true)
    private String name;
    @Column(name = "archived")
    private boolean archived;

    @Column(name = "qte_total")
    private int qteTotal;


    @OneToMany(mappedBy = "article", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE},
            orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<Equipment> equipment;
}
