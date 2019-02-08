package Exceptions;

public class keinSongException extends Exception{
	
	public keinSongException(){
		super("Es wurde kein Song ausgewählt.");
	}
	
	public keinSongException(String msg){
		super (msg);
	}
}
