package com.example.sensordata.reader;

import lombok.Getter;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
@Getter
public class SensorCsvFileReader {

    private final List<String> values = new ArrayList<>();

    public void readCSVFile(String filePath) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line = "";

        try {
            while ((line = reader.readLine()) != null) {
                values.add(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
