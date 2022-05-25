package hlf.java.rest.client.controller;

import hlf.java.rest.client.config.FabricProperties;
import hlf.java.rest.client.config.KafkaProperties;
import hlf.java.rest.client.model.FileUploadHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("/configuration")
public class ConfigurationController {

  @PostMapping("/update/config-file")
  public ResponseEntity<FileUploadHandler> handleFileUpload(@RequestBody byte[] file)
      throws IOException {
    FileUploadHandler response = FileUploadHandler.builder().build().uploadFile(file);
    return new ResponseEntity<>(response, response.getStatus());
  }
}
