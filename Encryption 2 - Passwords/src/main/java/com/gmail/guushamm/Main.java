package com.gmail.guushamm;

import javax.crypto.*;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import java.io.*;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.UUID;

import static sun.security.util.Password.readPassword;

public class Main {
	static PBEKeySpec pbeKeySpec;
	static PBEParameterSpec pbeParameterSpec;
	static SecretKeyFactory secretKeyFactory;
	static byte[] salt;


	static int iterations = 25;

	public static void main(String[] args) {
		// write your code here
//		System.out.println("What is the password");
//		String result = new BufferedReader(new InputStreamReader(System.in)).readLine();


		try {
			String result = "";
			while (result.isEmpty()) {
				System.out.println("What do you want to do [encrypt decrypt]");
				result = new BufferedReader(new InputStreamReader(System.in)).readLine();

				if (!result.toLowerCase().equals("encrypt") && !(result.toLowerCase().equals("decrypt"))) {
					System.out.println(String.format("%s is not a supported feature", result));
					result = "";
				}
			}

			if (result.toLowerCase().equals("encrypt")) {
				salt = new byte[8];

				new SecureRandom().nextBytes(salt);

				pbeParameterSpec = new PBEParameterSpec(salt, iterations);

				System.out.println("What is your password?");
				pbeKeySpec = new PBEKeySpec(readPassword(System.in));

				secretKeyFactory = SecretKeyFactory.getInstance("PBEWithMD5AndDES");
				SecretKey secretKey = secretKeyFactory.generateSecret(pbeKeySpec);
				pbeKeySpec.clearPassword();

				Cipher cipher = Cipher.getInstance("PBEWithMD5AndDES");
				cipher.init(Cipher.ENCRYPT_MODE, secretKey, pbeParameterSpec);


				result = "";
				while (result.isEmpty()) {
					System.out.println("What do you want to encrypt?");
					result = new BufferedReader(new InputStreamReader(System.in)).readLine();
				}

				byte[] ciphertext = cipher.doFinal(result.getBytes());

				String uuid = UUID.randomUUID().toString();
				File file = new File(String.format(uuid.substring(0, uuid.indexOf("-"))));

				file.createNewFile();

				ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(file));
				objectOutputStream.writeObject(salt);
				objectOutputStream.writeObject(ciphertext);
				objectOutputStream.close();

			} else if (result.toLowerCase().equals("decrypt")) {
				result = "";
				while (result.isEmpty()) {
					System.out.println("File to decrypt");
					result = new BufferedReader(new InputStreamReader(System.in)).readLine();
				}
				ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(result));
				salt = (byte[]) objectInputStream.readObject();
				byte[] encryptedMessage = (byte[]) objectInputStream.readObject();
				objectInputStream.close();

				pbeParameterSpec = new PBEParameterSpec(salt, iterations);
				System.out.println("What is your password?");
				pbeKeySpec = new PBEKeySpec(readPassword(System.in));

				secretKeyFactory = SecretKeyFactory.getInstance("PBEWithMD5AndDES");
				SecretKey secretKey = secretKeyFactory.generateSecret(pbeKeySpec);

				pbeKeySpec.clearPassword();

				Cipher cipher = Cipher.getInstance("PBEWithMD5AndDES");
				cipher.init(Cipher.DECRYPT_MODE, secretKey, pbeParameterSpec);

				byte[] message = cipher.doFinal(encryptedMessage);

				System.out.println("Message:");
				System.out.println(new String(message, "UTF-8"));
			}
		} catch (IOException | NoSuchAlgorithmException | InvalidKeySpecException | NoSuchPaddingException | InvalidKeyException | InvalidAlgorithmParameterException | IllegalBlockSizeException | ClassNotFoundException e) {
			e.printStackTrace();
		} catch (BadPaddingException e1) {
			System.out.println("Woooow that password was incorrect");
		}
	}
}
