package Controller;


import Exceptions.keinSongException;
import de.hsrm.mi.eibo.simpleplayer.SimpleAudioPlayer;
import de.hsrm.mi.eibo.simpleplayer.SimpleMinim;
import javafx.scene.image.Image;


import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
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


    public Playlist getAktPlaylist() {
        return aktPlaylist;
    }

    public void changeShuffle(){shuffle = !shuffle;}

    public boolean isShuffle() {
        return shuffle;
    }
    public void changeRepeat(){repeat = !repeat;}


    public boolean isRepeat() {
        return repeat;
    }



    private void autoNextOff(){autoNext = false;}
    private void autoNextOn(){autoNext = true;}

    public void setAktPlaylist (Playlist playlist){
        this.aktPlaylist = playlist;
    }
    public int getAktZeit(){
        if (audioPlayer == null){
            return 0;
        }

        return audioPlayer.position() ;
    }
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

    //Mp3agic Ende


    public MP3Player(){
        minim = new SimpleMinim();
    }



    public boolean isInitialized(){
        if(audioPlayer == null){
            return false;
        } else {
            return true;
        }
    }

    public boolean isPlayerActive() {
        return audioPlayer == null?false:audioPlayer.isPlaying();
    }

    public void play(Track track) throws keinSongException {

        if(playThread!=null) {

            playThread.interrupt();
        }
        audioPlayer = minim.loadMP3File(track.getPath());


        aktSong = track;

        play();

    }
    public void play(Track track, Playlist playlist) throws keinSongException {

        autoNextOff();

        try {
            aktPlaylist = playlist;
            play(track);



        } catch (Exception e) {
            throw new keinSongException();

        }



    }

    public void play() throws keinSongException {

        if (audioPlayer == null) {

            aktSong = getFirstSongFromPlaylist(PlaylistManager.getPlaylistArrayList().get(0));
            audioPlayer = minim.loadMP3File(aktSong.getPath());

            playThread = new MyThread();

            playThread.start();
            aktPlaylist = PlaylistManager.getPlaylistArrayList().get(0);



        }else {

            playThread = new MyThread();

            playThread.start();
        }



    }

    public void next() throws keinSongException{


        if (shuffle){
            jumpTo = 0;
            aktSong = aktPlaylist.getTracks().get(getRandomNumberInRange(0, aktPlaylist.getTracks().size()));
            autoNextOff();
            playThread.interrupt();

            play(aktSong);


        } else {

            for (Track track : aktPlaylist.getTracks()
                    ) {
                if (aktSong.equals(track)) {
                    int temp = aktPlaylist.getTracks().indexOf(track);
                    if (aktPlaylist.getTracks().get(temp + 1) != null) {
                        jumpTo = 0;
                        aktSong = aktPlaylist.getTracks().get(temp + 1);
                        autoNextOff();
                        playThread.interrupt();

                        play(aktSong);
                        break;
                    } else if (isRepeat() && aktPlaylist.getTracks().get(temp + 1) == null){
                        jumpTo = 0;
                        aktSong = aktPlaylist.getTracks().getFirst();
                        autoNextOff();
                        playThread.interrupt();

                        play(aktSong);
                    }
                }

            }
        }

    }

    public void skip(int mseconds){
        autoNextOff();
        if(isInitialized()) {

            skipping = true;
            audioPlayer.skip(mseconds);
           /* jumpTo = mseconds + audioPlayer.position();
            System.out.println(jumpTo);


            playThread.interrupt();

            audioPlayer= minim.loadMP3File(aktSong.getPath());
            playThread = new MyThread();
            playThread.start();

*/


        }
        skipping = false;
        autoNextOn();
    }

    public boolean previous() throws keinSongException{

        Track oldTrack = null;
        jumpTo = 0;


        for (Track track: aktPlaylist.getTracks()
                ) {
            if(aktSong.equals(track)){

                if(oldTrack != null){
                    aktSong = oldTrack;
                    autoNextOff();
                    playThread.interrupt();
                    play(aktSong);
                    return true;
                }
                return false;

            }
            oldTrack = track;

        }


        return false;
    }



    public void pause() throws keinSongException {

        if (audioPlayer == null) {
            throw new keinSongException("Leider wurde kein Song ausgewählt");
        }
        autoNextOff();
        jumpTo = audioPlayer.position();
        audioPlayer.pause();


    }

    public void stop() throws keinSongException{
        if (audioPlayer == null) {
            throw new keinSongException("Leider wurde kein Song ausgewählt");
        }
        audioPlayer.pause();
        audioPlayer.rewind();

    }

    public void volume (float value) {
        audioPlayer.setGain(LinearToDecibel(value));
    }

    public void getMeta() {
        audioPlayer.getMetaData();
    }

    public void exit() {
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

    public Track getFirstSongFromPlaylist(Playlist x) {

        return x.getTracks().getFirst();
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

        public void skip(int millis){

            audioPlayer.skip(millis);

            if(autoNext) {
                try {
                    next();
                } catch (keinSongException e) {
                    e.printStackTrace();
                }
            }
        }


    }
    private int getRandomNumberInRange(int min, int max) {

        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }

        Random r = new Random();
        return r.nextInt((max - min)) + min;
    }

}