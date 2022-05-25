package hlf.java.rest.client.service.impl;

import hlf.java.rest.client.config.ServerProperties;
import hlf.java.rest.client.model.FileUploadHandler;
import hlf.java.rest.client.service.ControllerService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
@Slf4j
public class ControllerServiceImpl implements ControllerService {

    @Autowired ServerProperties serverProperties;

    @Override
    public FileUploadHandler updateConfiguration(byte[] newConfigBytes) {
            try {
                String fileLocation = serverProperties.getConfigLocation();
                log.info("fileLocation {}", fileLocation);
                File path = new File(fileLocation);
                FileUtils.writeByteArrayToFile(path, newConfigBytes);
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
