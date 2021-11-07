package com.nab.config;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class ConfigApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConfigApplication.class, args);

        System.out.println("------------------------------START Config Server Application------------------------------");
        System.out.println("   Application         : Config Server");
        System.out.println("   Url Eureka	       : http://localhost:8000/");
        System.out.println("-------------------------START SUCCESS Config Server Application --------------------------");
    }

}
