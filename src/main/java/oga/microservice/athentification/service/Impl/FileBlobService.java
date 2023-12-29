package oga.microservice.athentification.service.Impl;

import oga.microservice.athentification.dtos.FileBlobDTO;
import oga.microservice.athentification.entities.FileBlob;
import oga.microservice.athentification.service.IFileBlobService;
import oga.microservice.athentification.service.abstracts.AbstractCrudService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class FileBlobService extends AbstractCrudService<FileBlob, FileBlobDTO> implements IFileBlobService {
}
