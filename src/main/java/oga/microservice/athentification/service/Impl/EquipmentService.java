package oga.microservice.athentification.service.Impl;

import oga.microservice.athentification.dtos.EquipmentDTO;
import oga.microservice.athentification.entities.Equipment;
import oga.microservice.athentification.service.IEquipmentService;
import oga.microservice.athentification.service.abstracts.AbstractCrudService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class EquipmentService extends AbstractCrudService<Equipment, EquipmentDTO> implements IEquipmentService {
}
