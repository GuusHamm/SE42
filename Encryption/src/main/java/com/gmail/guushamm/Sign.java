package com.gmail.guushamm;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.*;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * Created by guushamm on 17/06/16.
 */
public class Sign {
	public static void main(String[] args) {
		System.out.println("Choose your file [INPUT.ext]");

		PrivateKey privateKey = GenerateKeys.getPrivateKey();

		String inputText = getInputFile();

		try {
			Signature signature = Signature.getInstance("SHA1withRSA");
			signature.initSign(privateKey);

			byte[] signatureBytes = signature.sign();

			String name = "";

			while (name.isEmpty()){
				System.out.println("What is your name?");
				name = new BufferedReader(new InputStreamReader(System.in)).readLine();
			}

			File signatureFile = new File(String.format("INPUT(SignedBy%s)",name.replace(" ","")));

			signatureFile.createNewFile();

			ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(signatureFile));

			objectOutputStream.writeInt(signatureBytes.length);
			objectOutputStream.writeObject(signatureBytes);
			objectOutputStream.writeObject(inputText);
			objectOutputStream.close();

		} catch (NoSuchAlgorithmException | InvalidKeyException | SignatureException | IOException e) {
			e.printStackTrace();
		}
		System.out.println();

	}

	public static String getInputFile() {
		String input = "";
		try {
			input = new String(Files.readAllBytes(Paths.get("INPUT.ext")), UTF_8);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return input;
	}
}
