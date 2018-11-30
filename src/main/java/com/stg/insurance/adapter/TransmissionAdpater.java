/**
 * 
 */
package com.stg.insurance.adapter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.stg.insurance.properties.TransmissionProperties;

/**
 * @author abhinavkumar.gupta
 *
 */
@Component
@Configuration
public class TransmissionAdpater {

	@Autowired
	private TransmissionProperties transmissionProperties;

	@Bean
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}

	@Autowired
	RestTemplate restTemplate;

	public void callTransmissionApi(byte[] value) throws IOException {

		File file = File.createTempFile("temp", "transformer");
		try (FileOutputStream fos = new FileOutputStream(file)) {
			fos.write(value);
		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
		UriComponentsBuilder uriBuilder = UriComponentsBuilder
				.fromUriString(transmissionProperties.getTransmissionUrl());
		MultiValueMap<String, Object> obj = new LinkedMultiValueMap<>();
		obj.add("file", new FileSystemResource(file));
		restTemplate.postForEntity(uriBuilder.toUriString(), obj, MultiValueMap.class);

	}

}
