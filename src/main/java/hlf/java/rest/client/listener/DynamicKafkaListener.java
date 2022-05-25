package hlf.java.rest.client.listener;

import hlf.java.rest.client.config.KafkaConsumerConfig;
import hlf.java.rest.client.config.KafkaProperties;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.AbstractMessageListenerContainer;
import org.springframework.kafka.listener.AcknowledgingMessageListener;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.listener.ContainerProperties;

/*
 * This class is the configuration class for dynamically creating consumers to receiving the blockchain
 *  transaction from Kafka Topic and send it to Fabric channel.
 */
@Slf4j
@Configuration
@ConditionalOnProperty("kafka.integration")
public class DynamicKafkaListener {

  @Autowired KafkaProperties.ConsumerProperties consumerProperties;

  @Autowired KafkaConsumerConfig kafkaConsumerConfig;

  @Autowired TransactionConsumer transactionConsumer;

  private Set<ConcurrentMessageListenerContainer<String, String>> listeners;

  @EventListener
  public void handleEvent(ContextRefreshedEvent event) {
    // close or kill all existing listeners
    cleanListeners();
    List<KafkaProperties.Consumer> consumerList = consumerProperties.getIntegrationPoints();
    consumerList.forEach(this::generateAndStartConsumerGroup);
  }

  private void generateAndStartConsumerGroup(KafkaProperties.Consumer consumer) {
    DefaultKafkaConsumerFactory<String, String> factory =
        kafkaConsumerConfig.consumerFactory(consumer);
    ContainerProperties containerProperties = new ContainerProperties(consumer.getTopic());
    containerProperties.setAckMode(ContainerProperties.AckMode.MANUAL_IMMEDIATE);
    containerProperties.setMessageListener(
            (AcknowledgingMessageListener<String, String>) (message, acknowledgment) -> transactionConsumer.listen(message, acknowledgment));
    ConcurrentMessageListenerContainer<String, String> container =
        new ConcurrentMessageListenerContainer<>(factory, containerProperties);
    listeners.add(container);
    container.start();
    log.debug(
        "Created kafka message listener container"
            + container.metrics().keySet().iterator().next());
  }

  private void cleanListeners() {
    if (listeners == null) {
      listeners = new HashSet<>();
    }
    listeners.forEach(AbstractMessageListenerContainer::stop);
    listeners.clear();
  }
}
