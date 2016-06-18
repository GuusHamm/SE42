package com.gmail.guushamm;

import java.io.*;
import java.security.*;

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
		} catch (NoSuchAlgorithmException | IOException e) {
			e.printStackTrace();
		}
	}

	public static PublicKey getPublicKey() {
		PublicKey key = null;

		try {
			key = (PublicKey) new ObjectInputStream(new FileInputStream("id_rsa.pub")).readObject();
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}

		return key;
	}

	public static PrivateKey getPrivateKey() {
		PrivateKey key = null;

		try {
			key = (PrivateKey) new ObjectInputStream(new FileInputStream("id_rsa.pub")).readObject();
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}

		return key;
	}
}
