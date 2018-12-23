package scenes.singleSong.singleSongView;


import Controller.MP3Player;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.layout.VBox;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import scenes.singleSong.MainView;
import scenes.singleSong.observView.ObservView;

public class SingleSongView extends StackPane{
    StackPane root;
    ImageView actImg;
    int scale = 50;

    public SingleSongView(ObservView observView, MP3Player player) {
        root = new StackPane();
        Image test = new Image("scenes/singleSong/singleSongView/cover.jpg");
        Color averageColor = getAverageColor(test);

        root.setBackground(new Background(new BackgroundFill(averageColor, CornerRadii.EMPTY, Insets.EMPTY)));
        MainView bottom = new MainView(player);

        actImg = new ImageView();

        actImg.setImage(player.getAlbumImage());
        actImg.setImage(test);
        //actImg.setEffect(new GaussianBlur());
        //actImg.setBlendMode(BlendMode.ADD);
       // actImg.setFitWidth(root.getWidth()+scale);
        //actImg.setFitHeight(root.getHeight()+scale);
        actImg.setFitWidth(observView.getRootWidth()+scale);
        actImg.setFitHeight(observView.getRootWidth()+scale);


        Rectangle rectangle = new Rectangle();
        LinearGradient linearGrad = new LinearGradient(0,0,0,1,true,CycleMethod.NO_CYCLE,new Stop(0.05f, Color.rgb(0, 0, 0, 0)),new Stop(0.9f, averageColor));
        rectangle.setFill(linearGrad);
        rectangle.setWidth(observView.getRootWidth()+scale);
        rectangle.setHeight(observView.getRootHeight()+scale);

        bottom.setAlignment(Pos.BOTTOM_CENTER);
        VBox region = new VBox();
        VBox.setVgrow(region, Priority.ALWAYS);
        region.setPrefHeight(0);


        this.getChildren().addAll(actImg,rectangle, bottom);



    }

    private Color getAverageColor(Image image){

        double red = 0;
        double green = 0;
        double blue = 0;
        long pixelAmmount = 0;

        for (int y = 0; y < image.getHeight(); y++)
        {
            for (int x = 0; x < image.getWidth(); x++)
            {
                Color c = image.getPixelReader().getColor(x,y);
                pixelAmmount++;
                red += c.getRed();
                green += c.getGreen();
                blue += c.getBlue();
            }
        }

        int finalred = (int)((red/255)/10);
        int finalgreen = (int)((green/255)/10);
        int finalblue = (int)((blue/255)/10);


        Color averageColor = Color.rgb(finalred,finalgreen,finalblue);
        return averageColor;

    }

    public void setImgWidth(double x){
        actImg.setFitWidth(x+scale);
        actImg.setFitHeight(x+scale);
    }

}
