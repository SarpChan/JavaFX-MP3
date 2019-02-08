package Controller;


import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;


public class PlaylistManager {

	private static ObservableList<Playlist> playlistArrayList = FXCollections.observableArrayList();
	private static boolean createOnce= true;

	public static ObservableList<Playlist> getSuggestedPlaylists() {
		return suggestedPlaylists;
	}

	private static ObservableList<Playlist> suggestedPlaylists = FXCollections.observableArrayList();

	private static HashMap<String, Track> trackMap = new HashMap<>();

	private static SimpleIntegerProperty playlistsChange = new SimpleIntegerProperty();


	/**
	 * Gibt Playlist mit dem Namen nameOfPlaylist zurück
	 * @param nameOfPlaylist
	 * @return
	 */
	public static Playlist getPlaylist(String nameOfPlaylist){
		int temp = playlistArrayList.indexOf(nameOfPlaylist);
		return playlistArrayList.get(temp);
	}



	public static void addToSuggestedPlaylist(Playlist playlist){
		suggestedPlaylists.add(playlist);

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

	public static void savePlaylist(Playlist playlist, String name) throws IOException{

		try (BufferedWriter writer = new BufferedWriter(new FileWriter("/Users/"+ System.getProperty("user.name") +"/Music/" + name + ".m3u"))){

			for (Track i : playlist.getTracks()) {

				writer.write(i.getPath());
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
	
	public static ArrayList<String> searchAllTracks() {
		
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
	public static ObservableList<Playlist> getAllPlaylists(){
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
		playlistsChange.set(playlistArrayList.size());
		playlistArrayList.addAll(playlists.values());

		if(playlistArrayList.isEmpty()){
			try {
				savePlaylist(PlaylistManager.searchAllTracks(), "default");
			} catch (IOException e) {
				e.printStackTrace();

			}
			getAllPlaylists();
		}

		if(createOnce) {
			createSuggestionPlaylists();
			createOnce = false;
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
	public static ObservableList<Playlist> getPlaylistArrayList() {
		return playlistArrayList;
	}

	public static HashMap<String, Track> collectExistingTracks() {

		LinkedList<Track> temp = new LinkedList<>();
		for (Playlist playlist:
			 playlistArrayList) {
			temp = playlist.getTracks();
			for (Track tempTrack :
					temp) {
				if(tempTrack.getSpotId() != null) {
					trackMap.put(tempTrack.getSpotId(), tempTrack);
				}
			}
		}

		return trackMap;
	}



	public static void addTrack(Track track, Playlist playlist){
		playlist.addTrack(track);
	}

	public static SimpleIntegerProperty allPlaylistProperty(){
		return playlistsChange;
	}

	public static void addPlaylist(Playlist playlist) {
		playlistArrayList.add(playlist);

	}

	private static void createSuggestionPlaylists(){
		/*
		minmax Paare:
		BPM,VALENCE,ENERGY,DANCEABITLITY,INSTRUMENTALNESS, ACOUSTICNESS
		 */

		PlaylistCreator.createSuggestionPlaylist(new float [] {/*BPM*/100f,0.0f, /* VALENCE*/0.2f,0.0f, /*ENERGY*/0.8f,0.0f,
				/*DANCE*/0.0f,0.0f, /*INSTRU*/0.0f,0.0f, /*ACOUST*/0.0f,0.0f,},"Workout","x");

		PlaylistCreator.createSuggestionPlaylist(new float [] {/*BPM*/0.0f,60f, /* VALENCE*/0.2f,0.0f, /*ENERGY*/0.0f,0.3f,
				/*DANCE*/0.0f,0.4f, /*INSTRU*/0.0f,1.0f, /*ACOUST*/0.0f,0.0f,},"Chillout","x");

		PlaylistCreator.createSuggestionPlaylist(new float [] {/*BPM*/80f,0.0f, /* VALENCE*/0.6f,0.0f, /*ENERGY*/0.4f,0.0f,
				/*DANCE*/0.5f,0.0f, /*INSTRU*/0.0f,0.0f, /*ACOUST*/0.0f,0.1f,},"Party","x");

		PlaylistCreator.createSuggestionPlaylist(new float [] {/*BPM*/0.0f,60f, /* VALENCE*/0.0f,0.2f, /*ENERGY*/0.0f,0.4f,
				/*DANCE*/0.0f,0.3f, /*INSTRU*/0.0f,0.8f, /*ACOUST*/0.0f,0.9f,},"Traurig","x");

		PlaylistCreator.createSuggestionPlaylist(new float [] {/*BPM*/40f,0.0f, /* VALENCE*/0.4f,0.0f, /*ENERGY*/0.0f,0.5f,
				/*DANCE*/0.4f,0.0f, /*INSTRU*/0.0f,0.9f, /*ACOUST*/0.0f,0.9f,},"Slowdance","x");

		PlaylistCreator.createSuggestionPlaylist(new float [] {/*BPM*/90f,0.0f, /* VALENCE*/0.0f,0.0f, /*ENERGY*/0.4f,0.9f,
				/*DANCE*/0.0f,1.0f, /*INSTRU*/0.0f,0.9f, /*ACOUST*/1.0f,0.0f,},"Gaming","x");

	}
}
