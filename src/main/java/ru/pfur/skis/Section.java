package ru.pfur.skis;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * Created by Admin on 11.06.2016.
 */
public class Section extends Stage {

    Canvas canvas;

    private final int WIDTH_DEFAULT = 800;

    public static final int ICON_INFO = 0;
    public static final int ICON_ERROR = 1;

    public Section(Stage owner, String msg, int type) {
        setResizable(false);
        initModality(Modality.APPLICATION_MODAL);
        initStyle(StageStyle.TRANSPARENT);
        canvas = new Canvas(780, 540);

//        Label label = new Label(msg);
//        label.setWrapText(true);
//        label.setGraphicTextGap(20);
//        label.setGraphic(new ImageView(getImage(type)));

        Button button = new Button("Close");
        button.setOnAction(new EventHandler(){
            @Override
            public void handle(Event event) {
                Section.this.close();
            }
        });

        BorderPane borderPane = new BorderPane();
        borderPane.setStyle("-fx-background-color: #ffffff;\n" +
                "    -fx-background-radius: 5;\n" +
                "    -fx-padding: 10;");
        borderPane.setTop(canvas);

        HBox hbox2 = new HBox();
        hbox2.setAlignment(Pos.CENTER);
        hbox2.getChildren().add(button);
        borderPane.setBottom(hbox2);

        // calculate width of string
        final Text text = new Text(msg);
        text.snapshot(null, null);
        // + 20 because there is padding 10 left and right
        int width = (int) text.getLayoutBounds().getWidth() + 40;

        if (width < WIDTH_DEFAULT)
            width = WIDTH_DEFAULT;

        int height = 600;

        final Scene scene = new Scene(borderPane, width, height);
        scene.setFill(Color.TRANSPARENT);
        setScene(scene);

        // make sure this stage is centered on top of its owner
        setX(owner.getX() + (owner.getWidth() / 2 - width / 2));
        setY(owner.getY() + (owner.getHeight() / 2 - height / 2));
        drawBorder();
        draw();
    }

    private Image getImage(int type) {
        if (type == ICON_ERROR)
            return new Image(getClass().getResourceAsStream("/images/error.png"));
        else
            return new Image(getClass().getResourceAsStream("/images/info.png"));
//        return new Image(getClass().getResourceAsStream("/images/error.png"));
    }

    void drawBorder() {
        canvas.getGraphicsContext2D().setStroke(Color.BLACK);
        canvas.getGraphicsContext2D().setLineWidth(1);
        canvas.getGraphicsContext2D().clearRect(1, 1,  canvas.getWidth() - 2,  canvas.getHeight() - 2);
        canvas.getGraphicsContext2D().strokeRect(1, 1,  canvas.getWidth() - 2,  canvas.getHeight() - 2);
//        canvas.getGraphicsContext2D().strokeRect(1, 1, 400,400);
    }

    void draw(){
        double x0 = canvas.getWidth() / 2;
        double y0 = canvas.getHeight() / 2;


        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.CADETBLUE);
        gc.fillOval(x0-2 , y0-2, 4, 4);
    }
}