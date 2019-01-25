package Controller;

import com.wrapper.spotify.SpotifyApi;

public abstract class AbstractSearch {

    protected static final String clientId = "7030ee51bead4ebdaa5a4598951b304b";
    protected static final String clientSecret = "5cd5b5f9b5b94a08af027c10071c55c2";

    protected static final SpotifyApi spotifyApi = new SpotifyApi.Builder()
            .setClientId(clientId)
            .setClientSecret(clientSecret)
            .build();


}
