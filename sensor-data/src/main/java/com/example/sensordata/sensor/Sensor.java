package com.example.sensordata.sensor;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class Sensor implements Serializable {
    private String id;
    private String value;
    private String timestamp;
}
