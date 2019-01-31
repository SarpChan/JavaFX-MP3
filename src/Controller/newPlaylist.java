package Controller;

public class newPlaylist {

    private float acousticness, danceability, energy, instrumentalness,
            liveness, loudness, speechiness, valence, tempo;
    private String name;

    public void setName(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setTempo(float tempo) {
        this.tempo = tempo;
    }

    public void setAcousticness(float acousticness) {
        this.acousticness = acousticness;
    }

    public void setDanceability(float danceability) {
        this.danceability = danceability;
    }

    public void setEnergy(float energy) {
        this.energy = energy;
    }

    public void setInstrumentalness(float instrumentalness) {
        this.instrumentalness = instrumentalness;
    }

    public void setLiveness(float liveness) {
        this.liveness = liveness;
    }

    public void setLoudness(float loudness) {
        this.loudness = loudness;
    }

    public void setSpeechiness(float speechiness) {
        this.speechiness = speechiness;
    }

    public void setValence(float valence) {
        this.valence = valence;
    }

    public float getAcousticness() {
        return acousticness;
    }

    public float getDanceability() {
        return danceability;
    }

    public float getEnergy() {
        return energy;
    }

    public float getInstrumentalness() {
        return instrumentalness;
    }

    public float getLiveness() {
        return liveness;
    }

    public float getLoudness() {
        return loudness;
    }

    public float getSpeechiness() {
        return speechiness;
    }

    public float getValence() {
        return valence;
    }

    public float getTempo() {
        return tempo;
    }
}
