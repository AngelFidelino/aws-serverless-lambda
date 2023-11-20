package org.example.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.java.Log;
import software.amazon.awssdk.services.ssm.SsmClient;
import software.amazon.awssdk.services.ssm.model.GetParameterRequest;
import software.amazon.awssdk.services.ssm.model.GetParameterResponse;

import java.util.HashMap;
import java.util.Map;

@Log
public class ParameterStoreConfig {

    private final ObjectMapper objectMapper;
    private final Map<String, ParamValue> mapStore;

    public ParameterStoreConfig() {
        objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapStore = new HashMap<>();
    }

    public static class ParamValue<T> {
        T value;

        public void setValue(T value) {
            this.value = value;
        }

        public T getValue() {
            return value;
        }
    }

    public <T> ParamValue<T> getPropertyByName(String greetingParameterName) {
        if (mapStore.containsKey(greetingParameterName)) {
            return mapStore.get(greetingParameterName);
        }
        log.info("Calling Ssm service");
        SsmClient ssmClient = SsmClient.create();
        GetParameterRequest parameterRequest = GetParameterRequest.builder().name(greetingParameterName).build();
        GetParameterResponse parameterResponse = ssmClient.getParameter(parameterRequest);
        if (parameterResponse.parameter().value() != null) {
            final String value = parameterResponse.parameter().value();
            try {
                ParamValue paramValue = objectMapper.readValue(value, ParamValue.class);
                mapStore.put(greetingParameterName, paramValue);
                return paramValue;
            } catch (JsonProcessingException e) {
                throw new RuntimeException("Something went wrong while trying to read the parameter value. Cause: " + e.getMessage());
            }
        }
        throw new RuntimeException("Parameter not found");
    }

}
