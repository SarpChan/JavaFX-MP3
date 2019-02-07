package Controller;

import Exceptions.SuggestionError;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;


import static Controller.SuggestionParams.*;

public class PlaylistCreator {

    private static LinkedList<newPlaylist> openPlaylists = new LinkedList<>();
    private static ArrayList<Playlist> suggestedPlaylists = new ArrayList<>();



    public static void createSuggestionPlaylist(float [] array, String name){

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
            System.out.println(name + " konnte nicht erstellt werden - Keine passenden Lieder");
            return;
        }


        PlaylistManager.addPlaylist(new Playlist(name, suggestedTracklist));

		/*try {
			savePlaylist(playlistArrayList.get(playlistArrayList.size()-1), openPlaylists.getLast().getName());
		} catch (IOException e) {
			e.printStackTrace();
		}*/


    }
}
