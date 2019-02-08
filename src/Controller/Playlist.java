package Controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class Playlist {

    private String path, name;
    private int playtime=0, numberTracks=0;
    private LinkedList<Track> tracks = new LinkedList();

    /** Constructor
     *
     */
    public Playlist (String path){
        this.path = path;
    }
    /** Constructor
     *
     */
    public Playlist (String path, String name){
        this(path);
        this.name = name;
        compilePlaylist(path);
    }
    /** Constructor
     *
     */
    public Playlist(String name, HashMap<String,Track> trackList){
        this.name = name;
        for(Map.Entry<String, Track> entry : trackList.entrySet()){
            tracks.add(entry.getValue());
            playtime += entry.getValue().getSonglength();
            numberTracks++;
        }
    }

    /** Tracks werden zur Playlist hinzugefügt. Spotify-Features und Informationen werden aus den Daten gelesen.
     *
     */
    protected void compilePlaylist(String path){
        try (BufferedReader reader = new BufferedReader( new FileReader(path))){
            String trackAbsolPath = reader.readLine();
            while(trackAbsolPath != null){
                tracks.addLast(new Track(trackAbsolPath));
                if(tracks.getLast().getTitle() != null && tracks.getLast().getArtist() != null) {
                    SpotSearchFeats.search(tracks.getLast());
                }
                playtime += tracks.getLast().getSonglength();
                numberTracks++;

                trackAbsolPath = reader.readLine();
            }
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }

    }

    /** Löscht Track aus der Playlist.
     *
     */
    public void deleteTrack(Track track){
        playtime -= tracks.get(tracks.indexOf(track)).getSonglength();
        numberTracks--;
        tracks.remove(track);
    }

    /** Fügt Track zur Playlist hinzu.
     *
     */
    public void addTrack(Track track){
        tracks.addLast(track);
        playtime += tracks.getLast().getSonglength();
        numberTracks++;
    }


    /** Getter - Playlistpfad.
     *
     */
    public String getPath() {
        return path;
    }

    /** Getter - Trackanzahl der Playlist.
     *
     */
    public int getNumberTracks() {
        return numberTracks;
    }

    /** Getter - Spielzeit der Playlist.
     *
     */
    public int getPlaytime() {
        return playtime;
    }

    /** Getter - Name der Playlist.
     *
     */
    public String getName() {
        return name;
    }

    /** Getter - Playlistname ändern.
     *
     */
    public void setName(String name) {
        this.name = name;
    }

    /** Getter - Gespeicherte Tracks der Playlist werden zurückgegeben.
     *
     */
    public LinkedList<Track> getTracks() {
        return tracks;
    }

    public String toString (){
        return name;
    }
}
