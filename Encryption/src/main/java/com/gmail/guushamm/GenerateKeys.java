package com.gmail.guushamm;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * Created by guushamm on 17/06/16.
 */
public class GenerateKeys {
	public static void main(String[] args) {
		System.out.println("Generating your keys....");

		try {
			KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
			SecureRandom secureRandom = new SecureRandom();
			keyPairGenerator.initialize(1024, secureRandom);

			KeyPair keyPair = keyPairGenerator.generateKeyPair();

			new ObjectOutputStream(new FileOutputStream("id_rsa.pub")).writeObject(keyPair.getPublic());
			new ObjectOutputStream(new FileOutputStream("id_rsa")).writeObject(keyPair.getPrivate());

			System.out.println("Keys have been generated");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
