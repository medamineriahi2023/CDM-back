package oga.microservice.athentification.service;

import oga.microservice.athentification.dtos.EquipmentDTO;
import oga.microservice.athentification.entities.Equipment;
import oga.microservice.athentification.service.abstracts.ICrudService;

public interface IEquipmentService extends ICrudService<Equipment, EquipmentDTO> {
}
