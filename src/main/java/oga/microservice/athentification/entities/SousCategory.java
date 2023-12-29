package oga.microservice.athentification.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import oga.microservice.athentification.entities.abstracts.AbstractEntity;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "sous_category")
@Getter
@Setter
@NoArgsConstructor
public class SousCategory extends AbstractEntity {
    @Column(name = "sous_cat_name", unique = true, length = 100)
    private String sousCatName;

    @OneToMany(mappedBy = "sousCategory", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE},
            orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<Article> articles;

    @OneToMany(mappedBy = "sousCategory", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE},
            orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<Equipment> equipment;
}
