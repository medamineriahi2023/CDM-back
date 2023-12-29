package oga.microservice.athentification.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import oga.microservice.athentification.entities.abstracts.AbstractEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "fileBlob")
@Getter
@Setter
@NoArgsConstructor
public class FileBlob extends AbstractEntity {

    @Column(name = "file", columnDefinition = "longblob")
    private byte[] file;

    @Column(name = "file_extection", length = 4)
    private String extention;


}
