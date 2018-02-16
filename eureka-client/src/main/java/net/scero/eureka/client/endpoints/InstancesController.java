package net.scero.eureka.client.endpoints;

import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.client.config.IClientConfig;
import lombok.extern.slf4j.Slf4j;
import net.scero.eureka.client.endpoints.responses.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Ejemplo de controlador
 *
 * @author jlnogueira on 15/02/2018
 */
@RestController
@Slf4j
public class InstancesController {
	@Autowired
	private DiscoveryClient discoveryClient;

	@Autowired
	private LoadBalancerClient loadBalancer;

	private ObjectMapper objectMapper;

	public InstancesController(){
		objectMapper = new ObjectMapper();
	}

	@RequestMapping(method = RequestMethod.GET, value = "/instances", produces = {MediaType.APPLICATION_JSON_VALUE}, headers = "Accept=application/json")
	public ResponseEntity<String> instancesController() {
		HttpHeaders headers = new HttpHeaders();
		String responseExample;
		HttpStatus httpStatus;
		try {
			List<Service> services = discoveryClient.getServices().stream().map(e -> new Service(e, discoveryClient.getInstances(e))).collect(Collectors.toList());
			responseExample = objectMapper.writeValueAsString(services);
			httpStatus = HttpStatus.OK;
		} catch (Exception e) {
			log.error("Scrapping error", e);
			responseExample = e.toString();
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<>(responseExample, headers, httpStatus);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/instances/{service}", produces = {MediaType.APPLICATION_JSON_VALUE}, headers = "Accept=application/json")
	public ResponseEntity<String> instancesServiceController(@PathVariable("service") String service) {
		HttpHeaders headers = new HttpHeaders();
		String responseExample;
		HttpStatus httpStatus;
		try {
			responseExample = objectMapper.writeValueAsString(discoveryClient.getInstances(service));
			httpStatus = HttpStatus.OK;
		} catch (Exception e) {
			log.error("Scrapping error", e);
			responseExample = e.toString();
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<>(responseExample, headers, httpStatus);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/ribbon", produces = {MediaType.APPLICATION_JSON_VALUE}, headers = "Accept=application/json")
	public ResponseEntity<String> ribbonController() {
		HttpHeaders headers = new HttpHeaders();
		String responseExample;
		HttpStatus httpStatus;
		try {
			ServiceInstance serviceInstance = loadBalancer.choose("eureka-client-app");
			responseExample = objectMapper.writeValueAsString(serviceInstance);
			httpStatus = HttpStatus.OK;
		} catch (Exception e) {
			log.error("Scrapping error", e);
			responseExample = e.toString();
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<>(responseExample, headers, httpStatus);
	}
}
