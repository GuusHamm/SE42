package com.gmail.guushamm;

import java.io.*;
import java.security.*;

/**
 * Created by guushamm on 17/06/16.
 */
public class Sign {
	public static void main(String[] args) {
		System.out.println("Choose your file [INPUT.ext]");

		WriteReadFiles writeReadFiles = new WriteReadFiles();
		PrivateKey privateKey = writeReadFiles.getPrivateKey();

		String inputText = writeReadFiles.getInputFile();

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

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (SignatureException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println();

	}
}
