package ru.pfur.skis;

import javafx.event.ActionEvent;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.input.ZoomEvent;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    ComboBox<String> steel;
    @FXML
    ComboBox<String> concrete;
    @FXML
    ComboBox<String> rebars;
    @FXML
    Pane drawPanel;

    Canvas canvas = new Canvas(400,400);


    @FXML
    Button calculate;

    @FXML
    private void onShowComboBox(){

    }

    public void initialize(URL url, ResourceBundle resourceBundle) {
        assert steel != null : "fx:id=\"steel\" was not injected: check your FXML file 'main.fxml'.";
        assert concrete != null : "fx:id=\"concrete\" was not injected: check your FXML file 'main.fxml'.";
        assert rebars != null : "fx:id=\"rebars\" was not injected: check your FXML file 'main.fxml'.";

        for (String steelElement : Model.getSteels()){
            steel.getItems().add(steelElement);
            rebars.getItems().add(steelElement);
        }

        for (String concreteElement : Model.getConcretes()){
            concrete.getItems().add(concreteElement);
        }

        drawPanel.getChildren().add(canvas);

        canvas.getGraphicsContext2D().strokeOval(100,100,100,100);
        canvas.getGraphicsContext2D().strokeRect(1,1,700,700);


        calculate.setOnAction((ActionEvent e)-> System.out.println("clicked"));
    }


}
