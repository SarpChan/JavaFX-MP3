package Controller;

import com.wrapper.spotify.SpotifyApi;

import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.credentials.ClientCredentials;
import com.wrapper.spotify.model_objects.specification.AudioFeatures;


import java.io.IOException;
import java.util.HashMap;

public class SpotSearchIds extends AbstractSearch {

    private static HashMap<String, String> ids = new HashMap<>();


    public static String search(Track track) {

        assert !track.getArtist().equals(null) || !track.getArtist().equals("Unbekannt") ;
        assert !track.getTitle().equals(null) || !track.getTitle().equals("Unbekannt");

        if (ids.containsKey(track.getTitle()+track.getArtist())) {
            return ids.get(track.getTitle()+track.getArtist());
        } else {


            try {
                ClientCredentials cred = spotifyApi.clientCredentials().build().execute();
                spotifyApi.setAccessToken(cred.getAccessToken());

                com.wrapper.spotify.model_objects.specification.Track[] c = spotifyApi.searchTracks("artist:\"" + track.getArtist() + "\" track:\"" + track.getTitle()+"\"").build().execute().getItems();

                assert c[0].getArtists()[0].getName().equals(track.getArtist());
                assert c[0].getName().equals(track.getTitle());

                ids.put(track.getTitle() + track.getArtist(), c[0].getId());
                track.setSpotId(c[0].getId());

                return c[0].getId();



            } catch (IOException e) {
                e.printStackTrace();
            } catch (IndexOutOfBoundsException e) {

            } catch (AssertionError e) {

            } catch (SpotifyWebApiException e) {
                e.printStackTrace();
            }

            return null;
        }
    }


}
