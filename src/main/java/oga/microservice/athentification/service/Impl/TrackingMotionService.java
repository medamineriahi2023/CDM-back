package oga.microservice.athentification.service.Impl;

import lombok.extern.slf4j.Slf4j;
import oga.microservice.athentification.dtos.TrackingMotionDTO;
import oga.microservice.athentification.entities.TrackingMotion;
import oga.microservice.athentification.entities.User;
import oga.microservice.athentification.service.ITrackingService;
import oga.microservice.athentification.service.abstracts.AbstractCrudService;
import org.keycloak.email.EmailException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
@Transactional
@Slf4j
public class TrackingMotionService extends AbstractCrudService<TrackingMotion, TrackingMotionDTO> implements ITrackingService {

    @Autowired
    private JavaMailSender javaMailSender;
    @Value("${mail.smtp.host}")
    private String HOST;
    @Value("${mail.smtp.port}")
    private String PORT;
    @Value("${mail.smtp.auth}")
    private String AUTH;
    @Value("${mail.internet.address}")
    private String INTERNET_ADDRESS;
    @Value("${mail.header}")
    private String HEADER;
    @Value("${mail.protocol}")
    private String PROTOCOL;

    @Autowired
    UserService userService;


    public void sendMailToAllUsers(String motions, Long id) throws EmailException, MessagingException {
        List<User> users = userService.getUsersByService(id);
        List<String> emails = users.stream().map(User::getEmail).toList();


        // Create a fixed thread pool with 4 threads
        ExecutorService executor = Executors.newFixedThreadPool(4);

        try {

            String[] t = motions.split(",");
            for (String e : t) {
                MimeMessage message = javaMailSender.createMimeMessage();
                MimeMessageHelper helper = new MimeMessageHelper(message, true);
                Optional<TrackingMotion> trackingMotion = this.findById(Long.parseLong(e));
                    log.info("sending object name {}", trackingMotion.get().getReceiver().getObjet());
                    helper.setText("<h1>Hello</h1>", true);
                    helper.setFrom("support@mun.gov.tn");
                    helper.addAttachment("attachment."+trackingMotion.get().getReceiver().getFileBlob().getExtention(), new ByteArrayResource(trackingMotion.get().getReceiver().getFileBlob().getFile()));
                for (int i = 0; i < emails.size(); i++) {
                    // Submit a task to send an email to each user
                    final String email = emails.get(i);
                    executor.submit(() -> {
                        try {
                            log.info("send email to : {} with object {}", email,trackingMotion.get().getReceiver().getObjet());
                            helper.setTo(email);
                            helper.setSubject(trackingMotion.get().getReceiver().getObjet());
                            javaMailSender.send(message);
                        } catch (MessagingException ex) {
                            ex.printStackTrace();
                        }
                    });
                }
            }
        } catch (MessagingException e) {
            e.printStackTrace();
        } finally {
            // Shut down the thread pool
            executor.shutdown();
        }
    }
}
