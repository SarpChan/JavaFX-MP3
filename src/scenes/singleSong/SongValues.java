package scenes.singleSong;

public enum SongValues {ACOUSTICNESS, DANCEABILITY, ENERGY, INSTRUMENTALNESS, VALENCE, LIVENESS;

    @Override
    public String toString() {
        String s = this.name().toLowerCase();
        return s.replaceFirst("" + s.charAt(0), (s.charAt(0) + "").toUpperCase());
    }
}

