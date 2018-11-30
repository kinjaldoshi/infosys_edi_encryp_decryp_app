/**
 * 
 */
package com.stg.insurance.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author abhinavkumar.gupta
 *
 */
@Component
public class TransmissionProperties {

	@Value("${transmission.app.url}")
	private String transmissionUrl;

	public String getTransmissionUrl() {
		return transmissionUrl;
	}

	public void setTransmissionUrl(String transmissionUrl) {
		this.transmissionUrl = transmissionUrl;
	}
}
