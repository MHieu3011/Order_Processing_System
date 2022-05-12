package com.ptit.ops;

import com.ptit.ops.global.ConfigInfo;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

import java.util.Properties;

@SpringBootApplication
public class OrderProcessingSystemApplication {

    public static void main(String[] args) {
        Properties prop = new Properties();
        prop.put("server.port", ConfigInfo.SERVICE_PORT);

        SpringApplicationBuilder applicationBuilder = new SpringApplicationBuilder()
                .sources(OrderProcessingSystemApplication.class)
                .properties(prop);

        applicationBuilder.run(args);
    }

}
