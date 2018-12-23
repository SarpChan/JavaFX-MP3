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

	/**
	 * Wurde eine Playlist neu erstellt, aber noch nicht gespeichert?
	 * @return
	 */
	public static boolean isOpenPlaylist() {
		return openPlaylist;
	}

	/**
	 * Füge Track zu erstellter Playlist hinzu
	 * @param track
	 */
	public static void addToOpenPlaylist(Track track){
		newPlaylist.add(track);
	}

	/**
	 * Erstelle neue Playlist
	 */
	public static void createNewPlaylist(){
		newPlaylist = new ArrayList<>();
		openPlaylist = true;
	}


	/**
	 * Gibt Playlist mit dem Namen nameOfPlaylist zurück
	 * @param nameOfPlaylist
	 * @return
	 */
	public static Playlist getPlaylist(String nameOfPlaylist){
		int temp = playlistArrayList.indexOf(nameOfPlaylist);
		return playlistArrayList.get(temp);
	}

	/**
	 * Speichert Playlist mit Namen
	 * @param playlist
	 * @param name
	 * @throws IOException
	 */
	
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

	/** Überprüft Tracks auf Duplikate und gibt sie zurückt
	 *
	 * @return ArrayList aller Tracks als String
	 */
	
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

	/**
	 * Hilfsmethode sucht Tracks im Angegebenen Pfad
	 *
	 * @param path
	 * @return Liste von gefundendenen MP3 als Pfadstrings
	 */
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

	/** Sammelt alle Playlisten im Musikordner und überprüft sie auf Duplikate
	 *
	 * Wenn keine Playlist gefunden wurde, dann sucht die Methode nach allen MP3 im Musikordner und erstellt
	 * daraus eine Playlist.
	 *
	 * @return ArrayList aller Playlisten als Playlistobjekte
	 */
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

		if(playlistArrayList.isEmpty()){
			try {
				savePlaylist(PlaylistManager.getAllTracks(), "default");
			} catch (IOException e) {
				e.printStackTrace();

			}
			getAllPlaylists();
		}

		return playlistArrayList;
	}

	/**
	 * Hilfsmethode sucht Playlisten im Angegebenen Pfad
	 *
	 * @param path
	 * @return Liste von gefundendenen Playlisten als Pfadstrings
	 */
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

	/**
	 * Gibt Liste der Playlisten zurück
	 * @return
	 */
	public static ArrayList<Playlist> getPlaylistArrayList() {
		return playlistArrayList;
	}
}
