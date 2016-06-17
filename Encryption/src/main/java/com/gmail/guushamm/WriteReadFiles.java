package com.gmail.guushamm;

import java.io.*;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * Created by Nekkyou on 17-6-2016.
 */
public class WriteReadFiles {
	private FileOutputStream fos;
	private ObjectOutputStream out;

	private FileInputStream fis;
	private ObjectInputStream ois;

	public WriteReadFiles() {

	}

	public void writeKeysToFile(KeyPair keyPair) {
		try {
			fos = new FileOutputStream("private.key");
			out = new ObjectOutputStream(fos);

			out.writeObject(keyPair.getPrivate());


			fos = new FileOutputStream("public.key");
			out = new ObjectOutputStream(fos);

			out.writeObject(keyPair.getPublic());

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	public PublicKey getPublicKey() {
		PublicKey key = null;

		try {
			fis = new FileInputStream("public.key");
			ois = new ObjectInputStream(fis);

			key = (PublicKey) ois.readObject();
			return key;

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	public PrivateKey getPrivateKey() {
		PrivateKey key = null;

		try {
			fis = new FileInputStream("private.key");
			ois = new ObjectInputStream(fis);

			key = (PrivateKey) ois.readObject();
			return key;

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		return null;
	}

}
