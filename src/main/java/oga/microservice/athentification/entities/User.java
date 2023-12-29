package oga.microservice.athentification.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import oga.microservice.athentification.entities.abstracts.AbstractEntity;

import javax.persistence.*;

@Entity
@Table(name = "user")
@Data
@NoArgsConstructor
public class User extends AbstractEntity {
    @Column(name = "first_name")
    private String firstname;

    @Column(name = "last_name")
    private String lastname;

    @Column(name = "email")
    private String email;

    @Column(name = "user_id")
    private String userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "service_id")
    private Service service;

    @Column(name = "role")
    private String role;

    @Column(name = "password")
    private String password;

    @Column(name = "disabled")
    private boolean disabled;

}
