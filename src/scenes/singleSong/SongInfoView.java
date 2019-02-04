package scenes.singleSong;

import Controller.MP3Player;
import javafx.geometry.Insets;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.scene.text.TextBoundsType;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class SongInfoView extends VBox {

    Button back;
    ImageView cover;
    Text title, artist, album, length;
    Canvas firstAccordCanvas, thirdAccordCanvas, secondAccordCanvas, forthAccordCanvas;
    Text firstAccordValue, secondAccordValue, thirdAccordValue, forthAccordValue;


    public SongInfoView(MP3Player player){

        back = new Button("zurück");

        Color mainColor;
        mainColor = Color.rgb(116, 204, 219);

        HBox basicSongInfo = new HBox(50);

        cover = new ImageView();
        cover.setFitHeight(150);
        cover.setFitWidth(150);
        cover.setImage(player.getAlbumImage());

        HBox titleBox = new HBox();

        title = new Text(player.getTrack());
        title.setStyle("-fx-fill:#fff; -fx-font-size: 35px;");

        titleBox.getChildren().addAll(title);
        titleBox.setPadding(new Insets(0,20,6,0));


        artist = new Text(player.getSongArtist());
        artist.setStyle("-fx-fill:#bbb;");

        album = new Text(player.getAlbum());
        album.setStyle("-fx-fill:#bbb;");
        DateFormat zeitanzeige = new SimpleDateFormat("mm:ss");
        length = new Text("" + zeitanzeige.format(player.getSongLength()));
        length.setStyle("-fx-fill:#bbb;");


        VBox songNames = new VBox(3);
        songNames.getChildren().addAll(titleBox, artist, album, length);


        basicSongInfo.getChildren().addAll(cover, songNames);

        Circle firstAccordCicle = new Circle(75);
        firstAccordCicle.setStroke(mainColor);
        firstAccordCicle.setFill(null);
        firstAccordCicle.setStrokeWidth(3.5);

        firstAccordCanvas = new Canvas(150,150);
        GraphicsContext firstGc = firstAccordCanvas.getGraphicsContext2D();
        firstGc.setStroke(mainColor);
        firstGc.setLineWidth(4);
        firstGc.strokeArc(4, 4, 144, 144, 360, 360, ArcType.OPEN);
        firstGc.setFill(mainColor);

        firstAccordValue = new Text("Value");
        firstAccordValue.setFill(mainColor);
        firstAccordValue.setStyle("-fx-font-size: 20px;");
        firstAccordValue.setBoundsType(TextBoundsType.VISUAL);
        StackPane firstAccordStack = new StackPane();
        firstAccordStack.getChildren().addAll(firstAccordCanvas, firstAccordValue);

        Circle thirdAccordCircle = new Circle(75);
        thirdAccordCircle.setStroke(mainColor);
        thirdAccordCircle.setFill(null);
        thirdAccordCircle.setStrokeWidth(3.5);

        thirdAccordCanvas = new Canvas(150,150);
        GraphicsContext thirdGc = thirdAccordCanvas.getGraphicsContext2D();
        thirdGc.setStroke(mainColor);
        thirdGc.setLineWidth(4);
        thirdGc.strokeArc(4, 4, 144, 144, 360, 360, ArcType.OPEN);
        thirdGc.setFill(mainColor);

        thirdAccordValue = new Text("Value");
        thirdAccordValue.setFill(mainColor);
        thirdAccordValue.setStyle("-fx-font-size: 20px;");
        thirdAccordValue.setBoundsType(TextBoundsType.VISUAL);
        StackPane thirdAccordStack = new StackPane();
        thirdAccordStack.getChildren().addAll(thirdAccordCanvas, thirdAccordValue);


        Circle secondAccordCircle = new Circle(75);
        secondAccordCircle.setStroke(mainColor);
        secondAccordCircle.setFill(null);
        secondAccordCircle.setStrokeWidth(3.5);

        secondAccordCanvas = new Canvas(150,150);
        GraphicsContext secondGc = secondAccordCanvas.getGraphicsContext2D();
        secondGc.setStroke(mainColor);
        secondGc.setLineWidth(4);
        secondGc.strokeArc(4, 4, 144, 144, 360, 360, ArcType.OPEN);
        secondGc.setFill(mainColor);

        secondAccordValue = new Text("Value");
        secondAccordValue.setFill(mainColor);
        secondAccordValue.setStyle("-fx-font-size: 20px;");
        secondAccordValue.setBoundsType(TextBoundsType.VISUAL);
        StackPane secondAccordStack = new StackPane();
        secondAccordStack.getChildren().addAll(secondAccordCanvas, secondAccordValue);

        Circle forthAccordCircle = new Circle(75);
        forthAccordCircle.setStroke(mainColor);
        forthAccordCircle.setFill(null);
        forthAccordCircle.setStrokeWidth(3.5);

        forthAccordCanvas = new Canvas(150,150);
        GraphicsContext forthGc = forthAccordCanvas.getGraphicsContext2D();
        forthGc.setStroke(mainColor);
        forthGc.setLineWidth(4);
        forthGc.strokeArc(4, 4, 144, 144, 360, 360, ArcType.OPEN);
        forthGc.setFill(mainColor);

        forthAccordValue = new Text("Value");
        forthAccordValue.setFill(mainColor);
        forthAccordValue.setStyle("-fx-font-size: 20px;");
        forthAccordValue.setBoundsType(TextBoundsType.VISUAL);
        StackPane forthAccordStack = new StackPane();
        forthAccordStack.getChildren().addAll(forthAccordCanvas, forthAccordValue);


        VBox firstRings = new VBox(25);
        firstRings.setPadding(new Insets(25,0,0,0));
        firstRings.getChildren().addAll(firstAccordStack, thirdAccordStack);

        VBox secondRings = new VBox(25);
        secondRings.setPadding(new Insets(25,0,0,0));
        secondRings.getChildren().addAll(secondAccordStack, forthAccordStack);

        HBox songValues = new HBox(50);
        songValues.getChildren().addAll(firstRings, secondRings);


        this.getChildren().addAll(back, basicSongInfo, songValues);

    }




}
