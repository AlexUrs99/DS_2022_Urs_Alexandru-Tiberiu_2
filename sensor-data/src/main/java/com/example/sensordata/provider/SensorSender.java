package com.example.sensordata.provider;

import com.example.sensordata.rabbitmq.RabbitMQConfiguration;
import com.example.sensordata.sensor.Sensor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class SensorSender {

    private final RabbitTemplate rabbitTemplate;
    private final SensorProviderService sensorProviderService;
    private List<Sensor> sensors;

    @PostConstruct
    public void initJwtValidator() {
        sensors = sensorProviderService.getSensorValues();
    }

    @Scheduled(cron = "0 0/1 * * * ?")
    public void sendSensors() {
        log.info("Sending...");
        rabbitTemplate.convertAndSend(RabbitMQConfiguration.TOPIC_EXCHANGE, RabbitMQConfiguration.ROUTING_KEY, sensors.remove(0));
    }
}
