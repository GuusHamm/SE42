package com.gmail.guushamm;

import javax.crypto.*;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import javax.swing.*;
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
		try {
			String result = "";
			while (result.isEmpty()) {
				System.out.println("What do you want to do [encrypt, decrypt, quit]");
				result = new BufferedReader(new InputStreamReader(System.in)).readLine();

				//In case the user does not use the correct one, ask again
				if (!result.toLowerCase().equals("encrypt") && !(result.toLowerCase().equals("decrypt")) && !(result.toLowerCase().equals("quit"))) {
					System.out.println(String.format("%s is not a supported feature", result));
					result = "";
				}
			}

			if (result.toLowerCase().equals("encrypt")) {
				encrypt();
			} else if (result.toLowerCase().equals("decrypt")) {
				decrypt();
			} else if (result.toLowerCase().equals("quit")) {
				System.exit(0);
			}
		} catch (IOException | NoSuchAlgorithmException | InvalidKeySpecException | NoSuchPaddingException | InvalidKeyException | InvalidAlgorithmParameterException | IllegalBlockSizeException | ClassNotFoundException e) {
			e.printStackTrace();
		} catch (BadPaddingException e1) {
			System.out.println("Woooow that password was incorrect");
		}
	}


	public static void encrypt() throws IOException, NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidAlgorithmParameterException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
		//Creating a salt of 8 bytes (256 bits)
		salt = new byte[8];
		new SecureRandom().nextBytes(salt);


		pbeParameterSpec = new PBEParameterSpec(salt, iterations);
		//Asking the user to create a password for the message, not using Strings whatsoever
		System.out.println("Create a password");
		pbeKeySpec = new PBEKeySpec(readPassword(System.in));

		//Creating a key with the pasword
		secretKeyFactory = SecretKeyFactory.getInstance("PBEWithMD5AndDES");
		SecretKey secretKey = secretKeyFactory.generateSecret(pbeKeySpec);
		//We don't need the key from here on out, so we'll clear it for more security
		pbeKeySpec.clearPassword();

		//Initializing the encryption method
		Cipher cipher = Cipher.getInstance("PBEWithMD5AndDES");
		cipher.init(Cipher.ENCRYPT_MODE, secretKey, pbeParameterSpec);

		//Asking for the message to encrypt
		String result = "";
		while (result.isEmpty()) {
			System.out.println("What do you want to encrypt?");
			result = new BufferedReader(new InputStreamReader(System.in)).readLine();
		}
		//Converting the message to the encrypted text
		byte[] ciphertext = cipher.doFinal(result.getBytes());

		//Getting a random file name with the UUID class
		String uuid = UUID.randomUUID().toString();
		//It was too long so we made it a bit shorter ^_^
		File file = new File(String.format(uuid.substring(0, uuid.indexOf("-"))));
		file.createNewFile();

		//Writing the salt and the encrypted message
		ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(file));
		objectOutputStream.writeObject(salt);
		objectOutputStream.writeObject(ciphertext);
		objectOutputStream.close();
	}

	public static void decrypt() throws IOException, ClassNotFoundException, NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidAlgorithmParameterException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
		//Ask him what file he wants to decrypt
		String result = "";
		while (result.isEmpty()) {
			System.out.println("File to decrypt");
			result = new BufferedReader(new InputStreamReader(System.in)).readLine();
		}
		//Get the file and read the salt and encrypted message
		ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(result));
		salt = (byte[]) objectInputStream.readObject();
		byte[] encryptedMessage = (byte[]) objectInputStream.readObject();
		objectInputStream.close();

		//Creating the specs for the decryption
		pbeParameterSpec = new PBEParameterSpec(salt, iterations);
		//Ask the user for the password for the file
		System.out.println("What is your password?");
		pbeKeySpec = new PBEKeySpec(readPassword(System.in));

		//Creating the key with the password
		secretKeyFactory = SecretKeyFactory.getInstance("PBEWithMD5AndDES");
		SecretKey secretKey = secretKeyFactory.generateSecret(pbeKeySpec);

		//As we don't need the password anymore, we'll clear it
		pbeKeySpec.clearPassword();

		//Setting up for decryption
		Cipher cipher = Cipher.getInstance("PBEWithMD5AndDES");
		cipher.init(Cipher.DECRYPT_MODE, secretKey, pbeParameterSpec);

		//Decrypting message
		byte[] message = cipher.doFinal(encryptedMessage);

		//Showing final message
		System.out.println("Message:");
		System.out.println(new String(message, "UTF-8"));
	}

}
