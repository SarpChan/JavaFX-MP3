package Exceptions;

public class SuggestionError extends RuntimeException {

    public SuggestionError(){
        super("Playlist konnte nicht erstellt werden - Keine Lieder, die zur Auswahl passen");
    }

    public SuggestionError(String msg){
        super (msg);
    }
}
