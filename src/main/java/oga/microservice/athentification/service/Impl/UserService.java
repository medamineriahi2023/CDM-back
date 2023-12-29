package oga.microservice.athentification.service.Impl;

import oga.microservice.athentification.dtos.UserDto;
import oga.microservice.athentification.entities.User;
import oga.microservice.athentification.exceptions.EntityNotFoundException;
import oga.microservice.athentification.exceptions.InvalidEntityException;
import oga.microservice.athentification.repository.UserRepository;
import oga.microservice.athentification.service.IUserService;
import oga.microservice.athentification.service.abstracts.AbstractCrudService;
import oga.microservice.athentification.validator.RegisterRequestValidator;
import oga.microservice.athentification.validator.enums.ErrorCodes;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
public class UserService extends AbstractCrudService<User,UserDto> implements IUserService {
    @Autowired
    AuthenticationServiceImpl authenticationService;

    @Autowired
    MessageSource messageSource;

    @Autowired
    ServiceService serviceService;

    @Autowired
    UserRepository userRepository;
    public ResponseEntity<List<User>> importUsers(MultipartFile file) throws IOException, EntityNotFoundException, InvalidEntityException {
        List<User> users = new ArrayList<>();
        int i = 0;
        try (
                InputStream is = file.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(is));

                CSVParser csvParser = new CSVParser(br, CSVFormat.DEFAULT
                        .withFirstRecordAsHeader()
                        .withIgnoreHeaderCase()
                        .withTrim());
        ) {
            List<CSVRecord> records = csvParser.getRecords();
            if (records.isEmpty()){
                throw new EntityNotFoundException("Il n'ya pas des enregistrement dans le fichier", ErrorCodes.ENTITY_NOT_FOUND , new ArrayList<>());
            }
            for (CSVRecord csvRecord : records) {
                i++;
                String firstname = csvRecord.get("firstname");
                String lastname = csvRecord.get("lastname");
                String password = csvRecord.get("password");
                String email = csvRecord.get("email");
                String service = csvRecord.get("service");
                User u = new User();
                u.setLastname(lastname);
                u.setFirstname(firstname);
                u.setPassword(password);
                u.setEmail(email);
                var registerRequestValidator = new RegisterRequestValidator(messageSource);
                var errors = registerRequestValidator.validator(u, new Locale("fr"));
                if (!errors.isEmpty()) {
                    throw new InvalidEntityException(messageSource.getMessage("entity.invalid", null, new Locale("fr")),ErrorCodes.USER_NOT_VALID, errors);
                }
                Optional<oga.microservice.athentification.entities.Service> optionalService = serviceService.findByServiceName(service);
                if (optionalService.isPresent()){
                    u.setService(optionalService.get());
                }else{
                    throw new EntityNotFoundException("Service "+ service + " n'est pas introuvable dans la ligne " + i, ErrorCodes.ENTITY_NOT_FOUND, new ArrayList<>());
                }
                try {
                  users.add(authenticationService.createUser(u, new Locale("fr")).getBody());
                } catch (InvalidEntityException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return ResponseEntity.ok().body(users);
    }


    public List<User> getUsersByService(Long serviceId) {
        if (serviceId != null) {
            List<User> users = this.userRepository.findAllByService_Id(serviceId);
            return users;
        }
        return new ArrayList<>();
    }

}
