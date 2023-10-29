package startEnPckg;
import decorPckg.FileDataSource;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import decorPckg.AESEncDecorator;
import intPckg.DataSource;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		// 3 datoteke / original podaci; enkriptirani podaci; dekriptirani podaci
		String filePath = "encdec.txt";
		String encryptedFile = "encrypted.txt";
		String decryptedFile = "decrypted.txt";
		
		String sourceFilePath = "encdec.txt";
		String zipFilePath = "encdec.zip";
		
		// Kreiranje datoteke sa originalnim podacima
		DataSource fileDataSource = new FileDataSource(filePath);
		// Dekoracija podataka sa algoritmam za AES sha-256 enkripcijom
		DataSource encryptedDataSource = new AESEncDecorator(fileDataSource);
		// Kreiranje datoteke za dekripciju
		DataSource decryptedDataSource = new FileDataSource(decryptedFile);
		
		
		encryptedDataSource.writeData("Some data to write!!!");
		
		// èitanje i ispis originalne poruke
		String decryptedData = encryptedDataSource.readData();
		System.out.println("Original data:\n" + decryptedData);
		
		fileDataSource.writeData(encryptedFile);
		
		// èitanje i ispis enkriptiranog sadržaja
		DataSource encryptedFileDataSource = new FileDataSource(encryptedFile);
		String encryptedDataFromFile = encryptedFileDataSource.readData();
		System.out.println("Encrypted data from file:\n" + encryptedDataFromFile);
		
		
		decryptedDataSource.writeData(decryptedData);
		
		// èitanje i ispis dekriptiranog sadržaja
		DataSource decryptedFileDataSource = new FileDataSource(decryptedFile);
		//DataSource decryptionDecorator = new EncryptionDecorator(new FileDataSource(encryptedFile));
		String decryptedDataFromFile = decryptedFileDataSource.readData();
		System.out.println("Decrypted data from file:\n" + decryptedDataFromFile);
		
		
		compressFile(sourceFilePath, zipFilePath);
		
		// proces je gotov
		System.out.println("Encryption and decryption has completed!!!!");
	}
	
	public static void compressFile(String sourceFilePath, String zipFilePath) {
	    try {
	        FileInputStream fileInputStream = new FileInputStream(sourceFilePath);
	        FileOutputStream fileOutputStream = new FileOutputStream(zipFilePath);
	        ZipOutputStream zipOutputStream = new ZipOutputStream(fileOutputStream);

	        ZipEntry zipEntry = new ZipEntry(new File(sourceFilePath).getName());
	        zipOutputStream.putNextEntry(zipEntry);

	        byte[] buffer = new byte[1024];
	        int bytesRead;
	        while ((bytesRead = fileInputStream.read(buffer)) > 0) {
	            zipOutputStream.write(buffer, 0, bytesRead);
	        }

	        zipOutputStream.closeEntry();
	        zipOutputStream.close();
	        fileOutputStream.close();
	        fileInputStream.close();

	        System.out.println("File compressed successfully! File that was zipped: " + sourceFilePath + " Zipped File: " + zipFilePath);
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	

}