package hlf.java.rest.client.controller;

import hlf.java.rest.client.config.FabricProperties;
import hlf.java.rest.client.config.KafkaProperties;
import hlf.java.rest.client.model.FileUploadHandler;
import hlf.java.rest.client.service.ControllerService;
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

  @Autowired
  ControllerService controllerService;

  @PostMapping("/update/config-file")
  public ResponseEntity<FileUploadHandler> handleFileUpload(@RequestBody byte[] fileInBytes)
      throws IOException {
    FileUploadHandler response = controllerService.updateConfiguration(fileInBytes);
    return new ResponseEntity<>(response, response.getStatus());
  }
}
