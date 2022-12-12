package com.example.sensordata.provider;

import com.example.sensordata.reader.SensorCsvFileReader;
import com.example.sensordata.sensor.SensorRegistrationResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SensorProviderService {

    private final SensorCsvFileReader fileReader;

    public List<SensorRegistrationResponseDto> getSensorValues() {
        try {
            fileReader.readCSVFile("sensor.csv");
            return fileReader.getValues().stream()
                    .map(SensorProviderService::generateSensor)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    private static SensorRegistrationResponseDto generateSensor(String value) {
        return new SensorRegistrationResponseDto("1", value, String.valueOf(Instant.now().toEpochMilli()));
    }
}
