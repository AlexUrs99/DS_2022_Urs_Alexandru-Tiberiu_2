package com.example.sensordata.provider;

import com.example.sensordata.reader.SensorCsvFileReader;
import com.example.sensordata.sensor.Sensor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SensorProviderService {

    private final SensorCsvFileReader fileReader;

    public List<Sensor> getSensorValues() {
        try {
            fileReader.readCSVFile("C:\\Users\\alexandru.urs\\Desktop\\Faculta\\DS\\sensor-data\\sensor.csv");
            return fileReader.getValues().stream()
                    .map(SensorProviderService::generateSensor)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    private static Sensor generateSensor(String value) {
        return new Sensor(UUID.randomUUID().toString(), value, Instant.now().toString());
    }
}
