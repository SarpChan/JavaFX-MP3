package scenes.singleSong.actPlaylistView;

import Controller.MP3Player;
import scenes.singleSong.ControlView;

public class ActPlaylistViewController {
    private MP3Player player;
    private ActPlaylistView view;
    private ControlView controlView;

    ActPlaylistViewController(MP3Player player){
        this.player = player;
        //this.view = new ActPlaylistView(this, player);
        //this.controlView = view.controlView;
        initilize();
    }

    public ActPlaylistView getActPlaylistView(){
        return view;
    }

    public void initilize(){

    }
}
