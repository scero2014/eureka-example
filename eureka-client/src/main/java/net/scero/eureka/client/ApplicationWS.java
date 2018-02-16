package net.scero.eureka.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.ribbon.RibbonClient;

/**
 * Main principal de la aplicación Spring Boot
 * @author jlnogueira on 15/02/2018
 */
@SpringBootApplication
@EnableDiscoveryClient
@RibbonClient(name = "foo")
public class ApplicationWS {
	public static void main(String[] args){
		SpringApplication.run(ApplicationWS.class, args);
	}
}
