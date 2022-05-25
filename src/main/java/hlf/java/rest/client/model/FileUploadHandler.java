package hlf.java.rest.client.model;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.http.HttpStatus;

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
  public FileUploadHandler uploadFile(byte[] file){
    try {
      String fileLocation = "/usr/local/config/application.yml";
      log.info("fileLocation {}", fileLocation);
      File path = new File(fileLocation);
      FileUtils.writeByteArrayToFile(path, file);
      return FileUploadHandler.builder()
          .message("File is uploaded successfully!")
          .path(path.getPath())
          .status(HttpStatus.OK)
          .build();
    } catch (Exception e) {
      e.getStackTrace();
      return FileUploadHandler.builder()
          .message("File uploaded failed!")
          .path(null)
          .status(HttpStatus.INTERNAL_SERVER_ERROR)
          .build();
    }
  }

}

