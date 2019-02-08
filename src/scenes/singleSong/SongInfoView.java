package scenes.singleSong;

import Controller.MP3Player;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.scene.text.TextBoundsType;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import static scenes.singleSong.MainViewController.getPathFromSVG;

public class SongInfoView extends VBox {

    Button back;
    ImageView cover;
    Text title, artist, album, length, backSign;
    Canvas firstAccordCanvas, thirdAccordCanvas, secondAccordCanvas, forthAccordCanvas, fifthAccordCanvas, sixthAccordCanvas;
    Text firstAccordValue, secondAccordValue, thirdAccordValue, forthAccordValue, fifthAccordValue, sixthAccordValue;
    HBox firstRings, secondRings, thirdRings, buttonBox;
    Color mainColor;
    HBox titleBox;
    Line line;
    VBox contentBox, mobileRings;
    VBox songNames, mobileSongNames;
    HBox basicSongInfo;
    Text mobileTitle;
    HBox mobileTitleBox;
    StackPane firstAccordStack, secondAccordStack, thirdAccordStack, forthAccordStack, fifthAccordStack, sixthAccordStack;




    public SongInfoView(MP3Player player){

        back = new Button();
        back.setMaxWidth(7);
        back.setMaxHeight(7);
        back.setStyle("-fx-shape: \"" + getPathFromSVG("back2") + "\";");
        back.getStyleClass().add("back-button");
        backSign = new Text("BACK");
        backSign.setStyle("-fx-fill:white; -fx-font-size:12px");


        buttonBox = new HBox(3);
        buttonBox.setAlignment(Pos.CENTER_LEFT);

        buttonBox.getChildren().addAll(back, backSign);
        buttonBox.setMaxWidth(40);


        mainColor = Color.rgb(116, 204, 219);




        cover = new ImageView();
        cover.setFitHeight(150);
        cover.setFitWidth(150);
        cover.setImage(player.getAlbumImage());

        titleBox = new HBox();

        title = new Text(player.getTrack());
        title.setStyle("-fx-fill:#fff; -fx-font-size: 35px; ");

        titleBox.getChildren().addAll(title);
        titleBox.setPadding(new Insets(0,20,6,0));
        titleBox.setStyle("-overflow:hidden");

        mobileTitle = new Text();
        mobileTitle.setStyle("-fx-fill:#fff; -fx-font-size: 35px; ");

        mobileTitleBox = new HBox();
        mobileTitleBox.setAlignment(Pos.CENTER);


        artist = new Text(player.getSongArtist());
        artist.setStyle("-fx-fill:#bbb;");

        album = new Text(player.getAlbum());
        album.setStyle("-fx-fill:#bbb;");
        DateFormat zeitanzeige = new SimpleDateFormat("mm:ss");
        length = new Text("" + zeitanzeige.format(player.getSongLength()));
        length.setStyle("-fx-fill:#bbb;");


        songNames = new VBox(3);
        songNames.getChildren().addAll(titleBox, artist, album, length);

        mobileSongNames = new VBox(6);
        mobileSongNames.setPadding(new Insets(0,0,20,0));
        mobileSongNames.getChildren().addAll(mobileTitleBox);
        mobileSongNames.setAlignment(Pos.CENTER);


        basicSongInfo = new HBox(50);
        basicSongInfo.getChildren().addAll( cover, songNames);
        basicSongInfo.setPadding(new Insets(10,0,20,0));


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
        firstAccordValue.setStyle("-fx-font-size: 16px; -fx-fill:#bbb;");
        firstAccordValue.setBoundsType(TextBoundsType.VISUAL);
        firstAccordStack = new StackPane();
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
        thirdAccordValue.setStyle("-fx-font-size: 16px; -fx-fill:#bbb;");
        thirdAccordValue.setBoundsType(TextBoundsType.VISUAL);
        thirdAccordStack = new StackPane();
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
        secondAccordValue.setStyle("-fx-font-size: 16px; -fx-fill:#bbb;");
        secondAccordValue.setBoundsType(TextBoundsType.VISUAL);
        secondAccordStack = new StackPane();
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
        forthAccordValue.setStyle("-fx-font-size: 16px; -fx-fill:#bbb;");
        forthAccordValue.setBoundsType(TextBoundsType.VISUAL);
        forthAccordStack = new StackPane();
        forthAccordStack.getChildren().addAll(forthAccordCanvas, forthAccordValue);

        Circle fifthAccordCircle = new Circle(75);
        fifthAccordCircle.setStroke(mainColor);
        fifthAccordCircle.setFill(null);
        fifthAccordCircle.setStrokeWidth(3.5);

        fifthAccordCanvas = new Canvas(150,150);
        GraphicsContext fifthGc = fifthAccordCanvas.getGraphicsContext2D();
        fifthGc.setStroke(mainColor);
        fifthGc.setLineWidth(4);
        fifthGc.strokeArc(4, 4, 144, 144, 360, 360, ArcType.OPEN);
        fifthGc.setFill(mainColor);

        fifthAccordValue = new Text("Value");
        fifthAccordValue.setStyle("-fx-font-size: 16px; -fx-fill:#bbb;");
        fifthAccordValue.setBoundsType(TextBoundsType.VISUAL);
        fifthAccordStack = new StackPane();
        fifthAccordStack.getChildren().addAll(fifthAccordCanvas, fifthAccordValue);

        Circle sixthAccordCircle = new Circle(75);
        sixthAccordCircle.setStroke(mainColor);
        sixthAccordCircle.setFill(null);
        sixthAccordCircle.setStrokeWidth(3.5);

        sixthAccordCanvas = new Canvas(150,150);
        GraphicsContext sixthGc = sixthAccordCanvas.getGraphicsContext2D();
        sixthGc.setStroke(mainColor);
        sixthGc.setLineWidth(4);
        sixthGc.strokeArc(4, 4, 144, 144, 360, 360, ArcType.OPEN);
        sixthGc.strokeArc(4, 4, 144, 144, 360, 360, ArcType.OPEN);
        sixthGc.setFill(mainColor);

        sixthAccordValue = new Text("Value");
        sixthAccordValue.setStyle("-fx-font-size: 16px; -fx-fill:#bbb;");
        sixthAccordValue.setBoundsType(TextBoundsType.VISUAL);
        sixthAccordStack = new StackPane();
        sixthAccordStack.getChildren().addAll(sixthAccordCanvas, sixthAccordValue);




        firstRings = new HBox(50);
        firstRings.setPadding(new Insets(25,0,25,0));
        firstRings.getChildren().addAll(firstAccordStack, secondAccordStack, thirdAccordStack);

        secondRings = new HBox(50);
        secondRings.setPadding(new Insets(0,0,25,0));
        secondRings.getChildren().addAll(forthAccordStack, fifthAccordStack, sixthAccordStack);

        thirdRings = new HBox(50);

        VBox songValues = new VBox(20);
        songValues.getChildren().addAll(firstRings, secondRings, thirdRings);


        songValues.setAlignment(Pos.CENTER_RIGHT);

        ScrollPane scrollable;
        Pane region, region2;

        region = new HBox();
        HBox.setHgrow(region, Priority.ALWAYS);
        region2 = new HBox();
        HBox.setHgrow(region2, Priority.ALWAYS);

        line = new Line();

        line.setStroke(Color.rgb(187, 187, 187));

        mobileRings = new VBox(20);
        mobileRings.setPadding(new Insets(20,0,0,0));

        contentBox = new VBox();
        contentBox.getChildren().addAll(basicSongInfo, mobileSongNames, line, new HBox(region,mobileRings, songValues, region2));
        contentBox.setAlignment(Pos.CENTER_RIGHT);

        scrollable = new ScrollPane();
        scrollable.setContent(contentBox);
        scrollable.setPadding(new Insets(20, 0, 0, 0));
        scrollable.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollable.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        this.getChildren().addAll(buttonBox, scrollable);

    }




}
