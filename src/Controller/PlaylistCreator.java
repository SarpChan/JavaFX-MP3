package Controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


import static Controller.SuggestionParams.*;

public class PlaylistCreator {

    /** Sortiert die auf dem Computer vorhandenen Audiodateien in Themen-Playlisten die vorgeschlagen werden.
     *
     */
    public static void createSuggestionPlaylist(float [] array, String ... name){
        boolean checkIfEmpty = true;
        for (float f :
                array) {
            if(f > 0.0f){
                checkIfEmpty = false;
            }
        }
        if(checkIfEmpty){
            return;
        }

        SuggestionParams [] param = SuggestionParams.values();

        for (int i = 0; i < array.length; i++) {
            if(array[i] == 0.0f){
                param[i] = UNDEFINED;
            }

        }

        HashMap<String,Track> tempMap= (HashMap)PlaylistManager.collectExistingTracks().clone();
        HashMap<String,Track> suggestedTracklist = (HashMap)PlaylistManager.collectExistingTracks().clone();


        for (SuggestionParams actParam:
                param) {
            switch (actParam){
                case MINVALENCE:
                    for (Map.Entry<String, Track> entry : tempMap.entrySet()){

                        if(entry.getValue().getSpotId().equals(null)){
                            suggestedTracklist.remove(entry.getKey());
                        }

                        if(array[2] > entry.getValue().getValence()){
                            suggestedTracklist.remove(entry.getKey());
                        }
                    } break;
                case MINBPM:
                    for (Map.Entry<String, Track> entry : tempMap.entrySet()){

                        if(entry.getValue().getSpotId().equals(null)){
                            suggestedTracklist.remove(entry.getKey());
                        } else if(array[0] > entry.getValue().getBPM()){
                            suggestedTracklist.remove(entry.getKey());
                        }
                    } break;
                case MINACOUSTICNESS:
                    for (Map.Entry<String, Track> entry : tempMap.entrySet()){

                        if(entry.getValue().getSpotId().equals(null)){
                            suggestedTracklist.remove(entry.getKey());
                        } else if(array[10] > entry.getValue().getAcousticness()){
                            suggestedTracklist.remove(entry.getKey());
                        }
                    } break;
                case MINDANCEABILITY:
                    for (Map.Entry<String, Track> entry : tempMap.entrySet()){

                        if(entry.getValue().getSpotId().equals(null)){
                            suggestedTracklist.remove(entry.getKey());
                        } else if(array[6] > entry.getValue().getDanceability()){
                            suggestedTracklist.remove(entry.getKey());
                        }
                    } break;
                case MINENERGY:
                    for (Map.Entry<String, Track> entry : tempMap.entrySet()){
                        if(entry.getValue().getSpotId().equals(null)){
                            suggestedTracklist.remove(entry.getKey());
                        } else if(array[4] > entry.getValue().getEnergy()){
                            suggestedTracklist.remove(entry.getKey());
                        }
                    } break;
                case MININSTRUMENTALNESS:
                    for (Map.Entry<String, Track> entry : tempMap.entrySet()){
                        if(entry.getValue().getSpotId().equals(null)){
                            suggestedTracklist.remove(entry.getKey());
                        } else if(array[8] > entry.getValue().getInstrumentalness()){
                            suggestedTracklist.remove(entry.getKey());
                        }
                    } break;
                case MAXVALENCE:
                    for (Map.Entry<String, Track> entry : tempMap.entrySet()){

                        if(entry.getValue().getSpotId().equals(null)){
                            suggestedTracklist.remove(entry.getKey());
                        }

                        if(array[3] > entry.getValue().getValence()){
                            suggestedTracklist.remove(entry.getKey());
                        }
                    } break;
                case MAXBPM:
                    for (Map.Entry<String, Track> entry : tempMap.entrySet()){

                        if(entry.getValue().getSpotId().equals(null)){
                            suggestedTracklist.remove(entry.getKey());
                        } else if(array[1] > entry.getValue().getBPM()){
                            suggestedTracklist.remove(entry.getKey());
                        }
                    } break;
                case MAXACOUSTICNESS:
                    for (Map.Entry<String, Track> entry : tempMap.entrySet()){

                        if(entry.getValue().getSpotId().equals(null)){
                            suggestedTracklist.remove(entry.getKey());
                        } else if(array[11] < entry.getValue().getAcousticness()){
                            suggestedTracklist.remove(entry.getKey());
                        }
                    } break;
                case MAXDANCEABILITY:
                    for (Map.Entry<String, Track> entry : tempMap.entrySet()){

                        if(entry.getValue().getSpotId().equals(null)){
                            suggestedTracklist.remove(entry.getKey());
                        } else if(array[7] < entry.getValue().getDanceability()){
                            suggestedTracklist.remove(entry.getKey());
                        }
                    } break;
                case MAXENERGY:
                    for (Map.Entry<String, Track> entry : tempMap.entrySet()){
                        if(entry.getValue().getSpotId().equals(null)){
                            suggestedTracklist.remove(entry.getKey());
                        } else if(array[5] < entry.getValue().getEnergy()){
                            suggestedTracklist.remove(entry.getKey());
                        }
                    } break;
                case MAXINSTRUMENTALNESS:
                    for (Map.Entry<String, Track> entry : tempMap.entrySet()){
                        if(entry.getValue().getSpotId().equals(null)){
                            suggestedTracklist.remove(entry.getKey());
                        } else if(array[9] < entry.getValue().getInstrumentalness()){
                            suggestedTracklist.remove(entry.getKey());
                        }
                    } break;
                case UNDEFINED:
                    continue;
                default: continue;


            }

        }

        if(suggestedTracklist.isEmpty()) {
            return;
        }

        if(name.length == 1) {
            Playlist temp = new Playlist(name[0], suggestedTracklist);
            PlaylistManager.addPlaylist(temp);
            try {
                PlaylistManager.savePlaylist(temp, temp.getName());
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            PlaylistManager.addToSuggestedPlaylist((new Playlist(name[0], suggestedTracklist)));
        }
    }
}
