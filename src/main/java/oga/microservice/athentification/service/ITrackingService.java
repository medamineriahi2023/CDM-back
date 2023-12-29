package oga.microservice.athentification.service;

import oga.microservice.athentification.dtos.TrackingMotionDTO;
import oga.microservice.athentification.entities.TrackingMotion;
import oga.microservice.athentification.service.abstracts.ICrudService;

public interface ITrackingService extends ICrudService<TrackingMotion, TrackingMotionDTO> {
}
