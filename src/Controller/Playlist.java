package Controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.LinkedList;

public class Playlist {

    private String path, name;
    private int playtime=0, numberTracks=0;
    private LinkedList<Track> tracks = new LinkedList();

    public Playlist (String path){
        this.path = path;
    }
    public Playlist (String path, String name){
        this(path);
        this.name = name;
        compilePlaylist(path);

    }

    private void compilePlaylist(String path){
        try (BufferedReader reader = new BufferedReader( new FileReader(path))){

            String trackAbsolPath = reader.readLine();

            while(trackAbsolPath != null){
                tracks.addLast(new Track(trackAbsolPath));
                playtime += tracks.getLast().getSonglength();
                numberTracks++;

                trackAbsolPath = reader.readLine();
            }
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }

    }

    public void deleteTrack(String path){

        playtime -= tracks.get(tracks.indexOf(path)).getSonglength();
        numberTracks--;
        tracks.remove(tracks.indexOf(path));

    }

    public void addTrack(String path){
        tracks.addLast(new Track(path));
        playtime += tracks.getLast().getSonglength();
        numberTracks++;
    }


    public String getPath() {
        return path;
    }

    public int getNumberTracks() {
        return numberTracks;
    }

    public int getPlaytime() {
        return playtime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LinkedList<Track> getTracks() {
        return tracks;
    }
}
