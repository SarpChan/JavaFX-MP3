package controlElements;

import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;

public class VolumeAndTime extends ExtendedHBox {

    Button mute;
    Slider volume;
    ProgressBar pb2;
    Line line2;
    StackPane volumePane;
    Text time;

    public VolumeAndTime(){

        mute = new Button();
        mute.getStyleClass().add("mute-button");
        mute.setStyle("-fx-shape: \"" + getPathFromSVG("mute") + "\";");
        mute.setAlignment(Pos.BASELINE_LEFT);

        volume = new Slider();
        volume.setId("volume");
        volume.setMin(0);
        volume.setOrientation(Orientation.HORIZONTAL);
        volume.setPadding(new Insets(0, 0, 0, 10));
        volume.boundsInLocalProperty().addListener((observable, oldvar, newvar) -> {
            calculatePB(volume, pb2);
        });
        volume.valueProperty().addListener((observable, oldvar, newvar) -> {
            calculatePB(volume, pb2);
        });

        pb2 = new ProgressBar(0.0);
        pb2.minWidth(0);
        pb2.setId("pb2");

        line2 = new Line();
        line2.setStartX(0);
        line2.setEndX(100);
        line2.getStyleClass().add("progressLine");

        volumePane = new StackPane();
        volumePane.getChildren().addAll(volume,pb2,line2);
        volumePane.setAlignment(Pos.CENTER_LEFT);

        time = new Text("01:35 / 3:75");
        time.getStyleClass().add("secondarytext");

        this.getChildren().addAll(mute, volumePane, time);
        this.setAlignment(Pos.CENTER);
        this.setPadding(new Insets(0, 45, 0, 0));

    }

    private void calculatePB(Slider progress, ProgressBar bar1) {
        double actValue = progress.getValue();
        double width = progress.getWidth();
        double half = (progress.getMax()/2);

        bar1.setProgress(1);


        if(actValue == half){
            bar1.setMinWidth(width/2);

        }
        else if (actValue < half){
            double actProgress = 1.0-(actValue/half);
            double minwidth = progress.getWidth() / 2 + (progress.getWidth()/2) * (actProgress);
            bar1.setMinWidth(width-minwidth);
            bar1.setMaxWidth(width-minwidth);

        }
        else if (actValue > half ){
            double actProgress = (actValue-half)/half;
            double minwidth = progress.getWidth() / 2 + (progress.getWidth()/2) * (actProgress);
            bar1.setMinWidth(minwidth);
        }



    }

}
