package oga.microservice.athentification.service;

import oga.microservice.athentification.dtos.FileBlobDTO;
import oga.microservice.athentification.entities.FileBlob;
import oga.microservice.athentification.service.abstracts.ICrudService;

public interface IFileBlobService extends ICrudService<FileBlob, FileBlobDTO> {
}
