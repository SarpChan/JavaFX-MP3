package Controller;


import Exceptions.keinSongException;
import de.hsrm.mi.eibo.simpleplayer.SimpleAudioPlayer;
import de.hsrm.mi.eibo.simpleplayer.SimpleMinim;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableIntegerValue;
import javafx.scene.image.Image;


import java.util.Random;


public class MP3Player {
    private SimpleMinim minim;
    private SimpleAudioPlayer audioPlayer;

    private boolean autoNext = true, shuffle=false, repeat = false;

    private Playlist aktPlaylist;
    private Track aktSong;
    private MyThread playThread;
    private int jumpTo;
    private boolean skipping = false;
    private SimpleObjectProperty<Track> songProperty;
    private SimpleIntegerProperty aktTime;


    /** Constructor
     *
     */
    public MP3Player(){
        minim = new SimpleMinim();
        songProperty = new SimpleObjectProperty<Track>();
        aktTime = new SimpleIntegerProperty();

    }


    public SimpleIntegerProperty aktZeitProperty(){
        return aktTime;
    }

    public Playlist getAktPlaylist() {
        return aktPlaylist;
    }

    /**
     * boolean shuffle wird jeweils falsch/wahr gesetzt
     */
    public void changeShuffle(){shuffle = !shuffle;}

    /**
     * boolean repeat wird jeweils falsch/wahr gesetzt
     */
    public void changeRepeat(){repeat = !repeat;}


    public boolean isRepeat() {
        return repeat;
    }


    /**
     * automatisch Nächstes Lied aus
     */
    private void autoNextOff(){autoNext = false;}
    /**
     * automatisch Nächstes Lied an
     */
    private void autoNextOn(){autoNext = true;}

    /**
     * Gibt die Positionen des aktuellen Liedes zurück
     * @return aktPos in Milliseconds
     */
    public int getAktZeit(){
        if (audioPlayer == null){
            return 0;
        }

        return audioPlayer.position() ;
    }

    /**
     *
     * @return songlength in Milliseconds
     */
    public long getSongLength(){


        return aktSong!= null? aktSong.getSonglength():0;

    }

    public String getSongArtist(){

        return aktSong!=null? aktSong.getArtist():"N.A.";
    }

    public String getAlbum(){

        return aktSong!=null? aktSong.getAlbum():"N.A.";
    }

    public String getTrack(){

        return aktSong!=null? aktSong.getTitle():"N.A.";
    }



    public Image getAlbumImage(){


        Image img = new Image("defaultCover.png");


        if (aktSong!= null) {
            return aktSong.getImage() != null ? aktSong.getImage() : img;
        }
        return img;
    }


    /**
     * Ist der Player initialisiert?
     * @return true/false
     */
    public boolean isInitialized(){
        if(audioPlayer == null){
            return false;
        } else {
            return true;
        }
    }

    /**
     * Spielt der Player etwas ab?
     * @return true/false
     */
    public boolean isPlayerActive() {
        return audioPlayer == null?false:audioPlayer.isPlaying();
    }

    /**
     *
     * Lädt einen Track als aktuellen Song
     *
     * @param track der zu spielende Track
     * @throws keinSongException
     */
    public void play(Track track) throws keinSongException {

        if(playThread!=null) {

            playThread.interrupt();
        }
        audioPlayer = minim.loadMP3File(track.getPath());


        aktSong = track;

        play();

    }

    /**
     * Lädt einen Track und eine Playlist als aktuellen Track/ aktuelle Playlist
     *
     * @param track der zu spielende Track
     * @param playlist die Playlist in dem sich der Track befindet
     * @throws keinSongException
     */
    public void play(Track track, Playlist playlist) throws keinSongException {

        autoNextOff();

        try {
            aktPlaylist = playlist;
            play(track);



        } catch (Exception e) {
            throw new keinSongException();

        }



    }

