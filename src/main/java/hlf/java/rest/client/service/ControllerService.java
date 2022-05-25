package hlf.java.rest.client.service;

import hlf.java.rest.client.model.FileUploadHandler;

public interface ControllerService {
    FileUploadHandler updateConfiguration(byte[] newConfigBytes);
}
