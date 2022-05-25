package hlf.java.rest.client.integration;

import hlf.java.rest.client.config.FabricProperties;
import hlf.java.rest.client.config.KafkaProperties;
import hlf.java.rest.client.model.FileUploadHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.client.RestTemplate;

import java.io.File;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@AutoConfigureMockMvc
public class ConfigurationControllerIT {

  @LocalServerPort
  private int randomServerPort;
  @Autowired private MockMvc mockMvc;
  @Autowired private FabricProperties fabricProperties;

  @MockBean private FileUploadHandler fileUploadHandler;
  @Autowired private KafkaProperties kafkaProperties;


  @BeforeAll
  void setUp(){

  }


//  @Test
//  @Order(1)
//  void fetchFabricProperties() throws Exception {
//    MvcResult result = mockMvc
//        .perform(
//            get("/configuration/properties/fabric-properties")
//                .header("api-key", "ePVYHwAaQ0V1XOTX6U")
//        )
//        .andExpect(content().contentType("application/json"))
//        .andExpect(status().isOk()).andReturn();
//    log.info("Fabric {}", result.getResponse().getContentAsString());
//  }
//
//  @Test
//  @Order(2)
//  void fetchKafkaProperties() throws Exception {
//    MvcResult result = mockMvc
//        .perform(
//            get("/configuration/properties/kafka-properties")
//                .header("api-key", "ePVYHwAaQ0V1XOTX6U")
//        )
//        .andExpect(content().contentType("application/json"))
//        .andExpect(status().isOk()).andReturn();
//    log.info("Kafka {}", result.getResponse().getContentAsString());
//  }

  @Test
  //@Order(3)
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
//    String applicationYMLFile = "src/test/java/hlf/java/rest/client/integration/mockfiles/application.yml";
//    byte[] applicationYMLFileBytes = IOUtils.toByteArray(ResourceUtils.getURL(applicationYMLFile));
//    MockMultipartFile multipartFile = new MockMultipartFile("file", "application.yml",
//        "application/yml", applicationYMLFileBytes);
//    mockMvc.perform(multipart("/configuration/update/config-file").file(multipartFile)
//            .header("api-key", "ePVYHwAaQ0V1XOTX6U"))
//        .andExpect(status().isOk());
//
//    MvcResult result = mockMvc
//        .perform(
//            post("/actuator/refresh")
//                .header("api-key", "ePVYHwAaQ0V1XOTX6U")
//        )
//        .andExpect(content().contentType("application/vnd.spring-boot.actuator.v3+json"))
//        .andExpect(status().isOk()).andReturn();
//    Assertions.assertEquals("[]", result.getResponse().getContentAsString());
//    log.info("Actuator {}", result.getResponse().getContentAsString());
//    log.info("File {}", applicationYMLFileBytes.toString());
  }


}
