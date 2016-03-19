package ru.pfur.skis;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    ComboBox<String> tSection;

    @FXML
    Button calculate;

    @FXML
    private void onShowComboBox(){

    }

    public void initialize(URL url, ResourceBundle resourceBundle) {
        assert tSection != null : "fx:id=\"fruitCombo\" was not injected: check your FXML file 'fruitcombo.fxml'.";
        tSection.getItems().add("String 1");
        tSection.getItems().add("String 2");
        tSection.getItems().add("String 3");

        calculate.setOnAction((ActionEvent e)-> System.out.println("clicked"));
    }


}
