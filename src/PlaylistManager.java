import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;


public class PlaylistManager {
	
	public ArrayList<String> getPlaylist(String nameOfPlaylist){
		
		
		ArrayList<String> playlist = new ArrayList<String>();
		BufferedReader reader = null;
		try {
			
			reader = new BufferedReader(new FileReader(nameOfPlaylist + ".m3u"));
			String line = null;
			while ((line = reader.readLine()) != null) {
				playlist.add(line);
			}
			
		} catch(IOException e) {
			System.out.println("laden von " + nameOfPlaylist + ".m3u ist fehlgeschlagen.");
			e.printStackTrace();
		}finally {
		 try {
			reader.close();
		 } catch (IOException e) {
			e.printStackTrace();
		}
		}
		
		return playlist;
		
	}
	
	public void savePlaylist(ArrayList<String> playlist, String name) throws IOException {
		
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(name + ".m3u"))){
			
			for (String i : playlist) {
				
				writer.write(i);
				writer.newLine();
				
			}
			System.out.println("speichern erfolgreich");
			
		}catch(IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public ArrayList<String> getAllTracks() {
		
		ArrayList <String> allMp3s = new ArrayList <String> ();
		
		allMp3s.addAll(searchForMp3("/Users/"));
		
		HashMap<String, String>songs = new HashMap<String, String>();
		
		for (String i : allMp3s) {
			String name;
			String path[];
			path = i.split("/");
			
			name = path[path.length - 1];
			path = name.split("\\.");
			name = path[0];
			
			if (!songs.containsKey(name)) {
				songs.put(name, i);
			}
		}
		allMp3s = new ArrayList <String>(songs.values());
		
		return allMp3s;
	}
	
	private ArrayList<String> searchForMp3(String path) {
		
		File  files[];
		ArrayList<String> mp3s = new ArrayList<String>();
		
		File dir = new File (path);
			files = dir.listFiles();
			
			if (files != null) {
				for (File i: files) {
					
					if (i.isDirectory() && !i.getAbsolutePath().endsWith("Library")) {
						mp3s.addAll(searchForMp3(i.getAbsolutePath()));
					}
					else if (i.toString().endsWith("mp3")) {
						mp3s.add(i.getAbsolutePath());
					}
					
				}
			}
		
		return mp3s;
		
	}
	
}
