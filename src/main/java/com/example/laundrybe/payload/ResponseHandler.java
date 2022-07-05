package com.example.laundrybe.payload;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class ResponseHandler {
    public ResponseEntity<Object> generateResponse(String message, HttpStatus status, Object responseObject){
        Map<String, Object> map = new HashMap<>();
        map.put("message", message);
        map.put("status", status.value());
        map.put("data", responseObject);
        return new ResponseEntity<>(map, status);
    }
}
