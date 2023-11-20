package org.example.pojo;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;

import java.util.HashMap;
import java.util.Map;

public class MyAPIGatewayProxyResponseEvent extends APIGatewayProxyResponseEvent {
    public MyAPIGatewayProxyResponseEvent() {
        Map<String, String> headers = new HashMap<>();
        //Default configuration for CORS
        headers.put("Access-Control-Allow-Headers", "Content-Type");
        headers.put("Access-Control-Allow-Origin", "*");
        headers.put("Access-Control-Allow-Methods", "OPTIONS,POST,GET");
        setHeaders(headers);
    }
}
