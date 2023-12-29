package oga.microservice.athentification.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import oga.microservice.athentification.entities.abstracts.AbstractEntity;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "service")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Service extends AbstractEntity {
    @Column(name = "service_name")
    private String serviceName;

    @Column(name = "responsable")
    private String responsable;

    @Column(name = "disabled")
    private boolean disabled;

    @JsonIgnore
    @OneToMany(mappedBy = "service", fetch = FetchType.LAZY)
    private Set<User> user;

    @JsonIgnore
    @OneToMany(mappedBy = "service", fetch = FetchType.LAZY)
    private List<History> history;

    @OneToMany(mappedBy = "service", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE},
            orphanRemoval = true, fetch = FetchType.LAZY)
    private List<SortieStock> sortieStocks;

    @OneToMany(mappedBy = "emitteur", fetch = FetchType.LAZY)
    private List<Sender> senders;

    @OneToMany(mappedBy = "distinataire", fetch = FetchType.LAZY)
    private List<TrackingMotion> trackingMotions;

}
