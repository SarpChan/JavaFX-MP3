package Controller;

import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.specification.AudioFeatures;

import java.io.IOException;

public class SpotSearchFeats extends AbstractSearch {

    public static void search(Track track) {

        try {
            assert !track.getArtist().equals(null) || !track.getArtist().equals("Unbekannt") ;
            assert !track.getTitle().equals(null) || !track.getTitle().equals("Unbekannt");
            String id = SpotSearchIds.search(track);

            if (id != null) {
                AudioFeatures feats = spotifyApi.getAudioFeaturesForTrack(id).build().execute();

                if (feats != null) {
                    track.setAudioFeatures(feats);

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SpotifyWebApiException e) {
            e.printStackTrace();
        } catch (AssertionError e ){
            e.printStackTrace();
        }



    }
}
