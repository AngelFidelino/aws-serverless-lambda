package org.example.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.pojo.AwsSecret;
import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueRequest;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueResponse;

import java.util.HashMap;
import java.util.Map;


public class SecretManagerConfig {
    private String dbSecretName = "db-key";
    private final ObjectMapper objectMapper;
    private final Map<String, AwsSecret> secretStore;

    public SecretManagerConfig() {
        objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        secretStore = new HashMap<>();
    }

    /**
     * The functionality of getting aws secret values can be executed on the aws infrastructure or locally (when debugging for example)
     */
    public SecretsManagerClient getSecretsManagerClient() {
        Region region = Region.US_EAST_1;
        return SecretsManagerClient.builder()
                .region(region)
                .credentialsProvider(ProfileCredentialsProvider.create())
                .build();

    }

    private AwsSecret getSecret(SecretsManagerClient secretsClient, String secretName) {
        if (secretStore.containsKey(secretName)) {
            return secretStore.get(secretName);
        }
        GetSecretValueRequest secretValueRequest = GetSecretValueRequest.builder()
                .secretId(secretName)
                .build();
        GetSecretValueResponse secretValueResponse = secretsClient.getSecretValue(secretValueRequest);
        String secret = secretValueResponse.secretString();
        if (secret != null) {
            try {
                AwsSecret awsSecret = objectMapper.readValue(secret, AwsSecret.class);
                secretStore.put(secretName, awsSecret);
                return awsSecret;
            } catch (JsonProcessingException e) {
                throw new RuntimeException("Something went wrong while trying to read the secret value. Cause: " + e.getMessage());
            }
        }
        throw new RuntimeException("Secret value not found");
    }

}
