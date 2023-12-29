package oga.microservice.athentification.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import oga.microservice.athentification.entities.abstracts.AbstractEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "complaints")
@Getter
@Setter
@NoArgsConstructor
public class Complaints extends AbstractEntity {
    @Column(name = "date")
    private Date date;
    @Column(name = "complainant", length = 15)
    private String complainant;
    @Column(name = "accuser", length = 15)
    private String accuser;
    @Column(name = "address_complainant", length = 15)
    private String addressComplainant;
    @Column(name = "address_accuser", length = 15)
    private String addressAccuser;
    @Column(name = "real_estate_address", length = 15)
    private String realEstateAddress;
    @Column(name = "object_complaint", length = 15)
    private String objectComplaint;
    @Column(name = "remarque", length = 15)
    private String remarque;

}
