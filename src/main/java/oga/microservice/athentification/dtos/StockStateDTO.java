package oga.microservice.athentification.dtos;

import com.fasterxml.jackson.annotation.JsonFilter;
import lombok.*;
import oga.microservice.athentification.dtos.abstracts.AbstractDTO;
import oga.microservice.athentification.entities.StockState;
import oga.microservice.athentification.factories.factory.EquipmentFactory;
import oga.microservice.athentification.factories.factory.ServiceFactory;
import oga.microservice.athentification.factories.factory.UserFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@JsonFilter("StockStateDTO")
public class StockStateDTO extends AbstractDTO {
    private int numBl;
    private ServiceDTO fournisseur;
    private UserDto signataire;
    private String code;

    private List<EquipmentDTO> equipments = new ArrayList<>();

    public StockStateDTO(StockState entity, ServiceFactory serviceFactory, EquipmentFactory equipmentFactory, UserFactory userFactory) {
        this(entity,serviceFactory,equipmentFactory,userFactory,null);
    }
    public StockStateDTO(StockState entity, ServiceFactory serviceFactory,EquipmentFactory equipmentFactory, UserFactory userFactory, String fields) {
        super(entity, fields);
        Map<String, Set<String>> filters = _filterFromField(fields);

        if (_isFetched("num_bl", filters)) {
            this.setNumBl(entity.getNumBl());
        }

        if (_isFetched("code", filters)) {
            this.setCode(entity.getCode());
        }

        if (_isFetched("signataire", filters)) {
            this.setSignataire(userFactory.entityToDto(entity.getSignataire(), _fieldsFromChildFields("signataire",fields)));
        }
        if (_isFetched("fournisseur", filters)) {
            this.setFournisseur(serviceFactory.entityToDto(entity.getFournisseur(), _fieldsFromChildFields("fournisseur", fields)));
        }
        if (_isFetched("equipments", filters)) {
            this.setEquipments(
                    entity.getEquipment().stream().map(group -> equipmentFactory.entityToDto(group, _fieldsFromChildFields("equipments", fields)))
                            .collect(Collectors.toList()));
        }
    }

}
