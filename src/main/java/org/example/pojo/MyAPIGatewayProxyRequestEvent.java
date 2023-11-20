package org.example.pojo;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import lombok.ToString;

@ToString
public class MyAPIGatewayProxyRequestEvent extends APIGatewayProxyRequestEvent {

    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