    /**
     * Startet das Abspielen eines Songs.
     *
     * Hat der Player noch kein Lied geladen, dann wird das erste Lied aus der ersten Playlist geladen
     * und abgespielt
     *
     * @throws keinSongException
     */
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public SimpleObjectProperty<Track> songProperty(){
        return songProperty;
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void play() throws keinSongException {

        if (audioPlayer == null) {

            aktSong = getFirstSongFromPlaylist(PlaylistManager.getPlaylistArrayList().get(0));
            aktPlaylist = PlaylistManager.getPlaylistArrayList().get(0);
            audioPlayer = minim.loadMP3File(aktSong.getPath());
        }

            songProperty.set(aktSong);
            playThread = new MyThread();

            playThread.start();






    }

    /**
     * Spielt das nächste Lied ab
     *
     * @throws keinSongException
     * @return true/false war die Operation erfolgreich?
     */
    public boolean next() throws keinSongException{

    if (isInitialized()) {
        if (shuffle) {
            jumpTo = 0;
            aktSong = aktPlaylist.getTracks().get(getRandomNumberInRange(0, aktPlaylist.getTracks().size()));
            autoNextOff();
            playThread.interrupt();

            play(aktSong);
            return true;

        } else {

            for (Track track : aktPlaylist.getTracks()
                    ) {
                if (aktSong.equals(track)) {
                    int temp = aktPlaylist.getTracks().indexOf(track);
                    if (temp < aktPlaylist.getNumberTracks()-1) {
                        jumpTo = 0;
                        aktSong = aktPlaylist.getTracks().get(temp + 1);
                        autoNextOff();
                        playThread.interrupt();

                        play(aktSong);
                        return true;

                    } else if (repeat && temp == aktPlaylist.getNumberTracks()-1){
                        jumpTo = 0;
                        aktSong = aktPlaylist.getTracks().getFirst();
                        autoNextOff();
                        playThread.interrupt();

                        play(aktSong);
                        return true;
                    }
                }

            }
        }
    }
    return false;
}

    /**
     * Springt an eine bestimmte Stelle im Lied
     *
     * @param mseconds von der aktuellen Position relative Zeitangabe in Millisekunden zu der gesprungen wird
     */
    public void skip(int mseconds){
        autoNextOff();
        if(isInitialized()) {

            skipping = true;
            audioPlayer.mute();
            audioPlayer.skip(mseconds);


        }
        skipping = false;
        audioPlayer.unmute();
        autoNextOn();
    }

    /**
     * Spielt das vorherige Lied ab
     *
     * @return true/false war die Operation erfolgreich?
     * @throws keinSongException
     */
    public boolean previous() throws keinSongException {

        Track oldTrack = null;
        jumpTo = 0;

        if (isInitialized()) {
            for (Track track : aktPlaylist.getTracks()
                    ) {
                if (aktSong.equals(track)) {

                    if (oldTrack != null) {
                        aktSong = oldTrack;
                        autoNextOff();
                        playThread.interrupt();
                        play(aktSong);
                        return true;
                    }
                }
                oldTrack = track;
            }
        }
        return false;
    }


    /**
     * Pausiert das Lied
     *
     * @throws keinSongException
     */
    public void pause() throws keinSongException {

        if (audioPlayer == null) {
            throw new keinSongException("Leider wurde kein Song ausgewählt");
        }
        autoNextOff();
        jumpTo = audioPlayer.position();
        audioPlayer.pause();


    }

    /**
     * Stoppt das Lied
     *
     * @throws keinSongException
     */
    public void stop() throws keinSongException{
        if (audioPlayer == null) {
            throw new keinSongException("Leider wurde kein Song ausgewählt");
        }
        audioPlayer.pause();
        audioPlayer.rewind();

    }

    public Track getAktTrack(){
        return aktSong;
    }

    /**
     * Setzt die Lautstärke auf den Wert value
     *
     * @param value
     */
    public void volume (float value) {
        audioPlayer.setGain(LinearToDecibel(value));
    }

    public void getMeta() {
        audioPlayer.getMetaData();
    }

    public void exit() {
        minim.dispose();
    }

    /**
     * Hilfsmethode zur Umrechnung von linear zu dezibel
     *
     * @param linear
     * @return umgerechneter dezibelwert
     */
    private float LinearToDecibel(float linear)
    {
        float db;

        if (linear != 0.0f)
            db = (float) (20.0f * Math.log10(linear));
        else
            db = -144.0f;  // effectively minus infinity

        return db;
    }

    /**
     * Gibt den ersten Song einer Playlist zurück
     *
     * @param playlist aus der der erste Song geholt werden soll
     * @return Track
     */
    public Track getFirstSongFromPlaylist(Playlist playlist) {

        return playlist.getTracks().getFirst();
    }





    private class MyThread extends Thread{

        public MyThread(){
            super();
        }


        public void run() {

            do {
                audioPlayer.play();
            }while(skipping);
            if(autoNext) {
                try {
                    jumpTo = 0;
                    next();
                } catch (keinSongException e) {
                    e.printStackTrace();
                }
            }
            autoNextOn();
        }

        public void interrupt(){
            super.interrupt();
            minim.stop();

            return;
        }



    }

    /**
     * Hilfsmethode fürs Shuffeln, wählt zufällige Zahl in einem Bereich aus
     *
     * @param min untere Bereichsgrenze
     * @param max obere Bereichsgrenze
     * @return wert im Wertebereich
     */
    private int getRandomNumberInRange(int min, int max) {

        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }

        Random r = new Random();
        return r.nextInt((max - min)) + min;
    }

}