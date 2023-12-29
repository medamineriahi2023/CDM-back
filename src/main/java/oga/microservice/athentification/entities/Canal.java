package oga.microservice.athentification.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import oga.microservice.athentification.entities.abstracts.AbstractEntity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "canal")
@Getter
@Setter
@NoArgsConstructor
public class Canal extends AbstractEntity {
    @Column(name = "canal_name", unique = true)
    private String canalName;

    @OneToMany(mappedBy = "canal", fetch = FetchType.LAZY)
    private List<Receiver> receivers;

    @OneToMany(mappedBy = "canal", fetch = FetchType.LAZY)
    private List<Sender> senders;
}
