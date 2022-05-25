package hlf.java.rest.client.integration;

import hlf.java.rest.client.config.FabricProperties;
import hlf.java.rest.client.config.KafkaProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.IOException;

@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ConfigurationControllerIT {
  @LocalServerPort
  private int randomServerPort;
  @Autowired private FabricProperties fabricProperties;
  @Autowired private KafkaProperties kafkaProperties;

  @Test
  void uploadConfigFile() throws Exception {
    String applicationYMLFile = "src/test/resources/integration/sample-application.yml";
    RestTemplate restTemplate = new RestTemplate();
    String baseUrl = "http://localhost:" + this.randomServerPort + "/configuration/update/config-file";
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
    headers.set("api-key", "ePVYHwAaQ0V1XOTX6U");
    HttpEntity<byte[]> requestEntity
        = new HttpEntity<>(FileUtils.readFileToByteArray(new File(applicationYMLFile)), headers);
    ResponseEntity<String> response = restTemplate.postForEntity(baseUrl, requestEntity, String.class);
    log.info("Response Code {}", response.getStatusCode());
    log.info("Response Body {}", response.getBody());
    triggerActuatorRefresh();
    // Now is the time to verify beans! It will prove what refresh is capable of
    Assertions.assertEquals("expected-key", fabricProperties.getClient().getRest().getApikey());
  }

  void triggerActuatorRefresh(){
    RestTemplate restTemplate = new RestTemplate();
    final String baseUrl = "http://localhost:" + this.randomServerPort + "/actuator/refresh";
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
//    headers.set("api-key", "ePVYHwAaQ0V1XOTX6U");
    HttpEntity<String> entity = new HttpEntity<>(null, headers);
    ResponseEntity<String> response = restTemplate.postForEntity(baseUrl, entity, String.class);
    log.info("response {}", response.getBody());
  }

  @AfterEach
  public void cleanUp() throws IOException {
    FileUtils.delete(new File("src/test/resources/emptydir/sample-application.yml"));
    FileUtils.copyFile(new File("src/test/resources/integration/application.yml"), new File("src/test/resources/application.yml"));
  }
}
