package org.example.functions;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.example.config.ParameterStoreConfig;
import org.example.pojo.MyAPIGatewayProxyRequestEvent;
import org.example.pojo.MyAPIGatewayProxyResponseEvent;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.http.HttpStatusCode;

import java.util.function.Function;

@Slf4j
@Component("helloFunction")
public class HelloFunction implements Function<MyAPIGatewayProxyRequestEvent, MyAPIGatewayProxyResponseEvent> {

    private String greetingParameterName = "/java-app/dev/greeting-json";
    private final ObjectMapper objectMapper;
    private final ParameterStoreConfig parameterStoreConfig;

    public HelloFunction() {
        objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        parameterStoreConfig = new ParameterStoreConfig();
    }

    @Override
    public MyAPIGatewayProxyResponseEvent apply(MyAPIGatewayProxyRequestEvent request) {
        MyAPIGatewayProxyResponseEvent response = new MyAPIGatewayProxyResponseEvent();
        response.setStatusCode(HttpStatusCode.OK);

        try {
            log.info("Executing Function: " + request);
            //lambdaRequest.setBody(parameterStoreConfig.<String>getPropertyByName(greetingParameterName).getValue());
            request.setBody("Hello!!!!");
            String json = objectMapper.writeValueAsString(request);
            response.setBody(json);

        } catch (JsonProcessingException | RuntimeException e) {
            response.setStatusCode(HttpStatusCode.BAD_REQUEST);
            response.setBody("{ \"message\" : \"Something went wrong while trying to read the request. Make sure to use the right request format\"}");
            log.error("Error: " + e.getMessage());
        }

        return response;
    }
}