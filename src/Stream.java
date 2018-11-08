import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Stream {
	
	private BufferedWriter writer;
	
	public void open(String nameOfFile){
		
		writer = null;
		
		try {
			writer = new BufferedWriter(new FileWriter(nameOfFile));
			
		}catch(IOException e) {
			System.out.println("Auf die Datei " + nameOfFile + " konnte nicht zugegriffen werden");
			e.printStackTrace();
		}

	}

	
	
	
	
	public void paste(String line) throws IOException{
	
		
		
		writer.write(line);
		writer.newLine();
	}
	
	public void close() {
		try {
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
