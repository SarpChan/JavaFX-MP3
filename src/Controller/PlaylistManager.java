package Controller;


import javafx.scene.control.Label;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.nio.file.*;


public class PlaylistManager {

	private static ArrayList<Playlist> playlistArrayList = new ArrayList<>();
	private static ArrayList<Track> newPlaylist;
	private static boolean openPlaylist = false;

	public static boolean isOpenPlaylist() {
		return openPlaylist;
	}

	public static void addToOpenPlaylist(Track track){
		newPlaylist.add(track);
	}

	public static void createNewPlaylist(){
		newPlaylist = new ArrayList<>();
		openPlaylist = true;
	}

	public ArrayList<String> getPlaylists(String nameOfPlaylist){
		
		
		ArrayList<String> playlist = new ArrayList<String>();
		BufferedReader reader = null;
		try {
			
			reader = new BufferedReader(new FileReader(nameOfPlaylist + ".m3u"));
			String line;
			while ((line = reader.readLine()) != null) {
				playlist.add(line);
			}
			
		} catch(IOException e) {
			System.out.println("laden von " + nameOfPlaylist + ".m3u ist fehlgeschlagen. nicht");
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
	public static Playlist getPlaylist(String nameOfPlaylist){
		int temp = playlistArrayList.indexOf(nameOfPlaylist);
		return playlistArrayList.get(0);
	}
	
	public static void savePlaylist(ArrayList<String> playlist, String name) throws IOException {



		try (BufferedWriter writer = new BufferedWriter(new FileWriter("/Users/"+ System.getProperty("user.name") +"/Music/" + name + ".m3u"))){
			
			for (String i : playlist) {
				
				writer.write(i);
				writer.newLine();
				
			}
			System.out.println("speichern erfolgreich");
			
		}catch(IOException e) {
			e.printStackTrace();
		}
		
	}

	
	public static ArrayList<String> getAllTracks() {
		
		ArrayList <String> allMp3s = new ArrayList <String> ();

		String username = System.getProperty("user.name");
		
		allMp3s.addAll(searchForMp3("/Users/"+ username +"/Music"));
		
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
	
	private static ArrayList<String> searchForMp3(String path) {

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


	public static ArrayList<Playlist> getAllPlaylists(){
		ArrayList <String> allM3us = new ArrayList <> ();

		String username = System.getProperty("user.name");

		allM3us.addAll(searchForM3u("/Users/"+ username +"/Music"));

		HashMap<String, Playlist>playlists = new HashMap<>();


		for (String absolutePath : allM3us) {
			String name;
			String path[];
			path = absolutePath.split("/");

			name = path[path.length - 1];
			path = name.split("\\.");
			name = path[0];

			if (!playlists.containsKey(name)) {
				playlists.put(name, new Playlist(absolutePath, name));
			}
		}
		playlistArrayList = new ArrayList <>(playlists.values());

		return playlistArrayList;
	}

    private static ArrayList<String> searchForM3u(String path) {

        File  files[];
        ArrayList<String> m3us = new ArrayList<String>();

        File dir = new File (path);
        files = dir.listFiles();

        if (files != null) {
            for (File i: files) {

                if (i.isDirectory() && !i.getAbsolutePath().endsWith("Library")) {
                    m3us.addAll(searchForM3u(i.getAbsolutePath()));
                }
                else if (i.toString().endsWith(".m3u")) {
                    m3us.add(i.getAbsolutePath());
                }

            }
        }

        return m3us;

    }

	public static ArrayList<Playlist> getPlaylistArrayList() {
		return playlistArrayList;
	}
}
