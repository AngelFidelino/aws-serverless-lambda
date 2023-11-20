package org.example;

import lombok.extern.slf4j.Slf4j;
import org.example.pojo.MyAPIGatewayProxyRequestEvent;
import org.springframework.cloud.function.adapter.aws.SpringBootRequestHandler;

/**
 * Implement RequestStreamHandler if you want to get access the following parameters: InputStream inputStream, OutputStream outputStream, Context context
 * To make this function handler a generic function => extends SpringBootRequestHandler<Object, String>
 */
@Slf4j
public class FunctionHandler extends SpringBootRequestHandler<MyAPIGatewayProxyRequestEvent, String> {
}
