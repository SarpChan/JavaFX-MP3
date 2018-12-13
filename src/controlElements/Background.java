package controlElements;

import javafx.scene.control.Slider;
import javafx.scene.shape.Rectangle;

public class Background {

    public  Background(){


        //HINTERGRUND
        Rectangle progressBackground = new Rectangle();
        progressBackground.setId("progressBackground");
        progressBackground.setHeight(750);
        //progressBackground.xProperty().bind(bot.widthProperty());


       // progress.boundsInLocalProperty().addListener((observable, oldvar, newvar) -> {
           // calculateBackgroundProgress(progress, progressBackground);
       // });
       // progress.valueProperty().addListener((observable, oldvar, newvar) -> {
          //  calculateBackgroundProgress(progress, progressBackground);
       // });

    }

    private void calculateBackgroundProgress(Slider progress, Rectangle bg) {
        double actValue = progress.getValue();
        double width = progress.getWidth();
        double half = (progress.getMax()/2);


        if(actValue == half){
            bg.setWidth(width/2);

        }
        else if (actValue < half){
            double actProgress = 1.0-(actValue/half);
            double minwidth = progress.getWidth() / 2 + (progress.getWidth()/2) * (actProgress);
            bg.setWidth(width-minwidth);


        }
        else if (actValue > half ){
            double actProgress = (actValue-half)/half;
            double minwidth = progress.getWidth() / 2 + (progress.getWidth()/2) * (actProgress);
            bg.setWidth(minwidth);
        }



    }

}
