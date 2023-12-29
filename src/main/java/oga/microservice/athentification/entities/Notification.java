package oga.microservice.athentification.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import oga.microservice.athentification.entities.abstracts.AbstractEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "notification")
@Getter
@Setter
@NoArgsConstructor
public class Notification extends AbstractEntity {
//id reciver TODO
    //id user TODO
    @Column(name = "action", length = 15)
    private String action ;
}
