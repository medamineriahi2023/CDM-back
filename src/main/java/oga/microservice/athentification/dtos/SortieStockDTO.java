package oga.microservice.athentification.dtos;

import com.fasterxml.jackson.annotation.JsonFilter;
import lombok.*;
import oga.microservice.athentification.dtos.abstracts.AbstractDTO;
import oga.microservice.athentification.entities.SortieStock;
import oga.microservice.athentification.factories.factory.EquipmentFactory;
import oga.microservice.athentification.factories.factory.ServiceFactory;

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
@JsonFilter("SortieStockDTO")
public class SortieStockDTO extends AbstractDTO {
    private int numCommande;
    private ServiceDTO service;
    private String signataire;
    private List<EquipmentDTO> equipments = new ArrayList<>();

    public SortieStockDTO(SortieStock entity, ServiceFactory serviceFactory, EquipmentFactory equipmentFactory) {
        this(entity,serviceFactory,equipmentFactory,null);
    }
    public SortieStockDTO(SortieStock entity, ServiceFactory serviceFactory,EquipmentFactory equipmentFactory, String fields) {
        super(entity, fields);
        Map<String, Set<String>> filters = _filterFromField(fields);

        if (_isFetched("num_cde", filters)) {
            this.setNumCommande(entity.getNumCommande());
        }
        if (_isFetched("signataire", filters)) {
            this.setSignataire(entity.getSignataire());
        }
        if (_isFetched("service", filters)) {
            this.setService(serviceFactory.entityToDto(entity.getService(), _fieldsFromChildFields("service", fields)));
        }
        if (_isFetched("equipments", filters)) {
            this.setEquipments(
                    entity.getEquipment().stream().map(group -> equipmentFactory.entityToDto(group, _fieldsFromChildFields("equipments", fields)))
                            .collect(Collectors.toList()));
        }
    }
}
