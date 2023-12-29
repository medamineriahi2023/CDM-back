package oga.microservice.athentification.dtos;

import com.fasterxml.jackson.annotation.JsonFilter;
import lombok.*;
import oga.microservice.athentification.dtos.abstracts.AbstractDTO;
import oga.microservice.athentification.entities.TrackingMotion;
import oga.microservice.athentification.factories.factory.ReceiverFactory;
import oga.microservice.athentification.factories.factory.ServiceFactory;

import java.util.Date;
import java.util.Map;
import java.util.Set;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@JsonFilter("TrackingMotionDTO")
public class TrackingMotionDTO extends AbstractDTO {
    private ServiceDTO distinataire;
    private ReceiverDTO receiver;
    private Date date;

    public TrackingMotionDTO(TrackingMotion entity, ServiceFactory serviceFactory , ReceiverFactory receiverFactory) {
        this(entity,serviceFactory,receiverFactory, null);
    }
    public TrackingMotionDTO(TrackingMotion entity, ServiceFactory serviceFactory , ReceiverFactory receiverFactory, String fields) {
        super(entity, fields);
        Map<String, Set<String>> filters = _filterFromField(fields);

        if (_isFetched("date", filters)) {
            this.setDate(entity.getDate());
        }

        if (_isFetched("receiver", filters)) {
            this.setReceiver(receiverFactory.entityToDto(entity.getReceiver(), _fieldsFromChildFields("receiver", fields)));
        }
        if (_isFetched("distinataire", filters) && entity.getDistinataire() !=null) {
            this.setDistinataire(serviceFactory.entityToDto(entity.getDistinataire(), _fieldsFromChildFields("distinataire", fields)));
        }
    }
}
