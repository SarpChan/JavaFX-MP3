
public class keinSongException extends Exception{
	
	keinSongException(){
		super("es wurde kein Song ausgewählt");
	}
	
	keinSongException(String msg){
		super (msg);
	}
}
