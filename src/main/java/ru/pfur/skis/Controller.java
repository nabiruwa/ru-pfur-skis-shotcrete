package ru.pfur.skis;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.ZoomEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    ComboBox<String> steel;
    @FXML
    ComboBox<String> concrete;
    @FXML
    Pane drawPanel;
    @FXML
    TextField dPipe;
    @FXML
    TextField sPipe;
    @FXML
    TextField dSpan;
    @FXML
    TextField nFloors;
    @FXML
    TextField mDeform;
    @FXML
    TextField iModulB;
    @FXML
    TextField iModulS;
    @FXML
    TextField shortTermCol;
    @FXML
    TextField longTermCol;
    @FXML
    TextField selfWeightCol;
    @FXML
    TextField shortTermPlate;
    @FXML
    TextField longTermPlate;
    @FXML
    TextField selfWeightPlate;
    @FXML
    TextField cover;
    @FXML
    TextField h1Floors;
    @FXML
    TextField hFloors;
    @FXML
    TextField tPP;

    private Boolean show = false;


    Canvas canvas;

    Number wd = 0.0;
    Number hd = 0.0;

    @FXML
    Button calculate;
    @FXML
    Button draw;
    @FXML
    Button section;

    Model model = new Model();
    private Stage stage;

    //---------------------for calculate ----------------------


    @FXML
    private void onShowComboBox() {

    }

    public void initialize(URL url, ResourceBundle resourceBundle) {
        assert steel != null : "fx:id=\"steel\" was not injected: check your FXML file 'main.fxml'.";
        assert concrete != null : "fx:id=\"concrete\" was not injected: check your FXML file 'main.fxml'.";

        for (String steelElement : Model.getSteels()) {
            steel.getItems().add(steelElement);
        }

        for (String concreteElement : Model.getConcretes()) {
            concrete.getItems().add(concreteElement);
        }
        canvas = new Canvas(drawPanel.getWidth(), drawPanel.getHeight());
        canvas = new Canvas(1500, 1500);
        drawPanel.getChildren().add(canvas);

       // canvas.getGraphicsContext2D().strokeOval(100, 100, 100, 100);

        draw.setOnAction((ActionEvent e) -> {
            if (!show)
            draw();
            show = !show;
        });

        section.setOnAction(e->{
            new Section(stage, "Section", Section.ICON_INFO).showAndWait();
        });

        calculate.setOnAction((ActionEvent e) -> {
            try {
                model.setDpipe(Double.valueOf(dPipe.getText()));
                model.setSpipe(Double.valueOf(sPipe.getText()));
                model.setSteel(Model.getSteelValue(steel.getValue()));
                model.setConcrete(Model.getConcreteValue(concrete.getValue()));
                model.setDspan(Double.valueOf(dSpan.getText()));
                model.setNfloors(Integer.valueOf(nFloors.getText()));
//                model.setMdeform(Double.valueOf(mDeform.getText()));
//                model.setImodulb(Double.valueOf(iModulB.getText()));
//                model.setImoduls(Double.valueOf(iModulS.getText()));
//                model.setShorttermcol(Double.valueOf(shortTermCol.getText()));
//                model.setLongtermcol(Double.valueOf(longTermCol.getText()));
//                model.setSelfweightcol(Double.valueOf(selfWeightCol.getText()));
                model.setShorttermplate(Double.valueOf(shortTermPlate.getText()));
                model.setLongtermplate(Double.valueOf(longTermPlate.getText()));
                model.setSelfweightplate(Double.valueOf(selfWeightPlate.getText()));
                model.sethFloors(Double.valueOf(hFloors.getText()));
                model.setH1Floors(Double.valueOf(h1Floors.getText()));
                model.setCover(Double.valueOf(cover.getText()));
                model.setTpp(Double.valueOf(tPP.getText()));


                if (model.calculate(stage))
                    draw();
                draw();
            } catch (Exception e2) {
                new AlertDialog(stage, "Одно из полей не заполнено \nили заполнено неверно!", AlertDialog.ICON_INFO).showAndWait();
            }

        });

        drawPanel.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                wd = newValue;
                canvas.widthProperty().setValue(wd);
                drawBorder();
                if (show)
                    draw();
            }
        });

        drawPanel.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                hd = newValue;
                canvas.heightProperty().setValue(hd);
                drawBorder();
                if (show)
                    draw();
            }
        });
    }

    private void draw() {
        double x0 = canvas.getWidth() / 2;
        double y0 = canvas.getHeight() / 2;


        GraphicsContext gc = canvas.getGraphicsContext2D();
        // gc.clearRect(1, 1, (Double) wd - 2, (Double) hd - 2);

        gc.setMiterLimit(20);

        gc.setFill(Color.GREEN);
        gc.setStroke(Color.RED);
        gc.setLineWidth(1);

        //gc.setLineHeight(1);


        gc.strokeLine(x0 - canvas.getWidth() / 2 + 50, y0, x0 + canvas.getWidth() / 2 - 50, y0);
        gc.strokeLine(x0, y0 - canvas.getHeight() / 2 + 25, x0, y0 + canvas.getHeight() / 2 - 25);
        //gc.strokeLine(y0 - canvas.getHeight() / 2 + 50, x0, y0 + canvas.getHeight() / 2 - 50, x0);

        gc.setStroke(Color.CADETBLUE);
        double t = Double.valueOf(sPipe.getText());
        gc.setLineWidth(t * 7);
//        if (t<=0.5) t = 1;
//        else if (t<=1) t = 2;
//        else if (t<=1.5) t = 3;
//        else t = 4;


        double f = Double.valueOf(dPipe.getText()) * 5;

        gc.strokeOval(x0 - f / 2, y0 - f / 2, f, f);
        gc.setLineWidth(t * 2);
        gc.strokeOval(x0 - (3 * f / 2), y0 - (3 * f / 2), f * 3, f * 3);
        gc.setLineWidth(t * 5);


        gc.strokeLine(x0 - ((25 + 12.5) * 5), y0 - ((25 + 12.5) * 5), x0 - ((25 + 12.5) * 2.5), y0 - ((25 + 12.5) * 2.5));
        gc.strokeLine(x0 - ((25 + 12.5) * 5), y0 + ((25 + 12.5) * 5), x0 - ((25 + 12.5) * 2.5), y0 + ((25 + 12.5) * 2.5));
        gc.strokeLine(x0 + ((25 + 12.5) * 5), y0 - ((25 + 12.5) * 5), x0 + ((25 + 12.5) * 2.5), y0 - ((25 + 12.5) * 2.5));
        gc.strokeLine(x0 + ((25 + 12.5) * 5), y0 + ((25 + 12.5) * 5), x0 + ((25 + 12.5) * 2.5), y0 + ((25 + 12.5) * 2.5));
        gc.setLineWidth(t * 6);
        gc.setFill(Color.CADETBLUE);
        gc.fillOval(x0 - 12.5 * 5 - 14, y0 - 12.5 * 5 - 14, 14, 14);
        gc.fillOval(x0 + 12.5 * 5, y0 - 12.5 * 5 - 14, 14, 14);
        gc.fillOval(x0 - 12.5 * 5 - 14, y0 + 12.5 * 5, 14, 14);
        gc.fillOval(x0 + 12.5 * 5, y0 + 12.5 * 5, 14, 14);

        double ff = f / 2 * 10 / 7;
        gc.setStroke(Color.MEDIUMAQUAMARINE);
        gc.setLineWidth(2.5);
        gc.strokeLine(x0 - canvas.getWidth() / 2 + 50, y0 - 12.5 * 5, x0 + canvas.getWidth() / 2 - 50, y0 - 12.5 * 5);
        gc.strokeLine(x0 - canvas.getWidth() / 2 + 50, y0 - (12.5 + 25) * 5, x0 + canvas.getWidth() / 2 - 50, y0 - (12.5 + 25) * 5);
        gc.strokeLine(x0 - canvas.getWidth() / 2 + 50, y0 - (12.5 + 50) * 5, x0 + canvas.getWidth() / 2 - 50, y0 - (12.5 + 50) * 5);
        gc.setStroke(Color.AQUAMARINE);
        gc.setLineWidth(2);
        gc.strokeLine(x0 - canvas.getWidth() / 2 + 50, y0 - (12.5 + 75) * 5, x0 + canvas.getWidth() / 2 - 50, y0 - (12.5 + 75) * 5);

        gc.setStroke(Color.MEDIUMAQUAMARINE);
        gc.setLineWidth(2.5);
        gc.strokeLine(x0 - canvas.getWidth() / 2 + 50, y0 + 12.5 * 5, x0 + canvas.getWidth() / 2 - 50, y0 + 12.5 * 5);
        gc.strokeLine(x0 - canvas.getWidth() / 2 + 50, y0 + (12.5 + 25) * 5, x0 + canvas.getWidth() / 2 - 50, y0 + (12.5 + 25) * 5);
        gc.strokeLine(x0 - canvas.getWidth() / 2 + 50, y0 + (12.5 + 50) * 5, x0 + canvas.getWidth() / 2 - 50, y0 + (12.5 + 50) * 5);
        gc.setStroke(Color.AQUAMARINE);
        gc.setLineWidth(2);
        gc.strokeLine(x0 - canvas.getWidth() / 2 + 50, y0 + (12.5 + 75) * 5, x0 + canvas.getWidth() / 2 - 50, y0 + (12.5 + 75) * 5);
//        gc.strokeLine(x0 - canvas.getWidth() / 2 + 50, y0+(12.5+75) *5, x0 + canvas.getWidth() / 2 - 50, y0+(12.5+75)*5);


        // gc.setLineHeight(2);
        gc.setStroke(Color.MEDIUMAQUAMARINE);
        gc.setLineWidth(2.5);
        gc.strokeLine(x0 - 12.5 * 5, y0 - canvas.getHeight() / 2 + 25, x0 - 12.5 * 5, y0 + canvas.getHeight() / 2 - 25);
        gc.strokeLine(x0 - (12.5 + 25) * 5, y0 - canvas.getHeight() / 2 + 25, x0 - (12.5 + 25) * 5, y0 + canvas.getHeight() / 2 - 25);
        gc.strokeLine(x0 - (12.5 + 50) * 5, y0 - canvas.getHeight() / 2 + 25, x0 - (12.5 + 50) * 5, y0 + canvas.getHeight() / 2 - 25);
        gc.setStroke(Color.AQUAMARINE);
        gc.setLineWidth(2);
        gc.strokeLine(x0 - (12.5 + 75) * 5, y0 - canvas.getHeight() / 2 + 25, x0 - (12.5 + 75) * 5, y0 + canvas.getHeight() / 2 - 25);
        gc.strokeLine(x0 - (12.5 + 100) * 5, y0 - canvas.getHeight() / 2 + 25, x0 - (12.5 + 100) * 5, y0 + canvas.getHeight() / 2 - 25);
        gc.strokeLine(x0 - (12.5 + 125) * 5, y0 - canvas.getHeight() / 2 + 25, x0 - (12.5 + 125) * 5, y0 + canvas.getHeight() / 2 - 25);

        gc.setStroke(Color.MEDIUMAQUAMARINE);
        gc.setLineWidth(2.5);
        gc.strokeLine(x0 + 12.5 * 5, y0 - canvas.getHeight() / 2 + 25, x0 + 12.5 * 5, y0 + canvas.getHeight() / 2 - 25);
        gc.strokeLine(x0 + (12.5 + 25) * 5, y0 - canvas.getHeight() / 2 + 25, x0 + (12.5 + 25) * 5, y0 + canvas.getHeight() / 2 - 25);
        gc.strokeLine(x0 + (12.5 + 50) * 5, y0 - canvas.getHeight() / 2 + 25, x0 + (12.5 + 50) * 5, y0 + canvas.getHeight() / 2 - 25);
        gc.setStroke(Color.AQUAMARINE);
        gc.setLineWidth(2);
        gc.strokeLine(x0 + (12.5 + 75) * 5, y0 - canvas.getHeight() / 2 + 25, x0 + (12.5 + 75) * 5, y0 + canvas.getHeight() / 2 - 25);
        gc.strokeLine(x0 + (12.5 + 100) * 5, y0 - canvas.getHeight() / 2 + 25, x0 + (12.5 + 100) * 5, y0 + canvas.getHeight() / 2 - 25);
        gc.strokeLine(x0 + (12.5 + 125) * 5, y0 - canvas.getHeight() / 2 + 25, x0 + (12.5 + 125) * 5, y0 + canvas.getHeight() / 2 - 25);

        gc.setStroke(Color.DARKKHAKI);
        gc.setLineWidth(7 * t);

        gc.strokeLine(x0 - (12.5) * 5, y0 - (12.5) * 5, x0 - canvas.getWidth() / 2 + 150, y0 - (12.5) * 5);
        gc.strokeLine(x0 - (12.5) * 5, y0 + (12.5) * 5, x0 - canvas.getWidth() / 2 + 150, y0 + (12.5) * 5);
        gc.strokeLine(x0 + (12.5) * 5, y0 - (12.5) * 5, x0 + canvas.getWidth() / 2 - 150, y0 - (12.5) * 5);
        gc.strokeLine(x0 + (12.5) * 5, y0 + (12.5) * 5, x0 + canvas.getWidth() / 2 - 150, y0 + (12.5) * 5);

        gc.strokeLine(x0 - (12.5 + 25) * 5, y0 - (12.5 + 25) * 5, x0 - canvas.getWidth() / 2 + 200, y0 - (12.5 + 25) * 5);
        gc.strokeLine(x0 - (12.5 + 25) * 5, y0 + (12.5 + 25) * 5, x0 - canvas.getWidth() / 2 + 200, y0 + (12.5 + 25) * 5);
        gc.strokeLine(x0 + (12.5 + 25) * 5, y0 - (12.5 + 25) * 5, x0 + canvas.getWidth() / 2 - 200, y0 - (12.5 + 25) * 5);
        gc.strokeLine(x0 + (12.5 + 25) * 5, y0 + (12.5 + 25) * 5, x0 + canvas.getWidth() / 2 - 200, y0 + (12.5 + 25) * 5);

        gc.strokeLine(x0 - (12.5 + 25) * 5, y0 - (12.5 + 25) * 5, x0 - (12.5 + 25) * 5, y0 - canvas.getHeight() / 2 + 25);
        gc.strokeLine(x0 + (12.5 + 25) * 5, y0 - (12.5 + 25) * 5, x0 + (12.5 + 25) * 5, y0 - canvas.getHeight() / 2 + 25);
        gc.strokeLine(x0 - (12.5 + 25) * 5, y0 + (12.5 + 25) * 5, x0 - (12.5 + 25) * 5, y0 + canvas.getHeight() / 2 + 25);
        gc.strokeLine(x0 + (12.5 + 25) * 5, y0 + (12.5 + 25) * 5, x0 + (12.5 + 25) * 5, y0 + canvas.getHeight() / 2 + 25);

        gc.strokeLine(x0 - (12.5) * 5, y0 - (12.5) * 5, x0 - (12.5) * 5, y0 - canvas.getHeight() / 2 + 25);
        gc.strokeLine(x0 + (12.5) * 5, y0 - (12.5) * 5, x0 + (12.5) * 5, y0 - canvas.getHeight() / 2 + 25);
        gc.strokeLine(x0 - (12.5) * 5, y0 + (12.5) * 5, x0 - (12.5) * 5, y0 + canvas.getHeight() / 2 + 25);
        gc.strokeLine(x0 + (12.5) * 5, y0 + (12.5) * 5, x0 + (12.5) * 5, y0 + canvas.getHeight() / 2 + 25);


        // gc.fillOval(100,100, 300,300);

//        gc.moveTo(x0 - canvas.getWidth() / 2 + 50, y0);
//        gc.lineTo(x0 + canvas.getWidth() / 2 - 50, y0);


    }

    void drawBorder() {
        canvas.getGraphicsContext2D().setStroke(Color.BLACK);
        canvas.getGraphicsContext2D().setLineWidth(1);
        canvas.getGraphicsContext2D().clearRect(1, 1, (Double) wd - 2, (Double) hd - 2);
        canvas.getGraphicsContext2D().strokeRect(1, 1, (Double) wd - 2, (Double) hd - 2);
    }


    public void setStage(Stage primaryStage) {
        this.stage = primaryStage;
    }
}
