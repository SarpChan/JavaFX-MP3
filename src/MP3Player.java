import de.hsrm.mi.eibo.simpleplayer.SimpleAudioPlayer;
import de.hsrm.mi.eibo.simpleplayer.SimpleMinim;

public class MP3Player {
	private SimpleMinim minim;
	private SimpleAudioPlayer audioPlayer;
	
	MP3Player(){
		minim = new SimpleMinim(true);
	}
	
	protected void play(String fileName) {
		audioPlayer = minim.loadMP3File(fileName);
		audioPlayer.play();
	}
	
	protected void play() throws keinSongException{
		if (audioPlayer == null) {
			throw new keinSongException("Leider wurde kein Song 2ausgewählt");
		}
		audioPlayer.play();
	}
	
	protected void pause() throws keinSongException {
		
		if (audioPlayer == null) {
			throw new keinSongException("Leider wurde kein Song ausgewählt");
		}
		audioPlayer.pause();
	}
	
	protected void stop() throws keinSongException{
		if (audioPlayer == null) {
			throw new keinSongException("Leider wurde kein Song ausgewählt");
		}
		audioPlayer.pause();
		audioPlayer.rewind();
	}
	
	protected void volume (float value) {
		
		
	}
	
	
	protected void getMeta() {
		audioPlayer.getMetaData();
	}
	
	protected void exit() {
		minim.dispose();
	}
}
