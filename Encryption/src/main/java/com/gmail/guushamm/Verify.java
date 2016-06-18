package com.gmail.guushamm;

import java.io.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.Signature;
import java.security.SignatureException;

/**
 * Created by Nekkyou on 17-6-2016.
 */
public class Verify {

	public static void main(String[] args) {
		boolean fileIsValid = false;
		try{
			String name = "";

			while (name.isEmpty()){
				System.out.println("Who's signature do you want to check?");
				name = new BufferedReader(new InputStreamReader(System.in)).readLine();
			}
			ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(String.format("INPUT(SignedBy%s)",name.replace(" ",""))));
			int singnatureSize =  objectInputStream.readInt();
			byte[] signatureBytes = (byte[]) objectInputStream.readObject();

			String text = (String) objectInputStream.readObject();

			Signature signature = Signature.getInstance("SHA1withRSA");
			signature.initVerify(GenerateKeys.getPublicKey());
			fileIsValid = signature.verify(signatureBytes);

		} catch (IOException |  ClassNotFoundException | NoSuchAlgorithmException | InvalidKeyException | SignatureException e) {
			e.printStackTrace();
		}

		if (fileIsValid){
			System.out.println("That signature on this file is valid");
		}else{
			System.out.println("Woooow watch out with that file, the signature does not seem to be valid");
		}
	}
}


