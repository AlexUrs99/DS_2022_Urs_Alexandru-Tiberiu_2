package com.example.sensordata.provider;

import com.example.sensordata.rabbitmq.RabbitMQConfiguration;
import com.example.sensordata.sensor.SensorRegistrationResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class SensorSender {

    private final RabbitTemplate rabbitTemplate;
    private final SensorProviderService sensorProviderService;
    private List<SensorRegistrationResponseDto> sensorRegistrationResponseDtos;

    @PostConstruct
    public void initJwtValidator() {
        sensorRegistrationResponseDtos = sensorProviderService.getSensorValues();
    }

    @Scheduled(cron = "0 0/1 * * * ?")
    public void sendSensors() {
        log.info("Sending...");
        SensorRegistrationResponseDto sensor = sensorRegistrationResponseDtos.remove(0);
        sensor.setTimestamp(String.valueOf(Instant.now().toEpochMilli()));
        rabbitTemplate.convertAndSend(RabbitMQConfiguration.TOPIC_EXCHANGE, RabbitMQConfiguration.ROUTING_KEY, sensor);
    }
}
