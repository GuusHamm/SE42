package com.gmail.guushamm;

import javax.swing.*;
import java.io.*;
import java.security.*;

/**
 * Created by Nekkyou on 17-6-2016.
 */
public class Verify {

	public static void main(String[] args) {
		File inputEXTSigned = null;

		JFileChooser fc = new JFileChooser();
		fc.setCurrentDirectory(new File(System.getProperty("user.home")));
		int result = fc.showOpenDialog(null);

		if (result == JFileChooser.APPROVE_OPTION && fc.getSelectedFile().getName().contains("INPUT")) {
			inputEXTSigned = fc.getSelectedFile();
		}
		System.out.println(inputEXTSigned.getPath());


		WriteReadFiles writeReadFiles = new WriteReadFiles();
		PublicKey publicKey = writeReadFiles.getPublicKey();

		try {
			//Reading the signed file
			FileInputStream fis2 = new FileInputStream(inputEXTSigned);
			DataInputStream dis2 = new DataInputStream(fis2);
			int sigLength = 0;

			//Read the length of the signature
			sigLength = Byte.toUnsignedInt(dis2.readByte());

			//create a byte array with the correct length
			byte[] signature = new byte[(int) sigLength];

			//Read the actual signature
			dis2.read(signature, 0, sigLength);

			//Create a byte array the size of the contants
			byte[] contents = new byte[(int) inputEXTSigned.length() - sigLength - 1];
			//Read the rest of the file
			dis2.readFully(contents);

			dis2.close();
			fis2.close();

			//setting the contents to utf 8
			System.out.println(new String(contents, "UTF-8"));

			Signature sig = Signature.getInstance("SHA1withRSA");
			sig.initVerify(publicKey);
			sig.update(contents);
			boolean verify = sig.verify(signature);
			System.out.println(verify);

			if (verify) {
				DataOutputStream dis3 = new DataOutputStream(new FileOutputStream(inputEXTSigned.getPath().substring(0, inputEXTSigned.getPath().lastIndexOf(File.separator)) + File.separator + "INPUT(verified).EXT"));
				dis3.write(contents);
				dis3.close();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (SignatureException e) {
			e.printStackTrace();
		}

	}
}


