package oga.microservice.athentification.dtos;

import com.fasterxml.jackson.annotation.JsonFilter;
import lombok.*;
import oga.microservice.athentification.dtos.abstracts.AbstractDTO;

import javax.persistence.Column;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@JsonFilter("NotificationDTO")
public class NotificationDTO extends AbstractDTO {
//id reciver TODO
    //id user TODO
    @Column(name = "action", length = 15)
    private String action ;
}
