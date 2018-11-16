import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
public class Gui extends Application {

    MP3Player player;

    public void init() {
        this.player = new MP3Player("KOKAIN.mp3");
    }


    @Override
    public void start(Stage primaryStage) throws Exception {


        Pane root = new VBox();
        root.setStyle("-fx-background-color: transparent;");
        root.setPadding(new Insets(20));




        Scene scene = new Scene(root, 300, 500);
        scene.setFill(Color.WHITE);

        root.getChildren().addAll(SongControl());

        primaryStage.setScene(scene);
        primaryStage.initStyle(StageStyle.DECORATED);
        primaryStage.setTitle("CooleGruppe MP3");
        primaryStage.show();

    }

    public void stop() {


    }

    private Pane SongInfo() {
        VBox container = new VBox(15);

        return container;

    }

    private Pane SongControl() {

        VBox container = new VBox(15);
        HBox buttons = new HBox();
        HBox slider = new HBox();

        Button next = new Button("NEXT");
        Button prev = new Button("PREV");
        Button pause = new Button("PAUSE");
        Button play = new Button("PLAY");

        play.setOnAction(onButtonPlayClick());
        pause.setOnAction(onButtonPauseClick());

        Slider volume = new Slider();

        volume.setPrefWidth(300);
        volume.setMin(0);
        volume.setMax(100);
        volume.setValue(50);

        volume.valueProperty().addListener(changeValue());

        slider.getChildren().addAll(volume);
        slider.setAlignment(Pos.CENTER);

        buttons.getChildren().addAll(prev, play, pause, next);
        buttons.setAlignment(Pos.CENTER);

        container.getChildren().addAll(buttons, slider);

        return container;

    }

    private EventHandler<ActionEvent> onButtonPlayClick () {

        return new EventHandler <ActionEvent>() {

            @Override
            public void handle(ActionEvent arg0) {
                try {
                    player.play();
                } catch (keinSongException e) {
                    e.printStackTrace();
                }
            }

        };

    }

    private EventHandler<ActionEvent> onButtonPauseClick () {

        return new EventHandler <ActionEvent>() {

            @Override
            public void handle(ActionEvent arg0) {
                try {
                    player.pause();
                } catch (keinSongException e) {
                    e.printStackTrace();
                }
            }

        };

    }

    private ChangeListener<Number> changeValue(){



        return new ChangeListener<Number>(){
            @Override
            public void changed(ObservableValue<? extends Number> arg0, Number oldValue, Number newValue) {
                player.volume((newValue.floatValue() / 100));
            }
        };
    }

}
