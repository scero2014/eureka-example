package net.scero.eureka.client.endpoints.responses;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.cloud.client.ServiceInstance;

/**
 * @author jlnogueira on 16/02/2018
 */
@AllArgsConstructor
@Getter
public class Service{
	private String serviceName;
	private List<ServiceInstance> serviceInstances;
}
