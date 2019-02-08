package controlElements;

import javafx.geometry.Pos;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Slider;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

public class Progress extends StackPane {
        Slider progressSlider;
        ProgressBar progressBar;
        Line progressLine;


    public Progress() {
        //SLIDER
        progressSlider = new Slider();
        progressSlider.setMin(0);
        progressSlider.setMax(100);
        progressSlider.setId("progress");

        //LISTENER AUF SLIDERWERT
        progressSlider.boundsInLocalProperty().addListener((observable, oldvar, newvar) -> {
            calcProgressBar(progressSlider, progressBar);
        });
        progressSlider.valueProperty().addListener((observable, oldvar, newvar) -> {
            calcProgressBar(progressSlider, progressBar);
        });

        //PROGRESSBAR
        progressBar = new ProgressBar();
        progressBar.minWidth(0);
        progressBar.setId("pb1");

        //LINE
        progressLine = new Line();
        progressLine.setStartX(0);
        progressLine.setEndX(1024);
        progressLine.getStyleClass().add("progressLine");

        this.getChildren().addAll(progressLine, progressBar, progressSlider);
        this.setAlignment(Pos.CENTER_LEFT);
        this.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        this.setMinSize(Double.MIN_VALUE, Double.MIN_VALUE);

    }

    private void calcProgressBar(Slider progress, ProgressBar bar1) {
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
