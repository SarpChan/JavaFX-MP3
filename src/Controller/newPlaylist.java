package Controller;

public class newPlaylist {

    private float minAcousticness, minDanceability, minEnergy, minInstrumentalness,
             minValence, minTempo, maxAcousticness, maxDanceability, maxEnergy, maxInstrumentalness,
             maxValence, maxTempo;
    private String name;

    public newPlaylist(float [] array, String name){
        this.name = name;
        this.minAcousticness = array[0];
        this.maxAcousticness = array[1];
        this.minDanceability = array[2];
        this.maxDanceability = array [3];
        this.minEnergy = array[4];
        this.maxEnergy = array[5];
        this.minInstrumentalness = array[6];
        this.maxInstrumentalness = array[7];
        this.minValence = array[8];
        this.maxValence = array[9];
        this.minTempo = array[10];
        this.maxTempo = array[11];


    }


    public float getMinAcousticness() {
        return minAcousticness;
    }

    public float getMinDanceability() {
        return minDanceability;
    }

    public float getMinEnergy() {
        return minEnergy;
    }

    public float getMinInstrumentalness() {
        return minInstrumentalness;
    }


    public float getMinValence() {
        return minValence;
    }

    public float getMinTempo() {
        return minTempo;
    }

    public float getMaxAcousticness() {
        return maxAcousticness;
    }

    public float getMaxDanceability() {
        return maxDanceability;
    }

    public float getMaxEnergy() {
        return maxEnergy;
    }

    public float getMaxInstrumentalness() {
        return maxInstrumentalness;
    }

    public float getMaxValence() {
        return maxValence;
    }

    public float getMaxTempo() {
        return maxTempo;
    }

    public String getName() {
        return name;
    }
}
