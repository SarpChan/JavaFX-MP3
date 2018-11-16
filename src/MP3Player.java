import de.hsrm.mi.eibo.simpleplayer.SimpleAudioPlayer;
import de.hsrm.mi.eibo.simpleplayer.SimpleMinim;


public class MP3Player {
	private SimpleMinim minim;
	private SimpleAudioPlayer audioPlayer;
	
	MP3Player(){
		minim = new SimpleMinim(true);
	}

	MP3Player(String s){
	    minim = new SimpleMinim(true);
	    audioPlayer = minim.loadMP3File(s);
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
		audioPlayer.setGain(LinearToDecibel(value));
	}

	protected void getMeta() {
		audioPlayer.getMetaData();
	}

	protected void exit() {
		minim.dispose();
	}

	private float LinearToDecibel(float linear)
	{
		float db;

		if (linear != 0.0f)
			db = (float) (20.0f * Math.log10(linear));
		else
			db = -144.0f;  // effectively minus infinity

		return db;
	}
}
