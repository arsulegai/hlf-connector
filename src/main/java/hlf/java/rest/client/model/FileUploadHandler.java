package hlf.java.rest.client.model;

import hlf.java.rest.client.config.ServerProperties;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.File;

@Slf4j
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FileUploadHandler {
  private String message;
  private HttpStatus status;
  private String path;
}
