package scenes.singleSong;

import Controller.MP3Player;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;

public class MainViewController{
    MP3Player player;
    MainView view;

    private boolean listenToScrollbar = false;
    private boolean paused = true;
    private boolean listenToProgress = true;
    private long countMillis = 0, firstMillis;
    Slider progress;

    private double volumePosition = 50;

    MainViewController(MP3Player player){
        this.player = player;
        view = new MainView(player);
        progress = view.progress;
        initialize();


    }

    public MainView getView (){
        return view;
    }

    public void initialize(){



    }




}
