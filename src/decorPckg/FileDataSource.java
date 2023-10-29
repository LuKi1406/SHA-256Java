package decorPckg;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import intPckg.DataSource;
// Klasa za implementaciju putanje do file-a i metoda za čitanje i pisanje u file
public class FileDataSource implements DataSource {
	
	private final String filePath;
	
	public FileDataSource(String filePath) {
		this.filePath = filePath;
	}

	// metoda za čitanje podataka iz file-a
	@Override
	public String readData() {
		// TODO Auto-generated method stub
		try (FileInputStream input = new FileInputStream(filePath);
				ByteArrayOutputStream output = new ByteArrayOutputStream())	{	
				
		byte[] buffer = new byte[1024];
				int bytesRead;
		
			
			while((bytesRead = input.read(buffer)) != - 1) {
					output.write(buffer, 0, bytesRead);
				}
			return new String(output.toByteArray(), StandardCharsets.UTF_8);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
	}
	
	// metoda za upis podataka u file
	@Override
	public void writeData(String data) {
		// TODO Auto-generated method stub
		try (FileOutputStream fos = new FileOutputStream(filePath)) {
			fos.write(data.getBytes(StandardCharsets.UTF_8));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}

}