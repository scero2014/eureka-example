package net.scero.eureka.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.context.annotation.ComponentScan;

/**
 * Main principal de la aplicación Spring Boot
 * @author jlnogueira on 15/02/2018
 */
@SpringBootApplication
@EnableEurekaServer
public class ApplicationWS {
	public static void main(String[] args){
		SpringApplication.run(ApplicationWS.class, args);
	}
}
