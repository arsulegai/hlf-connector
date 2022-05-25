package hlf.java.rest.client.integration;

import hlf.java.rest.client.config.FabricProperties;
import hlf.java.rest.client.config.KafkaProperties;
import hlf.java.rest.client.model.FileUploadHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.MethodOrderer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.File;

@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@AutoConfigureMockMvc
public class ConfigurationControllerIT {
  @LocalServerPort
  private int randomServerPort;

  @Autowired private FabricProperties fabricProperties;
  @MockBean private FileUploadHandler fileUploadHandler;
  @Autowired private KafkaProperties kafkaProperties;

  @Test
  void uploadConfigFile() throws Exception {
    String applicationYMLFile = "src/test/java/hlf/java/rest/client/integration/mockfiles/application.yml";
    RestTemplate restTemplate = new RestTemplate();
    final String baseUrl = "http://localhost:" + this.randomServerPort + "/configuration/update/config-file";
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
    headers.set("api-key", "ePVYHwAaQ0V1XOTX6U");
    HttpEntity<byte[]> requestEntity
        = new HttpEntity<>(FileUtils.readFileToByteArray(new File(applicationYMLFile)), headers);
    ResponseEntity<String> response = restTemplate.postForEntity(baseUrl, requestEntity, String.class);
    log.info("Response Code {}", response.getStatusCode());
    log.info("Response Body {}", response.getBody());
  }

}
