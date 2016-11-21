package com.lazy.android.basefunc.encrypt;

import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Arrays;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/**
 * rsa加密、解密
 * 
 * @author gavin
 * 
 */
public class RSAHelper {
	KeyPairGenerator kpg;
	KeyPair kp;
	PublicKey publicKey;
	PrivateKey privateKey;
	byte[] encryptedBytes, decryptedBytes;
	Cipher cipher, cipher1;
	String encrypted, decrypted;

	public String Encrypt(String plain) throws NoSuchAlgorithmException,
			NoSuchPaddingException, InvalidKeyException,
			IllegalBlockSizeException, BadPaddingException {
		kpg = KeyPairGenerator.getInstance("RSA");
		kpg.initialize(1024);
		kp = kpg.genKeyPair();
		
		publicKey = kp.getPublic();
		privateKey = kp.getPrivate();

		cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.ENCRYPT_MODE, publicKey);
		encryptedBytes = cipher.doFinal(plain.getBytes());

		encrypted = bytesToString(encryptedBytes);
		return encrypted;

	}

	public String Decrypt(String result) throws NoSuchAlgorithmException,
			NoSuchPaddingException, InvalidKeyException,
			IllegalBlockSizeException, BadPaddingException {

		cipher1 = Cipher.getInstance("RSA");
		cipher1.init(Cipher.DECRYPT_MODE, privateKey);
		decryptedBytes = cipher1.doFinal(stringToBytes(result));
		decrypted = new String(decryptedBytes);
		return decrypted;
	}

	public String bytesToString(byte[] bytes) {
		byte[] byte2 = new byte[bytes.length + 1];
		byte2[0] = 1;
		System.arraycopy(bytes, 0, byte2, 1, bytes.length);
		return new BigInteger(byte2).toString(36);
	}

	public byte[] stringToBytes(String str) {
		byte[] byte2 = new BigInteger(str, 36).toByteArray();
		return Arrays.copyOfRange(byte2, 1, byte2.length);
	}
}
