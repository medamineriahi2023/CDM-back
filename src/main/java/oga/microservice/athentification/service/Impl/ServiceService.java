package oga.microservice.athentification.service.Impl;

import oga.microservice.athentification.dtos.ServiceDTO;
import oga.microservice.athentification.entities.Service;
import oga.microservice.athentification.exceptions.EntityNotFoundException;
import oga.microservice.athentification.exceptions.InvalidEntityException;
import oga.microservice.athentification.repository.ServiceRepository;
import oga.microservice.athentification.service.IServiceService;
import oga.microservice.athentification.service.abstracts.AbstractCrudService;
import oga.microservice.athentification.validator.enums.ErrorCodes;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Service
@Transactional
public class ServiceService extends AbstractCrudService<Service, ServiceDTO> implements IServiceService {
   @Autowired
    ServiceRepository serviceRepository;
    @Override
    public Optional<Service> findByServiceName(String name) {
        return serviceRepository.findServiceByServiceName(name);
    }


    public ResponseEntity<List<Service>> importService(MultipartFile file) throws IOException, EntityNotFoundException, InvalidEntityException {
        List<Service> services = new ArrayList<>();
        int i = 0;
        try (
                InputStream is = file.getInputStream();
                Reader reader = new InputStreamReader(is, "utf-8");
                BufferedReader br = new BufferedReader(reader);

                CSVParser csvParser = new CSVParser(br, CSVFormat.DEFAULT
                        .withFirstRecordAsHeader()
                        .withIgnoreHeaderCase()
                        .withTrim())
        ) {
            List<CSVRecord> records = csvParser.getRecords();
            if (records.isEmpty()){
                throw new EntityNotFoundException("Il n'ya pas des enregistrement dans le fichier", ErrorCodes.ENTITY_NOT_FOUND , new ArrayList<>());
            }
            for (CSVRecord csvRecord : records) {
                i++;
                String serviceName = csvRecord.get("serviceName");
                String responsable = csvRecord.get("responsable");
                Service service = new Service();
                service.setServiceName(serviceName);
                service.setResponsable(responsable);
                    services.add(this.create(service));
            }
        }
        return ResponseEntity.ok().body(services);
    }
}
