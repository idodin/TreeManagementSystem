package ca.mcgill.ecse321.TMS.controller.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "client.web")
public class WebFrontendProperties {

	/**
	 * The IP adress of the web frontend client
	 */
	private String ip = "http://ecse321-9.ece.mcgill.ca";
	/**
	 * The port on which the web frontend listens
	 */
	private int port = 8080;

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}
}
