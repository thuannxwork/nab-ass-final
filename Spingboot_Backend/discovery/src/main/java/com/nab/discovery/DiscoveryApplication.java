package com.nab.discovery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class DiscoveryApplication {

    public static void main(String[] args) {
        SpringApplication.run(DiscoveryApplication.class, args);
        System.out.println("------------------------------START Discovery Eureka Application------------------------------");
        System.out.println("   Application         : Discovery Eureka");
        System.out.println("   Url Eureka	       : http://localhost:8761/");
        System.out.println("-------------------------START SUCCESS Discovery Eureka Application --------------------------");
    }

}
