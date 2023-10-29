package decorPckg;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import intPckg.DataSource;


public class AESEncDecorator extends DataSourceDecorator {
	
	private static final String AES_KEY = "JosipaNapravilaAESkljucNeki";
	
	public AESEncDecorator(DataSource source) {
		super(source);
	}
	
	// generiranje tajnog ključa, koristi se sha-256 algoritam za enkripciju pomoću AES ključa
	private SecretKey generateAESKey(String key) {
		// TODO Auto-generated method stub
		byte[] keyBytes = key.getBytes(StandardCharsets.UTF_8);
		MessageDigest sha;
		try {
			sha = MessageDigest.getInstance("SHA-256");
			keyBytes = sha.digest(keyBytes);
			return new SecretKeySpec(keyBytes, "AES");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}	
		
	}
	
	// metoda za upis enkriptiranih podataka
	@Override
	public void writeData(String data) {
		// TODO Auto-generated method stub
		String encryptedData = encrypt(data);
		wrapper.writeData(encryptedData);
		
	}
	
	// metoda za enkripciju podataka
	private String encrypt(String data) {
		// TODO Auto-generated method stub
		try {
			SecretKey aesKey = generateAESKey(AES_KEY);
			Cipher cipher = Cipher.getInstance("AES");
			cipher.init(Cipher.ENCRYPT_MODE, aesKey);
			byte[] encryptedBytes = cipher.doFinal(data.getBytes(StandardCharsets.UTF_8));
			return Base64.getEncoder().encodeToString(encryptedBytes);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
	}
	
	// metoda za čitanje dekriptiranih podataka koji su prvotno bili enkriptirani
	@Override
	public String readData() {
		// TODO Auto-generated method stub
		String encryptedData = wrapper.readData();
		return decrypt(encryptedData);
	}
	
	// metoda za dekripciju enkriptiranih podataka
	private String decrypt(String encryptedData) {
		// TODO Auto-generated method stub
		try {
			SecretKey aesKey = generateAESKey(AES_KEY);
			Cipher cipher = Cipher.getInstance("AES");
			cipher.init(Cipher.DECRYPT_MODE, aesKey);
			byte [] encryptedBytes = Base64.getDecoder().decode(encryptedData);
			byte [] decryptedBytes = cipher.doFinal(encryptedBytes);
			return new String(decryptedBytes, StandardCharsets.UTF_8);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
}