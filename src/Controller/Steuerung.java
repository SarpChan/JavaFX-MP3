package Controller;

import de.hsrm.mi.prog.util.StaticScanner;
import javafx.application.Application;

import java.io.IOException;
import java.util.ArrayList;

public class Steuerung {
	
	
	public static void main(String [] args) throws Exceptions.keinSongException {


		MP3Player player = new MP3Player();

		Application.launch(Applikation.PlayerGUI.class, args);

		String eingabe;
//		player.play("KOKAIN.mp3");
		
		PlaylistManager manager = new PlaylistManager();
		ArrayList<String> mp3s = new ArrayList<>();
		
		try {
			manager.savePlaylist(manager.getAllTracks(), "Test");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		mp3s.addAll(manager.getPlaylist("Test"));
		
		for (String i : mp3s) {
			System.out.println(i);

		}
		
		
		do {
			eingabe = StaticScanner.nextString();

			switch (eingabe){
			case "play":
						try {
						player.play();
						} catch (keinSongException e) {
						e.printStackTrace();
						}
						break;
			case "pause": try {
						player.pause();
						} catch (keinSongException e) {
						e.printStackTrace();
						}
						break;
			case "stop":try {
						player.stop();
						} catch (keinSongException e) {
						e.printStackTrace();
						}
						break;
			default: break;
				
			}
			
		}
		while (!eingabe.equalsIgnoreCase("end"));
		
		player.exit();
		
	}


}
