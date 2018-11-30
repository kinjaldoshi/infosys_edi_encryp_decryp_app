/**
 * 
 */
package com.stg.insurance.controller;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.stg.insurance.adapter.TransmissionAdpater;

/**
 * @author abhinavkumar.gupta
 *
 */
@RequestMapping("/security")
@RestController
@CrossOrigin
public class EcryptionController {

	private static final String key = "aesEncryptionKey";
	private static final String initVector = "encryptionIntVec";

	@Autowired
	TransmissionAdpater transmissionAdpater;

	@PostMapping(value = "/encrypt")
	public void getEncryptedStream(@RequestParam(name = "file", required = false) MultipartFile file,
			@RequestParam(name = "encrptionString", required = false) String encrptionString) {
		try {
			IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
			SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
			cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
			byte[] encrypted = null;
			if (file != null) {
				encrypted = cipher.doFinal(file.getBytes());
			} else {
				encrypted = cipher.doFinal(encrptionString.getBytes());
			}
			byte[] finalEncodedBytes = Base64.encodeBase64(encrypted);
			transmissionAdpater.callTransmissionApi(finalEncodedBytes);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	@GetMapping(value = "/decrypt")
	public String getEncryptedStream(@RequestBody String ecodedString) {

		if (ecodedString != null) {

			try {
				IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
				SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");

				Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
				cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
				byte[] original = cipher.doFinal(Base64.decodeBase64(ecodedString));
				System.out.println(ecodedString);
				System.out.println(new String(original));
				return new String(original);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return null;
	}

}
