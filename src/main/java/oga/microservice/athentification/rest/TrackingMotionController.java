package oga.microservice.athentification.rest;

import oga.microservice.athentification.dtos.TrackingMotionDTO;
import oga.microservice.athentification.entities.TrackingMotion;
import oga.microservice.athentification.rest.AbstractRestController.AbstractCrudController;
import oga.microservice.athentification.service.Impl.TrackingMotionService;
import org.keycloak.email.EmailException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;

@RestController
@RequestMapping(path = "tracking")
public class TrackingMotionController extends AbstractCrudController<TrackingMotion, TrackingMotionDTO> {
    public TrackingMotionController() {
        super(TrackingMotion.class);
    }

    @Autowired
    TrackingMotionService trackingMotionService;
    @CrossOrigin("*")
    @PostMapping("send/{motion}/{id}")
    public ResponseEntity send (@PathVariable("motion") String motion , @PathVariable("id") Long serviceId) throws EmailException, MessagingException {
        trackingMotionService.sendMailToAllUsers(motion,serviceId);
        return ResponseEntity.ok().build();
    }

}
