package oga.microservice.athentification.rest;

import oga.microservice.athentification.entities.FileBlob;
import oga.microservice.athentification.repository.FileBlobRepository;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.ws.rs.core.MediaType;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Optional;

@RestController
@RequestMapping(path = "file")
public class FileBlobController {

    @Autowired
    FileBlobRepository fileBlobRepository;

    public FileBlobController() {

    }
    @CrossOrigin(origins = "*")
    @PostMapping("/upload")
    public ResponseEntity<FileBlob> handleFileUpload(@RequestParam("file") MultipartFile file) throws IOException {
        byte[] fileContent = file.getBytes();
        FileBlob fileEntity = new FileBlob();
        fileEntity.setFile(fileContent);
        String ext = FilenameUtils.getExtension(file.getOriginalFilename());
        fileEntity.setExtention(ext);
        fileEntity = fileBlobRepository.save(fileEntity);
        return ResponseEntity.ok(fileEntity);
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/download/{id}")
    public ResponseEntity<InputStreamResource> downloadFile(@PathVariable Long id) throws IOException {
        Optional<FileBlob> fileEntity = fileBlobRepository.findById(id);
        InputStream inputStream = new ByteArrayInputStream(fileEntity.get().getFile());
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=" + new Date().toString());
        return ResponseEntity.ok().headers(headers).contentType(org.springframework.http.MediaType.valueOf(MediaType.APPLICATION_OCTET_STREAM))
                .body(new InputStreamResource(inputStream));
    }

}
